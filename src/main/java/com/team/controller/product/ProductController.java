package com.team.controller.product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.team.entity.Product;
import com.team.entity.User;
import com.team.repository.ProductRepository;
import com.team.service.ProductService;
import com.team.service.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/product")
public class ProductController {
    
    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductService pService;

    @GetMapping(value="/test")
    public Map<String, Object> testproduct() {

        Map<String, Object> map = new HashMap<>();
        try{
            pService.codeNext();

            map.put("200", null);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("202", e.hashCode());
        }


        return map;
    }
    
    

}
