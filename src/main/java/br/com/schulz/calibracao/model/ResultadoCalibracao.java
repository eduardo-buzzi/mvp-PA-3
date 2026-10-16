package br.com.schulz.calibracao.model;

import jakarta.persistence.*;

@Entity
public class ResultadoCalibracao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parametro;
    private Double nominal;
    private Double toleranciaSuperior;
    private Double toleranciaInferior;
    private Double medida1;
    private Double medida2;
    private Double media;
    private Double erro;
    private Double erroAbsoluto;
    private Double lea;
    private String situacao;
    private Double desvioPadrao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Calibracao calibracao;

    public Long getId() { return id; }
    public String getParametro() { return parametro; }
    public void setParametro(String v) { parametro = v; }
    public Double getNominal() { return nominal; }
    public void setNominal(Double v) { nominal = v; }
    public Double getToleranciaSuperior() { return toleranciaSuperior; }
    public void setToleranciaSuperior(Double v) { toleranciaSuperior = v; }
    public Double getToleranciaInferior() { return toleranciaInferior; }
    public void setToleranciaInferior(Double v) { toleranciaInferior = v; }
    public Double getMedida1() { return medida1; }
    public void setMedida1(Double v) { medida1 = v; }
    public Double getMedida2() { return medida2; }
    public void setMedida2(Double v) { medida2 = v; }
    public Double getMedia() { return media; }
    public void setMedia(Double v) { media = v; }
    public Double getErro() { return erro; }
    public void setErro(Double v) { erro = v; }
    public Double getErroAbsoluto() { return erroAbsoluto; }
    public void setErroAbsoluto(Double v) { erroAbsoluto = v; }
    public Double getLea() { return lea; }
    public void setLea(Double v) { lea = v; }
    public String getSituacao() { return situacao; }
    public void setSituacao(String v) { situacao = v; }
    public Double getDesvioPadrao() { return desvioPadrao; }
    public void setDesvioPadrao(Double v) { desvioPadrao = v; }
    public Calibracao getCalibracao() { return calibracao; }
    public void setCalibracao(Calibracao v) { calibracao = v; }
}
