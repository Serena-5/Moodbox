package com.moodbox.DAO;

import com.moodbox.model.Carrello;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;

public class CarrelloDAO {
    
    public boolean doSave(Carrello carrello) {
        String sql = "INSERT INTO Carrello (utente_id, session_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (carrello.getUtenteId() != null) {
                stmt.setInt(1, carrello.getUtenteId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, carrello.getSessionId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Carrello doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Carrello WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Carrello c = new Carrello();
                c.setId(rs.getInt("id"));
                c.setUtenteId(rs.getObject("utente_id", Integer.class));
                c.setSessionId(rs.getString("session_id"));
                c.setUltimoAccesso(rs.getTimestamp("ultimo_accesso").toLocalDateTime());
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Carrello doRetrieveBySessionId(String sessionId) {
        String sql = "SELECT * FROM Carrello WHERE session_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sessionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Carrello c = new Carrello();
                c.setId(rs.getInt("id"));
                c.setUtenteId(rs.getObject("utente_id", Integer.class));
                c.setSessionId(rs.getString("session_id"));
                c.setUltimoAccesso(rs.getTimestamp("ultimo_accesso").toLocalDateTime());
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doUpdate(Carrello carrello) {
        String sql = "UPDATE Carrello SET utente_id = ?, ultimo_accesso = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (carrello.getUtenteId() != null) {
                stmt.setInt(1, carrello.getUtenteId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setInt(2, carrello.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Carrello WHERE id = ?";
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