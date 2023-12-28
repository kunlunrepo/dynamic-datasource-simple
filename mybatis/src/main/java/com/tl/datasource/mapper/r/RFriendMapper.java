package com.tl.datasource.mapper.r;


import com.tl.datasource.entity.Friend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RFriendMapper {
    @Select("SELECT * FROM friend")
    List<Friend> list();

    @Insert("INSERT INTO  friend(`name`) VALUES (#{name})")
    void save(Friend friend);
}