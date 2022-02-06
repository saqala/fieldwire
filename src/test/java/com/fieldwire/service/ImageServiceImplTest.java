package com.fieldwire.service;

import com.fieldwire.persistence.repository.ImageRepository;
import com.fieldwire.utile.ImageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

class ImageServiceImplTest {

    @Mock
    ImageRepository imageRepository;
    @Mock
    ImageStorage imageStorage;

    ImageService imageService;
    @BeforeEach
    void setup() {
        imageService = new ImageServiceImpl(imageRepository, imageStorage);
    }


}
