package com.example.d3.exercise.domain.mq.priority;

import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/21 16:49
 * @vVersion 1.0
 */
public class PriorityTest {
    private static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws IOException {
        Channel channel= MqUtils.getConnection();
        AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().priority(5).build();
        for(int i=0;i<10;i++){
            String message="消息："+i;
            if(i==5){
                channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
            }else{
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            System.out.println("发送消息完成:" + message);
        }
    }
}
