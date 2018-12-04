package cn.wsq.controller;

import cn.wsq.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @RequestMapping("/login")
    public String userLogin(User user){
        return "ok";
    }
}
