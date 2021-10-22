package com.team.service;

import java.util.List;

import com.team.entity.Product;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    
    // 입력받은 제품 정보를 DB에 저장
    // return 1
    public int insertProduct(Product product);

    // 제품 정보 수정
    // return 1
    public int updateProduct(Product product);

    // 제품 정보 삭제, 구현 X 사용 X, updateProduct 사용할것
    public int deleteProduct(Product product);

    // 제품 한개 조회
    // return product 혹은 null
    public Product selectProductOne(Long productCode);

    // 제품 전체 조회
    // return List<Product>
    public List<Product> selectProductAll();
}
