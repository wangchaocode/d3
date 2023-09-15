package com.example.d3.exercise.domain.mq.one;

import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;

/**
 * @author wangchao
 * @Description: 生产者
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
public class MqProducer1 {
    private static Channel channel=MqUtils.getConnection();
    private static boolean persistFlag=false;

    /**
     * 默认
     * @throws IOException
     */
    static void init() throws IOException {
        channel.queueDeclare(MqUtils.DEFAULT_QUEUE_NAME,persistFlag,false,false,null);
    }
    public static void main(String[] args) throws IOException{
        persistFlag=true;
        init();
        consume();
    }

    /**
     * 1 控制台输入消息
     * @throws IOException
     */
    public static void consume() throws IOException {
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",MqUtils.DEFAULT_QUEUE_NAME,(persistFlag?
                    MessageProperties.PERSISTENT_TEXT_PLAIN:null),message.getBytes());
            System.out.println("消息发送完毕");
        }
    }

    /**
     * 2 批量发消息
     * @throws IOException
     */
    public static void consume2() throws IOException {
        Instant now=Instant.now();
        for (int i = 0; i < 100000; i++) {
            channel.basicPublish("",MqUtils.DEFAULT_QUEUE_NAME,null,("msge哈哈哈"+i).getBytes());
            System.out.println("消息发送完毕");
        }
        System.out.println("时间："+(Instant.now().toEpochMilli()-now.toEpochMilli()) );
    }
}
