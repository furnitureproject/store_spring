package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductSubImage;
import com.team.entity.SubProjection;
import com.team.repository.ProductSubImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSubImageServiceImpl implements ProductSubImageService {

    @Autowired
    ProductSubImageRepository pRepository;

    @Override
    public int insertSubImage(ProductSubImage productSubImage) {
        pRepository.save(productSubImage);
        return 1;
    }

    @Override
    public int insertSubImageList(List<ProductSubImage> list) {
        pRepository.saveAll(list);
        return 1;
    }

    @Override
    public int updateSubImage(ProductSubImage productSubImage) {
        pRepository.save(productSubImage);

        return 1;
    }

    @Override
    public int deleteSubImage(Long SubImgNum) {
        pRepository.deleteById(SubImgNum);

        return 1;
    }

    @Override
    public ProductSubImage selectSubImageOne(Long SubImgNum) {

        Optional<ProductSubImage> productSubImage = pRepository.findById(SubImgNum);
        return productSubImage.orElse(null);
    }

    @Override
    public List<ProductSubImage> selectSubImageAll() {

        return pRepository.findAll();
    }

    @Override
    public List<ProductSubImage> selectByProductCode(Long productCode) {

        return pRepository.findByProduct_ProductCodeOrderBySubImgNumDesc(productCode);
    }

    @Override
    public List<SubProjection> SubImgNumList(Long productCode) {
        return pRepository.findByProduct_ProductCodeOrderBySubImgNumAsc(productCode);
    }

}
