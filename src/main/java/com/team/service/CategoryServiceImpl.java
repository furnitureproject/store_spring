package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Category1;
import com.team.entity.Category2;
import com.team.entity.Category3;
import com.team.repository.Category1Repository;
import com.team.repository.Category2Repository;
import com.team.repository.Category3Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    Category1Repository c1Repository;
    @Autowired
    Category2Repository c2Repository;
    @Autowired
    Category3Repository c3Repository;

    @Override
    public int insertCategory1(Category1 category1) {
        
        c1Repository.save(category1);
        
        return 1;
    }

    @Override
    public int insertCategory2(Category2 category2) {
        
        c2Repository.save(category2);
        
        return 1;
    }

    @Override
    public int insertCategory3(Category3 category3) {
        
        c3Repository.save(category3);
        
        return 1;
    }

    @Override
    public int updateCategory1(Category1 category1) {
        
        c1Repository.save(category1);
        
        return 1;
    }

    @Override
    public int updateCategory2(Category2 category2) {
        
        c2Repository.save(category2);
        
        return 1;
    }

    @Override
    public int updateCategory3(Category3 category3) {
        
        c3Repository.save(category3);
        
        return 1;
    }

    @Override
    public int deleteCategory1(Long category1Code) {
        
        c1Repository.deleteById(category1Code);

        return 1;
    }

    @Override
    public int deleteCategory2(Long category2Code) {
        
        c2Repository.deleteById(category2Code);

        return 1;
    }

    @Override
    public int deleteCategory3(Long category3Code) {
        
        c3Repository.deleteById(category3Code);

        return 1;
    }

    @Override
    public Category1 selectCategory1(Long category1Code) {
        
        Optional<Category1> category1 = c1Repository.findById(category1Code);

        return category1.orElse(null);
    }

    @Override
    public Category2 selectCategory2(Long category2Code) {
        
        Optional<Category2> category2 = c2Repository.findById(category2Code);

        return category2.orElse(null);
    }

    @Override
    public Category3 selectCategory3(Long category3Code) {
        
        Optional<Category3> category3 = c3Repository.findById(category3Code);

        return category3.orElse(null);
    }

    @Override
    public List<Category1> selectAllCategory1() {
        
        return c1Repository.findAll();
    }

    @Override
    public List<Category2> selectAllCategory2() {
        
        return c2Repository.findAll();
    }

    @Override
    public List<Category3> selectAllCategory3() {
        
        return c3Repository.findAll();
    }
    
}
