package com.team.service;

import com.team.entity.QnA;

import org.springframework.stereotype.Service;

@Service
public interface QnaService {
    
    //qna 등록
    public void insertQna(QnA qnA);

    //qna 정보 가져오기
    public QnA selectQna(long no);
}
