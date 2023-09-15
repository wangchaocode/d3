package com.example.d3.exercise.domain.mq.two;

import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author wangchao
 * @Description: 生产者
 * 多线程跑
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
@Component
public class MqProducer extends SynchronizedExample {
    @Override
    public void doSomeThing(int i) {
        try {
            sendMsgByUrl("name:"+i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveConsolemsg() throws IOException {
        Scanner scanner=new Scanner(System.in);
        Channel channel=MqUtils.getConnection();
        channel.queueDeclare(MqUtils.DEFAULT_QUEUE_NAME,false,false,false,null);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",MqUtils.DEFAULT_QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送完毕");
        }
    }
    public static void sendMsgByUrl(String msg) throws IOException {
        Scanner scanner=new Scanner(System.in);
        Channel channel=MqUtils.getConnection();
        channel.queueDeclare(MqUtils.DEFAULT_QUEUE_NAME,false,false,false,null);
        channel.basicPublish("",MqUtils.DEFAULT_QUEUE_NAME,null,msg.getBytes());
        System.out.println(msg+"消息发送完毕");
    }
}
