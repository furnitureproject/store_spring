package com.team.controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.dto.ProductDTO;
import com.team.entity.Category;
import com.team.entity.Product;
import com.team.entity.ProductDesImage;
import com.team.entity.ProductSubImage;
import com.team.entity.Seller;
import com.team.service.CategoryService;
import com.team.service.ProductDesImageService;
import com.team.service.ProductOptionService;
import com.team.service.ProductService;
import com.team.service.ProductSubImageService;
import com.team.vo.ProductVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService pService;

    @Autowired
    ProductOptionService poService;

    @Autowired
    ProductDesImageService pdServise;

    @Autowired
    CategoryService cService;

    @Autowired
    ProductSubImageService psService;

    @GetMapping(value = "/test")
    public Map<String, Object> testproduct() {

        Map<String, Object> map = new HashMap<>();
        try {
            pService.codeNext();
            System.out.println(pService.codeNext());
            map.put("200", null);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("202", e.hashCode());
        }

        return map;
    }

    @GetMapping(value = "/select_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(@RequestParam long productcode) {
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

    // 2개만 뽑는거
    @GetMapping(value = "/select_one1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET1(@RequestParam long productcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductDTO product = pService.selectProductDTOOne(productcode);
            map.put("product", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 전체물품 검색
    @GetMapping(value = "/select_listall", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET1(@RequestParam(name = "sort") long sort,
            @RequestParam(name = "title", required = false, defaultValue = "") String title) {
        Map<String, Object> map = new HashMap<>();
        try {

            // 최신순
            if (sort == 1) {
                List<Product> list = pService.selectCodeList(title);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                List<Product> list = pService.selectHitList(title);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                List<ProductVO> list = pService.selectPriceHigh(title);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                List<ProductVO> list = pService.selectPriceLow(title);
                map.put("list", list);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 기준을 잡고 물품 정렬(최신순, 조회순, 인기순, 가격순)
    // 127.0.0.1:8080/ROOT/product/select_list?sort=
    // return [{ Product }, { Product }...]
    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET(@RequestParam long sort,
            @RequestParam(name = "query", required = false, defaultValue = "1") int query,
            @RequestParam(name = "categoryCode", required = false, defaultValue = "0") long categoryCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 최신순
            if (sort == 1) {
                if (query == 1) {
                    List<Product> list = pService.categorySelerct(categoryCode);
                    map.put("list", list);
                    map.put("status", 200);
                }

                // 조회수순
            } else if (sort == 2) {
                // List<Product> list = pService.selectProductByHit();
                // map.put("list", list);
                // map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {

                // map.put("list", list);
                // map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {

                // map.put("list", list);
                // map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 상품등록
    // <POST> 127.0.0.1:8080/ROOT/product/insert
    // 필요 {productTitle, productDesc, category3, seller}
    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@ModelAttribute Product product, @RequestParam long categoryCode,
            @RequestParam(name = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            product.setSeller(seller);

            Category category = cService.selectCategory(categoryCode);
            product.setCategory(category);

            product.setThumImgData(file.getBytes());
            product.setThumImgName(file.getOriginalFilename());
            product.setThumImgSize(file.getSize());
            product.setThumImgType(file.getContentType());
            pService.insertProduct(product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // update 사용자 정보 필요
    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePUT(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            product.setSeller(seller);
            Date date = new Date();
            product.setProductEditdate(date);
            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestParam long productcode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Seller seller = new Seller();
            seller.setSellerId("a");
            Product product1 = pService.selectProductOne(productcode);
            Product product = new Product();
            product.setSeller(seller);
            product.setProductCode(productcode);
            product.setProductTitle(product1.getProductTitle());

            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_desimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertdesImagePOST(@RequestParam long productcode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductDesImage> list = new ArrayList<>();
            Product product = pService.selectProductOne(productcode);
            for (int i = 0; i < files.length; i++) {
                ProductDesImage productDesImage = new ProductDesImage();
                productDesImage.setProduct(product);
                productDesImage.setDesImgData(files[i].getBytes());
                productDesImage.setDesImgName(files[i].getOriginalFilename());
                productDesImage.setDesImgSize(files[i].getSize());
                productDesImage.setDesImgType(files[i].getContentType());
                list.add(productDesImage);
            }
            pdServise.insertDesImageList(list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_subimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertImagePOST(@RequestParam long productCode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductSubImage> list = new ArrayList<>();
            Product product = pService.selectProductOne(productCode);
            for (int i = 0; i < files.length; i++) {
                ProductSubImage productSubImage = new ProductSubImage();
                productSubImage.setProduct(product);
                productSubImage.setSubImgdata(files[i].getBytes());
                productSubImage.setSubImgName(files[i].getOriginalFilename());
                productSubImage.setSubImgSize(files[i].getSize());
                productSubImage.setSubImgType(files[i].getContentType());
                list.add(productSubImage);
            }
            psService.insertSubImageList(list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
