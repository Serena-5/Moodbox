package com.MoodBox.DAO;

import com.MoodBox.model.Recensione;
import com.MoodBox.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    public boolean doSave(Recensione rev) {
        String sql = "INSERT INTO Recensioni (utente_id, box_id, voto, commento, data_recensione, risposta_admin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rev.getUtenteId());
            stmt.setInt(2, rev.getBoxId());
            stmt.setInt(3, rev.getVoto());
            stmt.setString(4, rev.getCommento());
            
            // Se la data è null, si usa quella di default nel DB
            if (rev.getDataRecensione() != null) {
                stmt.setTimestamp(5, Timestamp.valueOf(rev.getDataRecensione()));
            } else {
                stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            }

            stmt.setString(6, rev.getRispostaAdmin());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rev.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Recensione doRetrieveByKey(int id) {
        String sql = "SELECT * FROM Recensioni WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Recensione rev = new Recensione();
                rev.setId(rs.getInt("id"));
                rev.setUtenteId(rs.getInt("utente_id"));
                rev.setBoxId(rs.getInt("box_id"));
                rev.setVoto(rs.getInt("voto"));
                rev.setCommento(rs.getString("commento"));
                rev.setDataRecensione(rs.getTimestamp("data_recensione").toLocalDateTime());
                rev.setRispostaAdmin(rs.getString("risposta_admin"));
                return rev;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Recensione> doRetrieveAll() {
        String sql = "SELECT * FROM Recensioni ORDER BY data_recensione DESC";
        List<Recensione> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Recensione rev = new Recensione();
                rev.setId(rs.getInt("id"));
                rev.setUtenteId(rs.getInt("utente_id"));
                rev.setBoxId(rs.getInt("box_id"));
                rev.setVoto(rs.getInt("voto"));
                rev.setCommento(rs.getString("commento"));
                rev.setDataRecensione(rs.getTimestamp("data_recensione").toLocalDateTime());
                rev.setRispostaAdmin(rs.getString("risposta_admin"));
                list.add(rev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean doUpdate(Recensione rev) {
        String sql = "UPDATE Recensioni SET voto = ?, commento = ?, risposta_admin = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rev.getVoto());
            stmt.setString(2, rev.getCommento());
            stmt.setString(3, rev.getRispostaAdmin());
            stmt.setInt(4, rev.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean doDelete(int id) {
        String sql = "DELETE FROM Recensioni WHERE id = ?";
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