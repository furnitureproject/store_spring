package com.team.controller.user;

import com.team.aes.AesCording;
import com.team.entity.User;
import com.team.entity.UserProjection;
import com.team.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    UserService uService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SpringTemplateEngine templateEngine;

    @RequestMapping(value = "pwchange", method = RequestMethod.POST)
    public String pwchangeGet(RedirectAttributes redirect, @ModelAttribute User user, @RequestParam String email)
            throws Exception {
        AesCording aesCording = new AesCording();
        String decordemail = aesCording.decrypt(email);
        UserProjection user1 = uService.selectUserByEmail(decordemail);
        String userid = user1.getuserId();
        User user2 = uService.selectUserOne(userid);
        if (user2.getPwChangeCheck() == 1 && user.getUserPw().length() > 1) {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            user2.setUserPw(bcpe.encode(user.getUserPw()));
            user2.setPwChangeCheck(0);
            uService.updateUser(user2);
            redirect.addAttribute("msg", "비밀번호 변경 성공");
            redirect.addAttribute("url", "../");
        } else {
            redirect.addAttribute("msg", "비밀번호 변경 실패");
            redirect.addAttribute("url", "../");
        }
        return "redirect:pwchangem";
    }

    @RequestMapping(value = "pwchangem", method = RequestMethod.GET)
    public String pwChangeMsg(Model model, @RequestParam("msg") String msg, @RequestParam("url") String url) {
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        return "pwmsg";
    }

}
