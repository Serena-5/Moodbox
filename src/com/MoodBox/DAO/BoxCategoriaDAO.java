package com.moodbox.DAO;

import com.moodbox.model.BoxCategoria;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoxCategoriaDAO {
    
    public boolean doSave(BoxCategoria boxCategoria) {
        String sql = "INSERT INTO Box_Categorie (box_id, categoria_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boxCategoria.getBoxId());
            stmt.setInt(2, boxCategoria.getCategoriaId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BoxCategoria> doRetrieveByBoxId(int boxId) {
        List<BoxCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Box_Categorie WHERE box_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boxId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BoxCategoria bc = new BoxCategoria();
                bc.setBoxId(rs.getInt("box_id"));
                bc.setCategoriaId(rs.getInt("categoria_id"));
                lista.add(bc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<BoxCategoria> doRetrieveByCategoriaId(int categoriaId) {
        List<BoxCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Box_Categorie WHERE categoria_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BoxCategoria bc = new BoxCategoria();
                bc.setBoxId(rs.getInt("box_id"));
                bc.setCategoriaId(rs.getInt("categoria_id"));
                lista.add(bc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean doDelete(int boxId, int categoriaId) {
        String sql = "DELETE FROM Box_Categorie WHERE box_id = ? AND categoria_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, boxId);
            stmt.setInt(2, categoriaId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}