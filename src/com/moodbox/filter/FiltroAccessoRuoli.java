package com.moodbox.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter() 
public class FiltroAccessoRuoli implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Forza HTTPS se non sicura
        if (!req.isSecure()) {
            String secureUrl = "https://" + req.getServerName() + req.getContextPath() + req.getRequestURI();
            res.sendRedirect(secureUrl);
            return;
        }

        //Controllo accesso per ruolo
        String path = req.getRequestURI().substring(req.getContextPath().length());
        HttpSession session = req.getSession(false);

        String ruolo = null;
        if (session != null && session.getAttribute("utente") != null) {
            ruolo = ((com.moodbox.model.Utente) session.getAttribute("utente")).getRuolo();
        }

        // Percorsi riservati all'admin
        if (path.startsWith("/admin")) {
            if (!"admin".equals(ruolo)) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        // Checkout solo per utenti registrati
        if (path.startsWith("/checkout")) {
            if (!"cliente".equals(ruolo) && !"admin".equals(ruolo)) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        // Accesso al profilo personale
        if (path.startsWith("/profile")) {
            if (!"cliente".equals(ruolo) && !"admin".equals(ruolo)) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
