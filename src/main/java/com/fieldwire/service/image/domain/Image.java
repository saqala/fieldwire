package com.fieldwire.service.image.domain;

import liquibase.util.StringUtil;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

public class Image {

    public static final String THUMB = "-THUMB";
    public static final String PATH_SEPARATOR = "/";
    public static final int THUMB_SIZE = 100;
    public static final String JPG = "jpg";
    public static final String IMAGES_URL = "/files";

    private Long id;
    private String name;
    private String imagePath;
    private String type;
    private ImageFile file;

    public Image(Long id, String name, String imagePath, String type, ImageFile file) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageUrl() {
        if(imagePath == null ) return null;
        type = StringUtil.isEmpty(type) ? "" : THUMB;
        return IMAGES_URL.concat(imagePath.strip()).concat(type);
    }

    public String getImageName() {
        return StringUtil.isEmpty(name) && file != null ? file.getOriginalFilename() : name;
    }

    public byte[] getFileBytes() throws IOException {
        return file == null ? null : file.getBytes();
    }

    public void updatePath(String imageDirPath) {
        this.imagePath = getDirectory(imageDirPath).toString().concat(PATH_SEPARATOR).concat(UUID.randomUUID().toString());
    }

    public Path getOriginalPath(){
        return this.imagePath == null ? null : Path.of(imagePath);
    }

    public Path getThumbPath(){
        return this.imagePath == null ? null : Path.of(imagePath.concat(THUMB));
    }

    public Path getDirectory(String imageDirPath) {
        return Path.of(StringUtil.trimToEmpty(imageDirPath).concat(LocalDate.now().toString()));
    }

    public byte[] createThumbImageByte() throws IOException {
        BufferedImage bufferedImage = toBufferedImage(getFileBytes());
        BufferedImage thumbImage = Scalr.resize(bufferedImage, THUMB_SIZE);
        return toByteArray(thumbImage);
    }

    private byte[] toByteArray(BufferedImage bi) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, JPG, baos);
        return baos.toByteArray();
    }

    private BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));

    }
}
