package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Product;
import com.team.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl implements ProductService{

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
    public int deleteProduct(Product product) {
        
        pRepository.deleteById(product.getProductCode());

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
    
}
