package com.fieldwire.service.domain;

import com.fieldwire.service.image.domain.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.fieldwire.service.image.domain.Image.THUMB;
import static com.fieldwire.service.storage.ImageStorageServiceImpl.IMAGES_URL;


class ImageTest {

    Long id = 0L;
    String name = "fieldware.jpg";
    String path = "/home/storage/images/";

    @Test
    void get_image_url_return_original_image_url_when_type_is_empty() {
        Image image = new Image(id, name, path, "");
        Assertions.assertEquals(image.getImageUrl(), IMAGES_URL + path);
    }

    @Test
    void get_image_url_return_thumb_image_url_when_type_is_not_empty() {
        Image image = new Image(id, name, path, "thumb");
        Assertions.assertEquals(image.getImageUrl(), IMAGES_URL + path + THUMB);
    }


}
