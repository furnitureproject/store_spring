package com.team.repository;

import java.util.List;

import com.team.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Long countByCategoryTier(Integer categoryTier);

    Long countByCategoryTierAndCategoryParent(Integer categoryTier, Long categoryParent);

    List<Category> findByCategoryParent(Long categoryParent);

    List<Category> findByCategoryTierOrderByCategoryCodeAsc(Integer categoryTier);

}