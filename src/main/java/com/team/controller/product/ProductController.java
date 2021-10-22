package com.team.controller.product;

import com.team.entity.Product;
import com.team.entity.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProductController {
    
    @GetMapping(value="path")
    public String getMethodName(@RequestParam String param) {

        


        return "test";
    }
    
    

}
