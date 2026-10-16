package br.com.schulz.calibracao.controller;

import br.com.schulz.calibracao.model.Calibracao;
import br.com.schulz.calibracao.repository.CalibracaoRepository;
import br.com.schulz.calibracao.service.ExcelService;
import br.com.schulz.calibracao.service.PdfService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class CalibracaoController {

    private final CalibracaoRepository repo; 
    private final ExcelService excel; 
    private final PdfService pdf;

    public CalibracaoController(CalibracaoRepository repo, ExcelService excel, PdfService pdf) {
        this.repo = repo;
        this.excel = excel;
        this.pdf = pdf;
    }

    @GetMapping("/") 
    public String index(Model m) {
        m.addAttribute("calibracoes", repo.findAll()); 
        return "index";
    }

    @PostMapping("/calibracoes") 
    public String criar(@RequestParam MultipartFile arquivo,
                        @RequestParam String codigoInstrumento, @RequestParam String tag, @RequestParam String denominacao,
                        @RequestParam String fabricante, @RequestParam String numeroSerie, @RequestParam String solicitante,
                        @RequestParam String codigoPadrao, @RequestParam String certificadoPadrao,
                        @RequestParam(required = false) String dataProximaCalibracaoPadrao, @RequestParam String codigoProcedimento,
                        @RequestParam String revisaoProcedimento, @RequestParam String tecnico, @RequestParam String signatario,
                        Model m) {
        try {
            Calibracao c = new Calibracao(); 
            c.setNumeroCertificado("CERT-" + System.currentTimeMillis()); 
            c.setCodigoInstrumento(codigoInstrumento);
            c.setTag(tag);
            c.setDenominacao(denominacao);
            c.setFabricante(fabricante);
            c.setNumeroSerie(numeroSerie);
            c.setSolicitante(solicitante);
            c.setCodigoPadrao(codigoPadrao);
            c.setCertificadoPadrao(certificadoPadrao);
            
            // Tratamento seguro para data vazia ou nula vinda da tela
            if (dataProximaCalibracaoPadrao != null && !dataProximaCalibracaoPadrao.isEmpty()) {
                LocalDate dataProx = LocalDate.parse(dataProximaCalibracaoPadrao);
                if (dataProx.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("O padrão informado está vencido.");
                }
                c.setDataProximaCalibracaoPadrao(dataProx);
            }

            c.setCodigoProcedimento(codigoProcedimento);
            c.setRevisaoProcedimento(revisaoProcedimento);
            c.setTecnico(tecnico);
            c.setSignatario(signatario);
            c.setArquivoOrigem(arquivo.getOriginalFilename() != null ? arquivo.getOriginalFilename() : "desconhecido.xlsx");

            // Processa a planilha Excel
            excel.processar(arquivo, c);

            // Valida o status final com base nos pontos calculados
            boolean aprovado = c.getResultados().stream()
                    .allMatch(r -> "DENTRO_DA_TOLERANCIA".equals(r.getSituacao()));
            
            c.setStatus(aprovado ? "APROVADO" : "REPROVADO"); 
            c.setDataEmissao(LocalDate.now());
            
            repo.save(c); 
            return "redirect:/calibracoes/" + c.getId();

        } catch (Exception e) {
            m.addAttribute("erro", "Erro ao processar calibração: " + e.getMessage());
            m.addAttribute("calibracoes", repo.findAll());
            return "index";
        }
    }

    @GetMapping("/calibracoes/{id}") 
    public String detalhe(@PathVariable Long id, Model m) {
        m.addAttribute("c", repo.findById(id).orElseThrow(() -> new RuntimeException("Calibração não encontrada")));
        return "detalhe";
    }

    @GetMapping("/calibracoes/{id}/pdf") 
    public ResponseEntity<byte[]> pdf(@PathVariable Long id) throws Exception {
        Calibracao c = repo.findById(id).orElseThrow(() -> new RuntimeException("Calibração não encontrada"));
        byte[] b = pdf.gerar(c);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + c.getNumeroCertificado() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(b);
    }
}