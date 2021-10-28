package com.team.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.OptionImage;
import com.team.entity.Product;
import com.team.entity.ProductEvent;
import com.team.entity.ProductOption;
import com.team.service.OptionImageService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/productoption")
public class ProductOptionController {

    @Autowired
    ProductOptionService poService;

    @Autowired
    OptionImageService oService;

    @GetMapping(value = "/select_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(@RequestParam("OPTION_CODE") long optioncode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductOption productOption = poService.selectProductOptionOne(optioncode);
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
    public Map<String, Object> insertPOST(@RequestBody ProductOption productoption, @RequestParam long productcode,
            @RequestParam long eventcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productcode);
            productoption.setProduct(product);

            ProductEvent productEvent = new ProductEvent();
            productEvent.setEventCode(eventcode);
            productoption.setProductEvent(productEvent);

            poService.insertProductOption(productoption);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody ProductOption productOption, @RequestParam long productcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productcode);
            productOption.setProduct(product);

            poService.updateProductOption(productOption);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestBody ProductOption productOption, @RequestParam long productcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = new Product();
            product.setProductCode(productcode);
            productOption.setProduct(product);

            poService.updateProductOption(productOption);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_optionimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertdesImagePOST(@RequestParam long optioncode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<OptionImage> list = new ArrayList<>();
            ProductOption productOption = poService.selectProductOptionOne(optioncode);
            for (int i = 0; i < files.length; i++) {
                OptionImage optionImage = new OptionImage();
                optionImage.setProductOption(productOption);
                optionImage.setOptionImgdata(files[i].getBytes());
                optionImage.setOptionImgName(files[i].getOriginalFilename());
                optionImage.setOptionImgSize(files[i].getSize());
                optionImage.setOptionImgType(files[i].getContentType());
                list.add(optionImage);
            }
            oService.insertOptionImageList(list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
