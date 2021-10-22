package com.team.service;

import java.util.List;

import com.team.entity.Category1;
import com.team.entity.Category2;
import com.team.entity.Category3;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    
    //  카테고리 추가
    // return 1
    public int insertCategory1(Category1 category1);

    public int insertCategory2(Category2 category2);
    
    public int insertCategory3(Category3 category3);

    // 카테고리 수정
    // return 1
    public int updateCategory1(Category1 category1);

    public int updateCategory2(Category2 category2);
    
    public int updateCategory3(Category3 category3);

    // 카테고리 삭제
    // return 1
    public int deleteCategory1(Long category1Code);

    public int deleteCategory2(Long category2Code);

    public int deleteCategory3(Long category3Code);

    // 카테고리 1개 조회
    // return category 1, 2, 3 혹은 null
    public Category1 selectCategory1(Long category1Code);

    public Category2 selectCategory2(Long category2Code);

    public Category3 selectCategory3(Long category3Code);

    // 카테고리 전체 조회
    // return List<Category1, 2 , 3>
    public List<Category1> selectAllCategory1();

    public List<Category2> selectAllCategory2();
    
    public List<Category3> selectAllCategory3();
}
