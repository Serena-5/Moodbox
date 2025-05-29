package com.MoodBox.DAO;

import com.MoodBox.model.*;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;

public class AbbonamentoDAO {
    public boolean doSave(Abbonamento abbonamento) {
        String sql = "INSERT INTO Abbonamenti (utente_id, tipo_abbonamento, attivo, data_inizio, data_fine, box_tema_preferito) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, abbonamento.getUtenteId());
            stmt.setString(2, abbonamento.getTipoAbbonamento());
            stmt.setBoolean(3, abbonamento.isAttivo());
            stmt.setTimestamp(4, Timestamp.valueOf(abbonamento.getDataInizio()));
            stmt.setDate(5, Date.valueOf(abbonamento.getDataFine()));
            stmt.setString(6, abbonamento.getBoxTemaPreferito());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Abbonamento doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Abbonamenti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Abbonamento a = new Abbonamento();
                a.setId(rs.getInt("id"));
                a.setUtenteId(rs.getInt("utente_id"));
                a.setTipoAbbonamento(rs.getString("tipo_abbonamento"));
                a.setAttivo(rs.getBoolean("attivo"));
                a.setDataInizio(rs.getTimestamp("data_inizio").toLocalDateTime());
                a.setDataFine(rs.getDate("data_fine").toLocalDate());
                a.setBoxTemaPreferito(rs.getString("box_tema_preferito"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doUpdate(Abbonamento abbonamento) {
        String sql = "UPDATE Abbonamenti SET utente_id = ?, tipo_abbonamento = ?, attivo = ?, data_inizio = ?, data_fine = ?, box_tema_preferito = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, abbonamento.getUtenteId());
            stmt.setString(2, abbonamento.getTipoAbbonamento());
            stmt.setBoolean(3, abbonamento.isAttivo());
            stmt.setTimestamp(4, Timestamp.valueOf(abbonamento.getDataInizio()));
            stmt.setDate(5, Date.valueOf(abbonamento.getDataFine()));
            stmt.setString(6, abbonamento.getBoxTemaPreferito());
            stmt.setInt(7, abbonamento.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Abbonamenti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}