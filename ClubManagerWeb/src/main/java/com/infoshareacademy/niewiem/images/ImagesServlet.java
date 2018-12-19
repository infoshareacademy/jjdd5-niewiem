package com.infoshareacademy.niewiem.images;

import com.infoshareacademy.niewiem.images.cdi.FileUploadProcessor;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImagesServlet extends HttpServlet {

    @Inject
    FileUploadProcessor fileUploadProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String filename = URLDecoder.decode(req.getPathInfo().substring(1), "UTF-8");
        File file = new File(fileUploadProcessor.getUploadImageFilesPath(), filename);

        resp.setHeader("Content-Type", Files.probeContentType(file.toPath()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());

    }
}

