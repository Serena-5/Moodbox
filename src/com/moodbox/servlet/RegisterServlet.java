package com.moodbox.servlet;

import com.moodbox.DAO.UtenteDAO;
import com.moodbox.model.Utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;
	private UtenteDAO utenteDAO;
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        boolean newsletter = "on".equals(request.getParameter("newsletter"));

        // Validazione
        StringBuilder errors = new StringBuilder();
        
        if (nome == null || nome.trim().isEmpty()) {
            errors.append("Il nome è obbligatorio. ");
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            errors.append("Il cognome è obbligatorio. ");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("L'email è obbligatoria. ");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("Formato email non valido. ");
        }
        if (password == null || password.length() < 6) {
            errors.append("La password deve essere di almeno 6 caratteri. ");
        }
        if (!password.equals(confirmPassword)) {
            errors.append("Le password non corrispondono. ");
        }

        if (errors.length() > 0) {
            request.setAttribute("errorMessage", errors.toString());
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("email", email);
            request.setAttribute("newsletter", newsletter);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            Utente nuovoUtente = new Utente();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome);
            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(password); // In produzione, hashare la password!
            nuovoUtente.setRuolo("cliente");
            nuovoUtente.setIscrizioneNewsletter(newsletter);

            boolean salvato = utenteDAO.doSave(nuovoUtente);
            
            if (salvato) {
                request.setAttribute("successMessage", "Registrazione completata con successo!");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Errore durante la registrazione. Email già esistente?");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore durante la registrazione");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}