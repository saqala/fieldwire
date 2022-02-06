package com.fieldwire.service.image;

import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImagePage;

import java.io.IOException;

public interface ImageService {

    ImagePage getPaginated(Integer page, Integer numberOfElements, String search, String sort, String type);

    void delete(Long id);

    void save(Image image) throws IOException;

    void update(Image image);

    byte[] get(String path);

}
