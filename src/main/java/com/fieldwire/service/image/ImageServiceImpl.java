package com.fieldwire.service.image;

import com.fieldwire.persistence.entity.ImageEntity;
import com.fieldwire.persistence.repository.ImageRepository;
import com.fieldwire.service.image.domain.Image;
import com.fieldwire.service.image.domain.ImagePage;
import com.fieldwire.service.image.mapper.ImageMapper;
import com.fieldwire.service.storage.ImageStorageUtil;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.fieldwire.service.image.domain.Image.THUMB;

@Service
public class ImageServiceImpl implements ImageService {

    public static final String SORT_FIELD = "name";

    @Value("${image.outputfile}")
    private String imageDirPath;


    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public ImagePage getPaginated(Integer page, Integer numberOfElements, String search, String sort, String type) {
        Pageable pageable = StringUtil.isEmpty(sort) ? PageRequest.of(page, numberOfElements) : PageRequest.of(page, numberOfElements, Sort.by(Sort.Direction.fromString(sort), SORT_FIELD));
        Page<ImageEntity> imageEntities = StringUtil.isEmpty(search) ? imageRepository.findAll(pageable) : imageRepository.findByNameContaining(search, pageable);
        return ImageMapper.mapToImagePage(imageEntities, type);
    }

    @Override
    public void delete(Long id) {
        imageRepository.findById(id).ifPresent(entity -> {
            ImageStorageUtil.delete(entity.getUrlToImage());
            ImageStorageUtil.delete(entity.getUrlToImage()+THUMB);
            imageRepository.deleteById(id);
        });
    }

    @Override
    public void save(Image image) throws IOException {
        image.updatePath(imageDirPath);
        ImageStorageUtil.store(image, image.getDirectory(imageDirPath));
        imageRepository.save(ImageMapper.mapToImageEntity(image));
    }

    @Override
    public void update(Image image) {
        imageRepository.findById(image.getId()).ifPresent(entity -> {
            entity.setName(image.getName());
            imageRepository.save(entity);
        });
    }

    @Override
    public byte[] get(String path) {
        return ImageStorageUtil.retrieve(path);
    }
}
