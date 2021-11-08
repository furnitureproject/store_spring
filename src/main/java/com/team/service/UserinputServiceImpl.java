package com.team.service;

import java.util.Optional;

import com.team.entity.UserInput;
import com.team.repository.UserinputRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserinputServiceImpl implements UserinputService{

    @Autowired
    UserinputRepository uiRepository;

    //userinput 등록
    @Override
    public void insertUserinput(UserInput userInput) {
        uiRepository.save(userInput);
    }

    //userinput 삭제
    @Override
    public void deleteUserinput(long no) {
        uiRepository.deleteById(no);
    }

    //userinput 정보 가져오기
    @Override
    public UserInput selectUserInput(long no) {
        Optional<UserInput> userinput = uiRepository.findById(no);
        return userinput.orElse(null);
    }

    //userinput 수정
    @Override
    public void updateUserInput(UserInput userInput) {
        uiRepository.save(userInput);
    }
    
}
