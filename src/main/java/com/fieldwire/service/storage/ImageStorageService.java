package com.fieldwire.service.storage;

import java.io.IOException;

public interface ImageStorageService {

    int THUMB_SIZE = 100;
    String IMAGES_URL = "/files";
    String PATH_SEPARATOR = "/";
    String JPG = "jpg";

    String store(String directory, byte[] originalImage) throws IOException;

    byte[] retrieve(String path);

    void delete(String path);

    static String getPublicImagesUrl(String imagePath, String type) {
        return IMAGES_URL + imagePath.strip() + type;
    }
}
