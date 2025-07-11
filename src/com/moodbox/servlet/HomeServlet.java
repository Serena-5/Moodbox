package com.moodbox.servlet;

import com.moodbox.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;

        if (utente != null) {
            String ruolo = utente.getRuolo();

            if ("admin".equals(ruolo)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            } else if ("cliente".equals(ruolo)) {
                request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
                return;
            }
        }

        // Guest
        request.getRequestDispatcher("/jsp/home-guest.jsp").forward(request, response);
    }
}
