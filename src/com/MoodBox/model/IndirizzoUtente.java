package com.moodbox.model;

public class IndirizzoUtente {
    private int id;
    private int utenteId;
    private String via;
    private String citta;
    private String provincia;
    private String cap;
    private String nazione;
    
    // Costruttori
    public IndirizzoUtente() {}
    
    public IndirizzoUtente(int utenteId, String via, String citta, String provincia, String cap, String nazione) {
        this.utenteId = utenteId;
        this.via = via;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.nazione = nazione;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }
    
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }
    
    public String getNazione() { return nazione; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    
}