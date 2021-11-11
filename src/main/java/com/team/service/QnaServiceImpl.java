package com.team.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;
import com.team.repository.QnaRepository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QnaServiceImpl implements QnaService{

    @Autowired
    QnaRepository qRepository;

    @Autowired
    SqlSessionFactory sqlFactory;

    //qna 등록
    @Override
    public void insertQna(QnA qnA) {
        qRepository.save(qnA);
    }

    //qna 정보 가져오기
    @Override
    public QnA selectQna(long no) {
        Optional<QnA> qna = qRepository.findById(no);
        return qna.orElse(null);
    }

    //qna 1개 조회
    @Override
    public QnAProjection selectQnaOne(long no) {
        return qRepository.queryQnaOne(no);
    }

    //qna 수정하기
    @Override
    public void updateQna(QnA qnA) {
        qRepository.save(qnA);
    }

    //qna 삭제하기
    @Override
    public void deleteQna(Long no) {
        qRepository.deleteById(no);
    }

    //qna 물품 코드 별 목록 조회
    @Override
    public List<QnAProjection> selectQnaList(Long code, Long page) {
        return qRepository.queryListPcodeQna(code, page);
    }

    //회원id 별 qna 조회
    @Override
    public List<QnAProjection> selectUserQnaList(String userid) {
        return qRepository.queryListUseridQna(userid);
    }

    //물품 코드 별 조회
    @Override
    public List<QnAProjection> selectqna(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_code_qna_list", map);
    }

    
}
