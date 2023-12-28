package com.tl.datasource.service;

import com.tl.datasource.entity.Friend;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 17:14
 */
public interface FriendService {

    List<Friend> list();

    void save(Friend friend);

}
