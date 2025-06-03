package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.model.Box;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/prodotti/*")
public class ProdottoAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoxDAO BoxDAO;

    @Override
    public void init() throws ServletException {
        BoxDAO = new BoxDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Box> Box = BoxDAO.doRetrieveAll();
            request.setAttribute("prodotti", Box);
            request.getRequestDispatcher("/WEB-INF/views/admin/prodotti-lista.jsp").forward(request, response);

        } else if (pathInfo.equals("/aggiungi")) {
            request.setAttribute("prodotto", null);
            request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);

        } else if (pathInfo.startsWith("/modifica/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring("/modifica/".length()));
                Box Box = BoxDAO.doRetrieveByKey(id);
                if (Box != null) {
                    request.setAttribute("prodotto", Box);
                    request.getRequestDispatcher("/WEB-INF/views/admin/prodotto-form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            BigDecimal prezzo = new BigDecimal(request.getParameter("prezzo"));

            if (pathInfo.equals("/aggiungi")) {
                Box Box = new Box(nome, descrizione, prezzo);
                BoxDAO.doSave(Box);

            } else if (pathInfo.startsWith("/modifica/")) {
                int id = Integer.parseInt(pathInfo.substring("/modifica/".length()));
                Box Box = new Box(id, nome, descrizione, prezzo);
                BoxDAO.doUpdate(Box);

            } else if (pathInfo.startsWith("/elimina/")) {
                int id = Integer.parseInt(pathInfo.substring("/elimina/".length()));
                BoxDAO.doDelete(id);
            }

            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nella gestione del prodotto.");
            request.getRequestDispatcher("/WEB-INF/views/admin/prodotti-lista.jsp").forward(request, response);
        }
    }
}
