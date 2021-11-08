package com.team.service;

import com.team.entity.UserInput;

import org.springframework.stereotype.Service;

@Service
public interface UserinputService {
    
    //userinput 등록
    public void insertUserinput(UserInput userInput);

    //userinput 삭제
    public void deleteUserinput(long no);

    //userinput 정보 가져오기
    public UserInput selectUserInput(long no);

    //userinput 수정
    public void updateUserInput(UserInput userInput);
}
