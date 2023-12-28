package com.tl.datasource.mapper;

import com.tl.datasource.entity.Friend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-27 17:12
 */
public interface FriendMapper {

    @Select("select * from friend")
    List<Friend> list();

    @Insert("insert into friend(`name`) values (#{name})")
    void save(Friend friend);


}
