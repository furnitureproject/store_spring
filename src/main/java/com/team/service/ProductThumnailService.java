package com.team.service;

import java.util.List;

import com.team.entity.ProductThumnail;

import org.springframework.stereotype.Service;

@Service
public interface ProductThumnailService {
    
    // 제품 썸네일 이미지 추가
    public int insertThumnail(ProductThumnail productThumnail);

    // 제품 썸네일 수정
    public int updateThumnail(ProductThumnail productThumnail);

    // 제품 썸네일 삭제
    public int deleteThumnail(Long thumImgNum);

    // 썸네일넘버로 한개 조회
    public ProductThumnail selectThumnailOne(Long thumImgNum);

    // 썸네일 전체 조회
    public List<ProductThumnail> selectThumnailAll();

    // 썸네일 상품코드로 조회
    public List<ProductThumnail> selectByProductCode(Long productCode);
}
