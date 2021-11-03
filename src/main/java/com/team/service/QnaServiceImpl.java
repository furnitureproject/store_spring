package com.team.service;

import com.team.entity.QnA;
import com.team.repository.QnaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImpl implements QnaService{

    @Autowired
    QnaRepository qRepository;

    //qna 등록
    @Override
    public void insertQna(QnA qnA) {
        qRepository.save(qnA);
    }
    
    
}
