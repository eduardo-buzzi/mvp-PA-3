package br.com.schulz.calibracao.service;

import br.com.schulz.calibracao.model.Calibracao;
import br.com.schulz.calibracao.model.ResultadoCalibracao;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class ExcelService {
    private static final List<String> REQUIRED = List.of("parametro","nominal","medida1","medida2","lea");

    public void processar(MultipartFile file, Calibracao c) throws Exception {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Selecione uma planilha Excel.");
        if (!file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) throw new IllegalArgumentException("O arquivo deve ser .xlsx.");

        try (InputStream in = file.getInputStream(); Workbook wb = WorkbookFactory.create(in)) {
            Sheet sheet = wb.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() < 2) throw new IllegalArgumentException("A planilha não possui dados.");
            Row header = sheet.getRow(0);
            Map<String,Integer> cols = new HashMap<>();
            for (Cell cell : header) cols.put(normalizar(cell.getStringCellValue()), cell.getColumnIndex());
            for (String req : REQUIRED) if (!cols.containsKey(req)) throw new IllegalArgumentException("Coluna obrigatória ausente: " + req);

            for (int i=1; i<=sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                String parametro = texto(row, cols.get("parametro"));
                if (parametro.isBlank()) continue;
                Double nominal = numero(row, cols.get("nominal"));
                Double m1 = numero(row, cols.get("medida1"));
                Double m2 = numero(row, cols.get("medida2"));
                Double lea = numero(row, cols.get("lea"));
                if (nominal == null || m1 == null || m2 == null || lea == null) throw new IllegalArgumentException("Dados obrigatórios inválidos na linha " + (i+1));

                Double ts = cols.containsKey("toleranciasuperior") ? numero(row, cols.get("toleranciasuperior")) : null;
                Double ti = cols.containsKey("toleranciainferior") ? numero(row, cols.get("toleranciainferior")) : null;
                double media = (m1+m2)/2.0;
                double erro = media-nominal;
                double abs = Math.abs(erro);
                double dp = Math.sqrt(((m1-media)*(m1-media)+(m2-media)*(m2-media))/2.0);

                ResultadoCalibracao r = new ResultadoCalibracao();
                r.setParametro(parametro); r.setNominal(nominal); r.setMedida1(m1); r.setMedida2(m2);
                r.setLea(lea); r.setMedia(media); r.setErro(erro); r.setErroAbsoluto(abs); r.setDesvioPadrao(dp);
                r.setToleranciaSuperior(ts); r.setToleranciaInferior(ti);
                r.setSituacao(abs <= lea ? "DENTRO_DA_TOLERANCIA" : "FORA_DA_TOLERANCIA");
                c.addResultado(r);
            }
            if (c.getResultados().isEmpty()) throw new IllegalArgumentException("Nenhum ponto válido encontrado na planilha.");
        }
    }

    private String normalizar(String s) { return s == null ? "" : java.text.Normalizer.normalize(s.trim().toLowerCase(), java.text.Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace(" ", "").replace("_", ""); }
    private String texto(Row r, int col) { Cell c=r.getCell(col); return c==null ? "" : c.toString().trim(); }
    private Double numero(Row r, int col) {
        Cell c=r.getCell(col); if(c==null) return null;
        if(c.getCellType()==CellType.NUMERIC) return c.getNumericCellValue();
        if(c.getCellType()==CellType.FORMULA && c.getCachedFormulaResultType()==CellType.NUMERIC) return c.getNumericCellValue();
        try { return Double.parseDouble(c.toString().trim().replace(",", ".")); } catch(Exception e) { return null; }
    }
}
