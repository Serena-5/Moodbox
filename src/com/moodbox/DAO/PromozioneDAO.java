package com.moodbox.DAO;

import com.moodbox.model.Promozione;
import com.moodbox.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromozioneDAO {

    public boolean doSave(Promozione promo) {
        String sql = "INSERT INTO Promozioni (nome, descrizione, sconto_percentuale, data_inizio, data_fine) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, promo.getNome());
            stmt.setString(2, promo.getDescrizione());
            stmt.setBigDecimal(3, promo.getScontoPercentuale());
            stmt.setDate(4, Date.valueOf(promo.getDataInizio()));
            stmt.setDate(5, Date.valueOf(promo.getDataFine()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Promozione doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Promozioni WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Promozione promo = new Promozione();
                promo.setId(rs.getInt("id"));
                promo.setNome(rs.getString("nome"));
                promo.setDescrizione(rs.getString("descrizione"));
                promo.setScontoPercentuale(rs.getBigDecimal("sconto_percentuale"));
                promo.setDataInizio(rs.getDate("data_inizio").toLocalDate());
                promo.setDataFine(rs.getDate("data_fine").toLocalDate());
                return promo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Promozione> doRetrieveAll() {
        String sql = "SELECT * FROM Promozioni";
        List<Promozione> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Promozione promo = new Promozione();
                promo.setId(rs.getInt("id"));
                promo.setNome(rs.getString("nome"));
                promo.setDescrizione(rs.getString("descrizione"));
                promo.setScontoPercentuale(rs.getBigDecimal("sconto_percentuale"));
                promo.setDataInizio(rs.getDate("data_inizio").toLocalDate());
                promo.setDataFine(rs.getDate("data_fine").toLocalDate());
                list.add(promo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean doUpdate(Promozione promo) {
        String sql = "UPDATE Promozioni SET nome = ?, descrizione = ?, sconto_percentuale = ?, data_inizio = ?, data_fine = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, promo.getNome());
            stmt.setString(2, promo.getDescrizione());
            stmt.setBigDecimal(3, promo.getScontoPercentuale());
            stmt.setDate(4, Date.valueOf(promo.getDataInizio()));
            stmt.setDate(5, Date.valueOf(promo.getDataFine()));
            stmt.setInt(6, promo.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Promozioni WHERE id = ?";
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
