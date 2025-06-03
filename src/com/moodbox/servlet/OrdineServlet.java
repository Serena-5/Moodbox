package com.moodbox.servlet;

import com.moodbox.DAO.OrdineDAO;
import com.moodbox.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/OrdineServlet")
public class OrdineServlet extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Recupera il carrello con CarrelloArticolo (già con quantità)
        Map<Integer, CarrelloArticolo> carrello = (Map<Integer, CarrelloArticolo>) session.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect("carrello.jsp?errore=vuoto");
            return;
        }

        // Recupera info ordine
        int utenteId = (int) session.getAttribute("utenteId"); // Assicurati che sia settato
        String indirizzo = request.getParameter("indirizzoSpedizione");
        String metodoSpedizione = request.getParameter("metodoSpedizione");
        String note = request.getParameter("noteOrdine");

        // Calcola il totale: somma di prezzo * quantità
        BigDecimal totale = BigDecimal.ZERO;
        for (CarrelloArticolo articolo : carrello.values()) {
            BigDecimal prezzo = articolo.getBox().getPrezzo();
            int quantita = articolo.getQuantita();
            totale = totale.add(prezzo.multiply(BigDecimal.valueOf(quantita)));
        }

        BigDecimal costoSpedizione = new BigDecimal("5.00");

        // Crea ordine
        Ordine ordine = new Ordine();
        ordine.setUtenteId(utenteId);
        ordine.setIndirizzoSpedizione(indirizzo);
        ordine.setMetodoSpedizione(metodoSpedizione);
        ordine.setCostoSpedizione(costoSpedizione);
        ordine.setTotale(totale.add(costoSpedizione));
        ordine.setNoteOrdine(note);
        ordine.setStatoOrdine("In elaborazione");
        ordine.setDataOrdine(LocalDateTime.now());

        OrdineDAO ordineDAO = new OrdineDAO();
        boolean salvato = ordineDAO.doSave(ordine);

        if (salvato) {
            session.removeAttribute("carrello");
            response.sendRedirect("confermaOrdine.jsp");
        } else {
            response.sendRedirect("checkout.jsp?errore=salvataggio");
        }
    }
}
