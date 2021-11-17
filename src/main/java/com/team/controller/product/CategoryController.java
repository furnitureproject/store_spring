package com.team.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Category;
import com.team.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    CategoryService cService;

    // categoryTier categoryName categoryParent만 있으면 됨 -> 코드는 자동생성,
    // 1tier 경우엔 name tier 값만!
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

}
