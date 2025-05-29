package com.MoodBox.DAO;

import com.MoodBox.model.*;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class BoxItemDAO {
    public boolean doSave(BoxItem item) {
        String sql = "INSERT INTO Box_Items (box_id, item_id, quantità) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getBoxId());
            stmt.setInt(2, item.getItemId());
            stmt.setInt(3, item.getQuantita());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BoxItem> doRetrieveByBoxId(int boxId) {
        List<BoxItem> items = new ArrayList<>();
        String sql = "SELECT * FROM Box_Items WHERE box_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boxId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BoxItem item = new BoxItem();
                item.setBoxId(rs.getInt("box_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantita(rs.getInt("quantità"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean doUpdate(BoxItem item) {
        String sql = "UPDATE Box_Items SET quantità = ? WHERE box_id = ? AND item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getQuantita());
            stmt.setInt(2, item.getBoxId());
            stmt.setInt(3, item.getItemId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int boxId, int itemId) {
        String sql = "DELETE FROM Box_Items WHERE box_id = ? AND item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boxId);
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}