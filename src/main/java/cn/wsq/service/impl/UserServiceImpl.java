package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.Friends;
import cn.wsq.entity.User;
import cn.wsq.mapper.FriendsMapper;
import cn.wsq.mapper.UserMapper;
import cn.wsq.service.UserService;
import cn.wsq.util.IDUtils;
import cn.wsq.util.JSONResult;
import cn.wsq.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Friends> getFriendList(String id) {
        
        return null;
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
                userMapper.addUser(user);
            }else{
                return JSONResult.errorMsg("用户名已存在，请重试");
            }
            return JSONResult.ok("注册成功");

        }else {
            return JSONResult.errorMsg("传入的参数有误");
        }
    }
}
