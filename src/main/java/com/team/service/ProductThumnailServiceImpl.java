package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductThumnail;
import com.team.repository.ProductThumnailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductThumnailServiceImpl implements ProductThumnailService{

    @Autowired
    ProductThumnailRepository pRepository;

    @Override
    public int insertThumnail(ProductThumnail productThumnail) {
        
        pRepository.save(productThumnail);

        return 1;
    }

    @Override
    public int updateThumnail(ProductThumnail productThumnail) {
        
        pRepository.save(productThumnail);

        return 1;
    }

    @Override
    public int deleteThumnail(Long thumImgNum) {
        
        pRepository.deleteById(thumImgNum);

        return 1;
    }

    @Override
    public ProductThumnail selectThumnailOne(Long thumImgNum) {
        
        Optional<ProductThumnail> productThumnail = pRepository.findById(thumImgNum);

        return productThumnail.orElse(null);
    }

    @Override
    public List<ProductThumnail> selectThumnailAll() {
        
        return pRepository.findAll();
    }

    @Override
    public List<ProductThumnail> selectByProductCode(Long productCode) {
        
        return pRepository.findByProduct_ProductCodeOrderByThumImgNumDesc(productCode);
    }
    
}
