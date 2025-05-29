package com.MoodBox.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Abbonamento {
    private int id;
    private int utenteId;
    private String tipoAbbonamento; // 'mensile' o 'annuale'
    private boolean attivo;
    private LocalDateTime dataInizio;
    private LocalDate dataFine;
    private String boxTemaPreferito;
    
    // Costruttori
    public Abbonamento() {}
    
    public Abbonamento(int utenteId, String tipoAbbonamento, LocalDate dataFine) {
        this.utenteId = utenteId;
        this.tipoAbbonamento = tipoAbbonamento;
        this.dataFine = dataFine;
        this.attivo = true;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public String getTipoAbbonamento() { return tipoAbbonamento; }
    public void setTipoAbbonamento(String tipoAbbonamento) { this.tipoAbbonamento = tipoAbbonamento; }
    
    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }
    
    public LocalDateTime getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDateTime dataInizio) { this.dataInizio = dataInizio; }
    
    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    
    public String getBoxTemaPreferito() { return boxTemaPreferito; }
    public void setBoxTemaPreferito(String boxTemaPreferito) { this.boxTemaPreferito = boxTemaPreferito; }
}