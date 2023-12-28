package com.tl.datasource.controller;

import com.tl.datasource.entity.Friend;
import com.tl.datasource.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 17:12
 */
@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping(value = "select")
    public List<Friend> select() {
        return friendService.list();
    }

    @GetMapping(value = "insert")
    public String insert() {
        Friend friend = new Friend();
        friend.setName("loulan");
        friendService.save(friend);
        return "data added";
    }

}
