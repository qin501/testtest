package cn.wsq.service.impl;

import cn.wsq.common.Result;
import cn.wsq.common.TableResult;
import cn.wsq.common.TrimSpace;
import cn.wsq.entity.Friends;
import cn.wsq.mapper.FriendsMapper;
import cn.wsq.service.FriendsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class FriendsServiceImpl implements FriendsService {
    @Autowired
    private FriendsMapper friendsMapper;

    /*
    * offset从第几条开始
    * limit查询多少条记录
    * */
    @Override
    public TableResult<Friends> getFriendsList(int offset, int limit) {
        //int pageNum, int pageSize
        //offset=(pageNum-1)*limit
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Friends> friendsList = friendsMapper.getList();
            PageInfo pageInfo = new PageInfo(friendsList);
            TableResult<Friends> result = new TableResult<Friends>();
            result.setRows(friendsList);
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
            friendsMapper.deleteFriends(ids[i]);
        }
        return new Result(200,"成功了");
    }

    @Override
    @Transactional
    public Result saveOrUpdateFriends(Friends friends) {
       TrimSpace.trim(friends);
        if (friends!=null){
            //更新
            if(friends.getId()!=null){
                friendsMapper.updateFriends(friends);
            //保存
            }else {
                friendsMapper.addFriends(friends);
            }
        }
        return new Result(200,"成功了");
    }

    @Override
    public Friends getFriendsById(String id) {
        Friends friends = friendsMapper.getFriendsById(id);
        return friends;
    }

    @Override
    public List<Friends> searchByEntity(Friends friends) {
        TrimSpace.trim(friends);
        List<Friends> friendss = friendsMapper.searchByEntity(friends);
        return friendss;
    }

    @Override
    public List<Friends> getList() {
        List<Friends> list = friendsMapper.getList();
        return list;
    }
    @Override
    public TableResult<Friends> getSearchPage(int offset, int limit, Friends friends) {
        TrimSpace.trim(friends);
        try {
            PageHelper.startPage(offset / limit + 1, limit);
            List<Friends> friendsList=new ArrayList<Friends>();
            if(friends==null){
                friendsList = friendsMapper.getList();
            }else{
                friendsList = friendsMapper.searchByEntity(friends);
            }
            PageInfo pageInfo = new PageInfo(friendsList);
            TableResult<Friends> result = new TableResult<Friends>();
            result.setRows(friendsList);
            result.setTotal((int) pageInfo.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
