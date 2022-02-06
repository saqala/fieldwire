package com.fieldwire.service.domain;

import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImageFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.fieldwire.service.image.domain.Image.IMAGES_URL;
import static com.fieldwire.service.image.domain.Image.THUMB;


class ImageTest {

    Long id = 0L;
    String name = "fieldware.jpg";
    String path = "/home/storage/images/";

    @Test
    void get_image_url_return_original_image_url_when_type_is_empty() {
        Image image = new Image(id, name, path, "", null);
        Assertions.assertEquals(image.getImageUrl(), IMAGES_URL + path);
    }

    @Test
    void get_image_url_return_thumb_image_url_when_type_is_not_empty() {
        Image image = new Image(id, name, path, "thumb", null);
        Assertions.assertEquals(image.getImageUrl(), IMAGES_URL + path + THUMB);
    }

    @Test
    void get_image_name_return_original_file_name_when_name_is_not_set() {
        String originalName = "originalName";
        ImageFile file = new ImageFile(originalName, null);
        Image image = new Image(id, null, path, null, file);
        Assertions.assertEquals(image.getImageName(), originalName);
    }

    @Test
    void get_image_name_return_name() {
        String originalName = "originalName";
        ImageFile file = new ImageFile(originalName, null);
        Image image = new Image(id, name, path, null, file);
        Assertions.assertEquals(image.getImageName(), name);
    }

    @Test
    void get_directory_extend_dir_with_today_date() {
        String dir = "/storage/images/";
        Image image = new Image(id, name, path, null, null);
        Assertions.assertEquals(image.getDirectory(dir).toString(), dir + LocalDate.now());
    }

    @Test
    void get_thumb_path_return_original_path_plus_thumb() {
        String dir = "/storage/images/";
        Image image = new Image(id, name, path, null, null);
        image.updatePath(dir);
        Assertions.assertNotNull(image.getOriginalPath());
        Assertions.assertEquals(image.getThumbPath().toString(), image.getOriginalPath()+THUMB);
    }

}
