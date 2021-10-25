package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductDesImage;
import com.team.repository.ProductDesImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDesImageServiceImpl implements ProductDesImageService{

    @Autowired
    ProductDesImageRepository pRepository;

    @Override
    public int insertDesImage(ProductDesImage productDesImage) {
        
        pRepository.save(productDesImage);

        return 1;
    }

    @Override
    public int updateDesImage(ProductDesImage productDesImage) {
        
        pRepository.save(productDesImage);

        return 1;
    }

    @Override
    public int deleteDesImage(Long desImgNum) {
        
        pRepository.deleteById(desImgNum);

        return 1;
    }

    @Override
    public ProductDesImage selectDesImageOne(Long desImgNum) {
        
        Optional<ProductDesImage> productDesImage = pRepository.findById(desImgNum);

        return productDesImage.orElse(null);
    }

    @Override
    public List<ProductDesImage> selectDesImageAll() {
        
        return pRepository.findAll();
    }

    @Override
    public List<ProductDesImage> selectByProductCode(Long productCode) {
        
        return pRepository.findByProduct_ProductCodeOrderByDesImgNumDesc(productCode);
    }
    
}
