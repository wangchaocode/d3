package com.example.d3.exercise.controller;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.mysql.MyJdbcService;
import com.example.d3.lock.synctest.mysql.UserSelectService;
import com.example.d3.lock.synctest.mysql.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/6 15:05
 * @vVersion 1.0
 */
@RestController
public class MysqlController {
    @Autowired
    private MyJdbcService myJdbcService;
    @Autowired
    private UserSelectService userSelectService;
    @Autowired
    private UserUpdateService userUpdateService;

    public void updateMethod(int i) throws SQLException {
        System.out.println(i+"------------updateMethod-------start---------");
        String sql="update user set name='张"+i+"' where id =1";
        myJdbcService.insertOrUpdate(sql,i);
        System.out.println(i+"------------updateMethod-------end---------");
    }
    @GetMapping("/queryUser")
    public String queryUser(int i) throws SQLException {
        updateMethod(100);
        myJdbcService.queryUser("select * from user");

        updateMethod(i);
        System.out.println("----------------------end----------------------");
        return "ok";
    }
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody  User user) {
        userUpdateService.updateUser(user);
        System.out.println("----------------------end----------------------");
        return "ok";
    }

    @PostMapping("/addUser")
    @Transactional(rollbackFor = Exception.class)
    public String addUser(@RequestBody  User user) {
        userUpdateService.addUser(user);
        System.out.println("----------------------end----------------------");
        return "ok";
    }

    @GetMapping("/selectUser")
    public User selectUser() {
        System.out.println("selectUser============");
        return userSelectService.selectUser();

    }
}
