package com.moodbox.DAO;

import com.moodbox.database.DatabaseConnection;
import com.moodbox.model.RigaOrdine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RigaOrdineDAO {

    public boolean doSave(RigaOrdine riga) {
        String sql = "INSERT INTO Righe_Ordine (ordine_id, box_id, quantita, prezzo_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, riga.getOrdineId());
            stmt.setInt(2, riga.getBoxId());
            stmt.setInt(3, riga.getQuantita());
            stmt.setBigDecimal(4, riga.getPrezzoUnitario());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        riga.setId(keys.getInt(1));
                    }
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
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRigaOrdine(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RigaOrdine> doRetrieveAll(int ordineId) {
        List<RigaOrdine> lista = new ArrayList<>();
        String sql = "SELECT * FROM Righe_Ordine WHERE ordine_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordineId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToRigaOrdine(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean doUpdate(RigaOrdine riga) {
        String sql = "UPDATE Righe_Ordine SET ordine_id = ?, box_id = ?, quantita = ?, prezzo_unitario = ? WHERE id = ?";

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

    private RigaOrdine mapResultSetToRigaOrdine(ResultSet rs) throws SQLException {
        RigaOrdine r = new RigaOrdine();
        r.setId(rs.getInt("id"));
        r.setOrdineId(rs.getInt("ordine_id"));
        r.setBoxId(rs.getInt("box_id"));
        r.setQuantita(rs.getInt("quantita")); // no accenti!
        r.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
        return r;
    }
}
