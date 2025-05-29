package com.MoodBox.model;

import java.time.LocalDateTime;

public class Magazzino {
    private int id;
    private int itemId;
    private int quantitaDisponibile;
    private LocalDateTime dataUltimoAggiornamento;
    
    // Costruttori
    public Magazzino() {}
    
    public Magazzino(int itemId, int quantitaDisponibile) {
        this.itemId = itemId;
        this.quantitaDisponibile = quantitaDisponibile;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public int getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(int quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }
    
    public LocalDateTime getDataUltimoAggiornamento() { return dataUltimoAggiornamento; }
    public void setDataUltimoAggiornamento(LocalDateTime dataUltimoAggiornamento) { this.dataUltimoAggiornamento = dataUltimoAggiornamento; }
}