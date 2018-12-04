package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.User;
import cn.wsq.mapper.UserMapper;
import cn.wsq.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

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
}
