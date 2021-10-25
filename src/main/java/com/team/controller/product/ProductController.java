package com.team.controller.product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.ProductOption;
import com.team.entity.Seller;
import com.team.entity.User;
import com.team.repository.ProductRepository;
import com.team.service.ProductOptionService;
import com.team.service.ProductService;
import com.team.service.ProductServiceImpl;

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
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService pService;

    @Autowired
    ProductOptionService poService;

    @GetMapping(value = "/test")
    public Map<String, Object> testproduct() {

        Map<String, Object> map = new HashMap<>();
        try {
            pService.codeNext();

            map.put("200", null);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("202", e.hashCode());
        }

        return map;
    }

    @GetMapping(value = "/select_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(@RequestParam("code") long productcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pService.selectProductOne(productcode);
            map.put("product", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET(@RequestParam("sort") long sort) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (sort == 1) {
                List<Product> list = pService.selectProductByCode();
                map.put("list", list);
                map.put("status", 200);
            } else if (sort == 2) {
                List<Product> list = pService.selectProductByHit();
                map.put("list", list);
                map.put("status", 200);
            } else if (sort == 3) {
                List<Product> list = pService.ProductDesc();
                map.put("list", list);
                map.put("status", 200);
            } else if (sort == 4) {
                List<Product> list = pService.ProductAsc();
                map.put("list", list);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            product.setSeller(seller);

            pService.insertProduct(product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // @PostMapping(value = "/insert1", consumes = MediaType.ALL_VALUE, produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> insertPOST1(@RequestBody Product product,
    // @RequestBody ProductOption productOption) {
    // Map<String, Object> map = new HashMap<>();
    // try {
    // Seller seller = new Seller();
    // seller.setSellerId("a");
    // product.setSeller(seller);

    // Product product2 = new Product();
    // product2.setProductCode(1L);
    // productOption.setProduct(product2);

    // poService.insertProductOption(productOption);
    // pService.insertProduct(product);
    // map.put("status", 200);
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", e.hashCode());
    // }
    // return map;
    // }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            product.setSeller(seller);

            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            product.setSeller(seller);

            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
