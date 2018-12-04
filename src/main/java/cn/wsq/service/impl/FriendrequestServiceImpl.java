package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.Friendrequest;
import cn.wsq.mapper.FriendrequestMapper;
import cn.wsq.service.FriendrequestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class FriendrequestServiceImpl implements FriendrequestService {
    @Autowired
    private FriendrequestMapper friendrequestMapper;

    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    @Override
    public TableResult<Friendrequest> getFriendrequestList(int offset, int limit) {
        //int pageNum, int pageSize
        //offset=(pageNum-1)*limit
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Friendrequest> friendrequestList = friendrequestMapper.getList();
            PageInfo pageInfo = new PageInfo(friendrequestList);
            TableResult<Friendrequest> result = new TableResult<Friendrequest>();
            result.setRows(friendrequestList);
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
            friendrequestMapper.deleteFriendrequest(ids[i]);
        }
        return new Result(200,"成功了");
    }

    @Override
    @Transactional
    public Result saveOrUpdateFriendrequest(Friendrequest friendrequest) {
       TrimSpace.trim(friendrequest);
        if (friendrequest!=null){
            //更新
            if(friendrequest.getId()!=null){
                friendrequestMapper.updateFriendrequest(friendrequest);
            //保存
            }else {
                friendrequestMapper.addFriendrequest(friendrequest);
            }
        }
        return new Result(200,"成功了");
    }

    @Override
    public Friendrequest getFriendrequestById(String id) {
        Friendrequest friendrequest = friendrequestMapper.getFriendrequestById(id);
        return friendrequest;
    }

    @Override
    public List<Friendrequest> searchByEntity(Friendrequest friendrequest) {
        TrimSpace.trim(friendrequest);
        List<Friendrequest> friendrequests = friendrequestMapper.searchByEntity(friendrequest);
        return friendrequests;
    }

    @Override
    public List<Friendrequest> getList() {
        List<Friendrequest> list = friendrequestMapper.getList();
        return list;
    }
    @Override
    public TableResult<Friendrequest> getSearchPage(int offset, int limit, Friendrequest friendrequest) {
        TrimSpace.trim(friendrequest);
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Friendrequest> friendrequestList=new ArrayList<Friendrequest>();
            if(friendrequest==null){
                friendrequestList = friendrequestMapper.getList();
            }else{
                friendrequestList = friendrequestMapper.searchByEntity(friendrequest);
            }
            PageInfo pageInfo = new PageInfo(friendrequestList);
            TableResult<Friendrequest> result = new TableResult<Friendrequest>();
            result.setRows(friendrequestList);
            result.setTotal((int) pageInfo.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
