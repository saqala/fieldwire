package com.fieldwire.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

import com.fieldwire.service.image.domain.ImagePage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ImagePageDto {

  private int totalPages;
  private int totalElements;
  private List<ImageDto> images;

  public static ImagePageDto mapToImagePageDto(ImagePage imagePage) {
    return new ImagePageDto(imagePage.getTotalPages(), imagePage.getTotalElements(), ImageDto.mapToImagesDto(imagePage.getImages()));
  }

}
