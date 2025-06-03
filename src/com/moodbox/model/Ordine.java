package com.moodbox.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int utenteId;
    private String indirizzoSpedizione;
    private String metodoSpedizione;
    private BigDecimal costoSpedizione;
    private String statoOrdine;
    private LocalDateTime dataOrdine;
    private BigDecimal totale;
    private String noteOrdine;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public String getIndirizzoSpedizione() { return indirizzoSpedizione; }
    public void setIndirizzoSpedizione(String indirizzoSpedizione) { this.indirizzoSpedizione = indirizzoSpedizione; }

    public String getMetodoSpedizione() { return metodoSpedizione; }
    public void setMetodoSpedizione(String metodoSpedizione) { this.metodoSpedizione = metodoSpedizione; }

    public BigDecimal getCostoSpedizione() { return costoSpedizione; }
    public void setCostoSpedizione(BigDecimal costoSpedizione) { this.costoSpedizione = costoSpedizione; }

    public String getStatoOrdine() { return statoOrdine; }
    public void setStatoOrdine(String statoOrdine) { this.statoOrdine = statoOrdine; }

    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }

    public BigDecimal getTotale() { return totale; }
    public void setTotale(BigDecimal totale) { this.totale = totale; }

    public String getNoteOrdine() { return noteOrdine; }
    public void setNoteOrdine(String noteOrdine) { this.noteOrdine = noteOrdine; }
}
