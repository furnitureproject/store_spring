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

    @Override
    public List<Product> categorySelerct(long categoryCode) {

        return pRepository.findByCategory_CategoryCodeOrderByProductCodeDesc(categoryCode);
    }

    // 전체물품 검색시
    @Override
    public List<ProductVO> selectPriceHigh(String Title) {
        return sqlFactory.openSession().selectList("Product.select_product_price_high_list", Title);
    }

    @Override
    public List<ProductVO> selectPriceLow(String Title) {
        return sqlFactory.openSession().selectList("Product.select_product_price_low_list", Title);
    }

    @Override
    public List<Product> selectCodeList(String Title) {
        return pRepository.findByProductTitleIgnoreCaseContainingOrderByProductCodeDesc(Title);
    }

    @Override
    public List<Product> selectHitList(String Title) {
        return pRepository.findByProductTitleIgnoreCaseContainingOrderByProductHitDesc(Title);
    }

}
