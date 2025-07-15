package com.moodbox.DAO;

import com.moodbox.model.CatalogItem;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogItemDAO {

    private static final String TABLE = "boxes";

    /* ---------- CREATE ---------- */
    public void save(CatalogItem c) throws SQLException {
        final String sql = """
            INSERT INTO %s
                   (nome, descrizione, prezzo, disponibile, data_creazione, immagine)
            VALUES (?,?,?,?,?,?)
            """.formatted(TABLE);

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescrizione());
            ps.setBigDecimal(3, c.getPrezzo());
            ps.setBoolean(4, c.isDisponibile());
            ps.setTimestamp(5, Timestamp.valueOf(c.getDataCreazione()));
            ps.setString(6, c.getImmagine());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        }
    }

    /* ---------- READ ---------- */
    public CatalogItem doRetrieveById(int id) throws SQLException {
        final String sql = "SELECT * FROM " + TABLE + " WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapping(rs) : null;
            }
        }
    }

    public List<CatalogItem> doRetrieveAll() throws SQLException {
        final String sql = "SELECT * FROM " + TABLE + " ORDER BY id DESC";
        List<CatalogItem> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapping(rs));
        }
        return list;
    }

    /* ---------- UPDATE ---------- */
    public void update(CatalogItem c) throws SQLException {
        final String sql = """
            UPDATE %s SET
                   nome = ?, descrizione = ?, prezzo = ?, disponibile = ?, immagine = ?
            WHERE id = ?
            """.formatted(TABLE);

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescrizione());
            ps.setBigDecimal(3, c.getPrezzo());
            ps.setBoolean(4, c.isDisponibile());
            ps.setString(5, c.getImmagine());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        }
    }

    /* ---------- SOFT‑DELETE / TOGGLE ---------- */
    public void setDisponibile(int id, boolean stato) throws SQLException {
        final String sql = "UPDATE " + TABLE + " SET disponibile=? WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, stato);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    /* ---------- DELETE (non più usata) ---------- */
    public void delete(int id) throws SQLException {
        final String sql = "DELETE FROM " + TABLE + " WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* ---------- mapping ---------- */
    private CatalogItem mapping(ResultSet rs) throws SQLException {
        CatalogItem c = new CatalogItem();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setDescrizione(rs.getString("descrizione"));
        c.setPrezzo(rs.getBigDecimal("prezzo"));
        c.setDisponibile(rs.getBoolean("disponibile"));
        c.setDataCreazione(rs.getTimestamp("data_creazione").toLocalDateTime());
        c.setImmagine(rs.getString("immagine"));
        return c;
    }
}
