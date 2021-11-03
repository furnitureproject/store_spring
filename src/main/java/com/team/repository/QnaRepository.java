package com.team.repository;

import com.team.entity.QnA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnA, Long>{
    
}
