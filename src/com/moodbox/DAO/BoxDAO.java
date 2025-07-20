package com.moodbox.DAO;

import com.moodbox.model.Box;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoxDAO {
    
    public boolean doSave(Box box) {
    	String sql = "INSERT INTO boxes (nome, descrizione, prezzo, disponibile, immagine) VALUES (?, ?, ?, ?, ?)";
    		try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, box.getNome());
            stmt.setString(2, box.getDescrizione());
            stmt.setBigDecimal(3, box.getPrezzo());
            stmt.setBoolean(4, box.isDisponibile());
            stmt.setString(5, box.getImmagine());


            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Box doRetrieveByKey(int id) {
        String sql = "SELECT * FROM boxes WHERE id = ?";
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
                b.setImmagine(rs.getString("immagine"));

                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Box> doRetrieveByName(String search) {
        List<Box> boxes = new ArrayList<>();
        String sql = "SELECT * FROM boxes WHERE disponibile = 1 AND LOWER(nome) LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + search.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Box box = new Box();
                box.setId(rs.getInt("id"));
                box.setNome(rs.getString("nome"));
                box.setDescrizione(rs.getString("descrizione"));
                box.setPrezzo(rs.getBigDecimal("prezzo"));
                box.setDisponibile(rs.getBoolean("disponibile"));
                box.setImmagine(rs.getString("immagine"));
                boxes.add(box);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boxes;
    }


    public List<Box> doRetrieveAll() {
        List<Box> lista = new ArrayList<>();
        String sql = "SELECT * FROM boxes";

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
                b.setImmagine(rs.getString("immagine"));

                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Box> doRetrieveAvailable() {
        List<Box> lista = new ArrayList<>();
        String sql = "SELECT * FROM boxes WHERE disponibile = TRUE";

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
                b.setImmagine(rs.getString("immagine"));

                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean doUpdate(Box box) {
        String sql = "UPDATE boxes SET nome=?, descrizione=?, prezzo=?, disponibile=?, immagine=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, box.getNome());
            stmt.setString(2, box.getDescrizione());
            stmt.setBigDecimal(3, box.getPrezzo());
            stmt.setBoolean(4, box.isDisponibile());
            stmt.setString(5, box.getImmagine());  // aggiunto
            stmt.setInt(6, box.getId());           // spostato da posizione 5 a 6

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean doDelete(int id) {
        String sql = "DELETE FROM boxes WHERE id=?";
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