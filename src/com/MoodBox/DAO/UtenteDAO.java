package com.MoodBox.DAO;

import com.MoodBox.model.Utente;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    //Aggiunta utente
    public boolean create(Utente utente) {
        String sql = "INSERT INTO Utenti (nome, email, passw, ruolo, iscrizione_newsletter) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getEmail());
            stmt.setString(3, utente.getPassword());
            stmt.setString(4, utente.getRuolo());
            stmt.setBoolean(5, utente.isIscrizioneNewsletter());

            int righeInserite = stmt.executeUpdate();
            return righeInserite > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Ricerca utente per ID
    public Utente findById(int id) {
        String sql = "SELECT * FROM Utenti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mappaUtente(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Login
    public Utente checkLogin(String email, String password) {
        String sql = "SELECT * FROM Utenti WHERE email = ? AND passw = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mappaUtente(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Lista di tutti gli utenti
    public List<Utente> findAll() {
        List<Utente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Utenti";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mappaUtente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //Update
    public boolean update(Utente utente) {
        String sql = "UPDATE Utenti SET nome=?, email=?, passw=?, ruolo=?, iscrizione_newsletter=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getEmail());
            stmt.setString(3, utente.getPassword());
            stmt.setString(4, utente.getRuolo());
            stmt.setBoolean(5, utente.isIscrizioneNewsletter());
            stmt.setInt(6, utente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Delete
    public boolean delete(int id) {
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

    // Metodo di supporto per mappare ResultSet -> Utente
    private Utente mappaUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("passw"));
        u.setRuolo(rs.getString("ruolo"));
        u.setIscrizioneNewsletter(rs.getBoolean("iscrizione_newsletter"));
        return u;
    }
}
