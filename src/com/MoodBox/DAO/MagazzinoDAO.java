package com.MoodBox.DAO;

import com.MoodBox.model.Magazzino;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;

public class MagazzinoDAO {
    
    public boolean doSave(Magazzino magazzino) {
        String sql = "INSERT INTO Magazzino (item_id, quantita_disponibile) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, magazzino.getItemId());
            stmt.setInt(2, magazzino.getQuantitaDisponibile());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Magazzino doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Magazzino WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Magazzino m = new Magazzino();
                m.setId(rs.getInt("id"));
                m.setItemId(rs.getInt("item_id"));
                m.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                m.setDataUltimoAggiornamento(rs.getTimestamp("data_ultimo_aggiornamento").toLocalDateTime());
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Magazzino doRetrieveByItemId(int itemId) {
        String sql = "SELECT * FROM Magazzino WHERE item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Magazzino m = new Magazzino();
                m.setId(rs.getInt("id"));
                m.setItemId(rs.getInt("item_id"));
                m.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
                m.setDataUltimoAggiornamento(rs.getTimestamp("data_ultimo_aggiornamento").toLocalDateTime());
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doUpdate(Magazzino magazzino) {
        String sql = "UPDATE Magazzino SET quantita_disponibile = ?, data_ultimo_aggiornamento = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, magazzino.getQuantitaDisponibile());
            stmt.setInt(2, magazzino.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Magazzino WHERE id = ?";
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