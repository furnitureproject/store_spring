package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductOption;
import com.team.repository.ProductOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionServiceImpl implements ProductOptionService{

    @Autowired
    ProductOptionRepository pRepository;

    @Override
    public int insertProductOption(ProductOption productOption) {
        
        pRepository.save(productOption);

        return 1;
    }

    @Override
    public int updateProductOption(ProductOption productOption) {

        pRepository.save(productOption);

        return 1;
    }

    @Override
    public int deleteProductOption(Long optionCode) {

        pRepository.deleteById(optionCode);

        return 1;
    }

    @Override
    public ProductOption selectProductOptionOne(Long optionCode) {
        
        Optional<ProductOption> productoption = pRepository.findById(optionCode);

        return productoption.orElse(null);
    }

    @Override
    public List<ProductOption> selectProductOptionAll() {

        List<ProductOption> productOptions = pRepository.findAll();

        return productOptions;
    }

   
    
}
