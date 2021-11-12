package com.team.service;

import java.util.List;
import java.util.Map;

import com.team.entity.QnA;
import com.team.entity.QnAProjection;
import com.team.vo.QnAVO;

import org.springframework.stereotype.Service;

@Service
public interface QnaService {
    
    //qna 등록
    public void insertQna(QnA qnA);

    //qna 정보 가져오기
    public QnA selectQna(long no);

    //qna 1개 조회
    public QnAProjection selectQnaOne(long no);

    //qna 수정하기
    public void updateQna(QnA qnA);

    //qna 삭제하기
    public void deleteQna(Long no);

    //물품 코드 별 qna 조회
    public List<QnAVO> selectPcodeQnaList(Map<String, Object> map);

    //회원 id 별 목록 조회
    public List<QnAVO> selectUserQnaList(Map<String, Object> map);

    //qna 총 개수 조회(제품 코드 기준)
    long countByPcodeQna(Long code);

    //qna 총 개수 조회(userid 기준)
    long countByUseridQna(String id);
}
