package com.tl.datasource.service.impl;

import com.tl.datasource.anno.WR;
import com.tl.datasource.entity.Friend;
import com.tl.datasource.mapper.FriendMapper;
import com.tl.datasource.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 17:25
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Override
//    @WR("R")
    public List<Friend> list() {
        return friendMapper.list();
    }

    @Override
//    @WR("W")
    public void save(Friend friend) {
        friendMapper.save(friend);
    }
}
