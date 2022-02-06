package com.fieldwire.service.image.domain;

import java.util.List;

public class ImagePage {

    private int totalPages;
    private int totalElements;
    private List<Image> images;

    public ImagePage(int totalPages, int totalElements, List<Image> images) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.images = images;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public List<Image> getImages() {
        return images;
    }
}
