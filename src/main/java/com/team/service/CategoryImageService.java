package com.team.service;

import java.util.List;

import com.team.entity.CateImageProjection;
import com.team.entity.CategoryImage;

import org.springframework.stereotype.Service;

@Service
public interface CategoryImageService {

    // 이미지 추가
    // return 1
    public int insertCateImage(CategoryImage categoryImage);

    // 이미지 다수 추가
    // return 1
    public int insertCateImageList(List<CategoryImage> list);

    // 이미지 수정
    // return 1
    public int updateCateImage(CategoryImage categoryImage);

    // 이미지 삭제
    // return 1
    public int deleteCateImage(Long cateImgNum);

    // 이미지 1개 조회
    // return ProductDescImage 혹은 null
    public CategoryImage selectCateImageOne(Long cateImgNum);

    // 이미지 전체 조회
    // return List<ProductDescImage>
    public List<CategoryImage> selectCateImageAll();

    // 이미지 카테고리별 조회
    public List<CateImageProjection> CateImgNumList(Long categoryCode);
}
