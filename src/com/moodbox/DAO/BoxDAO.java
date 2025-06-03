package com.moodbox.DAO;

import com.moodbox.model.Box;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoxDAO {
    
    public boolean doSave(Box box) {
        String sql = "INSERT INTO Boxes (nome, descrizione, prezzo, disponibile) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, box.getNome());
            stmt.setString(2, box.getDescrizione());
            stmt.setBigDecimal(3, box.getPrezzo());
            stmt.setBoolean(4, box.isDisponibile());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Box doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Boxes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Box b = new Box();
                b.setId(rs.getInt("id"));
                b.setNome(rs.getString("nome"));
                b.setDescrizione(rs.getString("descrizione"));
                b.setPrezzo(rs.getBigDecimal("prezzo"));
                b.setDisponibile(rs.getBoolean("disponibile"));
                b.setDataCreazione(rs.getTimestamp("data_creazione").toLocalDateTime());
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Box> doRetrieveAll() {
        List<Box> lista = new ArrayList<>();
        String sql = "SELECT * FROM Boxes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Box b = new Box();
                b.setId(rs.getInt("id"));
                b.setNome(rs.getString("nome"));
                b.setDescrizione(rs.getString("descrizione"));
                b.setPrezzo(rs.getBigDecimal("prezzo"));
                b.setDisponibile(rs.getBoolean("disponibile"));
                b.setDataCreazione(rs.getTimestamp("data_creazione").toLocalDateTime());
                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Box> doRetrieveAvailable() {
        List<Box> lista = new ArrayList<>();
        String sql = "SELECT * FROM Boxes WHERE disponibile = TRUE";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Box b = new Box();
                b.setId(rs.getInt("id"));
                b.setNome(rs.getString("nome"));
                b.setDescrizione(rs.getString("descrizione"));
                b.setPrezzo(rs.getBigDecimal("prezzo"));
                b.setDisponibile(rs.getBoolean("disponibile"));
                b.setDataCreazione(rs.getTimestamp("data_creazione").toLocalDateTime());
                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean doUpdate(Box box) {
        String sql = "UPDATE Boxes SET nome=?, descrizione=?, prezzo=?, disponibile=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, box.getNome());
            stmt.setString(2, box.getDescrizione());
            stmt.setBigDecimal(3, box.getPrezzo());
            stmt.setBoolean(4, box.isDisponibile());
            stmt.setInt(5, box.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Boxes WHERE id=?";
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