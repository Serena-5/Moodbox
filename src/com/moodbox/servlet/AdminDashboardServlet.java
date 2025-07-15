package com.moodbox.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.moodbox.model.Ordine;
import com.moodbox.DAO.OrdineDAO;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // access‑control: solo admin
        String ruolo = (String) req.getSession().getAttribute("ruolo");
        if (!"admin".equals(ruolo)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // parametri facoltativi
        String fromStr   = req.getParameter("from");
        String toStr     = req.getParameter("to");
        String cliente   = req.getParameter("cliente");

        LocalDate from = (fromStr == null || fromStr.isBlank()) ? null : LocalDate.parse(fromStr, FMT);
        LocalDate to   = (toStr   == null || toStr.isBlank())   ? null : LocalDate.parse(toStr,   FMT);

        List<Ordine> ordini = OrdineDAO.getInstance().findByFiltro(from, to, cliente);
        req.setAttribute("ordini", ordini);
        req.getRequestDispatcher("/jsp/admin/dashboard.jsp").forward(req, resp);
    }
}