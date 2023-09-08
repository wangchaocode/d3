package com.example.d3.lock.synctest.mysql;

import com.example.d3.exercise.config.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/6 15:38
 * @vVersion 1.0
 */
@Service
public class MyJdbcService {
    private JdbcConfig jdbcConfig=new JdbcConfig();

    public int insertOrUpdate(String sql,int i){
        System.out.println("插入 或更新...sql："+sql);
        PreparedStatement ps=null;
        boolean flag=false;
        List<Map> rtnList=null;
        try {
            Connection connection=jdbcConfig.getConnection();
            connection.setAutoCommit(false);
            ps=connection.prepareStatement(sql);
            flag =ps.execute();
            if(i<0){
                int m=i/0;
            }
            connection.commit();
            System.out.println("插入结果："+flag);
        } catch (Exception e) {
            if(i<0){
                System.out.println("回滚拉");
            }
        }
        return flag?1:0;
    }
    public void queryUser(String sql){
        jdbcConfig.doQueryMethod(sql);
    }
}
