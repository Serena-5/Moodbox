package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.DAO.CarrelloArticoloDAO;
import com.moodbox.DAO.CarrelloDAO;
import com.moodbox.DAO.OrdineDAO;
import com.moodbox.model.Box;
import com.moodbox.model.Carrello;
import com.moodbox.model.CarrelloArticolo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /* DAO già presenti nel progetto */
    private CarrelloDAO         carrelloDAO;
    private CarrelloArticoloDAO carrelloArticoloDAO;
    private BoxDAO              boxDAO;

    @Override
    public void init() throws ServletException {
        carrelloDAO         = new CarrelloDAO();
        carrelloArticoloDAO = new CarrelloArticoloDAO();
        boxDAO              = new BoxDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        /* ─── 1) Trova il carrello relativo alla sessione ─── */
        Carrello carrello = carrelloDAO.doRetrieveBySessionId(session.getId());
        if (carrello == null) {
            /* Nessun carrello = torna alla pagina carrello (vuoto) */
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        /* ─── 2) Recupera gli articoli del carrello ─── */
        List<CarrelloArticolo> articoli =
                carrelloArticoloDAO.doRetrieveByCarrelloId(carrello.getId());

        if (articoli == null || articoli.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        /* ─── 3) Collega a ogni riga il Box (nome, prezzo, ecc.) e calcola totale ─── */
        BigDecimal totaleProdotti = BigDecimal.ZERO;
        Map<Integer,CarrelloArticolo> mappaCarrello = new LinkedHashMap<>();

        for (CarrelloArticolo riga : articoli) {
            Box box = boxDAO.doRetrieveByKey(riga.getBoxId());
            riga.setBox(box);                     // utile in JSP
            BigDecimal sub = box.getPrezzo()
                                 .multiply(BigDecimal.valueOf(riga.getQuantita()));
            totaleProdotti = totaleProdotti.add(sub);
            mappaCarrello.put(riga.getBoxId(), riga);
        }

        /* ─── 4) Espone dati sia in request che in sessione ─── */
        request.setAttribute("articoli",       articoli);
        request.setAttribute("totaleProdotti", totaleProdotti);
        session.setAttribute("carrello",       mappaCarrello); // usato da OrdineServlet

        /* ─── 5) Forward alla vista ─── */
        request.getRequestDispatcher("/jsp/checkout.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* ========== 1. Recupera carrello dalla sessione ========== */
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer,CarrelloArticolo> carrello =
                (Map<Integer,CarrelloArticolo>) session.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/carrello");
            return;
        }

        /* ========== 2. Recupera utente loggato ========== */
        var utente = (com.moodbox.model.Utente) session.getAttribute("utente");
        if (utente == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        /* ========== 3. Leggi i dati form spedizione/pagamento ========== */
        String via       = req.getParameter("via");
        String civico    = req.getParameter("civico");
        String cap       = req.getParameter("cap");
        String citta     = req.getParameter("citta");
        String provincia = req.getParameter("provincia");
        String paese     = req.getParameter("paese");

        String metodoSped = req.getParameter("metodoSpedizione");
        String metodoPay  = req.getParameter("metodoPagamento");
        String note       = req.getParameter("note");

        // ========== 3b. Leggi dati carta solo se scelto "Carta" ==========
        if ("Carta".equals(metodoPay)) {
            String cardNumber = req.getParameter("cardNumber");
            String cardName   = req.getParameter("cardName");
            String cardExpiry = req.getParameter("cardExpiry");
            String cardCVC    = req.getParameter("cardCVC");
            // Non salvarli!  
            // Se vuoi, puoi aggiungere controlli fittizi o logiche di validazione
        }

        /* ========== 4. Calcola il totale carrello ========== */
        BigDecimal totale = carrello.values().stream()
            .map(r -> r.getBox().getPrezzo()
                       .multiply(BigDecimal.valueOf(r.getQuantita())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* ========== 5. Crea oggetto Ordine ========== */
        com.moodbox.model.Ordine ordine = new com.moodbox.model.Ordine();
        ordine.setUtenteId(utente.getId());
        ordine.setVia(via);
        ordine.setCivico(civico);
        ordine.setCap(cap);
        ordine.setCitta(citta);
        ordine.setProvincia(provincia);
        ordine.setPaese(paese);
        ordine.setMetodoSpedizione(metodoSped);
        ordine.setMetodoPagamento(metodoPay);
        ordine.setStatoOrdine("in attesa");
        ordine.setTotale(totale);
        ordine.setNoteOrdine(note);
        ordine.setDataOrdine(java.time.LocalDateTime.now());

        /* ========== 6. Salva ordine + righe ========== */
        OrdineDAO dao = OrdineDAO.getInstance();
        int ordineId = dao.doSave(ordine);           // INSERT in Ordini

        if (ordineId > 0) {
            // inserisci ogni riga
            for (CarrelloArticolo art : carrello.values()) {
                dao.insertRigaOrdine(
                        ordineId,
                        art.getBoxId(),
                        art.getQuantita(),
                        art.getBox().getPrezzo());
            }

            // 7. Svuota carrello lato DB e sessione
            Carrello carrelloDB = carrelloDAO.doRetrieveBySessionId(session.getId());
            if (carrelloDB != null) {
                carrelloArticoloDAO.doDeleteAllByCarrelloId(carrelloDB.getId());
            }
            
            session.removeAttribute("carrello");
            session.setAttribute("carrelloCount", 0);
            resp.sendRedirect(req.getContextPath() + "/jsp/confermaOrdine.jsp?id=" + ordineId);

        } else {
            req.setAttribute("msgErr", "Errore nel salvataggio dell’ordine.");
            req.getRequestDispatcher("/jsp/checkout.jsp").forward(req, resp);
        }
    }

}