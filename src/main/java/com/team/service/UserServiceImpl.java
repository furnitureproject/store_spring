package com.team.service;

import java.util.Optional;

import com.team.entity.User;
import com.team.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository uRepository;

    @Override
    public int insertUser(User user) {
        
        uRepository.save(user);

        return 1;
    }

    @Override
    public User selectUserOne(String userId) {

        Optional<User> user = uRepository.findById(userId);

        return user.orElse(null);
    }

    @Override
    public int updateUser(User user) {

        uRepository.save(user);

        return 1;
    }

    @Override
    public int deleteUser(User user) {

        uRepository.deleteById(user.getUserId());

        return 1;
    }
    
}
