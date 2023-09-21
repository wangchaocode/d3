package com.example.d3.tools;

import com.example.d3.exercise.domain.mq.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author wangchao
 * @Description:rabbitMq工具类
 * @date 2023/9/14 9:43
 * @vVersion 1.0
 */
public class MqUtils {

    public static Channel channel;
    public static final String DEFAULT_QUEUE_NAME=
            "ACK_QUEUE_NAME_persist2";
    public static final String RANDOM_QUEUE_NAME=UUID.randomUUID().toString();
    /**
     * 交换机
     */
    public static final String DEFAULT_EXCHANGE_NAME_FANOUT="FANOUT_ex";
    public static final String DEFAULT_EXCHANGE_NAME_DIRECT="DIRECT_ex";
    public static final String DEFAULT_EXCHANGE_NAME_TOPIC="TOPIC_ex";
    public static final String DEFAULT_EXCHANGE_NAME_HEADER="HEADER_ex";

    public static final String NORMAL_EXCHANGE_NAME="normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String  DEAD_PRE="x-dead-letter-";

    public static Map<String,Object> getInitMap(String exName,String routeKey,int testMaxNum, int ttl){
        Map<String,Object> pm=new HashMap<>();
        pm.put(DEAD_PRE+"exchange",exName);
        if(!"".equals(routeKey)) pm.put(DEAD_PRE+"routing-key",routeKey);
        if(testMaxNum>0) pm.put("x-max-length",testMaxNum);
        if(ttl>0)pm.put("x-message-ttl",ttl);
        return pm;
    }


    public static Channel getConnection(){
        if (null ==channel|| !channel.isOpen()){
            System.out.println("重新获取channel");
            channel=createChannel();
        }
        return channel;
    }
    public static Channel createChannel(){
        ConnectionFactory factory=new ConnectionFactory();
        RabbitMQConfig rb =ReadProperties.getRabbitMqConf();
        factory.setHost(rb.getHost());
        factory.setUsername(rb.getUser());
        factory.setPassword(rb.getPwd());
        Channel channel=null;
        try {
            Connection connection=factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

}
