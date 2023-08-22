package com.example.d3.exercise.config;

import com.example.d3.exercise.config.domain.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.*;

@Configuration
public class JedisClusterConfig {
  @Autowired
  JedisProperties jedisClusterProperties;
  @Bean("jedisCluster")
  public JedisCluster getJedisCluster(JedisPoolConfig jedisPoolConfigCluster) {
    List<String> nodesList = jedisClusterProperties.getNodes();
    Set<HostAndPort> nodesSet = new HashSet<>();
    for (String ipAndPort : nodesList) {
        String[] ipAndPortPair = ipAndPort.split(":");
        nodesSet.add(new HostAndPort(ipAndPortPair[0].trim(), Integer.parseInt(ipAndPortPair[1].trim())));
    }

    return new JedisCluster(nodesSet,
            jedisClusterProperties.getConnectionTimeout(),
            jedisClusterProperties.getSoTimeout(),
            jedisClusterProperties.getMaxRedirects(),
            jedisPoolConfigCluster
    );
  }
  @Bean("jedisPoolConfigCluster")
  public JedisPoolConfig jedisPoolConfigCluster() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(30);
    jedisPoolConfig.setMaxIdle(30);
    jedisPoolConfig.setMinIdle(1);
    jedisPoolConfig.setNumTestsPerEvictionRun(-1);
    jedisPoolConfig.setTestOnBorrow(true);
    jedisPoolConfig.setTestOnReturn(false);
    jedisPoolConfig.setBlockWhenExhausted(false);
    return jedisPoolConfig;
  }
}
