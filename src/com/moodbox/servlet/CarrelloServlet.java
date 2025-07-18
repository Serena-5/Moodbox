package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.DAO.CarrelloArticoloDAO;
import com.moodbox.DAO.CarrelloDAO;
import com.moodbox.model.Carrello;
import com.moodbox.model.CarrelloArticolo;
import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CarrelloDAO carrelloDAO;
    private CarrelloArticoloDAO articoloDAO;
    private BoxDAO boxDAO;

    @Override
    public void init() throws ServletException {
        carrelloDAO = new CarrelloDAO();
        articoloDAO = new CarrelloArticoloDAO();
        boxDAO = new BoxDAO();
    }

    /* --------------------- GESTIONE POST ------------------------ */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Carrello carrello = getOrCreateCarrello(session);

        if ("ajax-add".equals(action)) {
            gestisciAggiuntaAjax(req, resp, carrello, session);
            return;
        }

        try {
            int boxId = Integer.parseInt(req.getParameter("boxId"));

            switch (action) {
                case "add" -> {
                    int quantita = Integer.parseInt(req.getParameter("quantita"));
                    aggiungiAlCarrello(carrello, boxId, quantita);
                }
                case "update" -> {
                    int quantita = Integer.parseInt(req.getParameter("quantita"));
                    aggiornaQuantita(carrello, boxId, quantita);
                }
                case "remove" -> articoloDAO.doDelete(carrello.getId(), boxId);
            }

            aggiornaContatore(session, carrello.getId());
            resp.sendRedirect(req.getContextPath() + "/carrello");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri non validi");
        }
    }

    /* --------------------- GESTIONE GET ------------------------ */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Carrello carrello = getOrCreateCarrello(session);

        if (carrello != null) {
            List<CarrelloArticolo> articoli = articoloDAO.doRetrieveByCarrelloId(carrello.getId());
            BigDecimal totale = BigDecimal.ZERO;
            int count = 0;

            for (CarrelloArticolo a : articoli) {
                a.setBox(boxDAO.doRetrieveByKey(a.getBoxId()));
                if (a.getBox() != null) {
                    BigDecimal sub = a.getBox().getPrezzo().multiply(BigDecimal.valueOf(a.getQuantita()));
                    totale = totale.add(sub);
                    count += a.getQuantita();
                }
            }

            session.setAttribute("carrelloCount", count);
            req.setAttribute("articoli", articoli);
            req.setAttribute("totale", totale);
        }

        req.getRequestDispatcher("/jsp/carrello.jsp").forward(req, resp);
    }

    /* --------------------- METODI DI SUPPORTO ------------------------ */

    private void gestisciAggiuntaAjax(HttpServletRequest req, HttpServletResponse resp, Carrello carrello, HttpSession session)
            throws IOException {
        try {
            int boxId = Integer.parseInt(req.getParameter("boxId"));
            int quantita = Integer.parseInt(req.getParameter("quantita"));
            if (quantita <= 0) quantita = 1;

            aggiungiAlCarrello(carrello, boxId, quantita);

            // Leggi direttamente dal database il nuovo totale
            int count = articoloDAO.totaleQuantita(carrello.getId());
            session.setAttribute("carrelloCount", count); // aggiornamento sessione se serve

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"cartCount\":" + count + "}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel server");
        }
    }

    private void aggiungiAlCarrello(Carrello carrello, int boxId, int quantita) {
        CarrelloArticolo esistente = articoloDAO.doRetrieveByKey(carrello.getId(), boxId);
        if (esistente != null) {
            esistente.setQuantita(esistente.getQuantita() + quantita);
            articoloDAO.doUpdate(esistente);
        } else {
            CarrelloArticolo nuovo = new CarrelloArticolo();
            nuovo.setCarrelloId(carrello.getId());
            nuovo.setBoxId(boxId);
            nuovo.setQuantita(quantita);
            articoloDAO.doSave(nuovo);
        }
    }

    private void aggiornaQuantita(Carrello carrello, int boxId, int quantita) {
        if (quantita > 0) {
            CarrelloArticolo articolo = articoloDAO.doRetrieveByKey(carrello.getId(), boxId);
            if (articolo != null) {
                articolo.setQuantita(quantita);
                articoloDAO.doUpdate(articolo);
            }
        } else {
            articoloDAO.doDelete(carrello.getId(), boxId);
        }
    }

    private void aggiornaContatore(HttpSession session, int carrelloId) {
        int totale = articoloDAO.totaleQuantita(carrelloId);
        session.setAttribute("carrelloCount", totale);
    }

    private Carrello getOrCreateCarrello(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        String sessionId = session.getId();
        Carrello carrello = carrelloDAO.doRetrieveBySessionId(sessionId);

        if (carrello == null) {
            carrello = new Carrello();
            carrello.setSessionId(sessionId);
            if (utente != null) carrello.setUtenteId(utente.getId());

            carrelloDAO.doSave(carrello);
            carrello = carrelloDAO.doRetrieveBySessionId(sessionId);
        }

        if (utente != null && carrello.getUtenteId() == null) {
            carrello.setUtenteId(utente.getId());
            carrelloDAO.doUpdate(carrello);
        }

        return carrello;
    }
}
