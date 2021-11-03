package com.team.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.team.entity.Product;
import com.team.entity.ProductProjection;
import com.team.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository pRepository;

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
    public List<Product> selectProductByHit() {

        return pRepository.findAllByOrderByProductHitDesc();
    }

    // @Override
    // public List<Product> selectProductByCode() {

    // return pRepository.findAllByOrderByProductCodeDesc();
    // }

    @Override
    public List<ProductProjection> ProductHigh() {
        return pRepository.querySelectPriceProductHigh();
    }

    @Override
    public List<ProductProjection> ProductLow() {
        return pRepository.querySelectPriceProductLow();
    }

    @Override
    public List<Product> categoryTitleSelerct(long categoryCode, String productTitle) {

        return pRepository.findByCategory_CategoryCodeAndProductTitleIgnoreCaseContainingOrderByProductCodeDesc(
                categoryCode, productTitle);
    }

    // @Override
    // public List<Product> category2Select(long code) {
    // return pRepository.category2Select(code);
    // }

    // @Override
    // public List<Product> category1Select(long code) {
    // return pRepository.category1Select(code);
    // }

}
