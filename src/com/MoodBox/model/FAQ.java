package com.MoodBox.model;

import java.time.LocalDateTime;

public class FAQ {
    private int id;
    private int utenteId;
    private String domanda;
    private String rispostaAdmin;
    private LocalDateTime dataDomanda;
    
    // Costruttori
    public FAQ() {}
    
    public FAQ(int utenteId, String domanda) {
        this.utenteId = utenteId;
        this.domanda = domanda;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public String getDomanda() { return domanda; }
    public void setDomanda(String domanda) { this.domanda = domanda; }
    
    public String getRispostaAdmin() { return rispostaAdmin; }
    public void setRispostaAdmin(String rispostaAdmin) { this.rispostaAdmin = rispostaAdmin; }
    
    public LocalDateTime getDataDomanda() { return dataDomanda; }
    public void setDataDomanda(LocalDateTime dataDomanda) { this.dataDomanda = dataDomanda; }
}