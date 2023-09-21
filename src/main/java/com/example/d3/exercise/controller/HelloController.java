package com.example.d3.exercise.controller;

import com.example.d3.exercise.config.domain.Pig;
import com.example.d3.exercise.config.domain.Sheep;
import com.example.d3.exercise.config.domain.Tom;
import com.example.d3.exercise.domain.UserEntity;
import com.example.d3.exercise.domain.mq.two.MqCustomer;
import com.example.d3.exercise.domain.mq.two.MqProducer;
import com.example.d3.exercise.service.IRunQuotaService;
import com.example.d3.exercise.service.eventpush.EventSend;
import com.example.d3.exercise.service.eventpush.LoginSuccessEvent;
import com.example.d3.lock.synctest.SynchronizedExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangchao
 * @date 2023/8/17 08:45:53
 */
@Slf4j
@RestController
public class HelloController {
    @Autowired
    private IRunQuotaService runQuotaService;
    @Autowired
    private Tom tom;
    @Autowired
    private Pig pig;
    @Autowired
    private Pig pig2;
    @Autowired
    private Sheep sheep;
    @Autowired
    private Sheep sheep2;
    @Autowired
    EventSend eventSend;
    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    @Qualifier(value="mqCustomer")
    private SynchronizedExample synchronizedExample;

    @Autowired
    private MqProducer mqProducer;
    @GetMapping("h")
    public String getInfos(){
        try {
            runQuotaService.register();
        } catch (Exception e) {
            log.error("getClass error,{}",e.getMessage());
        }
        return "ok";
    }
    @GetMapping("c")
    public String getConfigss() {
//        pig.setAge(33);
//        return pig.toString() + "\n"+ pig2.toString();
        sheep2.setAge(39);
        return sheep.toString()+"\n"+sheep2.toString();
    }
    @GetMapping("login")
    public Map login(@RequestBody  UserEntity userEntity) {
        if (StringUtils.isEmpty(userEntity.getUsername())){
            userEntity.setUsername("张三");
        }
        LoginSuccessEvent loginSuccessEvent=new LoginSuccessEvent(userEntity);
        eventSend.sentEvent(loginSuccessEvent);
        Map h=new HashMap();
        h.put("user",userEntity);
      return h;
    }

    @GetMapping("randomPutJedis")
    public void randomPutJedis() {
        String s=UUID.randomUUID().toString();
        LoginSuccessEvent loginSuccessEvent=new LoginSuccessEvent(
                new UserEntity(s,s,new HashMap())
        );
        eventSend.sentEvent(loginSuccessEvent);
        log.info("randomPutJedis-------------over");
        jedisCluster.set(s,s);
    }


    @GetMapping("getRedis")
    public Map getRedis(String key) {
        Map map=new HashMap();
        map.put("jedisCluster:"+key,StringUtils.isEmpty(key)?jedisCluster.get("*"):jedisCluster.get(key));
        return map;
    }

    @GetMapping("mqAddUser")
    public void mqAddUser(){
        synchronizedExample.setExample(new MqCustomer());
        synchronizedExample.runMyMethod();
    }

    @GetMapping("setUser")
    public void setUser(int num) throws IOException {
        synchronizedExample.setExample(new MqProducer());
        for(int i=0;i<num/5;i++){
            synchronizedExample.runMyMethod();
        }
    }

}
