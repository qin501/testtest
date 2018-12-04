package cn.wsq.mapper;

import cn.wsq.entity.User;

import java.util.List;

public interface UserMapper {
    /*
    * 获取所有的数据
    * */
    public List<User> getList();
    /*
    * 根据id更新
    * */
    public void updateUser(User user);
    /*
    *添加数据
    * */
    public void addUser(User user);
    /*
    * 根据id删除数据
    * */
    public void deleteUser(String id);
    /*
    * 根据实体类对象查询
    * */
    public List<User> searchByEntity(User user);
    /*
    * 根据Id获取
    * */
    public User getUserById(String id);
}
