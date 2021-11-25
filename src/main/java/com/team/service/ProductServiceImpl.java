package com.team.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.dto.ProductDTO;
import com.team.entity.Product;
import com.team.entity.ProductProjection;
import com.team.repository.ProductRepository;
import com.team.vo.ProductVO;

import org.apache.ibatis.session.SqlSessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository pRepository;

    @Autowired
    SqlSessionFactory sqlFactory;

    @Override
    public Product insertProduct(Product product) {
        return pRepository.save(product);
    }

    @Override
    public int updateProduct(Product product) {

        pRepository.save(product);

        return 1;
    }

    @Override
    public int deleteProduct(Long productCode) {

        pRepository.deleteById(productCode);

        return 1;
    }

    @Override
    public Product selectProductOne(Long productCode) {

        Optional<Product> product = pRepository.findById(productCode);

        return product.orElse(null);
    }

    @Override
    public ProductProjection selectProductProjection(Long productCode) {

        return pRepository.findByProductCode(productCode);
    }

    @Override
    public ProductDTO selectProductDTOOne(Long productCode) {
        return sqlFactory.openSession().selectOne("select_product", productCode);

    }

    @Override
    public List<Product> selectProductAll() {

        return pRepository.findAll();
    }

    @Override
    public Long codeNext() {
        // 시퀀스 값 받기
        Long seq = pRepository.getNextSeqVal();

        String seq1 = String.format("%04d", seq);
        // 오늘 시간 값 받기
        Date time = new Date();
        // 받아오는 형태
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 받은값을 today에 넣음
        String today = format.format(time);

        // Long으로 되는지 확인해보기위한 형변환
        Long numtoday = Long.parseLong(today + seq1);
        System.out.println(numtoday);
        return numtoday;
    }

    // 전체물품 검색시
    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("Product.select_product_price_high_list", map);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("Product.select_product_price_low_list", map);
    }

    // 최신순
    @Override
    public List<ProductVO> selectCodeList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_code_list", map);
    }

    // 조회수순
    @Override
    public List<ProductVO> selectHitList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_Hit_list", map);
    }

    // 총개수
    @Override
    public long countByProduct1(String Title) {
        return pRepository.countByProductTitleIgnoreCaseContaining(Title);
    }

    // 소분류
    // 최신순
    @Override
    public List<ProductVO> categoryCodeSelect1(Map<String, Object> map) {

        return sqlFactory.openSession().selectList("select_product_code_list1", map);
    }

    // 조회수순
    @Override
    public List<ProductVO> categoryHitSelect1(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_hit_list1", map);
    }

    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh1(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_price_high_list1", map);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow1(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_price_row_list1", map);
    }

    // 총개수
    @Override
    public long countByProduct2(Long categoryCode) {
        return pRepository.countByCategory_CategoryCode(categoryCode);
    }

    // 중분류

    // 최신순
    @Override
    public List<ProductVO> categoryCodeSelect2(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_code_list2", map);
    }

    // 조회수순
    @Override
    public List<ProductVO> categoryHitSelect2(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_hit_list2", map);
    }

    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh2(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_price_high_list2", map);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow2(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_price_low_list2", map);

    }

    // 총개수
    @Override
    public long countByProduct3(Long categoryParent) {
        return pRepository.countBycategory_CategoryParent(categoryParent);
    }

}
