package cn.wsq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("loginPage")
    public String login(){
        return "login.html";
    }
    @RequestMapping("registerPage")
    public String register(){
        return "register.html";
    }
}
