package com.moodbox.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/checkout") // Questo è l'URL usato nel form (es: /MoodBox/checkout)
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verifica che l'utente sia loggato
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Inoltra alla pagina JSP del checkout
        request.getRequestDispatcher("/jsp/checkout.jsp")
               .forward(request, response);
    }
}
