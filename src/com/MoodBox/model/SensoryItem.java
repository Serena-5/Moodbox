package com.MoodBox.model;

import java.math.BigDecimal;

public class SensoryItem {
    private int id;
    private String nome;
    private String descrizione;
    private String tipoSenso; // 'vista', 'olfatto', 'tatto', 'gusto', 'udito'
    private BigDecimal prezzoUnitario;
    private String immagineUrl;
    private boolean disponibile;
    
    // Costruttori
    public SensoryItem() {}
    
    public SensoryItem(String nome, String descrizione, String tipoSenso, BigDecimal prezzoUnitario) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipoSenso = tipoSenso;
        this.prezzoUnitario = prezzoUnitario;
        this.disponibile = true;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public String getTipoSenso() { return tipoSenso; }
    public void setTipoSenso(String tipoSenso) { this.tipoSenso = tipoSenso; }
    
    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    
    public String getImmagineUrl() { return immagineUrl; }
    public void setImmagineUrl(String immagineUrl) { this.immagineUrl = immagineUrl; }
    
    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }
}