// package com.team.controller;

// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import com.team.entity.User;
// import com.team.service.SecurityUserDetailServiceimpl;
// import com.team.service.UserService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.MediaType;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping(value = "/test1")
// public class TestController {

// @Autowired
// UserService uService;







// // @PostMapping(value = "/join", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> joinUser(@RequestBody User user) {
// // Map<String, Object> map = new HashMap<>();
// // try {
// // if (uService.selectUserOne(user.getUserId()) == null) {
// // // BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
// // // user.setUserPw(bcpe.encode(user.getUserPw()));
// // uService.insertUser(user);
// // map.put("status", 200);
// // }
// // // } else {
// // // // 로그인이나 아이디 찾기 페이지로 이동
// // // map.put("status", "484");
// // // }

// // } catch (Exception e) {
// // e.printStackTrace();
// // map.put("status", e.hashCode());
// // }
// // return map;
// // }

// // @PostMapping(value = "/select", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> selectUser(@RequestBody User user) {
// // Map<String, Object> map = new HashMap<>();
// // try {

// // map.put("status", 200);
// // } catch (Exception e) {
// // e.printStackTrace();
// // map.put("status", e.hashCode());
// // }
// // return map;
// // }

// // // 바로 비밀번호 찾기 한 경우
// // @RequestMapping(value = "/passwordfinder", method = RequestMethod.POST,
// // consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> findUserPW(@RequestBody User user){
// // Map<String, Object> map = new HashMap<>();

// // String dbid = uService.selectUserOne(user.getUserId()).getUserId();
// // String inputid = user.getUserId();
// // String dbphone = uService.selectUserOne(user.getUserId()).getUserPhone();
// // String inputphone = user.getUserPhone();
// // String dbbirth = uService.selectUserOne(user.getUserId()).getUserBirth();
// // String inputbirth = user.getUserBirth();

// // if(dbid == inputid && dbphone == inputphone && dbbirth == inputbirth){
// // User user1 = new User();
// // User dbuser = uService.selectUserOne(user.getUserId());
// // String inputPW = user.getUserPw();
// // user1.setUserId(dbuser.getUserId());
// // user1.setUserPw(inputPW);
// // user1.setUserName(dbuser.getUserName());
// // user1.setUserBirth(dbuser.getUserBirth());
// // user1.setUserDeletecheck(dbuser.getUserDeletecheck());
// // user1.setUserEditdate(dbuser.getUserEditdate());
// // user1.setUserEmail(dbuser.getUserEmail());
// // user1.setUserPhone(dbuser.getUserPhone());
// // int updatecheck = uService.updateUser(user);
// // if(updatecheck == 1){
// // map.put("status", 200);
// // }
// // else{
// // map.put("error", "수정 실패");
// // }
// // }
// // else{
// // // 정보가 일치하지않습니다.
// // map.put("status", 999);
// // }
// // return map;
// // }

// // @RequestMapping(path = "/passwordfinder", method = RequestMethod.GET,
// // consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> findUserPW(){
// // Map<String, Object> map = new HashMap<>();

// // // 페이지를 어디에서 왔는지 알수있는가
// // // 프론트에서 숫자를 던져주고 그걸로 구분해도 되는가
// // //

// // map.put("status", 200);

// // return map;
// // }

// // @GetMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> deleteUserGET() {
// // Map<String, Object> map = new HashMap<>();
// // map.put("status", 200);
// // return map;
// // }

// // @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> deleteUser(@RequestBody User user) {
// // Map<String, Object> map = new HashMap<>();
// // try {
// // if (uService.selectUserOne(user.getUserId()) != null)
// // uService.deleteUser(user);
// // map.put("status", 200);

// // // String dbid = uService.selectUserOne(user.getUserId()).getUserId();
// // // String inputid = user.getUserId();
// // // String dbpw = uService.selectUserOne(user.getUserId()).getUserPw();
// // // String inputpw = user.getUserPw();
// // // if(dbid == inputid && dbpw == inputpw){
// // // int deletecheck = uService.deleteUser(user);
// // // map.put("deletecheck", deletecheck);
// // // map.put("status", 200);
// // // }
// // // else{
// // // // 정보가 일치하지 않습니다.
// // // map.put("warning", 999);
// // // }

// // } catch (Exception e) {
// // e.printStackTrace();
// // map.put("status", e.hashCode());
// // }
// // return map;
// // }

// // @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> updateUser(@RequestBody User user) {
// // Map<String, Object> map = new HashMap<>();
// // try {
// // uService.updateUser(user);
// // map.put("status", 200);
// // } catch (Exception e) {
// // e.printStackTrace();
// // map.put("status", e.hashCode());
// // }
// // return map;
// // }

// // @GetMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces =
// // MediaType.APPLICATION_JSON_VALUE)
// // public Map<String, Object> updateUserGET(@RequestBody User user) {
// // Map<String, Object> map = new HashMap<>();
// // try {

// // // String dbpw = uService.selectUserOne(user.getUserId()).getUserPw();
// // // String inputpw = user.getUserPw();
// // // if(dbpw == inputpw){
// // // map.put("userdata", uservice.selectUserOne(user.getUserId()));
// // // map.put("status", 200);
// // // }
// // // else{
// // // // 비밀번호가 일치하지않습니다.
// // // map.put("warning", 999);
// // // }

// // } catch (Exception e) {
// // e.printStackTrace();
// // map.put("status", e.hashCode());
// // }
// // return map;
// // }

// }
