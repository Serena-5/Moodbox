package com.moodbox.model;

public class CarrelloArticolo {
    private int carrelloId;
    private int boxId;
    private int quantita;
    private Box box; // <--- AGGIUNTO

    public CarrelloArticolo() {}

    public CarrelloArticolo(int carrelloId, int boxId, int quantita, Box box) {
        this.carrelloId = carrelloId;
        this.boxId = boxId;
        this.quantita = quantita;
        this.box = box;
    }

    // Getter e Setter
    public int getCarrelloId() { return carrelloId; }
    public void setCarrelloId(int carrelloId) { this.carrelloId = carrelloId; }

    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public Box getBox() { return box; }           // <--- AGGIUNTO
    public void setBox(Box box) { this.box = box; } // <--- AGGIUNTO
}
