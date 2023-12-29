package com.tl.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tl.datasource.entity.Friend;
import com.tl.datasource.mapper.FriendMapper;
import com.tl.datasource.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    @DS("slave_1")
    public List<Friend> list() {
        return friendMapper.list();
    }

    @Override
    @DS("master")
    @Transactional(rollbackFor = Exception.class)
    public void save(Friend friend) {
        friendMapper.save(friend);
    }
}
