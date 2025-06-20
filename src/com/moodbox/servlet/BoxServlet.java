// Servlet: BoxServlet.java
package com.moodbox.servlet;

import com.moodbox.DAO.BoxDAO;
import com.moodbox.model.Box;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/box/*")
public class BoxServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private BoxDAO boxDAO;

    @Override
    public void init() throws ServletException {
        boxDAO = new BoxDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Box> boxList = boxDAO.doRetrieveAvailable();
            request.setAttribute("boxList", boxList);
            request.getRequestDispatcher("/WEB-INF/views/box.jsp").forward(request, response);

        } else if (pathInfo.startsWith("/dettaglio/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring("/dettaglio/".length()));
                Box box = boxDAO.doRetrieveByKey(id);
                if (box != null && box.isDisponibile()) {
                    request.setAttribute("box", box);
                    request.getRequestDispatcher("/WEB-INF/views/box-dettaglio.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
