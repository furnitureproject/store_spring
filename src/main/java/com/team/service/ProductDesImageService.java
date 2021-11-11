package com.team.service;

import java.util.List;

import com.team.entity.DesProjection;
import com.team.entity.ProductDesImage;

import org.springframework.stereotype.Service;

@Service
public interface ProductDesImageService {

    // 상세 이미지 추가
    // return 1
    public int insertDesImage(ProductDesImage productDesImage);

    // 상세 이미지 다수 추가
    // return 1
    public int insertDesImageList(List<ProductDesImage> list);

    // 상세 이미지 수정
    // return 1
    public int updateDesImage(ProductDesImage productDesImage);

    // 상세 이미지 삭제
    // return 1
    public int deleteDesImage(Long desImgNum);

    // 상세 이미지 1개 조회
    // return ProductDescImage 혹은 null
    public ProductDesImage selectDesImageOne(Long desImgNum);

    // 상세 이미지 전체 조회
    // return List<ProductDescImage>
    public List<ProductDesImage> selectDesImageAll();

    // 상세이미지 제품코드로 조회
    // return List<ProductDescImage>
    // public List<ProductDesImage> selectByProductCode(Long productCode);

    public List<DesProjection> DesImgNumList(Long productCode);
}
