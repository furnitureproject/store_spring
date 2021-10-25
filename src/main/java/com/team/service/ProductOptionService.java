package com.team.service;

import java.util.List;

import com.team.entity.ProductOption;

import org.springframework.stereotype.Service;

@Service
public interface ProductOptionService{
    
    // 옵션 추가
    // return 1
    public int insertProductOption(ProductOption productOption);

    // 옵션 수정
    // return 1
    public int updateProductOption(ProductOption productOption);

    // 옵션 삭제
    // return 1
    public int deleteProductOption(Long optionCode);

    // 한개 조회
    // return ProductOption
    public ProductOption selectProductOptionOne(Long optionCode);

    // 전부 조회
    // return List<ProductOption>
    public List<ProductOption> selectProductOptionAll();
}
