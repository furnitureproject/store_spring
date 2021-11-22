package com.team.repository;

import java.util.List;

import com.team.entity.CateImageProjection;
import com.team.entity.CategoryImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long> {

    List<CateImageProjection> findByCategory_CategoryCodeOrderByCateImgNumAsc(Long CategoryCode);

}
