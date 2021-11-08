package com.team.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.ProductEvent;
import com.team.entity.ProductOption;
import com.team.service.ProductOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/productoption")
public class ProductOptionController {
    @Autowired
    ProductOptionService poService;

    @GetMapping(value = "/select_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(@RequestParam long optionCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductOption productOption = poService.selectProductOptionOne(optionCode);
            map.put("productOption", productOption);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductOption> list = poService.selectProductOptionAll();
            map.put("list", list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody ProductOption productOption, @RequestParam long productCode,
            @RequestParam(name = "eventcode", required = false, defaultValue = "0") long eventCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productCode);
            productOption.setProduct(product);

            ProductEvent productEvent = new ProductEvent();
            productEvent.setEventCode(eventCode);
            productOption.setProductEvent(productEvent);

            poService.insertProductOption(productOption);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody ProductOption productOption, @RequestParam long productCode,
            @RequestParam long eventCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productCode);
            productOption.setProduct(product);

            ProductEvent productEvent = new ProductEvent();
            productEvent.setEventCode(eventCode);
            productOption.setProductEvent(productEvent);

            poService.updateProductOption(productOption);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestParam long optionCode, @RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productCode);
            ProductOption productOption = new ProductOption();
            ProductOption productOption1 = poService.selectProductOptionOne(optionCode);
            productOption.setProduct(product);
            productOption.setOptionCode(productOption1.getOptionCode());
            poService.updateProductOption(productOption);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
