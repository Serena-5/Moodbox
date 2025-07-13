package com.moodbox.servlet;


import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/home")           //  r
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    

   

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* ──── Sessione & utente ─────────────────────────── */
        HttpSession session = req.getSession(false);   // false = non crearla se non c’è
        Utente ut = (session != null) ?
                     (Utente) session.getAttribute("utente") : null;

  

        /* ──── Routing in base al ruolo ──────────────────── */
        if (ut != null) {
            switch (ut.getRuolo()) {
                case "admin"   -> resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                case "cliente" -> req.getRequestDispatcher("/jsp/home.jsp")
                                      .forward(req, resp);
                default        -> req.getRequestDispatcher("/jsp/home-guest.jsp")
                                      .forward(req, resp);
            }
        } else { // guest senza login
            req.getRequestDispatcher("/jsp/home-guest.jsp").forward(req, resp);
        }
    }
}
