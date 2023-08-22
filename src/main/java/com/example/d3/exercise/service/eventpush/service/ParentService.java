package com.example.d3.exercise.service.eventpush.service;

import com.example.d3.exercise.domain.UserEntity;
import com.example.d3.exercise.service.eventpush.LoginSuccessEvent;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/21 14:06
 * @vVersion 1.0
 */
@Service
@Slf4j
public class ParentService implements ApplicationListener<LoginSuccessEvent> {
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        log.info("到达事件："+event.getSource());
        UserEntity userEntity= (UserEntity) event.getSource();
        redisTemplate.opsForValue().set(userEntity.getUsername(),userEntity,10, TimeUnit.MINUTES);
        doMethod(userEntity);
    }
    void doMethod(UserEntity userEntity){
    }
    void logInfo(String msg){
        log.info(msg);
    }
}
