package com.example.d3.lock.synctest.mysql;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.lock.synctest.mysql.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/7 15:31
 * @vVersion 1.0
 */
@Slf4j
@Service
public class User2Service extends SynchronizedExample {
    @Autowired
    private UserMapper userMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int updateUser(User u){
        System.out.println("updateUser开始事务....u:"+u.toString());

        int i=userMapper.updateUser(u);

        User u2=selectUser();
        System.out.println("user2 ："+u2.toString());
        if (u.getAge()<0){
            int m = 1/0;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("更新事务完成："+u.toString());
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        int j=userMapper.insertUser2(user);
        if (user.getAge()<0){
            int m = 1/0;
        }
        log.info("add--------j--------",j);

    }

    @Transactional(rollbackFor = Exception.class)
    public User selectUser(){
        return userMapper.selectUser();
    }

    /**
     * 这里我要进行更新数据
     * @param i
     */
    @Override
    public void doSomeThing(int i) {
        int age=new Random().nextInt(100);
        User u=new User(1L,"张1万",age);
        updateUser(u);

    }
}
