package com.example.d3.exercise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


/**
 * @author wangchao
 * @Description:
 * @date 2023/9/6 16:10
 * @vVersion 1.0
 */
//@Configuration
//@EnableTransactionManagement
public class TransactionConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource){
        return  new DataSourceTransactionManager(dataSource);
    }
}
