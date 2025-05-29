package com.MoodBox.model;

import java.time.LocalDateTime;

public class Recensione {
    private int id;
    private int utenteId;
    private int boxId;
    private int voto;
    private String commento;
    private LocalDateTime dataRecensione;
    private String rispostaAdmin;
    
    // Costruttori
    public Recensione() {}
    
    public Recensione(int utenteId, int boxId, int voto, String commento) {
        this.utenteId = utenteId;
        this.boxId = boxId;
        this.voto = voto;
        this.commento = commento;
    }
    
    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    
    public int getBoxId() { return boxId; }
    public void setBoxId(int boxId) { this.boxId = boxId; }
    
    public int getVoto() { return voto; }
    public void setVoto(int voto) { this.voto = voto; }
    
    public String getCommento() { return commento; }
    public void setCommento(String commento) { this.commento = commento; }
    
    public LocalDateTime getDataRecensione() { return dataRecensione; }
    public void setDataRecensione(LocalDateTime dataRecensione) { this.dataRecensione = dataRecensione; }
    
    public String getRispostaAdmin() { return rispostaAdmin; }
    public void setRispostaAdmin(String rispostaAdmin) { this.rispostaAdmin = rispostaAdmin; }
}