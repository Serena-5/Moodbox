package com.moodbox.model;

public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;
    private boolean iscrizioneNewsletter;

    // Costruttore vuoto (necessario per JavaBean)
    public Utente() {}

    // Costruttore completo
    public Utente(int id, String nome, String cognome, String email, String password, String ruolo, boolean iscrizioneNewsletter) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.iscrizioneNewsletter = iscrizioneNewsletter;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public boolean isIscrizioneNewsletter() { return iscrizioneNewsletter; }
    public void setIscrizioneNewsletter(boolean iscrizioneNewsletter) { this.iscrizioneNewsletter = iscrizioneNewsletter; }
}
