package com.moodbox.DAO;

import com.moodbox.model.Ordine;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public boolean doSave(Ordine ordine) {
        String sql = "INSERT INTO Ordini (utente_id, indirizzo_spedizione, metodo_spedizione, costo_spedizione, stato_ordine, totale, note_ordine, data_ordine) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ordine.getUtenteId());
            stmt.setString(2, ordine.getIndirizzoSpedizione());
            stmt.setString(3, ordine.getMetodoSpedizione());
            stmt.setBigDecimal(4, ordine.getCostoSpedizione());
            stmt.setString(5, ordine.getStatoOrdine());
            stmt.setBigDecimal(6, ordine.getTotale());
            stmt.setString(7, ordine.getNoteOrdine());
            stmt.setTimestamp(8, ordine.getDataOrdine() != null ? Timestamp.valueOf(ordine.getDataOrdine()) : null);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ordine doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Ordini WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractOrdineFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ordine> doRetrieveAll() {
        String sql = "SELECT * FROM Ordini ORDER BY data_ordine DESC";
        List<Ordine> ordineList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ordineList.add(extractOrdineFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordineList;
    }

    public boolean doUpdate(Ordine ordine) {
        String sql = "UPDATE Ordini SET indirizzo_spedizione = ?, metodo_spedizione = ?, costo_spedizione = ?, stato_ordine = ?, totale = ?, note_ordine = ?, data_ordine = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ordine.getIndirizzoSpedizione());
            stmt.setString(2, ordine.getMetodoSpedizione());
            stmt.setBigDecimal(3, ordine.getCostoSpedizione());
            stmt.setString(4, ordine.getStatoOrdine());
            stmt.setBigDecimal(5, ordine.getTotale());
            stmt.setString(6, ordine.getNoteOrdine());
            stmt.setTimestamp(7, ordine.getDataOrdine() != null ? Timestamp.valueOf(ordine.getDataOrdine()) : null);
            stmt.setInt(8, ordine.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Ordini WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Ordine extractOrdineFromResultSet(ResultSet rs) throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setId(rs.getInt("id"));
        ordine.setUtenteId(rs.getInt("utente_id"));
        ordine.setIndirizzoSpedizione(rs.getString("indirizzo_spedizione"));
        ordine.setMetodoSpedizione(rs.getString("metodo_spedizione"));
        ordine.setCostoSpedizione(rs.getBigDecimal("costo_spedizione"));
        ordine.setStatoOrdine(rs.getString("stato_ordine"));
        ordine.setTotale(rs.getBigDecimal("totale"));
        ordine.setNoteOrdine(rs.getString("note_ordine"));

        Timestamp ts = rs.getTimestamp("data_ordine");
        if (ts != null) {
            ordine.setDataOrdine(ts.toLocalDateTime());
        }

        return ordine;
    }
    
    public List<Ordine> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Ordini WHERE utente_id = ? ORDER BY data_ordine DESC";
        List<Ordine> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, utenteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(extractOrdineFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean doUpdateStato(int ordineId, String nuovoStato) {
        String sql = "UPDATE Ordini SET stato_ordine = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuovoStato);
            stmt.setInt(2, ordineId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    } 
    
    public List<Ordine> doRetrieveByDateRange(Timestamp dataInizio, Timestamp dataFine) {
        String sql = "SELECT * FROM Ordini WHERE data_ordine BETWEEN ? AND ? ORDER BY data_ordine DESC";
        List<Ordine> ordini = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, dataInizio);
            stmt.setTimestamp(2, dataFine);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ordini.add(extractOrdineFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordini;
    }

}