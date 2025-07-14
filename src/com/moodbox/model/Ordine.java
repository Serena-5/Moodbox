package com.moodbox.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int utenteId;

    
    private String via;
    private String civico;
    private String cap;
    private String citta;
    private String provincia;
    private String paese;

    private String metodoPagamento = "PayPal";
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

    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }

    public String getCivico() { return civico; }
    public void setCivico(String civico) { this.civico = civico; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getPaese() { return paese; }
    public void setPaese(String paese) { this.paese = paese; }

    public String getMetodoPagamento() {return metodoPagamento;}
    public void setMetodoPagamento(String metodoPagamento) {this.metodoPagamento = metodoPagamento;}

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
