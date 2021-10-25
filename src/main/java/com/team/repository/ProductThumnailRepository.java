package com.team.repository;

import java.util.List;

import com.team.entity.ProductThumnail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductThumnailRepository extends JpaRepository<ProductThumnail, Long>{
    
    // 작동 확인 바람
    // 제품 코드가 일치하는 값 전체 조회
    List<ProductThumnail> findByProduct_ProductCodeOrderByThumImgNumDesc(Long productCode);

}
