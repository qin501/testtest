package cn.wsq.controller;

import cn.wsq.entity.User;
import cn.wsq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String userLogin(User user){

        return "ok";
    }
}
