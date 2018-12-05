package cn.wsq.mapper;

import cn.wsq.entity.Friendrequest;
import cn.wsq.entity.User;

import java.util.List;

public interface FriendrequestMapper {
    /*
    * 获取所有的数据
    * */
    public List<Friendrequest> getList();
    /*
    * 根据id更新
    * */
    public void updateFriendrequest(Friendrequest friendrequest);
    /*
    *添加数据
    * */
    public void addFriendrequest(Friendrequest friendrequest);
    /*
    * 根据id删除数据
    * */
    public void deleteFriendrequest(String id);
    /*
    * 根据实体类对象查询
    * */
    public List<Friendrequest> searchByEntity(Friendrequest friendrequest);
    /*
    * 根据Id获取
    * */
    public Friendrequest getFriendrequestById(String id);
    //精确查询
    public List<Friendrequest> queryByEntity(Friendrequest friendrequest);
    
    List<User> selectByUserId(String id);
}
