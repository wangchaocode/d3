package com.example.d3.lock.synctest.mysql.mapper;

import com.example.d3.exercise.config.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/7 15:27
 * @vVersion 1.0
 */
public interface UserMapper {

    public int updateUser(User user);

    int  insertUser2(User user);
    User selectUser();
}
