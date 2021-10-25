package com.team.repository;

import java.util.List;

import com.team.entity.OptionImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionImageRepository extends JpaRepository<OptionImage, Long>{
    
    // 작동 확인 바람
    // 제품 옵션코드로 조회, 옵션이미지 번호 기준 내림차순 정렬
    List<OptionImage> findByProductOption_OptionCodeOrderByOptionImgNumDesc(Long optionCode);

}
