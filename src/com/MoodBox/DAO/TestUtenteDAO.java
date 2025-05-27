package com.MoodBox.DAO;

import com.MoodBox.model.Utente;

public class TestUtenteDAO {
    public static void main(String[] args) {
        UtenteDAO dao = new UtenteDAO();

        // Creazione utente
        Utente nuovo = new Utente();
        nuovo.setNome("Anna Verdi");
        nuovo.setEmail("anna@example.com");
        nuovo.setPassword("12345");
        nuovo.setRuolo("utente");
        nuovo.setIscrizioneNewsletter(true);

        boolean salvato = dao.create(nuovo);
        System.out.println("Utente salvato: " + salvato);

        // Recupero utente
        Utente trovato = dao.checkLogin("anna@example.com", "12345");
        if (trovato != null) {
            System.out.println("Utente trovato: " + trovato.getNome() + " (" + trovato.getEmail() + ")");
        } else {
            System.out.println("Login fallito.");
        }
    }
}
