package com.moodbox.DAO;

import com.moodbox.model.PromozioneBox;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromozioneBoxDAO {

    public boolean doSave(PromozioneBox promobox) {
        String sql = "INSERT INTO Promozione_Box (promozione_id, box_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promobox.getPromozioneId());
            stmt.setInt(2, promobox.getBoxId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PromozioneBox doRetrieveByKey(int promozioneId, int boxId) {
        String sql = "SELECT * FROM Promozione_Box WHERE promozione_id = ? AND box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promozioneId);
            stmt.setInt(2, boxId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PromozioneBox(rs.getInt("promozione_id"), rs.getInt("box_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PromozioneBox> doRetrieveAll() {
        String sql = "SELECT * FROM Promozione_Box";
        List<PromozioneBox> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PromozioneBox pb = new PromozioneBox(rs.getInt("promozione_id"), rs.getInt("box_id"));
                list.add(pb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean doDelete(int promozioneId, int boxId) {
        String sql = "DELETE FROM Promozione_Box WHERE promozione_id = ? AND box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, promozioneId);
            stmt.setInt(2, boxId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
