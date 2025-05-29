package com.MoodBox.model;

public class BoxItem {
    private int boxId;
    private int itemId;
    private int quantita;
    
    // Costruttori
    public BoxItem() {}
    
    public BoxItem(int boxId, int itemId, int quantita) {
        this.boxId = boxId;
        this.itemId = itemId;
        this.quantita = quantita;
    }
    
    // Getter e Setter
    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }
    
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}