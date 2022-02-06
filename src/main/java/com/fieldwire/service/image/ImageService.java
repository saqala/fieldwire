package com.fieldwire.service.image;

import com.fieldwire.presentation.dto.ImageDto;
import com.fieldwire.presentation.dto.ImagePageDto;

import java.io.IOException;

public interface ImageService {

    ImagePageDto getPaginatedImages(Integer page, Integer numberOfElements, String search, String sort, String type);

    void deleteImage(Long id);

    void saveImage(ImageDto imageDto) throws IOException;

    void updateImage(ImageDto imageDto);

    byte[] getImage(String path);

}
