package cn.wsq.controller;

import cn.wsq.entity.Friends;
import cn.wsq.entity.UploadImg;
import cn.wsq.entity.User;
import cn.wsq.service.UserService;
import cn.wsq.util.JSONResult;
import cn.wsq.util.JerseyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/*
* 用户功能
* */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${WebImgServerUrl}")
    private String WebImgServerUrl;
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
    /*
    * 用户注册实现
    * */
    @RequestMapping("/register")
    public JSONResult userRegitser(@RequestBody User user){
        JSONResult result=userService.userRegitser(user);
        return result;
    }
    /*
    * 查找用户
    * */
    @RequestMapping("frientRequest")
    public JSONResult frientRequest(@RequestBody User user,HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        user.setId(userLogin.getId());
        JSONResult result=userService.frientRequest(user);
        return result;
    }
    /*
    * 保存添加好友请求
    * */
    @RequestMapping("saveUserRequest")
    public JSONResult saveUserRequest(HttpServletRequest request,@RequestBody Friends friends){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.saveUserRequest(userLogin.getId(),friends.getUserId());
        return result;
    }
    /*
    * 修改昵称
    * */
    @RequestMapping("UpdateNickname")
    public JSONResult updateNickname(HttpServletRequest request,@RequestBody User user){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.updateNickname(userLogin.getId(),user.getNickname());
        if(result.getStatus()==200){
            userLogin.setNickname(user.getNickname());
            result.setData(userLogin);
        }
        return result;
    }
    /*
    * 获取新朋友列表
    * */
    @RequestMapping("getNewFriendList")
    public JSONResult getNewFriendList(HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.getNewFriendList(userLogin);
        return result;
    }
    /*
    * 保存好友请求
    * */
    @RequestMapping("saveFriendRequest")
    public JSONResult saveFriendRequest(HttpServletRequest request,@RequestBody Friends friendId){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.saveFriendRequest(userLogin.getId(),friendId.getFriendId());
        return result;
    }
    /*
    * 拒绝好友请求
    * */
    @RequestMapping("refuseFriendRequest")
    public JSONResult refuseFriendRequest(HttpServletRequest request,@RequestBody Friends friendId){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.refuseFriendRequest(userLogin.getId(),friendId.getFriendId());
        return result;
    }
    /*
    * 获取朋友列表
    * */
    @RequestMapping("getfriendList")
    public JSONResult getFriendList(HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.getFriendList(userLogin.getId());
        return result;
    }
    /*
    *获取未读信息列表
    * */
    @RequestMapping("getUnReadMessage")
    public JSONResult getUnReadMessage(HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        JSONResult result= userService.getUnReadMessage(userLogin.getId());
        return result;
    }
    /*
    * 用户上传头像
    * */
    @RequestMapping("uploadFaceBase64")
    public JSONResult uploadFaceBase64(@RequestBody UploadImg uploadImg, HttpServletRequest request){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        String base64url = uploadImg.getBase64url();
        //图片名
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int nextInt = random.nextInt(10);
        String name = "JMPT" + millis + nextInt;
        String url=WebImgServerUrl+name+".jpg";
        String thumpImgUrl="";
        thumpImgUrl=url;
        //MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String [] d = base64url.split("base64,");
        String dataPrix = "";
        String data = "";
        if(d != null && d.length == 2){
            dataPrix = d[0];
            data = d[1];
        }else{
            // return false;
        }
        byte[] bytes = Base64Utils.decodeFromString(data);

        JerseyUtils.upload(url,bytes);
        userLogin.setFaceicon(url);
        userService.updateImg(userLogin);
        // 更新用户头像
        return JSONResult.ok(userLogin);
    }
    /*
    * 修改好友备注
    * */
    @RequestMapping("updateFriendAlias")
    public JSONResult updateFriendAlias(HttpServletRequest request,@RequestBody Friends friendnote){
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if(userLogin==null){
            return JSONResult.errorMsg("用户没有登录");
        }
        friendnote.setUserId(userLogin.getId());
        JSONResult result=userService.updateFriendAlias(friendnote);
        return result;
    }

}
