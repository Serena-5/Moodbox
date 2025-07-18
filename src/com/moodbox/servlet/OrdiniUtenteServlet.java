package com.moodbox.servlet;

import com.moodbox.DAO.OrdineDAO;
import com.moodbox.model.Ordine;
import com.moodbox.model.RigaOrdine;
import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/ordini")
public class OrdiniUtenteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private OrdineDAO ordineDAO;

    @Override
    public void init() throws ServletException {
        ordineDAO = new OrdineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utente");

        // Recupera tutti gli ordini dell’utente
        List<Ordine> ordini = ordineDAO.doRetrieveByUtenteId(utente.getId());

        // Mappa per associare ogni ordine alle sue righe
        Map<Integer, List<RigaOrdine>> righeOrdiniMap = new HashMap<>();

        for (Ordine ordine : ordini) {
            List<RigaOrdine> righe = ordineDAO.findRigheByOrdineId(ordine.getId());
            righeOrdiniMap.put(ordine.getId(), righe);
        }

        // Passa i dati alla JSP
        request.setAttribute("ordini", ordini);
        request.setAttribute("righeOrdiniMap", righeOrdiniMap);

        request.getRequestDispatcher("/jsp/ordini.jsp").forward(request, response);
    }
}
