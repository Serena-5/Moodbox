package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.model.Box;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/prodotti/*")
public class ProdottoAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoxDAO boxDAO;

    @Override
    public void init() throws ServletException {
        boxDAO = new BoxDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Controllo accesso admin
        HttpSession session = request.getSession(false);
        Boolean isAdmin = (session != null) ? (Boolean) session.getAttribute("isAdmin") : false;
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?errore=accesso_admin");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Box> prodotti = boxDAO.doRetrieveAll();
                request.setAttribute("prodotti", prodotti);
                request.getRequestDispatcher("/WEB-INF/views/admin/prodotti-lista.jsp").forward(request, response);

            } else if (pathInfo.equals("/aggiungi")) {
                request.setAttribute("prodotto", null);
                request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);

            } else if (pathInfo.startsWith("/modifica/")) {
                int id = Integer.parseInt(pathInfo.substring("/modifica/".length()));
                Box box = boxDAO.doRetrieveByKey(id);
                if (box != null) {
                    request.setAttribute("prodotto", box);
                    request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            } else if (pathInfo.startsWith("/elimina/")) {
                int id = Integer.parseInt(pathInfo.substring("/elimina/".length()));
                boxDAO.doDelete(id);
                response.sendRedirect(request.getContextPath() + "/admin/prodotti");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 Controllo accesso admin
        HttpSession session = request.getSession(false);
        Boolean isAdmin = (session != null) ? (Boolean) session.getAttribute("isAdmin") : false;
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?errore=accesso_admin");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            String prezzoStr = request.getParameter("prezzo");

            if (nome == null || nome.isBlank() || prezzoStr == null || prezzoStr.isBlank()) {
                request.setAttribute("errorMessage", "Tutti i campi obbligatori devono essere compilati.");
                request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);
                return;
            }

            BigDecimal prezzo = new BigDecimal(prezzoStr);

            if (pathInfo.equals("/aggiungi")) {
                Box nuovoBox = new Box(nome, descrizione, prezzo);
                boxDAO.doSave(nuovoBox);

            } else if (pathInfo.startsWith("/modifica/")) {
                int id = Integer.parseInt(pathInfo.substring("/modifica/".length()));
                Box boxModificato = new Box(id, nome, descrizione, prezzo);
                boxDAO.doUpdate(boxModificato);
            }

            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nella gestione del prodotto.");
            request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);
        }
    }
}
