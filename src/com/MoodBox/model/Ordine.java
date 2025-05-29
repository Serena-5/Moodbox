package com.MoodBox.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int utenteId;
    private int spedizioneId;
    private int statoId;
    private LocalDateTime dataOrdine;
    private BigDecimal totale;
    private String noteOrdine;
    
    // Costruttori
    public Ordine() {}
    
    public Ordine(int utenteId, int spedizioneId, int statoId, BigDecimal totale) {
        this.utenteId = utenteId;
        this.spedizioneId = spedizioneId;
        this.statoId = statoId;
        this.totale = totale;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public int getSpedizioneId() { return spedizioneId; }
    public void setSpedizioneId(int spedizioneId) { this.spedizioneId = spedizioneId; }
    
    public int getStatoId() { return statoId; }
    public void setStatoId(int statoId) { this.statoId = statoId; }
    
    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }
    
    public BigDecimal getTotale() { return totale; }
    public void setTotale(BigDecimal totale) { this.totale = totale; }
    
    public String getNoteOrdine() { return noteOrdine; }
    public void setNoteOrdine(String noteOrdine) { this.noteOrdine = noteOrdine; }
}