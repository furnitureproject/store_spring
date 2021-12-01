package com.team.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.CartProjection;
import com.team.entity.Product;
import com.team.entity.Review;
import com.team.entity.ReviewImg;
import com.team.entity.ReviewImgProjection;
import com.team.entity.ReviewProjection;
import com.team.entity.User;
import com.team.enums.Status;
import com.team.jwt.JwtUtil;
import com.team.service.CartService;
import com.team.service.ProductService;
import com.team.service.ReviewImgService;
import com.team.service.ReviewService;
import com.team.service.UserService;
import com.team.vo.ReviewVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    ResourceLoader resourceLoader;

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

    @Autowired
    CartService cService;

    @GetMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductReview(@RequestParam("productcode") Long productCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ReviewProjection> list = rService.selectReviewList(productCode);
            List<ReviewVO> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ReviewProjection review = list.get(i);
                Long number = review.getReviewNum();
                ReviewVO reviewVO = new ReviewVO();
                reviewVO.setReviewNum(number);
                reviewVO.setReivewTitle(review.getReviewTitle());
                reviewVO.setReviewContent(review.getReviewContent());
                reviewVO.setReviewRegDate(review.getReviewRegDate());
                reviewVO.setReviewStar(review.getReviewStar());
                reviewVO.setUser(review.getUser_UserId());
                reviewVO.setReviewImage1("/ROOT/reviewimage?reviewnum=" + number + "&idx=0");
                reviewVO.setReviewImage2("/ROOT/reviewimage?reviewnum=" + number + "&idx=1");
                reviewVO.setReviewImage3("/ROOT/reviewimage?reviewnum=" + number + "&idx=2");
                list1.add(reviewVO);
            }
            map.put("status", Status.COMPLETE.getCode());
            map.put("list", list1);
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }

        return map;
    }

    @GetMapping(value = "/review/test", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductReviewtest(@RequestParam("productcode") Long productCode,
            @RequestParam(defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ReviewProjection> list = rService.selectReviewList(productCode);
            int page1 = Math.max(page, 1);
            PageRequest pageRequest = PageRequest.of(page1 - 1, 10);
            List<ReviewVO> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ReviewProjection review = list.get(i);
                ReviewVO reviewVO = new ReviewVO();
                Long number = review.getReviewNum();
                reviewVO.setReviewNum(number);
                reviewVO.setReivewTitle(review.getReviewTitle());
                reviewVO.setReviewContent(review.getReviewContent());
                reviewVO.setReviewRegDate(review.getReviewRegDate());
                reviewVO.setReviewStar(review.getReviewStar());
                reviewVO.setUser(review.getUser_UserId());
                Date time = review.getReviewRegDate();
                System.out.println(time);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String today = format.format(time);
                reviewVO.setReviewRegDateString(today);

                Long count = rIService.countReviewNum(review.getReviewNum());
                System.out.println(count);
                if (count == 3) {
                    reviewVO.setReviewImage1("/ROOT/reviewimage?reviewnum=" + number + "&idx=0");
                    reviewVO.setReviewImage2("/ROOT/reviewimage?reviewnum=" + number + "&idx=1");
                    reviewVO.setReviewImage3("/ROOT/reviewimage?reviewnum=" + number + "&idx=2");
                } else if (count == 2) {
                    reviewVO.setReviewImage1("/ROOT/reviewimage?reviewnum=" + number + "&idx=0");
                    reviewVO.setReviewImage2("/ROOT/reviewimage?reviewnum=" + number + "&idx=1");
                } else if (count == 1) {
                    reviewVO.setReviewImage1("/ROOT/reviewimage?reviewnum=" + number + "&idx=0");
                }

                list1.add(reviewVO);
            }
            final int start = (int) pageRequest.getOffset();
            final int end = Math.min((start + pageRequest.getPageSize()), list1.size());
            Page<ReviewVO> list2 = new PageImpl<>(list1.subList(start, end), pageRequest, list1.size());
            map.put("status", Status.COMPLETE.getCode());
            map.put("list", list2);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }

        return map;
    }

    @PostMapping(value = "/review/test", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertReview(@RequestBody Review review, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token);
        try {
            User user = uService.selectUserOne(userid);
            Long productCode = review.getProduct().getProductCode();
            Product product = pService.selectProductOne(productCode);
            List<CartProjection> list = cService.selectAllUserCart(userid);
            for (int i = 0; i < list.size(); i++) {
                if (review.getReviewTitle() == null) {
                    // 리뷰 제목을 입력 안 함
                    map.put("status", "제목을 입력하지 않았습니다");
                } else if (review.getReviewContent() == null) {
                    // 리뷰 내용을 입력하지 않음
                    map.put("status", "내용을 입력하지 않았습니다");
                } else if (review.getReviewStar() == 0) {
                    // 리뷰 별점을 입력하지 않음
                    map.put("status", "별점을 입력하지 않았습니다");
                } else if (list.get(i).getProductOption_Product_ProductCode().equals(productCode)
                        && list.get(i).getCartStatus() == 2 && user != null && product != null) {
                    review.setUser(user);
                    review.setProduct(product);
                    rService.insertReview(review);
                    map.put("status", Status.COMPLETE.getCode());
                    map.put("reviewNum", review.getReviewNum());
                    break;
                } else {
                    // 유저가 없거나 제품이 없을 경우 오류로 반환
                    map.put("status", "제품이 없거나 권한을 가지고 있지 않습니다");
                }
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/review", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteReview(@RequestParam("reviewnum") Long reviewNum,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token);
        try {
            if (rService.selectReview(reviewNum).getUser().getUserId().equals(userid)) {
                List<ReviewImgProjection> rimg = rIService.selectReviewImgList(reviewNum);
                for (int i = 0; i < rimg.size(); i++) {
                    ReviewImgProjection rP = rimg.get(i);
                    Long rINum = rP.getReviewImgNum();
                    rIService.deleteReviewImg(rINum);
                }
                rService.deleteReview(reviewNum);
                map.put("status", Status.COMPLETE.getCode());
            } else {
                // review를 작성한 유저와 delete하려는 user가 다를 때 반환
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PostMapping(value = "/reviewimage")
    public Map<String, Object> ImagePost(@RequestParam("reviewnum") Long reviewNum,
            @RequestHeader("token") String token, @RequestParam(name = "file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractUsername(token);
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
                    map.put("status", Status.COMPLETE.getCode());
                } else {
                    // 이미지가 3개를 넘을 경우
                    map.put("status", "이미지 숫자를 초과하였습니다");
                }
            } else {
                // 리뷰한 아이디와 이미지 넣는 아이디가 다를 경우
                map.put("status", Status.ERROR.getStatus());
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 이미지는 데이터랑 타입만 넘겨주도록
    // <img src="/reviewimage?reviewnum=16&idx=1">
    @GetMapping(value = "/reviewimage")
    public ResponseEntity<byte[]> ImageGet(@RequestParam("reviewnum") Long reviewNum, @RequestParam("idx") int idx) {
        List<ReviewImgProjection> list = rIService.selectReviewImgList(reviewNum);
        ReviewImgProjection rImg = list.get(idx);
        HttpHeaders headers = new HttpHeaders();
        try {
            if (rImg.getReviewImgType().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (rImg.getReviewImgType().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (rImg.getReviewImgType().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            ResponseEntity<byte[]> response = new ResponseEntity<>(rImg.getReviewImgData(), headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    // 제품별 리뷰 갯수
    @GetMapping(value = "/count")
    public Map<String, Object> reviewCountGet(@RequestParam("productcode") long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", rService.querySelectProductReviewCount(no));
        map.put("star", rService.querySelectProductReviewAvgStar(no));
        return map;
    }

}
