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

    private static final String VIEW_REGISTER = "/jsp/register.jsp"; // ← cartella jsp
    private static final String VIEW_LOGIN    = "/jsp/login.jsp";    // ← cartella jsp

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    /** Mostra il form di registrazione */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
    }

    /** Processa la registrazione */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome            = request.getParameter("nome");
        String cognome         = request.getParameter("cognome");
        String email           = request.getParameter("email");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        boolean newsletter     = "on".equals(request.getParameter("newsletter"));

        // ── Validazione base ────────────────────────────────────────────────
        StringBuilder errors = new StringBuilder();

        if (isBlank(nome))                errors.append("Il nome è obbligatorio. ");
        if (isBlank(cognome))             errors.append("Il cognome è obbligatorio. ");
        if (isBlank(email))               errors.append("L'email è obbligatoria. ");
        else if (!EMAIL_PATTERN.matcher(email).matches())
                                          errors.append("Formato email non valido. ");
        if (password == null || password.length() < 6)
                                          errors.append("La password deve essere di almeno 6 caratteri. ");
        if (!password.equals(confirmPassword))
                                          errors.append("Le password non corrispondono. ");

        if (errors.length() > 0) {
            backToForm(request, response, errors.toString(),
                       nome, cognome, email, newsletter);
            
            System.out.println("===  DEBUG PARAMETRI REGISTRAZIONE  ===");
            System.out.println("nome            = " + nome);
            System.out.println("cognome         = " + cognome);
            System.out.println("email           = " + email);
            System.out.println("password (len)  = " + (password != null ? password.length() : "null"));
            System.out.println("confirmPassword = " + confirmPassword);
            System.out.println("newsletter      = " + newsletter);

            return;
        }

        // ── Salvataggio utente ─────────────────────────────────────────────
        try {
            Utente u = new Utente();
            u.setNome(nome);
            u.setCognome(cognome);
            u.setEmail(email);
            u.setPassword(password);           // (hash in UtenteDAO.doSave)
            u.setRuolo("cliente");
            u.setIscrizioneNewsletter(newsletter);

            boolean salvato = utenteDAO.doSave(u);

            if (salvato) {
                request.setAttribute("successMessage", "Registrazione completata! Ora puoi accedere.");
                request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
            } else {
                backToForm(request, response, "Errore durante la registrazione. Email già esistente?",
                           nome, cognome, email, newsletter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            backToForm(request, response, "Errore interno durante la registrazione.",
                       nome, cognome, email, newsletter);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void backToForm(HttpServletRequest req, HttpServletResponse resp,
                            String errorMsg,
                            String nome, String cognome, String email, boolean newsletter)
            throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMsg);
        req.setAttribute("nome", nome);
        req.setAttribute("cognome", cognome);
        req.setAttribute("email", email);
        req.setAttribute("newsletter", newsletter);

        req.getRequestDispatcher(VIEW_REGISTER).forward(req, resp);
    }
}
