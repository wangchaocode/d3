package com.example.d3.exercise.domain.mq.priority;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.*;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/21 16:52
 * @vVersion 1.0
 */
public class PriorityCosumer {
    private static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception {
        Channel channel = MqUtils.getConnection();//.getChannel();
        //设置队列的最大优先级 最大可以设置到 255 官网推荐 1-10 如果设置太高比较吃内存和 CPU
        Map<String, Object> params = new HashMap();
        params.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE_NAME, true, false, false, params);

        System.out.println("消费者启动等待消费......");
        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String receivedMessage = new String(delivery.getBody());
            System.out.println("接收到消息:"+receivedMessage);
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,(consumerTag)->{
            System.out.println("消费者无法消费消息时调用，如队列被删除");
        });
    }
}
