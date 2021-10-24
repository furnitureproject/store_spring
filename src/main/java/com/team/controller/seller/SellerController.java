package com.team.controller.seller;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Seller;
import com.team.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/seller")
public class SellerController {
    
    @Autowired
    SellerService sservice;

    @RequestMapping(value="/insert", method=RequestMethod.GET,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registSellerGet() {

        Map<String, Object> map = new HashMap<>();
        try{
            map.put("200", "connect");
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;
    }
    
    @RequestMapping(value="/insert", method=RequestMethod.POST,
    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> registSellerPost(@RequestBody Seller seller) {

        Map<String, Object> map = new HashMap<>();
        try{
            
            sservice.insertSeller(seller);
            map.put("200", "connect");
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("400", e.hashCode());
        }

        return map;
    }



}
