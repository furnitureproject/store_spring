package com.team.controller.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.dto.ProductDTO;
import com.team.entity.Category;
import com.team.entity.Product;
import com.team.entity.ProductDesImage;
import com.team.entity.ProductOption;
import com.team.entity.ProductSubImage;
import com.team.entity.Seller;
import com.team.jwt.JwtUtil;
import com.team.service.CategoryService;
import com.team.service.ProductDesImageService;
import com.team.service.ProductOptionService;
import com.team.service.ProductService;
import com.team.service.ProductSubImageService;
import com.team.service.SellerService;
import com.team.vo.ProductVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ResourceLoader resourceLoader;

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

    @Autowired
    SellerService sService;

    @Autowired
    JwtUtil jwtUtil;

    int PAGECNT = 10;

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
    public Map<String, Object> selectOneGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pService.selectProductOne(productCode);
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
    public Map<String, Object> selectOneGET1(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductDTO product = pService.selectProductDTOOne(productCode);
            map.put("product", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_one2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET2(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pService.selectProductOne(productCode);
            List<ProductDesImage> deslist = pdServise.selectByProductCode(productCode);
            List<ProductSubImage> sublist = psService.selectByProductCode(productCode);
            List<ProductOption> oplist = poService.selectByProductCode(productCode);
            map.put("oplist", oplist);
            map.put("deslist", deslist);
            map.put("sublist", sublist);
            map.put("product", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_image", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> selectImage(@RequestParam long productCode) throws IOException {
        try {
            Product product = pService.selectProductOne(productCode);

            if (product.getThumImgData().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (product.getThumImgType().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (product.getThumImgType().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (product.getThumImgType().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(product.getThumImgData(), headers,
                        HttpStatus.OK);
                return response;
            }
            return null;

        } catch (Exception e) {
            InputStream is = resourceLoader.getResource("classpath:/static/images/noimage.jpg").getInputStream();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
            return response;

        }

    }

    // 전체물품 검색
    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET1(@RequestParam(name = "sort") long sort,
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {

            // 최신순
            long cnt = pService.countByProduct1(title);
            if (sort == 1) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.selectCodeList(title, pageRequest);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.selectHitList(title, pageRequest);

                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map1 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);

                map1.put("page", rpage);
                map1.put("page1", rpage1);
                map1.put("title", title);
                List<ProductVO> list = pService.selectPriceHigh(map1);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map2 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);

                map2.put("page", rpage);
                map2.put("page1", rpage1);
                map2.put("title", title);
                List<ProductVO> list = pService.selectPriceLow(map2);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 소분류 물품 검색
    // 127.0.0.1:8080/ROOT/product/select_list?sort=
    // return [{ Product }, { Product }...]
    @GetMapping(value = "/select_list1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET(@RequestParam long sort,
            @RequestParam(name = "categoryCode") long categoryCode,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 최신순
            long cnt = pService.countByProduct2(categoryCode);
            if (sort == 1) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.categoryCodeSelect1(categoryCode, pageRequest);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.categoryHitSelect1(categoryCode, pageRequest);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map1 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);

                map1.put("page", rpage);
                map1.put("page1", rpage1);
                map1.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.selectPriceHigh1(map1);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map2 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);

                map2.put("page", rpage);
                map2.put("page1", rpage1);
                map2.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.selectPriceLow1(map2);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_list2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectList1GET(@RequestParam long sort,
            @RequestParam(name = "categoryParent") long categoryParent,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        long cnt = pService.countByProduct3(categoryParent);
        try {
            // 최신순
            if (sort == 1) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.categoryCodeSelect2(categoryParent, pageRequest);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
                List<Product> list = pService.categoryHitSelect2(categoryParent, pageRequest);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map1 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);
                map1.put("page", rpage);
                map1.put("page1", rpage1);
                map1.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.selectPriceHigh2(map1);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map2 = new HashMap<>();
                int rpage1 = page * PAGECNT;
                int rpage = rpage1 - (PAGECNT - 1);
                map2.put("page", rpage);
                map2.put("page1", rpage1);
                map2.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.selectPriceLow2(map2);
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
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
            @RequestParam(name = "file") MultipartFile file, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerId = jwtUtil.extractUsername(token);
            Seller seller = sService.selectSellerOne(sellerId);
            product.setSeller(seller);

            Category category = cService.selectCategory(categoryCode);
            product.setCategory(category);
            product.setProductCode(pService.codeNext());

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

    // update 사용자 정보 필요 이름, 설명 변경가능
    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePUT(@RequestBody Product product, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String sellerId = jwtUtil.extractUsername(token);
            Seller seller = sService.selectSellerOne(sellerId);
            product.setSeller(seller);

            Date date = new Date();
            product.setProductEditdate(date);
            Product product1 = pService.selectProductOne(product.getProductCode());
            product.setCategory(product1.getCategory());
            product.setProductHit(product1.getProductHit());
            product.setThumImgData(product1.getThumImgData());
            product.setThumImgName(product1.getThumImgName());
            product.setThumImgSize(product1.getThumImgSize());
            product.setThumImgType(product1.getThumImgType());

            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ProductDelete(@RequestParam long productCode, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product1 = pService.selectProductOne(productCode);
            Product product = new Product();

            String sellerId = jwtUtil.extractUsername(token);
            Seller seller = sService.selectSellerOne(sellerId);
            product.setSeller(seller);
            product.setProductCode(productCode);
            product.setProductTitle(product1.getProductTitle());
            product.setCategory(product1.getCategory());

            pService.updateProduct(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_desimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertdesImagePOST(@RequestParam long productCode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductDesImage> list = new ArrayList<>();
            Product product = pService.selectProductOne(productCode);
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
