package com.team.repository;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnA, Long>{

    //qna 1개 조회
    @Query(value = "SELECT * FROM QNA_TB2 WHERE QNA_NUM =:no", nativeQuery = true)
    public QnAProjection queryQnaOne(@Param("no") Long no);

    //qna 총 개수 조회(제품 코드 기준)
    Long countByProduct_ProductCode(Long code);

    //qna 총 개수 조회(userid 기준)
    Long countByUser_UserId(String id);

    //qna 총 개수 조회(sellerid 기준)
    Long countByProduct_Seller_SellerId(String id);

}
