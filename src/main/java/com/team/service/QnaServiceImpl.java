package com.team.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;
import com.team.repository.QnaRepository;
import com.team.vo.QnAVO;

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

    //물품 코드 별 qna 조회
    @Override
    public List<QnAVO> selectPcodeQnaList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_product_code_qna_list", map);
    }

    //회원 id 별 목록 qna 조회
    @Override
    public List<QnAVO> selectUserQnaList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_user_id_qna_list", map);
    }

    //qna 총 개수 조회(제품 코드 기준)
    @Override
    public long countByPcodeQna(Long code) {
        return qRepository.countByProduct_ProductCode(code);
    }

    //qna 총 개수 조회(유저id 기준)
    @Override
    public long countByUseridQna(String id) {
        return qRepository.countByUser_UserId(id);
    }

}
