package com.team.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Review;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.ReviewService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class ReviewController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ReviewService rService;

    @Autowired
    UserService uService;

    @GetMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductReview(@RequestParam Long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);
            map.put("obj", rService.selectReviewList(productCode));
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }

        return map;
    }

    @PostMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertReview(@RequestBody Review review, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token);
        try {
            User user = uService.selectUserOne(userid);
            review.setUser(user);
            rService.insertReview(review);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteReview(@RequestParam("num") Long reviewNum, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token);
        try {
            if (rService.selectReview(reviewNum).getUser().getUserId().equals(userid)) {
                rService.deleteReview(reviewNum);
                map.put("status", 200);
            } else {
                map.put("status", 100);
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/review_image")
    public Map<String, Object> insertReviewImg(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {

            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/review_image")
    public Map<String, Object> ImagePost(@RequestBody Review review, @RequestParam(name = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            review.setReviewImgName(file.getOriginalFilename());
            review.setReviewImgData(file.getBytes());
            review.setReviewImgSize(file.getSize());
            review.setReviewImgType(file.getContentType());
            rService.insertReview(review);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 이미지는 데이터랑 타입만 넘겨주도록
    @GetMapping(value = "/review_image")
    public Map<String, Object> ImageGet(@RequestParam("num") Long reviewNum) {
        Map<String, Object> map = new HashMap<>();
        Review review = rService.selectReview(reviewNum);
        try {
            System.out.println(review);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }

        return map;
    }

}
