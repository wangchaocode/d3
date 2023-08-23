package com.example.d3.exercise.config.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/22 15:23
 * @vVersion 1.0
 * DispatcherServlet
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class JedisProperties {
    private List<String> nodes;
    private int connectionTimeout;
    private int soTimeout;
    private int maxRedirects;

}
