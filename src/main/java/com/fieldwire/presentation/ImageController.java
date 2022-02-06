package com.fieldwire.presentation;


import com.fieldwire.presentation.dto.ImageDto;
import com.fieldwire.presentation.dto.ImagePageDto;
import com.fieldwire.service.image.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.fieldwire.service.image.domain.Image.IMAGES_URL;

@RestController
public class ImageController {

    public static final Logger logger = Logger.getLogger(ImageController.class.getName());

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public ResponseEntity<ImagePageDto> getImages(@RequestParam("page") Integer page, @RequestParam("elements") Integer elements, @RequestParam(value = "search", required = false) String search,
                                                  @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "type", required = false) String type)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ImagePageDto.mapToImagePageDto(imageService.getPaginated(page, elements, search, sort, type)));
    }

    @PostMapping(value = "/images", consumes = {"multipart/form-data"})
    public ResponseEntity saveImages(@ModelAttribute ImageDto imageDto) {
        try {
            imageService.save(ImageDto.to(imageDto));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception exception) {
            logger.log(Level.WARNING, exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PatchMapping(value = "/images")
    public ResponseEntity updateImage(@RequestBody ImageDto imageDto) throws IOException {
        imageService.update(ImageDto.to(imageDto));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/images/{id}")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(IMAGES_URL + "/**")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) {

        String path = request.getRequestURI().split(request.getContextPath() + IMAGES_URL)[1];
        byte[] imageBytes = imageService.get(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
