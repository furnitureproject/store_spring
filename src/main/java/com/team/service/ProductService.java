package com.team.service;

import java.util.List;
import java.util.Map;

import com.team.dto.ProductDTO;
import com.team.entity.Product;
import com.team.entity.ProductProjection;
import com.team.vo.ProductVO;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    // 입력받은 제품 정보를 DB에 저장
    // return 1
    public Product insertProduct(Product product);

    // 제품 정보 수정
    // return 1
    public int updateProduct(Product product);

    // 제품 정보 삭제, 구현 X 사용 X, updateProduct 사용할것
    public int deleteProduct(Long productCode);

    // 제품 한개 조회
    // return product 혹은 null
    public Product selectProductOne(Long productCode);

    public ProductProjection selectProductProjection(Long productCode);

    public ProductDTO selectProductDTOOne(Long productCode);

    // 제품 전체 조회
    // return List<Product>
    public List<Product> selectProductAll();

    // 제품 코드임.
    // return Long 날짜 + 증가하는 SEQ
    public Long codeNext();

    // 코드 기준 정렬(최신순 정렬)

    // 전체 조회시
    // 최신 등록순
    public List<ProductVO> selectCodeList(Map<String, Object> map);

    // 조회수 높은순
    public List<ProductVO> selectHitList(Map<String, Object> map);

    // 가격 높은순
    public List<ProductVO> selectPriceHigh(Map<String, Object> map);

    // 가격 낮은순
    public List<ProductVO> selectPriceLow(Map<String, Object> map);

    // 총 개수
    long countByProduct1(String Title);

    // 소분류
    // 최신 등록순
    public List<ProductVO> categoryCodeSelect1(Map<String, Object> map);

    // 조회수 높은순
    public List<ProductVO> categoryHitSelect1(Map<String, Object> map);

    // 가격 높은순
    public List<ProductVO> selectPriceHigh1(Map<String, Object> map);

    // 가격 낮은순
    public List<ProductVO> selectPriceLow1(Map<String, Object> map);

    // 총 개수
    long countByProduct2(Long categoryCode);

    // 중분류
    // 최신 등록순
    public List<ProductVO> categoryCodeSelect2(Map<String, Object> map);

    // 조회수 높은순
    public List<ProductVO> categoryHitSelect2(Map<String, Object> map);

    // 가격 높은순
    public List<ProductVO> selectPriceHigh2(Map<String, Object> map);

    // 가격 낮은순
    public List<ProductVO> selectPriceLow2(Map<String, Object> map);

    // 총 개수
    long countByProduct3(Long categoryParent);

}
