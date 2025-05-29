package com.MoodBox.DAO;

import com.MoodBox.model.FAQ;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FAQDAO {
    private Connection connection;
    
    public FAQDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Salva una nuova FAQ nel database
     */
    public boolean doSave(FAQ faq) {
        String sql = "INSERT INTO faq (utente_id, domanda, data_domanda) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, faq.getUtenteId());
            stmt.setString(2, faq.getDomanda());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        faq.setId(generatedKeys.getInt(1));
                        faq.setDataDomanda(LocalDateTime.now());
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
     * Recupera una FAQ per ID
     */
    public FAQ doRetrieveById(int id) {
        String sql = "SELECT * FROM faq WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FAQ faq = new FAQ();
                    faq.setId(rs.getInt("id"));
                    faq.setUtenteId(rs.getInt("utente_id"));
                    faq.setDomanda(rs.getString("domanda"));
                    faq.setRispostaAdmin(rs.getString("risposta_admin"));
                    
                    Timestamp timestamp = rs.getTimestamp("data_domanda");
                    if (timestamp != null) {
                        faq.setDataDomanda(timestamp.toLocalDateTime());
                    }
                    
                    return faq;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Recupera tutte le FAQ
     */
    public List<FAQ> doRetrieveAll() {
        List<FAQ> faqList = new ArrayList<>();
        String sql = "SELECT * FROM faq ORDER BY data_domanda DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FAQ faq = new FAQ();
                faq.setId(rs.getInt("id"));
                faq.setUtenteId(rs.getInt("utente_id"));
                faq.setDomanda(rs.getString("domanda"));
                faq.setRispostaAdmin(rs.getString("risposta_admin"));
                
                Timestamp timestamp = rs.getTimestamp("data_domanda");
                if (timestamp != null) {
                    faq.setDataDomanda(timestamp.toLocalDateTime());
                }
                
                faqList.add(faq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqList;
    }
    
    /**
     * Recupera tutte le FAQ di un utente specifico
     */
    public List<FAQ> doRetrieveByUserId(int utenteId) {
        List<FAQ> faqList = new ArrayList<>();
        String sql = "SELECT * FROM faq WHERE utente_id = ? ORDER BY data_domanda DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utenteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FAQ faq = new FAQ();
                    faq.setId(rs.getInt("id"));
                    faq.setUtenteId(rs.getInt("utente_id"));
                    faq.setDomanda(rs.getString("domanda"));
                    faq.setRispostaAdmin(rs.getString("risposta_admin"));
                    
                    Timestamp timestamp = rs.getTimestamp("data_domanda");
                    if (timestamp != null) {
                        faq.setDataDomanda(timestamp.toLocalDateTime());
                    }
                    
                    faqList.add(faq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqList;
    }
    
    /**
     * Recupera tutte le FAQ senza risposta (per admin)
     */
    public List<FAQ> doRetrieveUnanswered() {
        List<FAQ> faqList = new ArrayList<>();
        String sql = "SELECT * FROM faq WHERE risposta_admin IS NULL OR risposta_admin = '' ORDER BY data_domanda ASC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FAQ faq = new FAQ();
                faq.setId(rs.getInt("id"));
                faq.setUtenteId(rs.getInt("utente_id"));
                faq.setDomanda(rs.getString("domanda"));
                faq.setRispostaAdmin(rs.getString("risposta_admin"));
                
                Timestamp timestamp = rs.getTimestamp("data_domanda");
                if (timestamp != null) {
                    faq.setDataDomanda(timestamp.toLocalDateTime());
                }
                
                faqList.add(faq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqList;
    }
    
    /**
     * Aggiorna una FAQ esistente
     */
    public boolean doUpdate(FAQ faq) {
        String sql = "UPDATE faq SET utente_id = ?, domanda = ?, risposta_admin = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, faq.getUtenteId());
            stmt.setString(2, faq.getDomanda());
            stmt.setString(3, faq.getRispostaAdmin());
            stmt.setInt(4, faq.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Aggiorna solo la risposta dell'admin
     */
    public boolean doUpdateAdminResponse(int id, String rispostaAdmin) {
        String sql = "UPDATE faq SET risposta_admin = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rispostaAdmin);
            stmt.setInt(2, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Elimina una FAQ per ID
     */
    public boolean doDelete(int id) {
        String sql = "DELETE FROM faq WHERE id = ?";
        
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
     * Elimina tutte le FAQ di un utente
     */
    public boolean doDeleteByUserId(int utenteId) {
        String sql = "DELETE FROM faq WHERE utente_id = ?";
        
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
     * Conta il numero totale di FAQ
     */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM faq";
        
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
     * Conta le FAQ senza risposta
     */
    public int countUnanswered() {
        String sql = "SELECT COUNT(*) FROM faq WHERE risposta_admin IS NULL OR risposta_admin = ''";
        
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
}