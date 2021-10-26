package com.team.repository;

import com.team.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("SELECT USER_TB.USERID, USER_TB.USEREMAIL, USER_TB.USER")
}
