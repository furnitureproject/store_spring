package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.User;
import com.team.entity.UserProjection;
import com.team.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public List<User> selectUserAll() {

        return uRepository.findAll();
    }

    @Override
    public UserProjection selectUserOneProjection(String userId) {
        Optional<UserProjection> user = uRepository.findByuserEmail(userId);
        return user.orElse(null);
    }

    @Override
    public UserProjection selectUserByEmail(String email) {
        Optional<UserProjection> user = uRepository.findByuserEmail(email);
        return user.orElse(null);
    }

    @Override
    public UserProjection selectUserByPhone(String phone) {
        Optional<UserProjection> user = uRepository.findByuserPhone(phone);
        return user.orElse(null);
    }

}
