package com.tl.datasource.service;


import com.tl.datasource.entity.Friend;

import java.util.List;

public interface FriendService {
    List<Friend> list();

    void save(Friend friend);

}
