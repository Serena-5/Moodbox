package com.moodbox.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    
	private static final long serialVersionUID = 1L;
	private Path uploadDir;

    @Override
    public void init() throws ServletException {
        String dir = System.getenv().getOrDefault("MOODBOX_UPLOAD_DIR",
                                                  System.getProperty("user.home") + "/moodbox_uploads");
        uploadDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String fileName = req.getPathInfo();
        if (fileName == null || fileName.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Path file = uploadDir.resolve(fileName.substring(1)).normalize();

        // sicurezza: il file deve stare sotto uploadDir
        if (!file.startsWith(uploadDir) || !Files.exists(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = getServletContext().getMimeType(file.toString());
        resp.setContentType(mime != null ? mime : "application/octet-stream");
        Files.copy(file, resp.getOutputStream());
    }
}
