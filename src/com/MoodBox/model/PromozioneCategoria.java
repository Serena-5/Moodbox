package com.MoodBox.model;

public class PromozioneCategoria {
    private int promozioneId;
    private int categoriaId;
    
    // Costruttori
    public PromozioneCategoria() {}
    
    public PromozioneCategoria(int promozioneId, int categoriaId) {
        this.promozioneId = promozioneId;
        this.categoriaId = categoriaId;
    }
    
    // Getter e Setter
    public int getPromozioneId() { return promozioneId; }
    public void setPromozioneId(int promozioneId) { this.promozioneId = promozioneId; }
    
    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }
}