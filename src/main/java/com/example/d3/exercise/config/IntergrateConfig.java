package com.example.d3.exercise.config;

import com.example.d3.exercise.config.domain.Pig;
import com.example.d3.exercise.config.domain.Sheep;
import com.example.d3.exercise.config.domain.Tom;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author wangchao
 * @Description: 统一管理
 * @date 2023/8/17 10:29
 * @vVersion 1.0
 */
@EnableConfigurationProperties(Sheep.class) //导入第三方写好的组件进行属性绑定
@SpringBootApplication
public class IntergrateConfig {
    @Scope("prototype")
    @Bean
    @ConfigurationProperties(prefix = "pig")
    public Pig pig(){
        return new Pig();
    }
    @Bean
    @ConfigurationProperties(prefix = "tom")
    public Tom tom(){
        return new Tom("zhangsan",19);
    }

}
