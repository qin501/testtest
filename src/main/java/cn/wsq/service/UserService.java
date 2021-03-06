package cn.wsq.service;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friends;
import cn.wsq.entity.User;
import cn.wsq.nettyServer.ChatMsg;
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

    JSONResult getFriendList(String id);

    JSONResult userRegitser(User user);

    JSONResult frientRequest(User user);

    JSONResult saveUserRequest(String id, String userId);

    JSONResult updateNickname(String id, String nickname);

    JSONResult getNewFriendList(User userLogin);

    JSONResult saveFriendRequest(String id, String friendId);

    JSONResult refuseFriendRequest(String id, String friendId);

    String saveMsg(ChatMsg chatMsg);

    JSONResult getUnReadMessage(String id);

    JSONResult updateMsg(String senderId);

    void updateImg(User userLogin);

    JSONResult updateFriendAlias(Friends friendnote);
}
