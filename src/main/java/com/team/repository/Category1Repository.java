package com.team.repository;

import com.team.entity.Category1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Category1Repository extends JpaRepository<Category1, Long>{
    
}
