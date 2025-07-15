package com.moodbox.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@WebFilter(urlPatterns = {"/admin/*","/jsp/admin/*", "/checkout/*", "/profile/*"})
public class FiltroAccessoRuoli implements Filter {

    private static final Set<String> RUOLI_LOGGATI = Set.of("cliente", "admin");
    private static final DateTimeFormatter LOG_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 1. Forza HTTPS solo in produzione
        if (!req.isSecure() && !"production".equals(System.getenv("MODE"))) {
            // In dev non forziamo HTTPS
            // In produzione (MODE=production) lo facciamo
        } else if (!req.isSecure()) {
            String query = req.getQueryString();
            String secureUrl = "https://" + req.getServerName()
                             + req.getRequestURI()
                             + (query != null ? "?" + query : "");
            res.sendRedirect(secureUrl);
            return;
        }

        // 2. Recupera il ruolo dell'utente dalla sessione
        HttpSession session = req.getSession(false);
        String ruolo = null;
        if (session != null) {
            Object utenteObj = session.getAttribute("utente");
            if (utenteObj instanceof com.moodbox.model.Utente u) {
                ruolo = u.getRuolo(); // il tuo Utente deve avere getRuolo()
            }
        }

        String path = req.getServletPath(); // path relativo (es: /admin/dashboard)

        // 3. Accesso per admin
        if (path.startsWith("/admin")) {
            if (!"admin".equals(ruolo)) {
                negaAccesso(req, res);
                return;
            }
        }

     // 4. Accesso per utenti loggati (admin o cliente)
        if (path.startsWith("/checkout") || path.startsWith("/profile")) {
            if (ruolo == null || !RUOLI_LOGGATI.contains(ruolo)) {
                negaAccesso(req, res);
                return;
            }
        }


        // 5. Tutto ok → continua
        chain.doFilter(request, response);
    }

    private void negaAccesso(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.printf("[%s] ACCESSO NEGATO a %s%n",
                LocalTime.now().format(LOG_FMT), req.getRequestURI());
        res.sendRedirect(req.getContextPath() + "/login?error=accesso-negato");
    }
}
