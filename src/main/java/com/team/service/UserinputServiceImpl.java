package com.team.service;

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
    
}
