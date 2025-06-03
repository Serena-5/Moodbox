package com.moodbox.model;

import java.math.BigDecimal;

public class RigaOrdine {
    private int id;
    private int ordineId;
    private int boxId;
    private int quantita;
    private BigDecimal prezzoUnitario;
    
    // Costruttori
    public RigaOrdine() {}
    
    public RigaOrdine(int ordineId, int boxId, int quantita, BigDecimal prezzoUnitario) {
        this.ordineId = ordineId;
        this.boxId = boxId;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }
    
    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }
    
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    
    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    
    // Metodo utile per calcolare il totale della riga
    public BigDecimal getTotaleRiga() {
        return prezzoUnitario.multiply(BigDecimal.valueOf(quantita));
    }
}