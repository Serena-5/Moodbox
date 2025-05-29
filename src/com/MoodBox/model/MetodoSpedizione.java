package com.MoodBox.model;

import java.math.BigDecimal;

public class MetodoSpedizione {
    private int id;
    private String nome;
    private BigDecimal costo;
    
    // Costruttori
    public MetodoSpedizione() {}
    
    public MetodoSpedizione(String nome, BigDecimal costo) {
        this.nome = nome;
        this.costo = costo;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
