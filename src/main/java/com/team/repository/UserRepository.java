package com.team.repository;

import java.util.Optional;

import com.team.entity.User;
import com.team.entity.UserUpdateInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<UserUpdateInfo> findByuserId(String userId);

}
