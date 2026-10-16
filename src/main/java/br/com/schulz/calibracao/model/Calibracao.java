package br.com.schulz.calibracao.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Calibracao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCertificado;
    private String codigoInstrumento;
    private String tag;
    private String denominacao;
    private String fabricante;
    private String numeroSerie;
    private String solicitante;
    private String codigoPadrao;
    private String certificadoPadrao;
    private LocalDate dataProximaCalibracaoPadrao;
    private String codigoProcedimento;
    private String revisaoProcedimento;
    private String tecnico;
    private String signatario;
    private String status;
    private LocalDate dataEmissao;
    private String arquivoOrigem;

    @OneToMany(mappedBy = "calibracao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoCalibracao> resultados = new ArrayList<>();

    public void addResultado(ResultadoCalibracao r) { resultados.add(r); r.setCalibracao(this); }
    public Long getId() { return id; }
    public String getNumeroCertificado() { return numeroCertificado; }
    public void setNumeroCertificado(String v) { numeroCertificado = v; }
    public String getCodigoInstrumento() { return codigoInstrumento; }
    public void setCodigoInstrumento(String v) { codigoInstrumento = v; }
    public String getTag() { return tag; }
    public void setTag(String v) { tag = v; }
    public String getDenominacao() { return denominacao; }
    public void setDenominacao(String v) { denominacao = v; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String v) { fabricante = v; }
    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String v) { numeroSerie = v; }
    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String v) { solicitante = v; }
    public String getCodigoPadrao() { return codigoPadrao; }
    public void setCodigoPadrao(String v) { codigoPadrao = v; }
    public String getCertificadoPadrao() { return certificadoPadrao; }
    public void setCertificadoPadrao(String v) { certificadoPadrao = v; }
    public LocalDate getDataProximaCalibracaoPadrao() { return dataProximaCalibracaoPadrao; }
    public void setDataProximaCalibracaoPadrao(LocalDate v) { dataProximaCalibracaoPadrao = v; }
    public String getCodigoProcedimento() { return codigoProcedimento; }
    public void setCodigoProcedimento(String v) { codigoProcedimento = v; }
    public String getRevisaoProcedimento() { return revisaoProcedimento; }
    public void setRevisaoProcedimento(String v) { revisaoProcedimento = v; }
    public String getTecnico() { return tecnico; }
    public void setTecnico(String v) { tecnico = v; }
    public String getSignatario() { return signatario; }
    public void setSignatario(String v) { signatario = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate v) { dataEmissao = v; }
    public String getArquivoOrigem() { return arquivoOrigem; }
    public void setArquivoOrigem(String v) { arquivoOrigem = v; }
    public List<ResultadoCalibracao> getResultados() { return resultados; }
}
