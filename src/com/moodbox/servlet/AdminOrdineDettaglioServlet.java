package com.moodbox.servlet;

import com.moodbox.DAO.OrdineDAO;
import com.moodbox.model.Ordine;
import com.moodbox.model.RigaOrdine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.time.ZoneId;
import java.util.Date;


import java.io.IOException;
import java.util.List;

@WebServlet("/admin/ordine")
public class AdminOrdineDettaglioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null || !idStr.matches("\\d+")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int ordineId = Integer.parseInt(idStr);

        OrdineDAO dao = OrdineDAO.getInstance();
        Ordine ordine = dao.doRetrieveByKey(ordineId);
        if (ordine == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<RigaOrdine> righe = dao.findRigheByOrdineId(ordineId);
        
        if (ordine.getDataOrdine() != null) {
            Date dataOrdineDate = Date.from(ordine.getDataOrdine().atZone(ZoneId.systemDefault()).toInstant());
            req.setAttribute("dataOrdineDate", dataOrdineDate);
        }


        req.setAttribute("ordine", ordine);
        req.setAttribute("righe", righe);
        req.getRequestDispatcher("/jsp/admin/ordine-dettaglio.jsp")
           .forward(req, resp);
    }
    
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            int id = Integer.parseInt(req.getParameter("id"));
            String nuovoStato = req.getParameter("stato");

            boolean ok = OrdineDAO.getInstance().doUpdateStato(id, nuovoStato);
            if (!ok) {
                req.setAttribute("msgErr", "Impossibile aggiornare lo stato.");
            }
            // redirect GET per PRG pattern
            resp.sendRedirect(req.getContextPath() + "/admin/ordine?id=" + id);
        }
    }

