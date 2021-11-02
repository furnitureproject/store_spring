package com.team.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Product;
import com.team.entity.Review;
import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;
import com.team.entity.ReviewProjection;
import com.team.entity.User;
import com.team.jwt.JwtUtil;
import com.team.service.ProductService;
import com.team.service.ReviewImgService;
import com.team.service.ReviewService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    ReviewImgService rIService;

    @Autowired
    ProductService pService;

    @GetMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductReview(@RequestParam("productcode") Long productCode) {
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
    public Map<String, Object> insertReview(@RequestBody Review review,
            // @RequestHeader("token") String token,
            @RequestParam("num") Long productCode) {
        Map<String, Object> map = new HashMap<>();
        // String userid = jwtUtil.extractUsername(token);
        try {
            // User user = uService.selectUserOne(userid);
            User user = uService.selectUserOne("s");
            Product product = pService.selectProductOne(productCode);
            if (user != null && product != null) {
                review.setUser(user);
                review.setProduct(product);
                rService.insertReview(review);
                map.put("status", 200);
            } else {
                map.put("status", 100);
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteReview(@RequestParam("reviewnum") Long reviewNum
    // , @RequestHeader("token") String token
    ) {
        Map<String, Object> map = new HashMap<>();
        // String userid = jwtUtil.extractUsername(token);
        User user = uService.selectUserOne("s");
        String userid = user.getUserId();
        try {
            if (rService.selectReview(reviewNum).getUser().getUserId().equals(userid)) {
                List<ReviewImgProjection> rimg = rIService.selectReviewImgList(reviewNum);
                for (int i = 0; i < rimg.size(); i++) {
                    ReviewImgProjection rP = rimg.get(i);
                    Long rINum = rP.getReviewImgNum();
                    rIService.deleteReviewImg(rINum);
                }
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

    @PostMapping(value = "/reviewimage")
    public Map<String, Object> ImagePost(@RequestParam("reviewnum") Long reviewNum,
            // @RequestHeader("token") String token,
            @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        User user = uService.selectUserOne("d");
        String userId = user.getUserId();
        try {
            Review review = rService.selectReview(reviewNum);
            if (review.getUser().getUserId().equals(userId)) {
                List<ReviewImg> list = new ArrayList<>();
                if (files.length <= 3) {
                    for (int i = 0; i < files.length; i++) {
                        ReviewImg reviewImg = new ReviewImg();
                        reviewImg.setReview(review);
                        reviewImg.setReviewImgData(files[i].getBytes());
                        reviewImg.setReviewImgName(files[i].getOriginalFilename());
                        reviewImg.setReviewImgSize(files[i].getSize());
                        reviewImg.setReviewImgType(files[i].getContentType());
                        list.add(reviewImg);
                    }
                    rIService.insertReviewImg(list);
                    map.put("status", 200);
                } else {
                    // 이미지가 3개를 넘을 경우
                    map.put("status", 100);
                }
            } else {
                // 리뷰한 아이디와 이미지 넣는 아이디가 다를 경우
                map.put("status", 484);
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 이미지는 데이터랑 타입만 넘겨주도록
    // <img src="/review_image?num=16&idx=1">
    @GetMapping(value = "/reviewimage")
    public ResponseEntity<byte[]> ImageGet(@RequestParam("reviewnum") Long reviewNum, @RequestParam("idx") int idx) {
        List<ReviewImgProjection> list = rIService.selectReviewImgList(reviewNum);
        ReviewImgProjection rImg = list.get(idx);
        HttpHeaders headers = new HttpHeaders();
        if (rImg.getReviewImgType().equals("image/jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (rImg.getReviewImgType().equals("image/png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (rImg.getReviewImgType().equals("image/gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        }
        ResponseEntity<byte[]> response = new ResponseEntity<>(rImg.getReviewImgData(), headers, HttpStatus.OK);
        return response;
    }

}
