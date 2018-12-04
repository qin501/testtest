package cn.wsq.service;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friends;

import java.util.List;

public interface FriendsService {
    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    public TableResult<Friends> getFriendsList(int offset, int limit);
    /*
    * 删除Ids集合
    * */
    public Result deleteByIds(String[] ids);
    /*
    * 保存或者是更新
    * */
    public Result saveOrUpdateFriends(Friends friends);
    /*
     * 根据Id获取
     * */
    public Friends getFriendsById(String id);
    /*
     * 根据实体类对象查询
     * */
    public List<Friends> searchByEntity(Friends friends);
    /*
     * 获取所有的数据
     * */
    public List<Friends> getList();
    /*
    *分页和条件查询
    **/
    public TableResult<Friends> getSearchPage(int offset, int limit, Friends friends);

}
