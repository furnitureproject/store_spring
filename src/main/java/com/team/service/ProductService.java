package com.team.service;

import java.util.List;

import com.team.dto.ProductDTO;
import com.team.entity.Product;
import com.team.vo.ProductVO;

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
    public int deleteProduct(Long productCode);

    // 제품 한개 조회
    // return product 혹은 null
    public Product selectProductOne(Long productCode);

    public ProductDTO selectProductDTOOne(Long productCode);

    // 제품 전체 조회
    // return List<Product>
    public List<Product> selectProductAll();

    // 제품 코드임.
    // return Long 날짜 + 증가하는 SEQ
    public Long codeNext();

    // 조회수 기준 정렬
    // return List<Product>

    // 코드 기준 정렬(최신순 정렬)
    public List<Product> categorySelerct(long categoryCode);

    // 전체 조회시
    // 최신순

    public List<Product> selectCodeList(String Title);

    public List<Product> selectHitList(String Title);

    // 가격 높은순
    public List<ProductVO> selectPriceHigh(String Title);

    // 가격 낮은순
    public List<ProductVO> selectPriceLow(String Title);

}
