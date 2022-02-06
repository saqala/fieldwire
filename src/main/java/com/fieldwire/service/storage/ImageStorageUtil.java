package com.fieldwire.service.storage;

import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.exception.ImageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.fieldwire.service.image.exception.ImageException.DELETE_FILE_EXCEPTION;
import static com.fieldwire.service.image.exception.ImageException.RETRIEVE_FILE_EXCEPTION;

public class ImageStorageUtil {


    public static void store(Image originalImage, Path directory) throws IOException {
        Files.createDirectories(directory);
        Files.write(originalImage.getOriginalPath(), originalImage.getFileBytes());
        Files.write(originalImage.getThumbPath(), originalImage.createThumbImageByte());
    }

    public static byte[] retrieve(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException exception) {
            throw new ImageException(RETRIEVE_FILE_EXCEPTION, exception);
        }
    }

    public static void delete(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException exception) {
            throw new ImageException(DELETE_FILE_EXCEPTION, exception);
        }
    }
}
