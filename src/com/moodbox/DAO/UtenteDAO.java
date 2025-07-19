package com.moodbox.DAO;

import com.moodbox.model.Utente;

import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;


public class UtenteDAO {

    // Salvataggio utente (equivalente a create)
	public boolean doSave(Utente utente) {
	    String sql = "INSERT INTO Utenti (nome, cognome, email, passw, ruolo, iscrizione_newsletter) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String hashedPassword = hashPassword(utente.getPassword());

	        stmt.setString(1, utente.getNome());
	        stmt.setString(2, utente.getCognome());
	        stmt.setString(3, utente.getEmail());
	        stmt.setString(4, hashedPassword); // 🔐 Password hashata
	        stmt.setString(5, utente.getRuolo());
	        stmt.setBoolean(6, utente.isIscrizioneNewsletter());

	        int righeInserite = stmt.executeUpdate();
	        return righeInserite > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

    // Recupero utente per ID
    public Utente doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Utenti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("passw"));
                u.setRuolo(rs.getString("ruolo"));
                u.setIscrizioneNewsletter(rs.getBoolean("iscrizione_newsletter"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Utente doRetrieveByEmail(String email) {
        if (email == null) return null;
        Utente u = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Utenti WHERE email = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Utente(); // <-- qui solo "u = ..." NON "Utente u = ..."
                u.setId(rs.getInt("id")); 
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("passw"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setRuolo(rs.getString("ruolo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    
    public Utente doRetrieveByCredentials(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Utenti WHERE email = ? AND passw = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, hashPassword(password)); // ✅ qui applichi l'hash

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setRuolo(rs.getString("ruolo"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    


    // Recupero di tutti gli utenti
    public List<Utente> doRetrieveAll() {
        List<Utente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Utenti";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("passw"));
                u.setRuolo(rs.getString("ruolo"));
                u.setIscrizioneNewsletter(rs.getBoolean("iscrizione_newsletter"));
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Aggiornamento utente
    public boolean doUpdate(Utente utente) {
        String sql = "UPDATE Utenti SET nome=?, cognome=?, email=?, passw=?, ruolo=?, iscrizione_newsletter=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getCognome());
            stmt.setString(3, utente.getEmail());
            stmt.setString(4, utente.getPassword());
            stmt.setString(5, utente.getRuolo());
            stmt.setBoolean(6, utente.isIscrizioneNewsletter());
            stmt.setInt(7, utente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cancellazione utente
    public boolean doDelete(int id) {
        String sql = "DELETE FROM Utenti WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cancellazione utente per oggetto
    public boolean doDelete(Utente utente) {
        return doDelete(utente.getId());
    }


private String hashPassword(String password) throws Exception {
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(bytes);
}
}