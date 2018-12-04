package cn.wsq.controller;

import cn.wsq.entity.Friends;
import cn.wsq.entity.User;
import cn.wsq.service.UserService;
import cn.wsq.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
* 用户功能
* */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /*
    * 用户登录实现
    * */
    @RequestMapping("/login")
    public JSONResult userLogin(@RequestBody User user, HttpServletRequest request){
        JSONResult result=userService.login(user);
        if(result.isOK()){
            request.getSession().setAttribute("userLogin",result.getData());
        }
        return result;
    }
    public JSONResult getFriendList(HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        List<Friends> list=userService.getFriendList(userLogin.getId());
        return null;
    }

}
