package com.team.controller.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.CateImageProjection;
import com.team.entity.Category;
import com.team.entity.CategoryImage;
import com.team.service.CategoryImageService;
import com.team.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    CategoryService cService;

    @Autowired
    CategoryImageService ciService;

    // categoryTier categoryName categoryParent만 있으면 됨 -> 코드는 자동생성,
    // 1tier 경우 => { categoryName: "", categoryTier: 1}
    // 나머지 경우 => { categoryName: "", categoryTier: 2 , categoryParent : 201000 }
    @PostMapping(value = "/insert_category", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody Category category) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (category.getCategoryTier() == 1) {
                long count = cService.countByTier(category.getCategoryTier());
                category.setCategoryCode((count + 1) * 100000);

            } else if (category.getCategoryTier() == 2) {
                long count = cService.countByTierParent(category.getCategoryTier(), category.getCategoryParent());

                category.setCategoryCode(category.getCategoryParent() + (count + 1) * 1000);
            } else if (category.getCategoryTier() == 3) {
                long count = cService.countByTierParent(category.getCategoryTier(), category.getCategoryParent());
                category.setCategoryCode(category.getCategoryParent() + (count + 1));
            }
            cService.insertCategory(category);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // { "categoryCode": 201001 , "categoryName": "", "categoryTier" : "",
    // "categoryParent": ""}
    @PutMapping(value = "/update_category", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody Category category) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.updateCategory(category);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete_category", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CategoryDelete(@RequestParam long categoryCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.deleteCategory(categoryCode);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list_category", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CategoryList(@RequestParam long categoryParent) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Category> list = cService.selectCategoryList(categoryParent);
            map.put("list", list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list_category2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CategoryList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Category> list = cService.selectCateTier2(2);
            map.put("list", list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list_category1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CategoryList1() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Category> list = cService.selectCateTier2(1);
            map.put("list", list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // http://127.0.0.1:8080/ROOT/category/insert_cateimage?categoryCode=
    @PostMapping(value = "/insert_cateimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertImagePOST(@RequestParam long categoryCode,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CategoryImage> list = new ArrayList<>();
            Category category = cService.selectCategory(categoryCode);
            for (int i = 0; i < files.length; i++) {
                CategoryImage categoryImage = new CategoryImage();
                categoryImage.setCategory(category);
                categoryImage.setCateImgData(files[i].getBytes());
                categoryImage.setCateImgName(files[i].getOriginalFilename());
                categoryImage.setCateImgSize(files[i].getSize());
                categoryImage.setCateImgType(files[i].getContentType());
                list.add(categoryImage);
            }
            ciService.insertCateImageList(list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // http://127.0.0.1:8080/ROOT/category/select_cateimage?cateImgNum=
    @GetMapping(value = "/select_cateimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> selectDesImage(@RequestParam long cateImgNum) throws IOException {
        try {
            CategoryImage categoryImage = ciService.selectCateImageOne(cateImgNum);

            if (categoryImage.getCateImgData().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (categoryImage.getCateImgType().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (categoryImage.getCateImgType().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (categoryImage.getCateImgType().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(categoryImage.getCateImgData(), headers,
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

    // http://127.0.0.1:8080/ROOT/category/select_cateimglist?categoryCode=
    @GetMapping(value = "/select_cateimglist", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectcateGET(@RequestParam long categoryCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CateImageProjection> list = ciService.CateImgNumList(categoryCode);

            List<String> list1 = new ArrayList<>();
            for (CateImageProjection cateImageProjection : list) {
                list1.add("/ROOT/category/select_cateimage?categoryCode=" + cateImageProjection.getcateImgNum());
            }
            map.put("list1", list1);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
