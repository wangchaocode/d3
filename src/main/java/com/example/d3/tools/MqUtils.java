package com.example.d3.tools;

import com.example.d3.exercise.domain.mq.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangchao
 * @Description:rabbitMq工具类
 * @date 2023/9/14 9:43
 * @vVersion 1.0
 */
public class MqUtils {

    public static Channel channel;
    public static final String DEFAULT_QUEUE_NAME="default_queue";

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