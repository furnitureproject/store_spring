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

    //qna 1개 조회
    @Query(value = "SELECT * FROM QNA_TB2 WHERE QNA_NUM =:no", nativeQuery = true)
    public QnAProjection queryQnaOne(@Param("no") Long no);

    //물품 코드 별 qna 조회
    @Query(value = "SELECT * FROM QNA_TB2 WHERE PRODUCT_CODE =:code", nativeQuery = true)
    public List<QnAProjection> queryListPcodeQna(@Param("code") Long code);

    //회원id 별 qna 조회
    @Query(value = "SELECT * FROM QNA_TB2 WHERE USER_ID =:userid", nativeQuery = true)
    public List<QnAProjection> queryListUseridQna(@Param("userid") String userid);
}
