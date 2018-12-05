package cn.wsq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    /*
    * 用户退出
    * */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:loginPage";
    }
}
