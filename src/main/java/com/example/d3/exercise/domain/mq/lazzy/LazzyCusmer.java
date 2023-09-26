package com.example.d3.exercise.domain.mq.lazzy;

import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/22 9:18
 * @vVersion 1.0
 */
public class LazzyCusmer {
    public static final String LAZY_QUEUE="fed.queue";
    public static final String LAZZY_KEY=MqUtils.ROUTE_KEY_ZHANGSAN;
    public static void main(String[] args) throws IOException {
        Map<String,Object> map=new HashMap<>();
        map.put("x-queue-mode","lazy");
        Channel channel= MqUtils.getConnection();
        channel.queueDeclare( LAZY_QUEUE,
                true,false,false,map);
        channel.queueBind(LAZY_QUEUE,MqUtils.FED_EXCHANGE_NAME,LAZZY_KEY);
        channel.basicConsume(LAZY_QUEUE,false, (consumerTag, message) ->{
                    System.out.println("应答消息:"+new String(message.getBody()));
                },(c)->{

                }
                );
    }
}
