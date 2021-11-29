package com.team.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team.entity.Category;
import com.team.entity.Product;
import com.team.entity.ProductEvent;
import com.team.entity.QnA;
import com.team.entity.Seller;
import com.team.entity.User;
import com.team.entity.UserAddress;
import com.team.jwt.JwtSellerUtil;
import com.team.jwt.JwtUtil;
import com.team.service.CategoryService;
import com.team.service.ProductEventService;
import com.team.service.ProductService;
import com.team.service.QnaService;
import com.team.service.SecurityUserDetailServiceimpl;
import com.team.service.SellerService;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    UserService uService;

    @Autowired
    SellerService sService;

    // @Autowired
    // UserinputController uController;

    @Autowired
    ProductService pService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CategoryService cService;

    @Autowired
    ProductEventService pEventService;

    @RequestMapping(value="/info", method=RequestMethod.POST)
    public Map<String, Object> getinfo(@RequestHeader("token") String token, @RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        String userid = jwtUtil.extractUsername(token);
        User user1 = uService.selectUserOne(userid);
        map.put("user", user1);
        return map;
    }

    // 비밀번호변경, 전화번호, 이메일 다 변경의 경우
    @RequestMapping(value="/user/update", method=RequestMethod.POST)
    public Map<String, Object> userupdate(@RequestHeader("token") String token, @RequestBody Map<String, String> user) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            User user1 = uService.selectUserOne(userid);
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            String userpw = user.get("userPw");
            if(bcpe.matches(userpw, user1.getUserPw())) {
            user1.setUserPw(bcpe.encode(user.get("userNewPw")));
            user1.setUserPhone(user.get("userPhone"));
            user1.setUserEmail(user.get("userEmail"));
            uService.updateUser(user1);
            map.put("status", 200);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            map.put("error", e);
        }
        
        return map;
    }
    
    

    @PostMapping(value = "/eee", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> userinsertTest(@RequestBody ProductEvent productEvent) {
        Map<String, Object> map = new HashMap<>();
        try {
            int a = pEventService.insertProductEvent(productEvent);
            System.out.println(productEvent.toString());
            map.put("status", a);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    @PostMapping(value="/userpw")
    public Map<String, Object> userpwchecker(@RequestBody Map<String, Object> checkpw) {
        Map<String, Object> map = new HashMap<>();
        if(checkpw.get("userPw").equals(checkpw.get("userPw2"))){
            System.out.println(checkpw);
            map.put("status", 200);
        }
        else{
            System.out.println(checkpw);
            map.put("status", 401);
        }
        return map;
    }
    
    @PostMapping(value="/userid")
    public Map<String, Object> useridchecker(@RequestBody Map<String, String> checkid) {
        
        Map<String, Object> map = new HashMap<>();
        System.out.println(checkid);
        if(uService.selectUserOne(checkid.get("userId")) == null && sService.selectSellerOne(checkid.get("userId")) == null){
            map.put("status", 200);
        }
        else{
            map.put("status", 401);
        }
        return map;
    }

    // 상품등록
    // <POST> 127.0.0.1:8080/ROOT/product/insert
    // 필요 {productTitle, productDesc, category3, seller}
    @PostMapping(value = "/productregist", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertProduct(@RequestBody Product product, @RequestParam long categoryCode,
            @RequestParam(name = "file") MultipartFile file, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println("------------productController Run-------------");
            String sellerId = jwtUtil.extractUsername(token);
            System.out.println("---productC id" + sellerId);
            // String role = jwtUtil.extractUserRole(token);
            // System.out.println("---productC role" + role);
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
// @Autowired
// UserService uService;

// @PostMapping(value = "/join", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> joinUser(@RequestBody User user) {
// Map<String, Object> map = new HashMap<>();
// try {
// if (uService.selectUserOne(user.getUserId()) == null) {
// // BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
// // user.setUserPw(bcpe.encode(user.getUserPw()));
// uService.insertUser(user);
// map.put("status", 200);
// }
// // } else {
// // // 로그인이나 아이디 찾기 페이지로 이동
// // map.put("status", "484");
// // }

// } catch (Exception e) {
// e.printStackTrace();
// map.put("status", e.hashCode());
// }
// return map;
// }

// @PostMapping(value = "/select", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> selectUser(@RequestBody User user) {
// Map<String, Object> map = new HashMap<>();
// try {

// map.put("status", 200);
// } catch (Exception e) {
// e.printStackTrace();
// map.put("status", e.hashCode());
// }
// return map;
// }

// // 바로 비밀번호 찾기 한 경우
// @RequestMapping(value = "/passwordfinder", method = RequestMethod.POST,
// consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> findUserPW(@RequestBody User user){
// Map<String, Object> map = new HashMap<>();

// String dbid = uService.selectUserOne(user.getUserId()).getUserId();
// String inputid = user.getUserId();
// String dbphone = uService.selectUserOne(user.getUserId()).getUserPhone();
// String inputphone = user.getUserPhone();
// String dbbirth = uService.selectUserOne(user.getUserId()).getUserBirth();
// String inputbirth = user.getUserBirth();

// if(dbid == inputid && dbphone == inputphone && dbbirth == inputbirth){
// User user1 = new User();
// User dbuser = uService.selectUserOne(user.getUserId());
// String inputPW = user.getUserPw();
// user1.setUserId(dbuser.getUserId());
// user1.setUserPw(inputPW);
// user1.setUserName(dbuser.getUserName());
// user1.setUserBirth(dbuser.getUserBirth());
// user1.setUserDeletecheck(dbuser.getUserDeletecheck());
// user1.setUserEditdate(dbuser.getUserEditdate());
// user1.setUserEmail(dbuser.getUserEmail());
// user1.setUserPhone(dbuser.getUserPhone());
// int updatecheck = uService.updateUser(user);
// if(updatecheck == 1){
// map.put("status", 200);
// }
// else{
// map.put("error", "수정 실패");
// }
// }
// else{
// // 정보가 일치하지않습니다.
// map.put("status", 999);
// }
// return map;
// }

// @RequestMapping(path = "/passwordfinder", method = RequestMethod.GET,
// consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> findUserPW(){
// Map<String, Object> map = new HashMap<>();

// // 페이지를 어디에서 왔는지 알수있는가
// // 프론트에서 숫자를 던져주고 그걸로 구분해도 되는가
// //

// map.put("status", 200);

// return map;
// }

// @GetMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> deleteUserGET() {
// Map<String, Object> map = new HashMap<>();
// map.put("status", 200);
// return map;
// }

// @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> deleteUser(@RequestBody User user) {
// Map<String, Object> map = new HashMap<>();
// try {
// if (uService.selectUserOne(user.getUserId()) != null)
// uService.deleteUser(user);
// map.put("status", 200);

// // String dbid = uService.selectUserOne(user.getUserId()).getUserId();
// // String inputid = user.getUserId();
// // String dbpw = uService.selectUserOne(user.getUserId()).getUserPw();
// // String inputpw = user.getUserPw();
// // if(dbid == inputid && dbpw == inputpw){
// // int deletecheck = uService.deleteUser(user);
// // map.put("deletecheck", deletecheck);
// // map.put("status", 200);
// // }
// // else{
// // // 정보가 일치하지 않습니다.
// // map.put("warning", 999);
// // }

// } catch (Exception e) {
// e.printStackTrace();
// map.put("status", e.hashCode());
// }
// return map;
// }

// @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> updateUser(@RequestBody User user) {
// Map<String, Object> map = new HashMap<>();
// try {
// uService.updateUser(user);
// map.put("status", 200);
// } catch (Exception e) {
// e.printStackTrace();
// map.put("status", e.hashCode());
// }
// return map;
// }

// @GetMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces =
// MediaType.APPLICATION_JSON_VALUE)
// public Map<String, Object> updateUserGET(@RequestBody User user) {
// Map<String, Object> map = new HashMap<>();
// try {

// // String dbpw = uService.selectUserOne(user.getUserId()).getUserPw();
// // String inputpw = user.getUserPw();
// // if(dbpw == inputpw){
// // map.put("userdata", uservice.selectUserOne(user.getUserId()));
// // map.put("status", 200);
// // }
// // else{
// // // 비밀번호가 일치하지않습니다.
// // map.put("warning", 999);
// // }

// } catch (Exception e) {
// e.printStackTrace();
// map.put("status", e.hashCode());
// }
// return map;
// }

}
