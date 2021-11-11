package com.team.repository;

import java.util.List;

import com.team.entity.ProductSubImage;
import com.team.entity.SubProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSubImageRepository extends JpaRepository<ProductSubImage, Long> {

    List<ProductSubImage> findByProduct_ProductCodeOrderBySubImgNumDesc(Long productCode);

    List<SubProjection> findByProduct_ProductCodeOrderBySubImgNumAsc(Long productCode);

}
