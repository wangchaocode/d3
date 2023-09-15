package com.example.d3.exercise.domain.mq.one;

import com.example.d3.tools.MqUtils;
import com.example.d3.tools.SleepUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author wangchao
 * @Description: 生产者 靠输入，或者循环
 * 默认交换机，固定队列
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
public class MqCustomer2 {
    public static void main(String[] args) throws IOException{
       consume(false);
    }

    /**
     * 基础版
     * @throws IOException
     * @param handMovement 手动应答标记
     */
    public static void consume(boolean handMovement) throws IOException {
        Channel channel=MqUtils.getConnection();
        System.out.println("c2等待接收消息--等待30s");
        boolean autoAck=!handMovement;
        channel.basicConsume(MqUtils.DEFAULT_QUEUE_NAME,autoAck,
                (s, delivery) -> {
                    String msg=new String(delivery.getBody());
                    System.out.println("接到消息："+msg);
                    if(handMovement){
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),autoAck);
                    }
                },
                (cancel
                )->{
                    System.out.println("被中断");
                }
        );
    }
}
