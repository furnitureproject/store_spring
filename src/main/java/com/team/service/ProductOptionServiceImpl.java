package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductOption;
import com.team.repository.ProductOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

    @Autowired
    ProductOptionRepository poRepository;

    @Override
    public int insertProductOption(ProductOption productOption) {

        poRepository.save(productOption);

        return 1;
    }

    @Override
    public int updateProductOption(ProductOption productOption) {

        poRepository.save(productOption);

        return 1;
    }

    @Override
    public int deleteProductOption(Long optionCode) {

        poRepository.deleteById(optionCode);

        return 1;
    }

    @Override
    public ProductOption selectProductOptionOne(Long optionCode) {

        Optional<ProductOption> productoption = poRepository.findById(optionCode);

        return productoption.orElse(null);
    }

    @Override
    public List<ProductOption> selectProductOptionAll() {

        return poRepository.findAll();
    }

}
