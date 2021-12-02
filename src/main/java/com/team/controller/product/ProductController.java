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
import com.team.entity.DesProjection;
import com.team.entity.Product;
import com.team.entity.ProductDesImage;
import com.team.entity.ProductProjection;
import com.team.entity.ProductSubImage;
import com.team.entity.Seller;
import com.team.entity.SubProjection;
import com.team.jwt.JwtUtil;
import com.team.service.CategoryService;
import com.team.service.ProductDesImageService;
import com.team.service.ProductOptionService;
import com.team.service.ProductService;
import com.team.service.ProductSubImageService;
import com.team.service.SellerService;
import com.team.vo.ProductVO;

import org.springframework.beans.factory.annotation.Autowired;
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

    int PAGECNT = 12;

    @GetMapping(value = "/test")
    public Map<String, Object> testproduct(@RequestParam long productCode) {

        Map<String, Object> map = new HashMap<>();
        try {
            List<DesProjection> list = pdServise.DesImgNumList(productCode);
            System.out.println(list.size());
            map.put("200", null);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("202", e.hashCode());
        }

        return map;
    }

    // http://127.0.0.1:8080/ROOT/product/select_one?productCode=202111090004
    @GetMapping(value = "/select_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectOneGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductProjection product = pService.selectProductProjection(productCode);

            map.put("product", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 상세페이지 조회수 1증가
    @PutMapping(value = "/hit", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> HitPut(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pService.selectProductOne(productCode);
            product.setProductHit(product.getProductHit() + 1);
            pService.updateProduct(product);

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

    // http://127.0.0.1:8080/ROOT/product/select_desimglist?productCode=202111090005
    @GetMapping(value = "/select_desimglist", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectDesGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<DesProjection> list = pdServise.DesImgNumList(productCode);

            List<String> list1 = new ArrayList<>();
            for (DesProjection desProjection : list) {
                list1.add("/ROOT/product/select_desimage?desImgNum=" + desProjection.getdesImgNum());
            }
            map.put("list1", list1);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // http://127.0.0.1:8080/ROOT/product/select_subimglist?productCode=202111090004
    @GetMapping(value = "/select_subimglist", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectSubGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<SubProjection> list = psService.SubImgNumList(productCode);

            List<String> list1 = new ArrayList<>();
            for (SubProjection subProjection : list) {
                list1.add("/ROOT/product/select_subimage?subImgNum=" + subProjection.getsubImgNum());
            }
            map.put("list1", list1);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select_Thumimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectThumGET(@RequestParam long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("image", "/ROOT/product/select_image?productCode=" + productCode);
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

    // 메인페이지 -> page 없음 안붙여도됨
    @GetMapping(value = "/main", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectMainGET(@RequestParam(name = "sort") long sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (sort == 1) { // 이름에 원목들어가는 애들
                Map<String, Object> map1 = new HashMap<>();
                String title = "원목";
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);
                map1.put("spage", spage);
                map1.put("epage", epage);
                map1.put("title", title);
                List<ProductVO> list = pService.selectPriceHigh(map1);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("list", list);
                map.put("status", 200);

            } else if (sort == 2) { // 이름에 엔틱 들어가는 애들
                Map<String, Object> map2 = new HashMap<>();
                String title = "엔틱";
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map2.put("spage", spage);
                map2.put("epage", epage);
                map2.put("title", title);
                List<ProductVO> list = pService.selectPriceHigh(map2);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("list", list);
                map.put("status", 200);

            } else if (sort == 3) { // 이름에 고무나무 들어가는 애들
                Map<String, Object> map3 = new HashMap<>();

                String title = "고무나무";

                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map3.put("spage", spage);
                map3.put("epage", epage);
                map3.put("title", title);
                List<ProductVO> list = pService.selectPriceHigh(map3);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("list", list);
                map.put("status", 200);

            } else if (sort == 4) { // 물품 전체에서 최신 등록순
                Map<String, Object> map4 = new HashMap<>();

                String title = "";

                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map4.put("spage", spage);
                map4.put("epage", epage);
                map4.put("title", title);
                List<ProductVO> list = pService.selectCodeList(map4);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("list", list);
                map.put("status", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 전체물품 검색 sort 1:최신순 2: 조회수순 3: 가격높은순, 4: 가격낮은순
    // or title[검색어] 없으면 전체검색 or page: 없으면 1페이지
    // http://127.0.0.1:8080/ROOT/product/select_list?sort=3
    @GetMapping(value = "/select_list", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET1(@RequestParam(name = "sort") long sort,
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {

            // 최신순
            long cnt = pService.countByProduct1(title);
            if (sort == 1) {
                Map<String, Object> map1 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map1.put("spage", spage);
                map1.put("epage", epage);
                map1.put("title", title);
                List<ProductVO> list = pService.selectCodeList(map1);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                Map<String, Object> map2 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map2.put("spage", spage);
                map2.put("epage", epage);
                map2.put("title", title);
                List<ProductVO> list = pService.selectHitList(map2);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map3 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map3.put("spage", spage);
                map3.put("epage", epage);
                map3.put("title", title);
                List<ProductVO> list = pService.selectPriceHigh(map3);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map4 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map4.put("spage", spage);
                map4.put("epage", epage);
                map4.put("title", title);
                List<ProductVO> list = pService.selectPriceLow(map4);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
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

    // 소분류 물품 검색 필요한 파라미터
    // sort 1:최신순 2: 조회수순 3: 가격높은순, 4: 가격낮은순 and categoryCode or page: 없으면 1페이지
    // http://127.0.0.1:8080/ROOT/product/select_list1?sort=4&categoryCode=201001&page=2
    @GetMapping(value = "/select_list1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectListGET(@RequestParam long sort,
            @RequestParam(name = "categoryCode") long categoryCode,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 최신순
            long cnt = pService.countByProduct2(categoryCode);
            if (sort == 1) {
                Map<String, Object> map1 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map1.put("spage", spage);
                map1.put("epage", epage);
                map1.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.categoryCodeSelect1(map1);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                Map<String, Object> map2 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map2.put("spage", spage);
                map2.put("epage", epage);
                map2.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.categoryHitSelect1(map2);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map3 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map3.put("spage", spage);
                map3.put("epage", epage);
                map3.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.selectPriceHigh1(map3);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map4 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);

                map4.put("spage", spage);
                map4.put("epage", epage);
                map4.put("categoryCode", categoryCode);
                List<ProductVO> list = pService.selectPriceLow1(map4);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
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

    // 중분류(거실 주방 ...) 필요한 파라미터 sort 1:최신순 2: 조회수순 3: 가격높은순, 4: 가격낮은순
    // and categeryParent or page: 없으면 1페이지
    // http://127.0.0.1:8080/ROOT/product/select_list2?sort=2&page=2&categoryParent=201000
    @GetMapping(value = "/select_list2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectList1GET(@RequestParam long sort,
            @RequestParam(name = "categoryParent") long categoryParent,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        long cnt = pService.countByProduct3(categoryParent);
        try {
            // 최신순
            if (sort == 1) {
                Map<String, Object> map1 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);
                map1.put("spage", spage);
                map1.put("epage", epage);
                map1.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.categoryCodeSelect2(map1);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);

                // 조회수순
            } else if (sort == 2) {
                Map<String, Object> map2 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);
                map2.put("spage", spage);
                map2.put("epage", epage);
                map2.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.categoryHitSelect2(map2);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 높은순
            } else if (sort == 3) {
                Map<String, Object> map3 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);
                map3.put("spage", spage);
                map3.put("epage", epage);
                map3.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.selectPriceHigh2(map3);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
                map.put("cnt", (cnt - 1) / PAGECNT + 1);
                map.put("list", list);
                map.put("status", 200);
                // 가격 낮은순
            } else if (sort == 4) {
                Map<String, Object> map4 = new HashMap<>();
                int epage = page * PAGECNT;
                int spage = epage - (PAGECNT - 1);
                map4.put("spage", spage);
                map4.put("epage", epage);
                map4.put("categoryParent", categoryParent);
                List<ProductVO> list = pService.selectPriceLow2(map4);
                for (ProductVO productvo : list) {
                    productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                }
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

    @GetMapping(value = "/select_list3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectList3GET(@RequestParam long sort,
            @RequestParam(name = "categoryParent", required = false, defaultValue = "0") long categoryParent,
            @RequestParam(name = "categoryCode", required = false, defaultValue = "0") long categoryCode,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        long cnt = pService.countByProduct3(categoryParent);
        long cnt1 = pService.countByProduct2(categoryCode);
        try {
            // 최신순
            if (sort == 1) {
                if (categoryCode == 0) {
                    Map<String, Object> map1 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);
                    map1.put("spage", spage);
                    map1.put("epage", epage);
                    map1.put("categoryParent", categoryParent);
                    List<ProductVO> list = pService.categoryCodeSelect2(map1);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                } else if (categoryParent == 0) {
                    Map<String, Object> map1 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);
                    map1.put("spage", spage);
                    map1.put("epage", epage);
                    map1.put("categoryCode", categoryCode);
                    List<ProductVO> list = pService.categoryCodeSelect1(map1);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt1 - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);

                }

                // 조회수순
            } else if (sort == 2) {
                if (categoryCode == 0) {
                    Map<String, Object> map2 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);
                    map2.put("spage", spage);
                    map2.put("epage", epage);
                    map2.put("categoryParent", categoryParent);
                    List<ProductVO> list = pService.categoryHitSelect2(map2);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                } else if (categoryParent == 0) {
                    Map<String, Object> map2 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);

                    map2.put("spage", spage);
                    map2.put("epage", epage);
                    map2.put("categoryCode", categoryCode);
                    List<ProductVO> list = pService.categoryHitSelect1(map2);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt1 - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                }

                // 가격 높은순
            } else if (sort == 3) {
                if (categoryCode == 0) {
                    Map<String, Object> map3 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);
                    map3.put("spage", spage);
                    map3.put("epage", epage);
                    map3.put("categoryParent", categoryParent);
                    List<ProductVO> list = pService.selectPriceHigh2(map3);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                } else if (categoryParent == 0) {
                    Map<String, Object> map3 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);

                    map3.put("spage", spage);
                    map3.put("epage", epage);
                    map3.put("categoryCode", categoryCode);
                    List<ProductVO> list = pService.selectPriceHigh1(map3);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt1 - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                }
                // 가격 낮은순
            } else if (sort == 4) {
                if (categoryCode == 0) {
                    Map<String, Object> map4 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);
                    map4.put("spage", spage);
                    map4.put("epage", epage);
                    map4.put("categoryParent", categoryParent);
                    List<ProductVO> list = pService.selectPriceLow2(map4);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);
                } else if (categoryParent == 0) {
                    Map<String, Object> map4 = new HashMap<>();
                    int epage = page * PAGECNT;
                    int spage = epage - (PAGECNT - 1);

                    map4.put("spage", spage);
                    map4.put("epage", epage);
                    map4.put("categoryCode", categoryCode);
                    List<ProductVO> list = pService.selectPriceLow1(map4);
                    for (ProductVO productvo : list) {
                        productvo.setImage("/ROOT/product/select_image?productCode=" + productvo.getProductCode());
                    }
                    map.put("cnt", (cnt1 - 1) / PAGECNT + 1);
                    map.put("list", list);
                    map.put("status", 200);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 상품등록
    // http://127.0.0.1:8080/ROOT/product/insert?categoryCode=201001
    // 필요 {productTitle, productDesc, category3, seller} - form data
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
            System.out.println("----");

            product.setThumImgData(file.getBytes());
            product.setThumImgName(file.getOriginalFilename());
            product.setThumImgSize(file.getSize());
            product.setThumImgType(file.getContentType());
            Product product1 = pService.insertProduct(product);
            map.put("product", product1.getProductCode());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // update 사용자 정보 필요 이름, 설명 변경가능 { "productTitle": " ", "productDesc": " "}
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

    // http://127.0.0.1:8080/ROOT/product/insert_desimage?productCode=202111090005
    @PostMapping(value = "/insert_desimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertdesImagePOST(@RequestParam long productCode,
            @RequestParam(name = "File") MultipartFile[] files) {
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

    // http://127.0.0.1:8080/ROOT/product/insert_subimage?productCode=202111090005
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

    // http://127.0.0.1:8080/ROOT/product/select_desimage?desImgNum=6
    @GetMapping(value = "/select_desimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> selectDesImage(@RequestParam long desImgNum) throws IOException {
        try {
            ProductDesImage productDesImage = pdServise.selectDesImageOne(desImgNum);

            if (productDesImage.getDesImgData().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (productDesImage.getDesImgType().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (productDesImage.getDesImgType().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (productDesImage.getDesImgType().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(productDesImage.getDesImgData(), headers,
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

    // http://127.0.0.1:8080/ROOT/product/select_subimage?subImgNum=6
    @GetMapping(value = "/select_subimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> selectSubImage(@RequestParam long subImgNum) throws IOException {
        try {
            ProductSubImage productSubImage = psService.selectSubImageOne(subImgNum);

            if (productSubImage.getSubImgdata().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (productSubImage.getSubImgType().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (productSubImage.getSubImgType().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (productSubImage.getSubImgType().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(productSubImage.getSubImgdata(), headers,
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

}
