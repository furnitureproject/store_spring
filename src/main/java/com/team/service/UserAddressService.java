package com.team.service;

import java.util.List;

import com.team.entity.UserAddress;

import org.springframework.stereotype.Service;

@Service
public interface UserAddressService {
    
    // 주소 추가
    // return 1
    public int insertUserAddress(UserAddress userAddress);

    // 주소 수정
    // return 1
    public int updateUserAddress(UserAddress userAddress);

    // 주소 삭제
    // return 1
    public int deleteUserAddress(Long addressNo);

    // 주소 전체 조회
    // return List<UserAddress>
    public List<UserAddress> selectUserAddressAll();

    // 주소 1개 조회
    // return UserAddress 혹은 null
    public UserAddress selectUserAddressOne(Long addressNo);
}
