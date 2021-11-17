package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Category;

import com.team.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository cRepository;

    @Override
    public int insertCategory(Category category) {

        cRepository.save(category);

        return 1;
    }

    @Override
    public int updateCategory(Category category) {

        cRepository.save(category);

        return 1;
    }

    @Override
    public int deleteCategory(Long categoryCode) {

        cRepository.deleteById(categoryCode);

        return 1;
    }

    @Override
    public Category selectCategory(Long categoryCode) {

        Optional<Category> category = cRepository.findById(categoryCode);

        return category.orElse(null);
    }

    @Override
    public List<Category> selectAllCategory() {

        return cRepository.findAll();
    }

    @Override
    public List<Category> selectCategoryList(Long categoryParent) {
        return cRepository.findByCategoryParent(categoryParent);
    }

    @Override
    public Long countByTier(Integer categoryTier) {
        return cRepository.countByCategoryTier(categoryTier);
    }

    @Override
    public Long countByTierParent(Integer categoryTier, Long categoryParent) {

        return cRepository.countByCategoryTierAndCategoryParent(categoryTier, categoryParent);
    }

    @Override
    public List<Category> selectCateTier2(int categoryTier) {
        return cRepository.findByCategoryTierOrderByCategoryCodeAsc(categoryTier);
    }

}
