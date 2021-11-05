package com.team.repository;

import com.team.entity.UserInput;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinputRepository extends JpaRepository<UserInput, Long>{
    
}
