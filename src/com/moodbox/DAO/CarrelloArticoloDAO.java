package com.moodbox.DAO;

import com.moodbox.model.CarrelloArticolo;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelloArticoloDAO {

    public boolean doSave(CarrelloArticolo carrelloArticolo) {
        String sql = "INSERT INTO carrello_articoli (carrello_id, box_id, quantità) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloArticolo.getCarrelloId());
            stmt.setInt(2, carrelloArticolo.getBoxId());
            stmt.setInt(3, carrelloArticolo.getQuantita());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public CarrelloArticolo doRetrieveByKey(int carrelloId, int boxId) {
        String sql = "SELECT * FROM carrello_articoli WHERE carrello_id = ? AND box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloId);
            stmt.setInt(2, boxId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CarrelloArticolo ca = new CarrelloArticolo();
                ca.setCarrelloId(rs.getInt("carrello_id"));
                ca.setBoxId(rs.getInt("box_id"));
                ca.setQuantita(rs.getInt("quantità"));
                return ca;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CarrelloArticolo> doRetrieveByCarrelloId(int carrelloId) {
        List<CarrelloArticolo> list = new ArrayList<>();
        String sql = "SELECT * FROM carrello_articoli WHERE carrello_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CarrelloArticolo ca = new CarrelloArticolo();
                ca.setCarrelloId(rs.getInt("carrello_id"));
                ca.setBoxId(rs.getInt("box_id"));
                ca.setQuantita(rs.getInt("quantità"));
                list.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean doUpdate(CarrelloArticolo carrelloArticolo) {
        String sql = "UPDATE carrello_articoli SET quantità = ? WHERE carrello_id = ? AND box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloArticolo.getQuantita());
            stmt.setInt(2, carrelloArticolo.getCarrelloId());
            stmt.setInt(3, carrelloArticolo.getBoxId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int carrelloId, int boxId) {
        String sql = "DELETE FROM carrello_articoli WHERE carrello_id = ? AND box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloId);
            stmt.setInt(2, boxId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int totaleQuantita(int carrelloId) {
        String sql = "SELECT COALESCE(SUM(quantità), 0) FROM carrello_articoli WHERE carrello_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carrelloId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean doDeleteAllByCarrelloId(int carrelloId) {
        String sql = "DELETE FROM carrello_articoli WHERE carrello_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrelloId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}