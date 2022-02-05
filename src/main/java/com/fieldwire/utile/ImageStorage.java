package com.fieldwire.utile;


import liquibase.util.StringUtil;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.fieldwire.service.domain.Image.THUMB;

@Service
public class ImageStorage {

    public static final String JPG = "jpg";
    @Value("${image.outputfile}")
    private String imageDirPath;

    private static final int THUMB_SIZE = 100;
    public static final String IMAGES_URL = "/files";
    public static final String PATH_SEPARATOR = "/";

    public String store(String directory, byte[] originalImage) throws IOException {
        String directoryName = imageDirPath.concat(directory);
        Files.createDirectories(Paths.get(directoryName));
        Path originalImagePath = Paths.get(directoryName + PATH_SEPARATOR + UUID.randomUUID());
        Path thumbImagePath = Paths.get(originalImagePath + THUMB);
        Files.write(originalImagePath, originalImage);
        Files.write(thumbImagePath, createThumbImage(originalImage));
        return originalImagePath.toString();
    }

    private byte[] createThumbImage(byte[] originalImage) throws IOException {
        BufferedImage bufferedImage = toBufferedImage(originalImage);
        BufferedImage thumbImage = Scalr.resize(bufferedImage, THUMB_SIZE);
        return toByteArray(thumbImage, JPG);
    }

    public static byte[] toByteArray(BufferedImage bi, String type) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, type, baos);
        return baos.toByteArray();
    }

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));

    }

    public static byte[] retrieve(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static void delete(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static String getPublicImagesUrl(String imagePath, String type) {
        return IMAGES_URL + imagePath.strip() + type;
    }

}
