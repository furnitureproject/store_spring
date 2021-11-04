package com.team.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.team.dto.ProductDTO;
import com.team.entity.Product;
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
    public int insertProduct(Product product) {

        pRepository.save(product);

        return 1;
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
        String seq = pRepository.getNextSeqVal();
        // 오늘 시간 값 받기
        Date time = new Date();
        // 받아오는 형태
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 받은값을 today에 넣음
        String today = format.format(time);
        // Long으로 되는지 확인해보기위한 형변환
        Long numtoday = Long.parseLong(today + seq);

        System.out.println(numtoday);
        return numtoday;
    }

    // 전체물품 검색시
    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh(String Title) {
        return sqlFactory.openSession().selectList("Product.select_product_price_high_list", Title);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow(String Title) {
        return sqlFactory.openSession().selectList("Product.select_product_price_low_list", Title);
    }

    // 최신순
    @Override
    public List<Product> selectCodeList(String Title) {
        return pRepository.findByProductTitleIgnoreCaseContainingOrderByProductCodeDesc(Title);
    }

    // 조회수순
    @Override
    public List<Product> selectHitList(String Title) {
        return pRepository.findByProductTitleIgnoreCaseContainingOrderByProductHitDesc(Title);
    }

    // 소분류
    // 최신순
    @Override
    public List<Product> categoryCodeSelect1(long categoryCode) {

        return pRepository.findByCategory_CategoryCodeOrderByProductCodeDesc(categoryCode);
    }

    // 조회수순
    @Override
    public List<Product> categoryHitSelect1(long categoryCode) {
        return pRepository.findByCategory_CategoryCodeOrderByProductHitDesc(categoryCode);
    }

    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh1(Long categoryCode) {
        return sqlFactory.openSession().selectList("select_product_price_high_list1", categoryCode);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow1(Long categoryCode) {
        return sqlFactory.openSession().selectList("select_product_price_row_list1", categoryCode);
    }

    // 중분류

    // 최신순
    @Override
    public List<Product> categoryCodeSelect2(long categoryParent) {
        return pRepository.findByCategory_CategoryParentOrderByProductCodeDesc(categoryParent);
    }

    // 조회수순
    @Override
    public List<Product> categoryHitSelect2(long categoryParent) {
        return pRepository.findByCategory_CategoryParentOrderByProductHitDesc(categoryParent);
    }

    // 가격 높은순
    @Override
    public List<ProductVO> selectPriceHigh2(Long categoryParent) {
        return sqlFactory.openSession().selectList("select_product_price_high_list2", categoryParent);
    }

    // 가격 낮은순
    @Override
    public List<ProductVO> selectPriceLow2(Long categoryParent) {
        return sqlFactory.openSession().selectList("select_product_price_low_list2", categoryParent);

    }

}
