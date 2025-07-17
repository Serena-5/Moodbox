package com.moodbox.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/images_uploaded/*")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Path uploadDir;

    @Override
    public void init() throws ServletException {
        String dir = System.getenv().getOrDefault("MOODBOX_UPLOAD_DIR",
            System.getProperty("user.home") + "/moodbox_uploads");

        uploadDir = Paths.get(dir).toAbsolutePath().normalize();
        System.out.println(">> Upload dir inizializzata in: " + uploadDir);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String fileName = req.getPathInfo();
        System.out.println(">> Richiesta immagine: " + fileName);

        if (fileName == null || fileName.equals("/")) {
            System.out.println(">> File mancante nella richiesta");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Path file = uploadDir.resolve(fileName.substring(1)).normalize();
        System.out.println(">> Path assoluto risolto: " + file);

        if (!file.startsWith(uploadDir)) {
            System.out.println(">> Tentativo di accesso non autorizzato: " + file);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (!Files.exists(file)) {
            System.out.println(">> File NON trovato: " + file);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = getServletContext().getMimeType(file.toString());
        System.out.println(">> MIME: " + mime);

        resp.setContentType(mime != null ? mime : "application/octet-stream");
        Files.copy(file, resp.getOutputStream());
        System.out.println(">> Immagine servita con successo.");
    }
}
