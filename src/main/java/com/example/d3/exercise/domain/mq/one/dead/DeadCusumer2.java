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
public class DeadCusumer2 {
    //普通交换机名称
    private static final String NORMAL_EXCHANGE = MqUtils.NORMAL_EXCHANGE_NAME;
    //死信交换机名称
    private static final String DEAD_EXCHANGE = MqUtils.DEAD_EXCHANGE;

    private boolean testMax=false;
    public static void main(String[] args) throws IOException {
        testDead(6);
    }

    private static void testDead(int testMaxNum) throws IOException{
        String deadQ="deadQ",normalQ="normalQ",deadPre="x-dead-letter-"
                ,routeKey="lisi",normalRouteKey="zhangsan";
        Channel channel = MqUtils.getConnection();

        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);


        channel.queueDeclare(deadQ,true,false,false,null);
        channel.queueBind(deadQ,DEAD_EXCHANGE,routeKey);


        Map<String,Object> pm=new HashMap<>();
        pm.put(deadPre+"exchange",DEAD_EXCHANGE);
        pm.put(deadPre+"routing-key",routeKey);

        if(testMaxNum>0){
            pm.put("x-max-length",testMaxNum);
        }
        // 关键：正常队列要绑定死信队列参数。
        channel.queueDeclare(normalQ,true,
                false,false,
                pm);
        channel.queueBind(normalQ,NORMAL_EXCHANGE,normalRouteKey);

        System.out.println("等待接收....");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Consumer01 接收到消息"+message);
        };
        channel.basicConsume(normalQ, true, deliverCallback, consumerTag -> {
        });
    }
}
