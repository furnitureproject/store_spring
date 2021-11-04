package com.team.repository;

import java.util.List;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnA, Long>{
    
    //물품 코드 별 qna 조회
    @Query(value = "SELECT * FROM QNA_TB2 WHERE PRODUCT_CODE =:code", nativeQuery = true)
    public List<QnAProjection> queryListPcodeQna(@Param("code") Long code);
}
