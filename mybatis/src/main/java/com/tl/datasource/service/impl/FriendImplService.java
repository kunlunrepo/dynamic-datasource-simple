package com.tl.datasource.service.impl;

import com.tl.datasource.entity.Friend;
import com.tl.datasource.mapper.r.RFriendMapper;
import com.tl.datasource.mapper.w.WFriendMapper;
import com.tl.datasource.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-28 11:21
 */
@Service
public class FriendImplService implements FriendService {

    @Autowired
    private RFriendMapper rFriendMapper;

    @Autowired
    private WFriendMapper wFriendMapper;

    // 读 读库
    @Override
    public List<Friend> list() {
        return rFriendMapper.list();
    }

    // 写 写库
    @Override
    public void save(Friend friend) {
        wFriendMapper.save(friend);
    }
}
