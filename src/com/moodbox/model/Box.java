package com.moodbox.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Box {
    private int id;
    private String nome;
    private String descrizione;
    private BigDecimal prezzo;
    private boolean disponibile;
    private LocalDateTime dataCreazione;
    
    // Costruttori
    public Box() {}
    
    public Box(String nome, String descrizione, BigDecimal prezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.disponibile = true;
    }
    
    public Box(int id, String nome, String descrizione, BigDecimal prezzo) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.disponibile = true;
    }
    private String immagine;

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }


    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }
    
    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }
    
    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }
}
