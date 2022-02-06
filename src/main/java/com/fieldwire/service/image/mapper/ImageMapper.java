package com.fieldwire.service.image.mapper;

import com.fieldwire.persistence.entity.ImageEntity;
import com.fieldwire.presentation.dto.ImageDto;
import com.fieldwire.presentation.dto.ImagePageDto;
import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImagePage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ImageMapper {

    public static ImagePageDto mapToImagePageDto(ImagePage imagePage) {
        return new ImagePageDto(imagePage.getTotalPages(), imagePage.getTotalElements(), mapToImagesDto(imagePage.getImages()));
    }

    public static List<ImageDto> mapToImagesDto(List<Image> images) {
        return CollectionUtils.emptyIfNull(images).stream().map(ImageMapper::mapToImageDto).collect(Collectors.toList());
    }

    public static ImageDto mapToImageDto(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(image.getId(), image.getName(), null, image.getImageUrl());
    }


    public static ImagePage mapToImagePage(Page<ImageEntity> page, String type) {
        return new ImagePage(page.getTotalPages(), (int) page.getTotalElements(), mapToImages(page.getContent(), type));
    }


    public static List<Image> mapToImages(List<ImageEntity> entities, String type) {
        return CollectionUtils.emptyIfNull(entities).stream().map(image -> ImageMapper.mapToImage(image, type)).collect(Collectors.toList());
    }


    private static Image mapToImage(ImageEntity entity, String type) {
        if (entity == null) {
            return null;
        }
        return new Image(entity.getId(), entity.getName(), entity.getUrlToImage(), type);
    }

    public static ImageEntity mapToImageEntity(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageEntity(image.getId(), image.getName(), image.getImagePath());
    }

}
