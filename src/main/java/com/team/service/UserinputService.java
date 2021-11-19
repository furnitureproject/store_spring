package com.team.service;

import java.util.List;

import com.team.entity.UserInput;
import com.team.entity.UserInputProjection;

import org.springframework.stereotype.Service;

@Service
public interface UserinputService {
    
    //userinput 등록
    public void insertUserinput(UserInput userInput);

    //일괄 등록
    public void insertAllUserinput(List<UserInput> list);

    //userinput 삭제
    public void deleteUserinput(long no);

    //userinput 정보 가져오기
    public UserInput selectUserInput(long no);

    //userinput 수정
    public void updateUserInput(UserInput userInput);

    //userinput 조회
    public UserInputProjection selectUserInputOne(long no);

    //userinput 1개 조회
    public UserInputProjection selectUserInput1(long no);

}
