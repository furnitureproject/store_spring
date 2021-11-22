package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.UserAddress;
import com.team.entity.UserAddressProjection;
import com.team.repository.UserAddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    UserAddressRepository uAddressRepository;

    @Override
    public int insertUserAddress(UserAddress userAddress) {

        uAddressRepository.save(userAddress);

        return 1;
    }

    @Override
    public int updateUserAddress(UserAddress userAddress) {

        uAddressRepository.save(userAddress);

        return 1;
    }

    @Override
    public int deleteUserAddress(Long addressNo) {

        uAddressRepository.deleteById(addressNo);

        return 1;
    }

    @Override
    public List<UserAddress> selectUserAddressAll() {

        return uAddressRepository.findAll();
    }

    @Override
    public UserAddress selectUserAddressOne(Long addressNo) {

        Optional<UserAddress> userAddress = uAddressRepository.findById(addressNo);

        return userAddress.orElse(null);
    }

    @Override
    public List<UserAddressProjection> selectOneUserAddressList(String userId) {

        return uAddressRepository.findByUser_UserId(userId);
    }

    @Override
    public Optional<UserAddressProjection> selectUserAddressOneProjection(Long addressNo) {
        return uAddressRepository.findByAddressNo(addressNo);
    }

    @Override
    public UserAddressProjection selectUserAddressOneOrderByAddressNo(String userid) {
        Optional<UserAddressProjection> adressone = uAddressRepository
                .findFirstByUser_UserIdOrderByAddressNoDesc(userid);
        return adressone.orElse(null);
    }

    // @Override
    // public List<UserAddress> selectOneUserAddressALL(String userId) {
    // return uAddressRepository.findByUser_UserIdOrderByUserNameDesc(userId);
    // }

}
