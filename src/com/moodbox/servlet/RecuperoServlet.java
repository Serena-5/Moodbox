package com.moodbox.servlet;

import com.moodbox.DAO.UtenteDAO;
import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RecuperoServlet")
public class RecuperoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String email = request.getParameter("email");
        String message;

        if (email == null || email.trim().isEmpty()) {
            message = "Inserisci un'email valida.";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/jsp/recupero-pass.jsp").forward(request, response);
            return; // IMPORTANTE: fermati qui!
        } else {
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.doRetrieveByEmail(email);

            // Messaggio unico per sicurezza, non distinguiamo se l'email è presente o meno
            message = "Se l'email è registrata, riceverai un link per il recupero della password.";

            // Qui puoi simulare l'invio mail, se vuoi
            if (utente != null) {
                System.out.println("Simulazione invio email a: " + email);
            }

            // Redirect alla login con messaggio
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?recuperoMsg=" + java.net.URLEncoder.encode(message, "UTF-8"));
            return; // IMPORTANTE: fermati qui!
        }
    }
}