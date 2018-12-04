package cn.wsq.service;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Friendrequest;

import java.util.List;

public interface FriendrequestService {
    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    public TableResult<Friendrequest> getFriendrequestList(int offset, int limit);
    /*
    * 删除Ids集合
    * */
    public Result deleteByIds(String[] ids);
    /*
    * 保存或者是更新
    * */
    public Result saveOrUpdateFriendrequest(Friendrequest friendrequest);
    /*
     * 根据Id获取
     * */
    public Friendrequest getFriendrequestById(String id);
    /*
     * 根据实体类对象查询
     * */
    public List<Friendrequest> searchByEntity(Friendrequest friendrequest);
    /*
     * 获取所有的数据
     * */
    public List<Friendrequest> getList();
    /*
    *分页和条件查询
    **/
    public TableResult<Friendrequest> getSearchPage(int offset, int limit, Friendrequest friendrequest);

}
