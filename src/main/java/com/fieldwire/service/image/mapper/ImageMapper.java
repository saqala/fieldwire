package com.fieldwire.service.image.mapper;

import com.fieldwire.persistence.entity.ImageEntity;
import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImagePage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface ImageMapper {

    static ImagePage mapToImagePage(Page<ImageEntity> page, String type) {
        return new ImagePage(page.getTotalPages(), (int) page.getTotalElements(), mapToImages(page.getContent(), type));
    }

    static List<Image> mapToImages(List<ImageEntity> entities, String type) {
        return CollectionUtils.emptyIfNull(entities).stream().map(image -> ImageMapper.mapToImage(image, type)).collect(Collectors.toList());
    }

    static Image mapToImage(ImageEntity entity, String type) {
        if (entity == null) {
            return null;
        }
        return new Image(entity.getId(), entity.getName(), entity.getUrlToImage(), type, null);
    }

    static ImageEntity mapToImageEntity(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageEntity(image.getId(), image.getName(), image.getImagePath());
    }


}
