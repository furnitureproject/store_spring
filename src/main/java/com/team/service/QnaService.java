package com.team.service;

import com.team.entity.QnA;

import org.springframework.stereotype.Service;

@Service
public interface QnaService {
    
    //qna 등록
    public void insertQna(QnA qnA);
}
