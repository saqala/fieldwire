package com.fieldwire.service.storage;


import com.fieldwire.service.image.exception.ImageException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.fieldwire.service.image.domain.Image.THUMB;
import static com.fieldwire.service.image.exception.ImageException.DELETE_FILE_EXCEPTION;
import static com.fieldwire.service.image.exception.ImageException.RETRIEVE_FILE_EXCEPTION;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    @Value("${image.outputfile}")
    private String imageDirPath;

    @Override
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

    private byte[] toByteArray(BufferedImage bi, String type) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, type, baos);
        return baos.toByteArray();
    }

    private BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));

    }

    @Override
    public byte[] retrieve(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException exception) {
            throw new ImageException(RETRIEVE_FILE_EXCEPTION, exception);
        }
    }

    @Override
    public void delete(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException exception) {
            throw new ImageException(DELETE_FILE_EXCEPTION, exception);
        }
    }

}
