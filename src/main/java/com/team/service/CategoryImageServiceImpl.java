package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.CateImageProjection;
import com.team.entity.CategoryImage;
import com.team.repository.CategoryImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryImageServiceImpl implements CategoryImageService {

    @Autowired
    CategoryImageRepository cRepository;

    @Override
    public int insertCateImage(CategoryImage categoryImage) {
        cRepository.save(categoryImage);
        return 1;
    }

    @Override
    public int insertCateImageList(List<CategoryImage> list) {
        cRepository.saveAll(list);
        return 1;
    }

    @Override
    public int updateCateImage(CategoryImage categoryImage) {
        cRepository.save(categoryImage);
        return 1;
    }

    @Override
    public int deleteCateImage(Long cateImgNum) {
        cRepository.deleteById(cateImgNum);
        return 1;
    }

    @Override
    public CategoryImage selectCateImageOne(Long cateImgNum) {
        Optional<CategoryImage> categoryImage = cRepository.findById(cateImgNum);
        return categoryImage.orElse(null);
    }

    @Override
    public List<CategoryImage> selectCateImageAll() {
        return cRepository.findAll();
    }

    @Override
    public List<CateImageProjection> CateImgNumList(Long categoryCode) {
        return cRepository.findByCategory_CategoryCodeOrderByCateImgNumAsc(categoryCode);
    }

}
