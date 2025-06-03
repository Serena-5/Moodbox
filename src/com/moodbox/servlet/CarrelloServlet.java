package com.moodbox.servlet;

import com.moodbox.DAO.CarrelloDAO;
import com.moodbox.DAO.CarrelloArticoloDAO;
import com.moodbox.model.Carrello;
import com.moodbox.model.CarrelloArticolo;
import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private CarrelloDAO carrelloDAO;
    private CarrelloArticoloDAO carrelloArticoloDAO;

    @Override
    public void init() throws ServletException {
        carrelloDAO = new CarrelloDAO();
        carrelloArticoloDAO = new CarrelloArticoloDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Carrello carrello = getOrCreateCarrello(session);
        
        if (carrello != null) {
            List<CarrelloArticolo> articoli = carrelloArticoloDAO.doRetrieveByCarrelloId(carrello.getId());
            request.setAttribute("articoli", articoli);
            request.setAttribute("carrello", carrello);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/carrello.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if ("add".equals(action)) {
            aggiungiAlCarrello(request, session);
        } else if ("update".equals(action)) {
            aggiornaQuantita(request, session);
        } else if ("remove".equals(action)) {
            rimuoviDalCarrello(request, session);
        }
        
        response.sendRedirect(request.getContextPath() + "/carrello");
    }

    private void aggiungiAlCarrello(HttpServletRequest request, HttpSession session) {
        try {
            int boxId = Integer.parseInt(request.getParameter("boxId"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            
            Carrello carrello = getOrCreateCarrello(session);
            if (carrello == null) return;
            
            // Verifica se il prodotto è già nel carrello
            CarrelloArticolo esistente = carrelloArticoloDAO.doRetrieveByKey(carrello.getId(), boxId);
            
            if (esistente != null) {
                // Aggiorna la quantità
                esistente.setQuantita(esistente.getQuantita() + quantita);
                carrelloArticoloDAO.doUpdate(esistente);
            } else {
                // Aggiungi nuovo articolo
                CarrelloArticolo nuovo = new CarrelloArticolo();
                nuovo.setCarrelloId(carrello.getId());
                nuovo.setBoxId(boxId);
                nuovo.setQuantita(quantita);
                carrelloArticoloDAO.doSave(nuovo);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void aggiornaQuantita(HttpServletRequest request, HttpSession session) {
        try {
            int boxId = Integer.parseInt(request.getParameter("boxId"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            
            Carrello carrello = getOrCreateCarrello(session);
            if (carrello == null) return;
            
            if (quantita > 0) {
                CarrelloArticolo articolo = carrelloArticoloDAO.doRetrieveByKey(carrello.getId(), boxId);
                if (articolo != null) {
                    articolo.setQuantita(quantita);
                    carrelloArticoloDAO.doUpdate(articolo);
                }
            } else {
                carrelloArticoloDAO.doDelete(carrello.getId(), boxId);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void rimuoviDalCarrello(HttpServletRequest request, HttpSession session) {
        try {
            int boxId = Integer.parseInt(request.getParameter("boxId"));
            
            Carrello carrello = getOrCreateCarrello(session);
            if (carrello != null) {
                carrelloArticoloDAO.doDelete(carrello.getId(), boxId);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private Carrello getOrCreateCarrello(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        String sessionId = session.getId();
        
        // Cerca carrello esistente per session ID
        Carrello carrello = carrelloDAO.doRetrieveBySessionId(sessionId);
        
        if (carrello == null) {
            // Crea nuovo carrello
            carrello = new Carrello();
            carrello.setSessionId(sessionId);
            if (utente != null) {
                carrello.setUtenteId(utente.getId());
            }
            
            if (carrelloDAO.doSave(carrello)) {
                carrello = carrelloDAO.doRetrieveBySessionId(sessionId);
            }
        } else if (utente != null && carrello.getUtenteId() == null) {
            // Associa carrello anonimo all'utente loggato
            carrello.setUtenteId(utente.getId());
            carrelloDAO.doUpdate(carrello);
        }
        
        return carrello;
    }
}