package com.fieldwire.service.domain;

import com.fieldwire.utile.ImageStorage;
import liquibase.util.StringUtil;

public class Image {

    public static final String THUMB = "-THUMB";
    private Long id;
    private String name;
    private String imagePath;
    private String type;

    public Image(Long id, String name, String imagePath, String type) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
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
        type = StringUtil.isEmpty(type) ? "" : THUMB;
        return ImageStorage.getPublicImagesUrl(imagePath, type);
    }



}
