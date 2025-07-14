package com.moodbox.DAO;

import com.moodbox.database.DatabaseConnection;
import com.moodbox.model.Ordine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO per la tabella Ordini
 */
public class OrdineDAO {

    /* ============================================================== 
       CREA (ritorna ID generato)
       ============================================================== */
    public int doSave(Ordine ordine) {

        final String sql = """
            INSERT INTO Ordini
              (utente_id, via, civico, cap, citta, provincia, paese,
               metodo_spedizione, metodo_pagamento, costo_spedizione, stato_ordine, totale,
               note_ordine, data_ordine)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt       (1,  ordine.getUtenteId());
            stmt.setString    (2,  ordine.getVia());
            stmt.setString    (3,  ordine.getCivico());
            stmt.setString    (4,  ordine.getCap());
            stmt.setString    (5,  ordine.getCitta());
            stmt.setString    (6,  ordine.getProvincia());
            stmt.setString    (7,  ordine.getPaese());
            stmt.setString    (8,  ordine.getMetodoSpedizione());
            stmt.setString	  (9, ordine.getMetodoPagamento());
            stmt.setBigDecimal(10,  ordine.getCostoSpedizione());
            stmt.setString    (11, ordine.getStatoOrdine());
            stmt.setBigDecimal(12, ordine.getTotale());
            stmt.setString    (13, ordine.getNoteOrdine());
            stmt.setTimestamp (14,ordine.getDataOrdine() != null ? Timestamp.valueOf(ordine.getDataOrdine()) : null);

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);            // ✅ ID dell’ordine
                    }
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return -1;                                       // errore
    }

    /* ============================================================== 
       READ – by PK
       ============================================================== */
    public Ordine doRetrieveByKey(int id) {
        final String sql = "SELECT * FROM Ordini WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extract(rs);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    /* ============================================================== 
       READ – tutti
       ============================================================== */
    public List<Ordine> doRetrieveAll() {
        final String sql = "SELECT * FROM Ordini ORDER BY data_ordine DESC";
        List<Ordine> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st  = conn.createStatement();
             ResultSet rs  = st.executeQuery(sql)) {

            while (rs.next()) lista.add(extract(rs));
        } catch (SQLException ex) { ex.printStackTrace(); }
        return lista;
    }

    /* ============================================================== 
       READ – per utente
       ============================================================== */
    public List<Ordine> doRetrieveByUtenteId(int utenteId) {
        final String sql = "SELECT * FROM Ordini WHERE utente_id = ? ORDER BY data_ordine DESC";
        List<Ordine> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(extract(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return lista;
    }

    /* ============================================================== 
       READ – range date
       ============================================================== */
    public List<Ordine> doRetrieveByDateRange(Timestamp dal, Timestamp al) {
        final String sql = """
            SELECT * FROM Ordini
             WHERE data_ordine BETWEEN ? AND ?
             ORDER BY data_ordine DESC""";
        List<Ordine> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, dal);
            ps.setTimestamp(2, al);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(extract(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return lista;
    }

    /* ============================================================== 
       UPDATE completo
       ============================================================== */
    public boolean doUpdate(Ordine ordine) {
        final String sql = """
             UPDATE Ordini SET
               via = ?, civico = ?, cap = ?, citta = ?, provincia = ?, paese = ?,
               metodo_spedizione = ?,metodo_pagamento = ?, costo_spedizione = ?, stato_ordine = ?,
               totale = ?, note_ordine = ?, data_ordine = ?
             WHERE id = ?""";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString    (1,  ordine.getVia());
            ps.setString    (2,  ordine.getCivico());
            ps.setString    (3,  ordine.getCap());
            ps.setString    (4,  ordine.getCitta());
            ps.setString    (5,  ordine.getProvincia());
            ps.setString    (6,  ordine.getPaese());
            ps.setString    (7,  ordine.getMetodoSpedizione());
            ps.setString	(8,ordine.getMetodoPagamento());
            ps.setBigDecimal(9,  ordine.getCostoSpedizione());
            ps.setString    (10,  ordine.getStatoOrdine());
            ps.setBigDecimal(11, ordine.getTotale());
            ps.setString    (12, ordine.getNoteOrdine());
            ps.setTimestamp (13, ordine.getDataOrdine() != null ? Timestamp.valueOf(ordine.getDataOrdine()) : null);
            ps.setInt       (14, ordine.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    /* ============================================================== 
       UPDATE – solo stato
       ============================================================== */
    public boolean doUpdateStato(int ordineId, String nuovoStato) {
        final String sql = "UPDATE Ordini SET stato_ordine = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuovoStato);
            ps.setInt(2, ordineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    /* ============================================================== 
       DELETE
       ============================================================== */
    public boolean doDelete(int id) {
        final String sql = "DELETE FROM Ordini WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    /* ============================================================== 
       Utility – mappa ResultSet→Ordine
       ============================================================== */
    private Ordine extract(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setId(rs.getInt("id"));
        o.setUtenteId(rs.getInt("utente_id"));

        o.setVia       (rs.getString("via"));
        o.setCivico    (rs.getString("civico"));
        o.setCap       (rs.getString("cap"));
        o.setCitta     (rs.getString("citta"));
        o.setProvincia (rs.getString("provincia"));
        o.setPaese     (rs.getString("paese"));

        o.setMetodoSpedizione(rs.getString("metodo_spedizione"));
        o.setMetodoPagamento(rs.getString("metodo_pagamento"));
        o.setCostoSpedizione(rs.getBigDecimal("costo_spedizione"));
        o.setStatoOrdine(rs.getString("stato_ordine"));
        o.setTotale(rs.getBigDecimal("totale"));
        o.setNoteOrdine(rs.getString("note_ordine"));

        Timestamp ts = rs.getTimestamp("data_ordine");
        if (ts != null) o.setDataOrdine(ts.toLocalDateTime());

        return o;
    }
}
