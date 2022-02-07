package me.jungboke.baekshop.web.controller;

import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.web.controller.login.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name = SessionConst.Login_Member, required = false) Member loginMember, Model model) {
        log.info("home controller");
        if (loginMember == null) {
            return "home";
        }

        return "loginhome";
    }
}
