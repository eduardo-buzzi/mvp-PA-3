package br.com.schulz.calibracao.service;

import br.com.schulz.calibracao.model.Calibracao;
import br.com.schulz.calibracao.model.ResultadoCalibracao;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    public byte[] gerar(Calibracao c) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 30,30,30,30);
        PdfWriter.getInstance(doc,out); doc.open();
        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
        Font normal = FontFactory.getFont(FontFactory.HELVETICA, 9);
        Paragraph p = new Paragraph("CERTIFICADO DE CALIBRAÇÃO", title); p.setAlignment(Element.ALIGN_CENTER); doc.add(p);
        doc.add(new Paragraph("Número: " + c.getNumeroCertificado()));
        doc.add(new Paragraph("Data de emissão: " + c.getDataEmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("INSTRUMENTO", bold));
        doc.add(new Paragraph("Código: " + c.getCodigoInstrumento() + " | TAG: " + c.getTag()));
        doc.add(new Paragraph("Descrição: " + c.getDenominacao() + " | Fabricante: " + c.getFabricante()));
        doc.add(new Paragraph("Série: " + c.getNumeroSerie() + " | Solicitante: " + c.getSolicitante()));
        doc.add(new Paragraph("PADRÃO E PROCEDIMENTO", bold));
        doc.add(new Paragraph("Padrão: " + c.getCodigoPadrao() + " | Certificado: " + c.getCertificadoPadrao()));
        doc.add(new Paragraph("Procedimento: " + c.getCodigoProcedimento() + " | Revisão: " + c.getRevisaoProcedimento()));
        doc.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(8); table.setWidthPercentage(100);
        String[] headers={"Parâmetro","Nominal","Med. 1","Med. 2","Média","Erro","LEA","Situação"};
        for(String h:headers){PdfPCell cell=new PdfPCell(new Phrase(h,bold)); cell.setPadding(4); table.addCell(cell);}
        for(ResultadoCalibracao r:c.getResultados()){
            table.addCell(new Phrase(r.getParametro(),normal)); table.addCell(fmt(r.getNominal())); table.addCell(fmt(r.getMedida1())); table.addCell(fmt(r.getMedida2()));
            table.addCell(fmt(r.getMedia())); table.addCell(fmt(r.getErro())); table.addCell(fmt(r.getLea())); table.addCell(new Phrase(r.getSituacao(),normal));
        }
        doc.add(table); doc.add(new Paragraph(" "));
        doc.add(new Paragraph("PARECER FINAL: " + c.getStatus(), title));
        doc.add(new Paragraph("Técnico: " + c.getTecnico()));
        doc.add(new Paragraph("Signatário: " + c.getSignatario()));
        doc.add(new Paragraph("Documento gerado pelo MVP de automação da emissão de certificados de calibração."));
        doc.close(); return out.toByteArray();
    }
    private String fmt(Double d){return d==null?"-":String.format(java.util.Locale.US,"%.6f",d);}
}
