package com.fieldwire.service.image.domain;

public class ImageFile {

    private String name;
    private byte[] data;

    public ImageFile(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getOriginalFilename() {
        return name;
    }

    public byte[] getBytes() {
        return data;
    }
}
