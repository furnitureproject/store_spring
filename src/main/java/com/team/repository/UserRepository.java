package com.team.repository;

import java.util.Optional;

import com.team.entity.User;
import com.team.entity.UserProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<UserProjection> findByuserId(String userId);

    Optional<UserProjection> findByuserEmail(String email);

    Optional<UserProjection> findByuserPhone(String phone);

}
