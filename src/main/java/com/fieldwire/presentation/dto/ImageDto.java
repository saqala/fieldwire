package com.fieldwire.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImageFile;
import com.fieldwire.service.image.mapper.ImageMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ImageDto {

    private Long id;
    private String name;
    @NotNull
    private MultipartFile imageFile;
    private String imageUrl;

    public static Image to(ImageDto dto) throws IOException {
        if (dto == null) return null;
        return new Image(dto.getId(), dto.getName(), dto.getImageUrl(), null, mapToImageFile(dto.getImageFile()));
    }

    public static List<ImageDto> mapToImagesDto(List<Image> images) {
        return CollectionUtils.emptyIfNull(images).stream().map(ImageDto::mapToImageDto).collect(Collectors.toList());
    }

    public static ImageDto mapToImageDto(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(image.getId(), image.getName(), null, image.getImageUrl());
    }

    public static ImageFile mapToImageFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }
        return new ImageFile(multipartFile.getOriginalFilename(), multipartFile.getBytes());
    }

}
