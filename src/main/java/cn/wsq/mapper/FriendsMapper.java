package cn.wsq.mapper;

import cn.wsq.entity.Friends;
import cn.wsq.entity.User;

import java.util.List;

public interface FriendsMapper {
    /*
    * 获取所有的数据
    * */
    public List<Friends> getList();
    /*
    * 根据id更新
    * */
    public void updateFriends(Friends friends);
    /*
    *添加数据
    * */
    public void addFriends(Friends friends);
    /*
    * 根据id删除数据
    * */
    public void deleteFriends(String id);
    /*
    * 根据实体类对象查询
    * */
    public List<Friends> searchByEntity(Friends friends);
    /*
    * 根据Id获取
    * */
    public Friends getFriendsById(String id);
    //精确查询
    public List<Friends> queryByEntity(Friends friends);

    List<User> queryByUserId(String id);

    void updateFriendAlias(Friends friendnote);
}
