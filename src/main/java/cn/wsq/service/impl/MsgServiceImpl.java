package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.Msg;
import cn.wsq.mapper.MsgMapper;
import cn.wsq.service.MsgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgMapper msgMapper;

    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    @Override
    public TableResult<Msg> getMsgList(int offset, int limit) {
        //int pageNum, int pageSize
        //offset=(pageNum-1)*limit
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Msg> msgList = msgMapper.getList();
            PageInfo pageInfo = new PageInfo(msgList);
            TableResult<Msg> result = new TableResult<Msg>();
            result.setRows(msgList);
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
            msgMapper.deleteMsg(ids[i]);
        }
        return new Result(200,"成功了");
    }

    @Override
    @Transactional
    public Result saveOrUpdateMsg(Msg msg) {
       TrimSpace.trim(msg);
        if (msg!=null){
            //更新
            if(msg.getId()!=null){
                msgMapper.updateMsg(msg);
            //保存
            }else {
                msgMapper.addMsg(msg);
            }
        }
        return new Result(200,"成功了");
    }

    @Override
    public Msg getMsgById(String id) {
        Msg msg = msgMapper.getMsgById(id);
        return msg;
    }

    @Override
    public List<Msg> searchByEntity(Msg msg) {
        TrimSpace.trim(msg);
        List<Msg> msgs = msgMapper.searchByEntity(msg);
        return msgs;
    }

    @Override
    public List<Msg> getList() {
        List<Msg> list = msgMapper.getList();
        return list;
    }
    @Override
    public TableResult<Msg> getSearchPage(int offset, int limit, Msg msg) {
        TrimSpace.trim(msg);
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Msg> msgList=new ArrayList<Msg>();
            if(msg==null){
                msgList = msgMapper.getList();
            }else{
                msgList = msgMapper.searchByEntity(msg);
            }
            PageInfo pageInfo = new PageInfo(msgList);
            TableResult<Msg> result = new TableResult<Msg>();
            result.setRows(msgList);
            result.setTotal((int) pageInfo.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
