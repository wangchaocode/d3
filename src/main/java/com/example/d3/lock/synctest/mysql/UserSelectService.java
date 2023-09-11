package com.example.d3.lock.synctest.mysql;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.lock.synctest.mysql.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/7 15:31
 * @vVersion 1.0
 */
@Slf4j
@Service
public class UserSelectService  extends SynchronizedExample {
    @Autowired
    private UserMapper userMapper;
    @Transactional(rollbackFor = Exception.class
            ,isolation = Isolation.READ_COMMITTED
    )
    public User selectUser(){
        System.out.println("进入查询事务...");
        User u=userMapper.selectUser();
        System.out.println("查询事务完成，到的user:"+u.toString());
        return u;
    }

    /**
     * 这里我要进行更新数据
     * @param i
     */
    @Override
    public void doSomeThing(int i) {
        User u=selectUser();

    }
}
