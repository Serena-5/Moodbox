package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.DAO.CarrelloArticoloDAO;
import com.moodbox.DAO.CarrelloDAO;
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

    /* Se servisse il POST per pagare, lo aggiungerai qui */
}
