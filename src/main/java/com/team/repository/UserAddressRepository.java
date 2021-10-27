package com.team.repository;

import java.util.List;
import java.util.Optional;

import com.team.entity.User;
import com.team.entity.UserAddress;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<User> findByUser_UserId(@Param("id") String userId);
}
