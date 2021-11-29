package com.team.service;

import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.Seller;
import com.team.vo.DeliveryVO;
import com.team.vo.ProductVO;
import com.team.vo.QnAVO;

import org.springframework.stereotype.Service;

@Service
public interface SellerService {
        
    // 입력받은 판매자 정보 DB에 저장
    // return 1
    public int insertSeller(Seller seller);

    // 판매자 1명 조회
    // return Seller 혹은 return null(값이 없을 경우)
    public Seller selectSellerOne(String sellerId);

    // 판매자 전체 조회
    // return List<Seller>
    public List<Seller> selectSellerAll();

    // 판매자 정보 수정
    // return 1
    public int updateSeller(Seller seller);

    // 판매자 정보 삭제, 구현 X 사용 X, updateUser 사용할것
    // return 1
    public int deleteSeller(Seller seller);

    // 판매자 별 제품 조회
    public List<ProductVO> selectProductList(Map<String, Object> map);

    // 제품 총 개수 조회(sellerid 기준)
    long countBySelleridProduct(String id);

    // 판매자 별 qna 조회
    public List<QnAVO> selectQnaList(Map<String, Object> map);

    // qna 총 개수 조회(sellerid 기준)
    long countBySelleridQna(String id);

    // 판매자 별 delivery 조회
    public List<DeliveryVO> selectDelList(Map<String, Object> map);

    // delivery 총 개수 조회(sellerid 기준)
    long countBySelleridDelivery(String id);

}
