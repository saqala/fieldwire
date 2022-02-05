package com.fieldwire.persistence.repository;

import com.fieldwire.persistence.entity.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<ImageEntity, Long> {

  Page<ImageEntity> findByNameContaining(String name, Pageable pageable);
}
