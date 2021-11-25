package com.team.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.OptionProjection;
import com.team.entity.Product;
import com.team.entity.ProductEvent;
import com.team.entity.ProductOption;
import com.team.service.ProductEventService;
import com.team.service.ProductOptionService;
import com.team.service.ProductService;

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

    @Autowired
    ProductService pService;

    @Autowired
    ProductEventService peService;

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

    // 상품에 따른 옵션리스트
    // http://127.0.0.1:8080/ROOT/productoption/select_list?productCode=202111100007
    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<OptionProjection> list = poService.selectByProductCode(productCode);
            Long price = poService.selectOptionPrice(productCode);
            System.out.println(price);
            map.put("price", price);
            map.put("list", list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 한개 등록
    // http://127.0.0.1:8080/ROOT/productoption/insert?productCode=202111100007&eventCode=1
    // {"optionCode" : 1 ,"optionName" : "", "optionQuantity": 1, "optionPrice": 10}

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody ProductOption productOption, @RequestParam long productCode,
            @RequestParam(name = "eventCode", required = false, defaultValue = "1") long eventCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            long count = poService.countByCode(productCode);
            System.out.println(count);
            Product product = pService.selectProductOne(productCode);
            productOption.setProduct(product);

            String code1 = String.valueOf(productCode);
            String code2 = String.format("%02d", count + 1);
            Long code = Long.parseLong(code1 + code2);
            productOption.setOptionCode(code);
            ProductEvent productEvent = peService.selectProductEventOne(eventCode);
            productOption.setProductEvent(productEvent);

            poService.insertProductOption(productOption);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 다수 등록
    // http://127.0.0.1:8080/ROOT/productoption/insertAll?productCode=202111110009&eventCode=1
    // ["optionName" : "", "optionQuantity": 1, "optionPrice": 10}]

    @PostMapping(value = "/insertAll", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody List<ProductOption> list, @RequestParam long productCode,
            @RequestParam(name = "eventCode", required = false, defaultValue = "1") long eventCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            long count = poService.countByCode(productCode);
            System.out.println(count);
            long index = count;
            for (ProductOption productOption : list) {
                index++;
                Product product = pService.selectProductOne(productCode);
                productOption.setProduct(product);
                ProductEvent productEvent = peService.selectProductEventOne(eventCode);
                productOption.setProductEvent(productEvent);
                String code1 = String.valueOf(productCode);
                String code2 = String.format("%02d", index);
                Long code = Long.parseLong(code1 + code2);
                productOption.setOptionCode(code);
            }
            poService.insertProductOptionList(list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 옵션 이름 ,가격 , 수량 수정, 이벤트 코드 수정가능
    // {"optionCode" : 1 ,"optionName" : "", "optionQuantity": 1, "optionPrice": 10}
    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody ProductOption productOption, @RequestParam long productCode,
            @RequestParam long eventCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pService.selectProductOne(productCode);
            productOption.setProduct(product);
            ProductEvent productEvent = peService.selectProductEventOne(eventCode);
            productOption.setProductEvent(productEvent);

            ProductOption productOption1 = poService.selectProductOptionOne(productOption.getOptionCode());
            productOption.setOptionCode(productOption1.getOptionCode());

            poService.updateProductOption(productOption);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestParam long optionCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductOption productOption = new ProductOption();

            ProductOption productOption1 = poService.selectProductOptionOne(optionCode);
            productOption.setProductEvent(productOption1.getProductEvent());
            productOption.setProduct(productOption1.getProduct());
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
