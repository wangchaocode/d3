package com.example.d3.exercise.domain.mq.one.dead;

import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/19 16:54
 * @vVersion 1.0
 */
public class DeadCusumer1 {
    //普通交换机名称
    //死信交换机名称

    public static void main(String[] args) throws IOException {
        String deadQ="deadQ",routeKey="lisi";
        Channel channel = MqUtils.getConnection();

        channel.exchangeDeclare(MqUtils.DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);


        channel.queueDeclare(deadQ,true,false,false,null);
        channel.queueBind(deadQ,MqUtils.DEAD_EXCHANGE,routeKey);
        System.out.println("等死信。。。。");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Consumer01 接收到消息"+message);
        };
        channel.basicConsume(deadQ, true, deliverCallback, consumerTag -> {
        });
    }
}
