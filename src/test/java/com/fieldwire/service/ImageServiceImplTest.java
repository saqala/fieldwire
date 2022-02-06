package com.fieldwire.service;

import com.fieldwire.persistence.entity.ImageEntity;
import com.fieldwire.persistence.repository.ImageRepository;
import com.fieldwire.service.image.ImageService;
import com.fieldwire.service.image.ImageServiceImpl;
import com.fieldwire.service.storage.ImageStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    Long id = 0L;

    @Mock
    ImageRepository imageRepository;
    @Mock
    ImageStorageService imageStorage;

    ImageService imageService;

    @BeforeEach
    void setup() {
        imageRepository = mock(ImageRepository.class);
        imageStorage = mock(ImageStorageService.class);

        doNothing().when(imageStorage).delete(any());
        doNothing().when(imageRepository).deleteById(any());

        imageService = new ImageServiceImpl(imageRepository, imageStorage);
    }

    @Test
    void check_storage_and_db_delete_calls_when_entity_found(){
        when(imageRepository.findById(any())).thenReturn(Optional.of(new ImageEntity()));
        imageService.deleteImage(id);
        //two calls : delete original and thumb image
        verify(imageStorage, times(2)).delete(any());
        verify(imageRepository, times(1)).deleteById(any());
    }


}
