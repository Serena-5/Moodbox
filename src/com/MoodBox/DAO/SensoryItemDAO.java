package com.MoodBox.DAO;

import com.MoodBox.model.SensoryItem;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensoryItemDAO {
    
    public boolean doSave(SensoryItem item) {
        String sql = "INSERT INTO Sensory_Items (nome, descrizione, tipo_senso, prezzo_unitario, immagine_url, disponibile) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescrizione());
            stmt.setString(3, item.getTipoSenso());
            stmt.setBigDecimal(4, item.getPrezzoUnitario());
            stmt.setString(5, item.getImmagineUrl());
            stmt.setBoolean(6, item.isDisponibile());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SensoryItem doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Sensory_Items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SensoryItem s = new SensoryItem();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setTipoSenso(rs.getString("tipo_senso"));
                s.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
                s.setImmagineUrl(rs.getString("immagine_url"));
                s.setDisponibile(rs.getBoolean("disponibile"));
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SensoryItem> doRetrieveBySense(String tipoSenso) {
        List<SensoryItem> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sensory_Items WHERE tipo_senso = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoSenso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SensoryItem s = new SensoryItem();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setTipoSenso(rs.getString("tipo_senso"));
                s.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
                s.setImmagineUrl(rs.getString("immagine_url"));
                s.setDisponibile(rs.getBoolean("disponibile"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<SensoryItem> doRetrieveAll() {
        List<SensoryItem> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sensory_Items";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SensoryItem s = new SensoryItem();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setTipoSenso(rs.getString("tipo_senso"));
                s.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
                s.setImmagineUrl(rs.getString("immagine_url"));
                s.setDisponibile(rs.getBoolean("disponibile"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean doUpdate(SensoryItem item) {
        String sql = "UPDATE Sensory_Items SET nome=?, descrizione=?, tipo_senso=?, prezzo_unitario=?, immagine_url=?, disponibile=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescrizione());
            stmt.setString(3, item.getTipoSenso());
            stmt.setBigDecimal(4, item.getPrezzoUnitario());
            stmt.setString(5, item.getImmagineUrl());
            stmt.setBoolean(6, item.isDisponibile());
            stmt.setInt(7, item.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Sensory_Items WHERE id=?";
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