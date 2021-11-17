package com.team.service;

import java.util.List;

import com.team.entity.Category;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    // 카테고리 추가
    // return 1
    public int insertCategory(Category category);

    // 카테고리 수정
    // return 1
    public int updateCategory(Category category);

    // 카테고리 삭제
    // return 1
    public int deleteCategory(Long categoryCode);

    // 카테고리 1개 조회
    // return category
    public Category selectCategory(Long categoryCode);

    // 카테고리 전체 조회
    // return List<Category>
    public List<Category> selectAllCategory();

    public List<Category> selectCategoryList(Long categoryParent);

    public List<Category> selectCateTier2(int categoryTier);

    Long countByTier(Integer categoryTier);

    Long countByTierParent(Integer categoryTier, Long categoryParent);

}
