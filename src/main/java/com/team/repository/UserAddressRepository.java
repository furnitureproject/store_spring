package com.team.repository;

import java.util.List;
import java.util.Optional;

import com.team.entity.UserAddress;
import com.team.entity.UserAddressProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddressProjection> findByUser_UserId(String userId);

    // List<UserAddress> findByUser_UserIdOrderByUserNameDesc(String userId);

    Optional<UserAddressProjection> findByAddressNo(long addressNo);

    Optional<UserAddressProjection> findFirstByUser_UserIdOrderByAddressNoDesc(String userId);

}
