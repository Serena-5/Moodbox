package com.MoodBox.model;

public class StatoOrdine {
    private int id;
    private String stato; // 'In lavorazione', 'Spedito', 'Consegnato', 'Annullato'
    private String descrizione;
    
    // Costruttori
    public StatoOrdine() {}
    
    public StatoOrdine(String stato, String descrizione) {
        this.stato = stato;
        this.descrizione = descrizione;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}
