package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.UserAddress;
import com.team.entity.UserAddressProjection;
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

    // 한 사람 주소 조회(그 사람의 이름, 폰번호 추가)
    // return List
    public List<UserAddressProjection> selectOneUserAddressList(String userId);

    // 한 사람 주소 조회(전부)
    // public List<UserAddress> selectOneUserAddressALL(String userId);

    // 주소 1개 조회(그 사람의 이름, 폰번호 추가)
    // return Optional
    public Optional<UserAddressProjection> selectUserAddressOneProjection(Long addressNo);

    // 주소 1개 조회(가장 높은 번호 하나만)
    // return Optional
    public UserAddressProjection selectUserAddressOneOrderByAddressNo(String userid);

}
