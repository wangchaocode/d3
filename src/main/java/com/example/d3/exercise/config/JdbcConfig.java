package com.example.d3.exercise.config;

import com.example.d3.tools.CollectionTools;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/5 16:34
 * @vVersion 1.0
 */
//@Component
public class JdbcConfig  {
    Connection connection=null;

    public Connection getConnection() {
        try {
            if(null == connection || connection.isClosed()){
                System.out.println("重新获取...");
                connection=getMyConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

   /* @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if(null == connection){
            System.out.println("重新获取...");
            connection=getMyConnection();
        }
        return connection;
    }
*/
    public Connection getMyConnection(){
        // 读取配置文件
        Properties properties = new Properties();
        try {
            ClassLoader cc=JdbcConfig.class.getClassLoader();
            ;
            properties.load(cc.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取数据库连接信息
        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        // 加载数据库驱动
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("数据库连接成功");
        // 建立数据库连接
        Connection connection=null;
        try  {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Map> getResult(String sql){
        System.out.println("查询...");
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Map> rtnList=null;
        try {
            ps=getConnection().prepareStatement(sql);
            rs=ps.executeQuery();
            rtnList= CollectionTools.convertList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rtnList;
    }


    public void closeResource(Connection c, Statement s){
        System.out.println("关流");
        if(null !=c){
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null !=s){
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void doQueryMethod(String sql){
        List<Map> rtnList=this.getResult(sql);
        rtnList.forEach(map -> System.out.println(map));
    }
/*

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }*/
}
