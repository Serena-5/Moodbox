package com.moodbox.servlet;

import com.moodbox.DAO.OrdineDAO;
import com.moodbox.DAO.RigaOrdineDAO;
import com.moodbox.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/ordine")
public class OrdineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrdineDAO     ordineDAO;
    private RigaOrdineDAO rigaOrdineDAO;

    @Override
    public void init() throws ServletException {
        ordineDAO     = new OrdineDAO();
        rigaOrdineDAO = new RigaOrdineDAO();
    }

    /* ──────────────────────────────────────────────────────────────────────
       Accetta SOLO POST dal form di checkout. Nessun GET.                  */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        /* ─── 1. Controlli preliminari ─────────────────────────────────── */
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer,CarrelloArticolo> carrello =
                (Map<Integer,CarrelloArticolo>) session.getAttribute("carrello");

        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello?errore=vuoto");
            return;
        }

        /* ─── 2. Leggi campi dettagliati del form checkout ─────────────── */
        String via            = request.getParameter("via");
        String civico         = request.getParameter("civico");
        String cap            = request.getParameter("cap");
        String citta          = request.getParameter("citta");
        String provincia      = request.getParameter("provincia");
        String paese          = request.getParameter("paese");

        String metodoSpedizione = request.getParameter("metodoSpedizione");
        String note             = request.getParameter("noteOrdine");

        if (via == null || via.isBlank() ||
            civico == null || civico.isBlank() ||
            cap == null || cap.isBlank() ||
            citta == null || citta.isBlank() ||
            provincia == null || provincia.isBlank() ||
            paese == null || paese.isBlank() ||
            metodoSpedizione == null || metodoSpedizione.isBlank()) {

            response.sendRedirect(request.getContextPath() + "/checkout?errore=dati");
            return;
        }

        /* ─── 3. Calcola totale prodotti & spedizione ──────────────────── */
        BigDecimal totaleProdotti = BigDecimal.ZERO;
        for (CarrelloArticolo art : carrello.values()) {
            BigDecimal sub = art.getBox().getPrezzo()
                               .multiply(BigDecimal.valueOf(art.getQuantita()));
            totaleProdotti = totaleProdotti.add(sub);
        }

        BigDecimal costoSpedizione =
              "Express".equalsIgnoreCase(metodoSpedizione) ? new BigDecimal("9.90")
                                                           : new BigDecimal("5.00");

        BigDecimal totaleOrdine = totaleProdotti.add(costoSpedizione);

        /* ─── 4. Crea e salva l’Ordine ─────────────────────────────────── */
        Utente utente = (Utente) session.getAttribute("utente");

        Ordine ordine = new Ordine();
        ordine.setUtenteId(utente.getId());

        // Nuovi campi indirizzo
        ordine.setVia(via);
        ordine.setCivico(civico);
        ordine.setCap(cap);
        ordine.setCitta(citta);
        ordine.setProvincia(provincia);
        ordine.setPaese(paese);

        ordine.setMetodoSpedizione(metodoSpedizione);
        ordine.setCostoSpedizione(costoSpedizione);
        ordine.setTotale(totaleOrdine);
        ordine.setNoteOrdine(note);
        ordine.setStatoOrdine("In elaborazione");
        ordine.setDataOrdine(LocalDateTime.now());

        int ordineId = ordineDAO.doSave(ordine);
        if (ordineId == -1) {
            response.sendRedirect(request.getContextPath() + "/checkout?errore=salvataggio");
            return;
        }

        /* ─── 5. Salva le righe ordine ─────────────────────────────────── */
        for (CarrelloArticolo art : carrello.values()) {
            RigaOrdine riga = new RigaOrdine();
            riga.setOrdineId(ordineId);
            riga.setBoxId(art.getBoxId());
            riga.setQuantita(art.getQuantita());
            riga.setPrezzoUnitario(art.getBox().getPrezzo());
            rigaOrdineDAO.doSave(riga);
        }

        /* ─── 6. Svuota il carrello e redirect alla pagina conferma ────── */
        session.removeAttribute("carrello");
        session.setAttribute("ordineConfermato", ordine); // per conferma visuale

        response.sendRedirect(request.getContextPath()
                               + "/jsp/confermaOrdine.jsp?id=" + ordineId);
    }

    /* GET non supportato */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                       "Usare POST per confermare l’ordine");
    }
}
