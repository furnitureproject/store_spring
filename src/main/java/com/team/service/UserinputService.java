package com.team.service;

import com.team.entity.UserInput;

import org.springframework.stereotype.Service;

@Service
public interface UserinputService {
    
    //userinput 등록
    public void insertUserinput(UserInput userInput);
}
