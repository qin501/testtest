package cn.wsq.service;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.entity.Msg;

import java.util.List;

public interface MsgService {
    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    public TableResult<Msg> getMsgList(int offset, int limit);
    /*
    * 删除Ids集合
    * */
    public Result deleteByIds(String[] ids);
    /*
    * 保存或者是更新
    * */
    public Result saveOrUpdateMsg(Msg msg);
    /*
     * 根据Id获取
     * */
    public Msg getMsgById(String id);
    /*
     * 根据实体类对象查询
     * */
    public List<Msg> searchByEntity(Msg msg);
    /*
     * 获取所有的数据
     * */
    public List<Msg> getList();
    /*
    *分页和条件查询
    **/
    public TableResult<Msg> getSearchPage(int offset, int limit, Msg msg);

}
