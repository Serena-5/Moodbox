package com.moodbox.DAO;

import com.moodbox.model.IndirizzoUtente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndirizzoUtenteDAO {
    private Connection connection;
    
    public IndirizzoUtenteDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Salva un nuovo indirizzo nel database
     */
    public boolean doSave(IndirizzoUtente indirizzo) {
        String sql = "INSERT INTO indirizzo_utente (utente_id, via, citta, provincia, cap, nazione) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, indirizzo.getUtenteId());
            stmt.setString(2, indirizzo.getVia());
            stmt.setString(3, indirizzo.getCitta());
            stmt.setString(4, indirizzo.getProvincia());
            stmt.setString(5, indirizzo.getCap());
            stmt.setString(6, indirizzo.getNazione());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        indirizzo.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Recupera un indirizzo per ID
     */
    public IndirizzoUtente doRetrieveById(int id) {
        String sql = "SELECT * FROM indirizzo_utente WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    IndirizzoUtente indirizzo = new IndirizzoUtente();
                    indirizzo.setId(rs.getInt("id"));
                    indirizzo.setUtenteId(rs.getInt("utente_id"));
                    indirizzo.setVia(rs.getString("via"));
                    indirizzo.setCitta(rs.getString("citta"));
                    indirizzo.setProvincia(rs.getString("provincia"));
                    indirizzo.setCap(rs.getString("cap"));
                    indirizzo.setNazione(rs.getString("nazione"));
                    
                    return indirizzo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Recupera tutti gli indirizzi
     */
    public List<IndirizzoUtente> doRetrieveAll() {
        List<IndirizzoUtente> indirizziList = new ArrayList<>();
        String sql = "SELECT * FROM indirizzo_utente ORDER BY utente_id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                IndirizzoUtente indirizzo = new IndirizzoUtente();
                indirizzo.setId(rs.getInt("id"));
                indirizzo.setUtenteId(rs.getInt("utente_id"));
                indirizzo.setVia(rs.getString("via"));
                indirizzo.setCitta(rs.getString("citta"));
                indirizzo.setProvincia(rs.getString("provincia"));
                indirizzo.setCap(rs.getString("cap"));
                indirizzo.setNazione(rs.getString("nazione"));
                
                indirizziList.add(indirizzo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indirizziList;
    }
    
    /**
     * Recupera tutti gli indirizzi di un utente specifico
     */
    public List<IndirizzoUtente> doRetrieveByUserId(int utenteId) {
        List<IndirizzoUtente> indirizziList = new ArrayList<>();
        String sql = "SELECT * FROM indirizzo_utente WHERE utente_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    IndirizzoUtente indirizzo = new IndirizzoUtente();
                    indirizzo.setId(rs.getInt("id"));
                    indirizzo.setUtenteId(rs.getInt("utente_id"));
                    indirizzo.setVia(rs.getString("via"));
                    indirizzo.setCitta(rs.getString("citta"));
                    indirizzo.setProvincia(rs.getString("provincia"));
                    indirizzo.setCap(rs.getString("cap"));
                    indirizzo.setNazione(rs.getString("nazione"));
                    
                    indirizziList.add(indirizzo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indirizziList;
    }
    
    /**
     * Aggiorna un indirizzo esistente
     */
    public boolean doUpdate(IndirizzoUtente indirizzo) {
        String sql = "UPDATE indirizzo_utente SET utente_id = ?, via = ?, citta = ?, provincia = ?, cap = ?, nazione = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, indirizzo.getUtenteId());
            stmt.setString(2, indirizzo.getVia());
            stmt.setString(3, indirizzo.getCitta());
            stmt.setString(4, indirizzo.getProvincia());
            stmt.setString(5, indirizzo.getCap());
            stmt.setString(6, indirizzo.getNazione());
            stmt.setInt(7, indirizzo.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina un indirizzo per ID
     */
    public boolean doDelete(int id) {
        String sql = "DELETE FROM indirizzo_utente WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina tutti gli indirizzi di un utente
     */
    public boolean doDeleteByUserId(int utenteId) {
        String sql = "DELETE FROM indirizzo_utente WHERE utente_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Conta il numero totale di indirizzi
     */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM indirizzo_utente";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Conta gli indirizzi di un utente specifico
     */
    public int countByUserId(int utenteId) {
        String sql = "SELECT COUNT(*) FROM indirizzo_utente WHERE utente_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}