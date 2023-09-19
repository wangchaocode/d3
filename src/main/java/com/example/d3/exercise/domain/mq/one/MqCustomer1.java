package com.example.d3.exercise.domain.mq.one;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.lock.synctest.mysql.UserUpdateService;
import com.example.d3.tools.MqUtils;
import com.example.d3.tools.SleepUtils;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangchao
 * @Description: 生产者 靠输入，或者循环
 * 默认交换机，固定队列
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
public class MqCustomer1  {
    public static void main(String[] args) throws IOException{
        //
            consume(false,true);
    }

    /**
     * 基础版
     * @throws IOException
     * @param handMovement 手动应答标记
     */
    public static void consume(boolean handMovement,boolean beSure) throws IOException {
        Channel channel=MqUtils.getConnection();
        System.out.println("c1等待接收消息--等待1s");
//        SleepUtils.sleep(1);
        channel.basicConsume(MqUtils.DEFAULT_QUEUE_NAME,!handMovement,
                (s, delivery) -> {
                    String msg=new String(delivery.getBody());
                    System.out.println("接到消息："+msg);
                    if(handMovement){
                        System.out.println("设置应答模式");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),!handMovement);
                    }
                    if(beSure){
                        try {
                            if (channel.waitForConfirms()){
                                System.out.println("确认成功");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                (cancel
                )->{
                    System.out.println("被中断");
                }
        );
    }
}
