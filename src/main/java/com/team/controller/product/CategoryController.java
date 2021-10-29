package com.team.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Category1;
import com.team.entity.Category2;
import com.team.entity.Category3;
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

    @PostMapping(value = "/insert_category1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST(@RequestBody Category1 category1) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.insertCategory1(category1);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_category2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insert2POST(@RequestBody Category2 category2, @RequestParam long category1code) {
        Map<String, Object> map = new HashMap<>();
        try {
            Category1 category1 = cService.selectCategory1(category1code);
            category2.setCategory1(category1);
            cService.insertCategory2(category2);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/insert_category3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insert3POST(@RequestBody Category3 category3, @RequestParam long category2code) {
        Map<String, Object> map = new HashMap<>();
        try {
            Category2 category2 = cService.selectCategory2(category2code);
            category3.setCategory2(category2);
            cService.insertCategory3(category3);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update_category1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdatePOST(@RequestBody Category1 category1) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.updateCategory1(category1);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update_category2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Update2POST(@RequestBody Category2 category2) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.updateCategory2(category2);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update_category3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Update3POST(@RequestBody Category3 category3) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.updateCategory3(category3);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete_category1", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CategoryDelete(@RequestParam long category1code) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.deleteCategory1(category1code);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete_category2", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Category2Delete(@RequestParam long category2code) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.deleteCategory2(category2code);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete_category3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Category3Delete(@RequestParam long category3code) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService.deleteCategory3(category3code);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/list_category3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Category3List() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Category3> list = cService.selectAllCategory3();
            map.put("list", list);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
