package com.team.service;

import java.util.List;

import com.team.entity.ProductSubImage;
import com.team.entity.SubProjection;

import org.springframework.stereotype.Service;

@Service
public interface ProductSubImageService {

    // 상세 이미지 추가
    // return 1
    public int insertSubImage(ProductSubImage productSubImage);

    // 상세 이미지 다수 추가
    // return 1
    public int insertSubImageList(List<ProductSubImage> list);

    // 상세 이미지 수정
    // return 1
    public int updateSubImage(ProductSubImage productDesImage);

    // 상세 이미지 삭제
    // return 1
    public int deleteSubImage(Long SubImgNum);

    // 상세 이미지 1개 조회
    // return ProductDescImage 혹은 null
    public ProductSubImage selectSubImageOne(Long SubImgNum);

    // 상세 이미지 전체 조회
    // return List<ProductDescImage>
    public List<ProductSubImage> selectSubImageAll();

    public List<ProductSubImage> selectByProductCode(Long productCode);

    public List<SubProjection> SubImgNumList(Long productCode);

}
