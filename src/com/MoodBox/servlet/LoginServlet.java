package com.MoodBox.servlet;

import com.MoodBox.DAO.UtenteDAO;
import com.MoodBox.model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Mostra la pagina di login
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email e password sono obbligatori");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        try {
            Utente utente = utenteDAO.doRetrieveByCredentials(email, password);
            
            if (utente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);
                session.setAttribute("utenteId", utente.getId());
                session.setAttribute("ruolo", utente.getRuolo());
                
                // Redirect basato sul ruolo
                if ("admin".equals(utente.getRuolo())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } else {
                request.setAttribute("errorMessage", "Credenziali non valide");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore durante il login");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}