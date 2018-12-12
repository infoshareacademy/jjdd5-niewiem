package com.infoshareacademy.niewiem.cdi;

import com.infoshareacademy.niewiem.exceptions.HallImageNotFound;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@RequestScoped
public class FileUploadProcessor {

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

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if(fileName == null || fileName.isEmpty()){
            throw new HallImageNotFound(NO_USER_IMAGE_HAS_BEEN_UPLOADED);
        }

        File file = new File(getUploadImageFilesPath() + fileName);

        Files.deleteIfExists(file.toPath());

        InputStream fileContent = filePart.getInputStream();

        Files.copy(fileContent, file.toPath());

        fileContent.close();

        return file;

    }



}
