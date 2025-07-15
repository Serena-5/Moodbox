package com.moodbox.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class CatalogItem {

    private int id;
    private String nome;
    private String descrizione;
    private BigDecimal prezzo;
    private String immagine;             // es: "/img/catalog/box.jpg"
    private boolean disponibile;         // nuovo
    private LocalDateTime dataCreazione; // nuovo

    // ---------- Costruttori ----------
    public CatalogItem() { }

    public CatalogItem(int id, String nome, String descrizione,
                       BigDecimal prezzo, String immagine,
                       boolean disponibile, LocalDateTime dataCreazione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.immagine = immagine;
        this.disponibile = disponibile;
        this.dataCreazione = dataCreazione;
    }

    // ---------- Getter & Setter ----------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    // ---------- equals, hashCode, toString ----------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatalogItem)) return false;
        CatalogItem that = (CatalogItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", disponibile=" + disponibile +
                ", dataCreazione=" + dataCreazione +
                '}';
    }
}
