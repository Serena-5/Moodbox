package com.moodbox.model;

public class BoxCategoria {
    private int boxId;
    private int categoriaId;
    
    // Costruttori
    public BoxCategoria() {}
    
    public BoxCategoria(int boxId, int categoriaId) {
        this.boxId = boxId;
        this.categoriaId = categoriaId;
    }
    
    // Getter e Setter
    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }
    
    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
}