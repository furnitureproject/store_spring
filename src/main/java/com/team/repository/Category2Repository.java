package com.team.repository;

import com.team.entity.Category2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Category2Repository extends JpaRepository<Category2, Long>{
    
}
