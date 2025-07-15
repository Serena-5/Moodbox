package com.moodbox.servlet;

import com.moodbox.DAO.CatalogItemDAO;
import com.moodbox.model.CatalogItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)           // 5 MB
@WebServlet("/admin/catalogo")
public class AdminCatalogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CatalogItemDAO dao = new CatalogItemDAO();

    /* ------------------------------ GET ------------------------------ */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new" ->
                    req.getRequestDispatcher("/jsp/admin/catalog-form.jsp").forward(req, resp);

                case "edit" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("item", dao.doRetrieveById(id));
                    req.getRequestDispatcher("/jsp/admin/catalog-form.jsp").forward(req, resp);
                }

                default -> {
                    req.setAttribute("items", dao.doRetrieveAll());
                    req.getRequestDispatcher("/jsp/admin/catalog-list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException ex) {
            throw new ServletException("Errore DB", ex);
        }
    }

    /* ------------------------------ POST ----------------------------- */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        /* ------ toggle disponibilità ------ */
        if ("toggle".equals(req.getParameter("action"))) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                CatalogItem cur = dao.doRetrieveById(id);
                if (cur != null) dao.setDisponibile(id, !cur.isDisponibile());
                resp.sendRedirect(req.getContextPath() + "/admin/catalogo");
                return;
            } catch (SQLException ex) {
                throw new ServletException("Errore DB toggle", ex);
            }
        }

        /* ------ insert / update ------ */
        String idParam       = req.getParameter("id");
        String nome          = req.getParameter("nome");
        String descrizione   = req.getParameter("descrizione");
        String prezzoParam   = req.getParameter("prezzo");
        String disponibileCb = req.getParameter("disponibile");
        String immagineOld   = req.getParameter("immagineOld");

        if (nome == null || nome.isBlank() ||
            prezzoParam == null || prezzoParam.isBlank()) {
            req.setAttribute("error", "Nome e prezzo sono obbligatori.");
            req.getRequestDispatcher("/jsp/admin/catalog-form.jsp").forward(req, resp);
            return;
        }

        /* ---- upload facoltativo ---- */
        Part part = req.getPart("fileImg");
        String fileName = null;

        if (part != null && part.getSize() > 0) {
            String ext = Paths.get(part.getSubmittedFileName())
                              .getFileName().toString()
                              .replaceAll("^.+(\\.[a-zA-Z0-9]+)$", "$1")
                              .toLowerCase();
            fileName = "box_" + System.currentTimeMillis() + ext;

            String baseDir = System.getenv().getOrDefault(
                    "MOODBOX_UPLOAD_DIR",
                    System.getProperty("user.home") + "/moodbox_uploads");

            Path uploadDir = Paths.get(baseDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);
            part.write(uploadDir.resolve(fileName).toString());
        }

        /* ---- bean ---- */
        CatalogItem box = new CatalogItem();
        box.setNome(nome.trim());
        box.setDescrizione(descrizione != null ? descrizione.trim() : "");
        box.setPrezzo(new BigDecimal(prezzoParam));
        box.setDisponibile(disponibileCb != null);
        box.setDataCreazione(LocalDateTime.now());
        box.setImmagine(fileName != null ? fileName
                                         : (immagineOld != null ? immagineOld.trim() : ""));

        try {
            if (idParam == null || idParam.isBlank()) {
                dao.save(box);                     // INSERT
            } else {
                box.setId(Integer.parseInt(idParam));
                dao.update(box);                   // UPDATE
            }
            resp.sendRedirect(req.getContextPath() + "/admin/catalogo");
        } catch (SQLException ex) {
            throw new ServletException("Errore DB", ex);
        }
    }
}
