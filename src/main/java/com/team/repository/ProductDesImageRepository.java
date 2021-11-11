package com.team.repository;

import java.util.List;
import com.team.entity.DesProjection;
import com.team.entity.ProductDesImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDesImageRepository extends JpaRepository<ProductDesImage, Long> {

    // 작동 확인 바람
    // 제품 코드가 일치하는 값 전체 조회
    List<ProductDesImage> findByProduct_ProductCodeOrderByDesImgNumDesc(Long productCode);

    // 제품 코드가 일치하는 코드값 전체 조회
    List<DesProjection> findByProduct_ProductCodeOrderByDesImgNumAsc(Long productCode);

}
