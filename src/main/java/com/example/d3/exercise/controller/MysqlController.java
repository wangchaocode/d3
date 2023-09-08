package com.example.d3.exercise.controller;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.SyncClass;
import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.lock.synctest.mysql.MyJdbcService;
import com.example.d3.lock.synctest.mysql.User2Service;
import com.example.d3.lock.synctest.mysql.UserService;
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
    private UserService userService;
    @Autowired
    private User2Service userService2;

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

        userService2.updateUser(user);

        User u=userService2.selectUser();

        System.out.println("user:"+u.toString());
        System.out.println("----------------------end----------------------");
        return "ok";
    }

    @PostMapping("/addUser")
    @Transactional(rollbackFor = Exception.class)
    public String addUser(@RequestBody  User user) {
        userService2.addUser(user);
        System.out.println("----------------------end----------------------");
        return "ok";
    }

    @GetMapping("/selectUser")
    public void selectUser() {
        System.out.println("当前user:"+userService2.selectUser().toString());
        SynchronizedExample sy=new SyncClass();
        sy.setExample(userService2);
        sy.runMyMethod();
        sy.setExample(userService);
        sy.runMyMethod();
        System.out.println("selectUser============end");
    }
}
