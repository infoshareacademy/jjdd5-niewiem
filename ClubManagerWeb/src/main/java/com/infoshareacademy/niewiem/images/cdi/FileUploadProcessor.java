package com.infoshareacademy.niewiem.images.cdi;

import com.infoshareacademy.niewiem.halls.exceptions.HallImageNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@RequestScoped
public class FileUploadProcessor {

    private static final Logger LOG = LogManager.getLogger(FileUploadProcessor.class);

    private static final String SETTINGS_FILE = "settings.properties";
    public static final String UPLOAD_PATH_IMAGES = "Upload.Path.Images";
    public static final String NO_USER_IMAGE_HAS_BEEN_UPLOADED = "No user image has been uploaded";

    public String getUploadImageFilesPath() throws IOException {

        Properties settings = new Properties();
        settings.load(Thread.currentThread()
                .getContextClassLoader().getResource(SETTINGS_FILE).openStream());
        return settings.getProperty(UPLOAD_PATH_IMAGES);
    }

    public File uploadImageFile(Part filePart) throws IOException, HallImageNotFound {

        Path path = Paths.get(getUploadImageFilesPath());

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if(fileName == null || fileName.isEmpty()){
            throw new HallImageNotFound(NO_USER_IMAGE_HAS_BEEN_UPLOADED);
        }

        File file = new File(getUploadImageFilesPath()+ File.separator + fileName);

        Files.deleteIfExists(file.toPath());

        InputStream fileContent = filePart.getInputStream();

        createDirectory(path);

        Files.copy(fileContent, file.toPath());

        fileContent.close();

        return file;

    }

    private static void createDirectory(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                LOG.warn("Directory not created");
            }
        }

    }



}
