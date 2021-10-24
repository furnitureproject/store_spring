package com.team.repository;

import java.math.BigDecimal;

import com.team.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    // 시퀀스를 1 증가시키고 그 값을 받아온다.
    @Query(value = "SELECT SEQ_TEST01.nextval FROM dual", nativeQuery = true)
    String getNextSeqVal();

    // String findByProductTitleOrderByAsc(String productTitle);

    

}
