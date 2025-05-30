package com.MoodBox.DAO;

import com.MoodBox.database.DatabaseConnection;
import com.MoodBox.model.RigaOrdine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class RigaOrdineDAO {

    public boolean doSave(RigaOrdine riga) {
        String sql = "INSERT INTO Righe_Ordine (ordine_id, box_id, quantità, prezzo_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, riga.getOrdineId());
            stmt.setInt(2, riga.getBoxId());
            stmt.setInt(3, riga.getQuantita());
            stmt.setBigDecimal(4, riga.getPrezzoUnitario());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    riga.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public RigaOrdine doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Righe_Ordine WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRigaOrdine(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Recupera tutte le righe di un determinato ordine
    public List<RigaOrdine> doRetrieveAll(int ordineId) {
        String sql = "SELECT * FROM Righe_Ordine WHERE ordine_id = ?";
        List<RigaOrdine> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordineId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToRigaOrdine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean doUpdate(RigaOrdine riga) {
        String sql = "UPDATE Righe_Ordine SET ordine_id = ?, box_id = ?, quantità = ?, prezzo_unitario = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, riga.getOrdineId());
            stmt.setInt(2, riga.getBoxId());
            stmt.setInt(3, riga.getQuantita());
            stmt.setBigDecimal(4, riga.getPrezzoUnitario());
            stmt.setInt(5, riga.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Righe_Ordine WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Metodo di supporto per mappare un ResultSet a un oggetto RigaOrdine
    private RigaOrdine mapResultSetToRigaOrdine(ResultSet rs) throws SQLException {
        RigaOrdine riga = new RigaOrdine();
        riga.setId(rs.getInt("id"));
        riga.setOrdineId(rs.getInt("ordine_id"));
        riga.setBoxId(rs.getInt("box_id"));
        riga.setQuantita(rs.getInt("quantità"));
        riga.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
        return riga;
    }
}