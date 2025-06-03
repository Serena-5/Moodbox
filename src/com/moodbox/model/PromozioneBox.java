package com.moodbox.model;

public class PromozioneBox {
    private int promozioneId;
    private int boxId;
    
    // Costruttori
    public PromozioneBox() {}
    
    public PromozioneBox(int promozioneId, int boxId) {
        this.promozioneId = promozioneId;
        this.boxId = boxId;
    }
    
    // Getter e Setter
    public int getPromozioneId() { return promozioneId; }
    public void setPromozioneId(int promozioneId) { this.promozioneId = promozioneId; }
    
    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }
}