package cn.wsq.service;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friends;
import cn.wsq.entity.User;
import cn.wsq.util.JSONResult;

import java.util.List;

public interface UserService {
    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    public TableResult<User> getUserList(int offset, int limit);
    /*
    * 删除Ids集合
    * */
    public Result deleteByIds(String[] ids);
    /*
    * 保存或者是更新
    * */
    public Result saveOrUpdateUser(User user);
    /*
     * 根据Id获取
     * */
    public User getUserById(String id);
    /*
     * 根据实体类对象查询
     * */
    public List<User> searchByEntity(User user);
    /*
     * 获取所有的数据
     * */
    public List<User> getList();
    /*
    *分页和条件查询
    **/
    public TableResult<User> getSearchPage(int offset, int limit, User user);

    public JSONResult login(User user);

    List<Friends> getFriendList(String id);

    JSONResult userRegitser(User user);
}
