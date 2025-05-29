package com.MoodBox.model;

import java.time.LocalDateTime;

public class EmotionalProfile {
    private int utenteId;
    private String preferenzeSensi; // JSON come String
    private String emozioniChiave; // JSON come String
    private LocalDateTime ultimoUpdate;
    
    // Costruttori
    public EmotionalProfile() {}
    
    public EmotionalProfile(int utenteId, String preferenzeSensi, String emozioniChiave) {
        this.utenteId = utenteId;
        this.preferenzeSensi = preferenzeSensi;
        this.emozioniChiave = emozioniChiave;
    }
    
    // Getter e Setter
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public String getPreferenzeSensi() { return preferenzeSensi; }
    public void setPreferenzeSensi(String preferenzeSensi) { this.preferenzeSensi = preferenzeSensi; }
    
    public String getEmozioniChiave() { return emozioniChiave; }
    public void setEmozioniChiave(String emozioniChiave) { this.emozioniChiave = emozioniChiave; }
    
    public LocalDateTime getUltimoUpdate() { return ultimoUpdate; }
    public void setUltimoUpdate(LocalDateTime ultimoUpdate) { this.ultimoUpdate = ultimoUpdate; }
}