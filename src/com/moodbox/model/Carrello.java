package com.moodbox.model;

import java.time.LocalDateTime;

public class Carrello {
    private int id;
    private Integer utenteId; // Nullable per utenti non loggati
    private String sessionId;
    private LocalDateTime ultimoAccesso;
    
    // Costruttori
    public Carrello() {}
    
    public Carrello(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Carrello(Integer utenteId, String sessionId) {
        this.utenteId = utenteId;
        this.sessionId = sessionId;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Integer getUtenteId() { return utenteId; }
    public void setUtenteId(Integer utenteId) { this.utenteId = utenteId; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public LocalDateTime getUltimoAccesso() { return ultimoAccesso; }
    public void setUltimoAccesso(LocalDateTime ultimoAccesso) { this.ultimoAccesso = ultimoAccesso; }
}