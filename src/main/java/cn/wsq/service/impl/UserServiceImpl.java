package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.Friendrequest;
import cn.wsq.entity.Friends;
import cn.wsq.entity.Msg;
import cn.wsq.entity.User;
import cn.wsq.mapper.FriendrequestMapper;
import cn.wsq.mapper.FriendsMapper;
import cn.wsq.mapper.MsgMapper;
import cn.wsq.mapper.UserMapper;
import cn.wsq.nettyServer.ChatMsg;
import cn.wsq.service.MsgService;
import cn.wsq.service.UserService;
import cn.wsq.util.IDUtils;
import cn.wsq.util.JSONResult;
import cn.wsq.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendsMapper friendsMapper;
    @Autowired
    private FriendrequestMapper friendrequestMapper;
    @Autowired
    private MsgMapper msgMapper;
    @Value("${Faceicon}")
    private String Faceicon;

    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    @Override
    public TableResult<User> getUserList(int offset, int limit) {
        //int pageNum, int pageSize
        //offset=(pageNum-1)*limit
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<User> userList = userMapper.getList();
            PageInfo pageInfo = new PageInfo(userList);
            TableResult<User> result = new TableResult<User>();
            result.setRows(userList);
            result.setTotal((int) pageInfo.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Result deleteByIds(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            userMapper.deleteUser(ids[i]);
        }
        return new Result(200,"成功了");
    }

    @Override
    @Transactional
    public Result saveOrUpdateUser(User user) {
       TrimSpace.trim(user);
        if (user!=null){
            //更新
            if(user.getId()!=null){
                userMapper.updateUser(user);
            //保存
            }else {
                userMapper.addUser(user);
            }
        }
        return new Result(200,"成功了");
    }

    @Override
    public User getUserById(String id) {
        User user = userMapper.getUserById(id);
        return user;
    }

    @Override
    public List<User> searchByEntity(User user) {
        TrimSpace.trim(user);
        List<User> users = userMapper.searchByEntity(user);
        return users;
    }

    @Override
    public List<User> getList() {
        List<User> list = userMapper.getList();
        return list;
    }
    @Override
    public TableResult<User> getSearchPage(int offset, int limit, User user) {
        TrimSpace.trim(user);
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<User> userList=new ArrayList<User>();
            if(user==null){
                userList = userMapper.getList();
            }else{
                userList = userMapper.searchByEntity(user);
            }
            PageInfo pageInfo = new PageInfo(userList);
            TableResult<User> result = new TableResult<User>();
            result.setRows(userList);
            result.setTotal((int) pageInfo.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 登录功能检验
    * */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult login(User user) {
        if(user==null)return JSONResult.errorMsg("用户名或密码错误了");
        if(StringUtils.isNotBlank(user.getUsername())&&StringUtils.isNotBlank(user.getPassword())){
            User u=new User();
            u.setUsername(user.getUsername());
            List<User> users = userMapper.queryByEntity(u);
            //查找不到用户
            if(users==null||users.size()==0)return JSONResult.errorMsg("用户名或密码错误了");
            u=users.get(0);
            //登录成功
            try {
                if(MD5Utils.getMD5Str(user.getPassword()).equals(u.getPassword())){
                    return JSONResult.ok(u);
                }
                return JSONResult.errorMsg("用户名或密码错误了");
            } catch (Exception e) {
                e.printStackTrace();
                return JSONResult.errorMsg("用户名或密码错误了");
            }
        }else{
            return JSONResult.errorMsg("用户名或密码错误了");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult getFriendList(String id) {
        List<User> list=friendsMapper.queryByUserId(id);
        if(list!=null&&list.size()!=0){

            return JSONResult.ok(list);
        }
        return JSONResult.ok(new ArrayList<User>());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONResult userRegitser(User user) {
        if(user==null)return JSONResult.errorMsg("用户名或密码错误了");
        if(StringUtils.isNotBlank(user.getUsername())&&StringUtils.isNotBlank(user.getPassword())
        &&StringUtils.isNotBlank(user.getNickname())) {
            User u=new User();
            u.setUsername(user.getUsername());
            List<User> users = userMapper.queryByEntity(u);
            //没有此用户
            if(users==null||users.size()==0){
                user.setId(IDUtils.getId());
                user.setCreateDate(new Date());
                try {
                    user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                user.setFaceicon(Faceicon);
                userMapper.addUser(user);
            }else{
                return JSONResult.errorMsg("用户名已存在，请重试");
            }
            return JSONResult.ok("注册成功");

        }else {
            return JSONResult.errorMsg("传入的参数有误");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult frientRequest(User user) {
        if(user==null||!StringUtils.isNotBlank(user.getUsername()))return JSONResult.errorMsg("传入的参数有误");
        User uu=new User();
        uu.setUsername(user.getUsername());
        List<User> users = userMapper.queryByEntity(uu);
        //没有找到
        if(users==null||users.size()==0){
            return JSONResult.errorMsg("没有找到此用户");
        }
        User u=users.get(0);
        //查找的是自己
        if(user.getId().equals(u.getId())){
            return JSONResult.errorMsg("不能添加自己为好友");
        }
        Friends friends=new Friends();
        friends.setUserId(user.getId());
        friends.setFriendId(u.getId());
        //如果此好友已经存在
        List<Friends> friendsList = friendsMapper.queryByEntity(friends);
        if(friendsList!=null&&friendsList.size()>0){
            return JSONResult.errorMsg("此好友已存在");
        }
       Friendrequest friendrequest = new Friendrequest();
        friendrequest.setSendId(user.getId());
        friendrequest.setAcceptId(u.getId());
        //查询是否好友请求表有此条记录
        List<Friendrequest> friendrequests = friendrequestMapper.queryByEntity(friendrequest);
        if(friendrequests!=null&&friendrequests.size()>0){
            return JSONResult.errorMsg("正在等待对方的回复");
        }

        return JSONResult.ok(u);
    }
    /*
    * 保存用户请求
    * */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONResult saveUserRequest(String id, String userId) {
        Friendrequest friendrequest=new Friendrequest();
        friendrequest.setSendId(id);
        if(!StringUtils.isNotBlank(userId))return JSONResult.errorMsg("参数有误");
        friendrequest.setAcceptId(userId);
        friendrequest.setRequest_data_time(new Date());
        friendrequest.setId(IDUtils.getId());
        friendrequestMapper.addFriendrequest(friendrequest);

        return JSONResult.ok("已添加到数据库请求表中");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult updateNickname(String id, String nickname) {
        if(!StringUtils.isNotBlank(nickname)){
            return JSONResult.errorMsg("参数有误");
        }
        User user = new User();
        user.setNickname(nickname);
        user.setId(id);
        userMapper.updateUser(user);

        return JSONResult.ok("修改成功");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult getNewFriendList(User userLogin) {
        List<User> userList =friendrequestMapper.selectByUserId(userLogin.getId());
        if(userList==null)return JSONResult.ok(new ArrayList<User>());
        for(User u:userList){
            u.setPassword("");
        }
        return JSONResult.ok(userList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult saveFriendRequest(String id, String friendId) {
        if(!StringUtils.isNotBlank(friendId))return JSONResult.errorMsg("参数有误");
        Friendrequest friendrequest=new Friendrequest();
        friendrequest.setSendId(friendId);
        friendrequest.setAcceptId(id);
        List<Friendrequest> friendrequests = friendrequestMapper.queryByEntity(friendrequest);
        if(friendrequests==null||friendrequests.size()==0)return JSONResult.errorMsg("系统错误了");
        //最终要删除的好友请求
        Friendrequest ff = friendrequests.get(0);
        friendrequestMapper.deleteFriendrequest(ff.getId());
        //添加到好友表里
        Friends friends=new Friends();
        friends.setId(IDUtils.getId());
        friends.setFriendId(friendId);
        friends.setUserId(id);
        User friendById = userMapper.getUserById(friendId);
        friends.setAlias(friendById.getNickname());
        friendsMapper.addFriends(friends);
        Friends f1=new Friends();
        f1.setId(IDUtils.getId());
        f1.setFriendId(id);
        f1.setUserId(friendId);
        User userById = userMapper.getUserById(id);
        f1.setAlias(userById.getNickname());
        friendsMapper.addFriends(f1);
        return JSONResult.ok("添加成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult refuseFriendRequest(String id, String friendId) {
        if(!StringUtils.isNotBlank(friendId))return JSONResult.errorMsg("参数有误");
        Friendrequest friendrequest=new Friendrequest();
        friendrequest.setSendId(friendId);
        friendrequest.setAcceptId(id);
        List<Friendrequest> friendrequests = friendrequestMapper.queryByEntity(friendrequest);
        if(friendrequests==null||friendrequests.size()==0)return JSONResult.errorMsg("系统错误了");
        //最终要删除的好友请求
        Friendrequest ff = friendrequests.get(0);
        friendrequestMapper.deleteFriendrequest(ff.getId());
        return JSONResult.ok("拒绝成功");
    }
    /*
    * 保存聊天记录在数据库
    * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveMsg(ChatMsg chatMsg) {
        Msg msg = new Msg();
        msg.setSendId(chatMsg.getSenderId());
        msg.setAcceptId(chatMsg.getReceiverId());
        msg.setSignFlag(1);
        msg.setMsg(chatMsg.getMsg());
        String id=IDUtils.getId();
        msg.setId(id);
        msg.setCreateTime(new Date());
        msgMapper.addMsg(msg);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult getUnReadMessage(String id) {
        Msg msg = new Msg();
        msg.setAcceptId(id);
        msg.setSignFlag(1);
        List<Msg> msgs = msgMapper.queryByEntity(msg);
        JSONResult result=JSONResult.ok(msgs);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult updateMsg(String senderId) {
        msgMapper.meupdateMsg(senderId);
        return JSONResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateImg(User userLogin) {
        userMapper.updateUser(userLogin);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult updateFriendAlias(Friends friendnote) {
        friendsMapper.updateFriendAlias(friendnote);
        return JSONResult.ok();
    }

}
