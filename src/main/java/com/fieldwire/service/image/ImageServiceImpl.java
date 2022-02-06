package com.fieldwire.service.image;

import com.fieldwire.persistence.entity.ImageEntity;
import com.fieldwire.persistence.repository.ImageRepository;
import com.fieldwire.presentation.dto.ImageDto;
import com.fieldwire.presentation.dto.ImagePageDto;
import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImagePage;
import com.fieldwire.service.image.mapper.ImageMapper;
import com.fieldwire.service.storage.ImageStorageService;
import liquibase.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

import static com.fieldwire.service.image.domain.Image.THUMB;

@Service
public class ImageServiceImpl implements ImageService {

    public static final String SORT_FIELD = "name";

    ImageRepository imageRepository;

    ImageStorageService imageStorage;

    public ImageServiceImpl(ImageRepository imageRepository, ImageStorageService imageStorage) {
        this.imageRepository = imageRepository;
        this.imageStorage = imageStorage;
    }

    @Override
    public ImagePageDto getPaginatedImages(Integer page, Integer numberOfElements, String search, String sort, String type) {
        Pageable pageable = StringUtil.isEmpty(sort) ? PageRequest.of(page, numberOfElements) : PageRequest.of(page, numberOfElements, Sort.by(Sort.Direction.fromString(sort), SORT_FIELD));
        Page<ImageEntity> imageEntities = StringUtil.isEmpty(search) ? imageRepository.findAll(pageable) : imageRepository.findByNameContaining(search, pageable);
        ImagePage imagePage = ImageMapper.mapToImagePage(imageEntities, type);
        return ImageMapper.mapToImagePageDto(imagePage);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresent(entity -> {
            imageStorage.delete(entity.getUrlToImage());
            imageStorage.delete(entity.getUrlToImage()+THUMB);
            imageRepository.deleteById(id);
        });
    }

    @Override
    public void saveImage(ImageDto imageDto) throws IOException {
        String directory = LocalDate.now().toString();
        Image image = saveImageToStorage(imageDto, directory);
        saveImageInfoToDb(image);
    }

    @Override
    public void updateImage(ImageDto imageDto) {
        imageRepository.findById(imageDto.getId()).ifPresent(entity -> {
            entity.setName(imageDto.getName());
            imageRepository.save(entity);
        });
    }

    private void saveImageInfoToDb(Image image) {
        ImageEntity imageEntity = ImageMapper.mapToImageEntity(image);
        imageRepository.save(imageEntity);
    }

    private Image saveImageToStorage(ImageDto imageDto, String directory) throws IOException {
        MultipartFile image = imageDto.getImageFile();
        String imageName = StringUtil.isEmpty(imageDto.getName()) ? image.getOriginalFilename() : imageDto.getName();
        String imagePath = imageStorage.store(directory, imageDto.getImageFile().getBytes());
        return new Image(null, imageName, imagePath, null);
    }

    @Override
    public byte[] getImage(String path) {
        return imageStorage.retrieve(path);
    }
}
