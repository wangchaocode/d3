package com.example.d3.exercise.controller;

import com.example.d3.exercise.domain.mq.back.BackExConfig;
import com.example.d3.exercise.domain.mq.comfirm.ConfirmConfig;
import com.example.d3.exercise.domain.mq.comfirm.MyConfirmCallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Instant;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/20 9:42
 * @vVersion 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/ttl")
public class RabbitMqController {
    //7.7.4. 消息生产者代码
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyConfirmCallBack myCallBack;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(myCallBack);

        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(myCallBack);
        log.info("设置confirm,设置mandatory");
    }
    @GetMapping("/sendMsg/{msg}")
    public String sendMsg(@PathVariable(name = "msg") String msg)
    {
        log.info(Instant.now().toString());
        rabbitTemplate.convertAndSend("X","XA","ttl=10的消息" +
                msg);
        rabbitTemplate.convertAndSend("X","XB","ttl=40的消息" +
                msg);

        return "Ok";
    }

    /**
     * 发延迟队列
     * @param msg
     * @param ttl
     * @return
     */
    @GetMapping("/sendMsg/{msg}/{ttl}")
    public String sendMsg(@PathVariable String msg,@PathVariable int ttl)
    {
        log.info(Instant.now().toString());
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME,
                DELAYED_ROUTING_KEY,"ttl=10的消息" +
                msg,coreelationData->{
                    coreelationData.getMessageProperties().setDelay(ttl);
                return coreelationData;
            }   );
        return "Ok";
    }

    @ApiOperation(value = "确认队列")
    @GetMapping("/sendCon/{msg}")
    public String sendMsg2(@PathVariable String msg )
    {
        log.info(Instant.now().toString());
        CorrelationData correlationData=new CorrelationData("1");
        send("key1",msg,correlationData, ConfirmConfig.CONFIRM_QUEUE_NAME);

        correlationData=new CorrelationData("2");
        send("key2",msg,correlationData, ConfirmConfig.CONFIRM_QUEUE_NAME);
        return "Ok";
    }

    private void send(String key1, String msg,CorrelationData correlationData,String ExName) {
        String routeKey=key1;
        rabbitTemplate.convertAndSend(ExName,
                routeKey,msg+routeKey,correlationData );
    }
    @ApiOperation(value = "备份交换机")
    @GetMapping("/sendBack/{msg}")
    public String sendMsgBack(@PathVariable String msg )
    {
        log.info(Instant.now().toString());
        CorrelationData correlationData=new CorrelationData("1");
        send("key1",msg,correlationData, BackExConfig.CONFIRM_EXCHANGE_NAME);

        correlationData=new CorrelationData("2");
        send("key2",msg,correlationData, BackExConfig.CONFIRM_EXCHANGE_NAME);
        return "Ok";
    }
    @GetMapping("/sendMsg2")
    public String sendMsg2()
    {
        Instant now=Instant.now();
        log.info("start:"+now.toString());
        for(int i=0;i<90000;i++){
            String msg="消息："+i;
            rabbitTemplate.convertAndSend("X","XA","ttl=10的消息" +
                    msg);
           /* rabbitTemplate.convertAndSend("X","XB","ttl=40的消息" +
                    msg);*/
        }
        Instant end= Instant.now();
        log.info("end:"+end.toString()+",消耗："+(end.toEpochMilli()-now.toEpochMilli()) );

        return "Ok";
    }

}
