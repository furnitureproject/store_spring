package com.team.service;

import java.util.List;

import com.team.entity.User;
import com.team.entity.UserProjection;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    // 입력받은 유저 정보 DB에 저장
    // return 1
    public int insertUser(User user);

    // 유저 1명 조회
    // return User 혹은 return null(값이 없을 경우)
    public User selectUserOne(String userId);

    // 유저 전체 조회
    // return List<User>
    public List<User> selectUserAll();

    // 유저 정보 수정
    // return 1
    public int updateUser(User user);

    // 유저 정보 삭제, 구현 X 사용 X, updateUser 사용할것
    // return 1
    public int deleteUser(User user);

    // 유저 1명 조회
    // return 값은 UserProjection
    public UserProjection selectUserOneProjection(String userId);

    // 유저 1명 조회
    // return UserProjection
    public UserProjection selectUserByEmail(String email);

    // 유저 1명 조회
    // return UserProjection
    public UserProjection selectUserByPhone(String phone);

}
