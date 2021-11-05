package com.team.service;

import java.util.List;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;

import org.springframework.stereotype.Service;

@Service
public interface QnaService {
    
    //qna 등록
    public void insertQna(QnA qnA);

    //qna 정보 가져오기
    public QnA selectQna(long no);

    //qna 수정하기
    public void updateQna(QnA qnA);

    //qna 물품 코드 별 목록 조회
    public List<QnAProjection> selectQnaList(Long code);

    //회원id 별 qna 조회
    public List<QnAProjection> selectUserQnaList(String userid);
}
