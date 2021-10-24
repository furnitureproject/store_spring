package com.team.service;

import java.util.List;

import com.team.entity.Seller;

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
}
