package cn.wsq.mapper;

import cn.wsq.entity.Msg;

import java.util.List;

public interface MsgMapper {
    /*
    * 获取所有的数据
    * */
    public List<Msg> getList();
    /*
    * 根据id更新
    * */
    public void updateMsg(Msg msg);
    /*
    *添加数据
    * */
    public void addMsg(Msg msg);
    /*
    * 根据id删除数据
    * */
    public void deleteMsg(String id);
    /*
    * 根据实体类对象查询
    * */
    public List<Msg> searchByEntity(Msg msg);
    /*
    * 根据Id获取
    * */
    public Msg getMsgById(String id);
}
