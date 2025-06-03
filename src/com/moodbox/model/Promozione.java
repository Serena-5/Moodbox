package com.moodbox.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Promozione {
    private int id;
    private String nome;
    private String descrizione;
    private BigDecimal scontoPercentuale;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    
    // Costruttori
    public Promozione() {}
    
    public Promozione(String nome, BigDecimal scontoPercentuale, LocalDate dataInizio, LocalDate dataFine, String tipoTarget) {
        this.nome = nome;
        this.scontoPercentuale = scontoPercentuale;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public BigDecimal getScontoPercentuale() { return scontoPercentuale; }
    public void setScontoPercentuale(BigDecimal scontoPercentuale) { this.scontoPercentuale = scontoPercentuale; }
    
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    
    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
}