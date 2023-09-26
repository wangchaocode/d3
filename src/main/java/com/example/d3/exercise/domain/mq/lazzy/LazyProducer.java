package com.example.d3.exercise.domain.mq.lazzy;

import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;


/**
 * @author wangchao
 * @Description:
 * @date 2023/9/19 16:54
 * @vVersion 1.0
 */
public class LazyProducer {
    private static final String LAZY_EXCHANGE_NAME = MqUtils.FED_EXCHANGE_NAME;
    public static void main(String[] args) throws Exception{
        Channel channel= MqUtils.getConnection();
        channel.exchangeDeclare(LAZY_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //该信息是用作演示队列个数限制
        for (int i = 1; i <11 ; i++) {
            String message="info"+i;
            channel.basicPublish(LAZY_EXCHANGE_NAME,MqUtils.ROUTE_KEY_ZHANGSAN , null,
                    message.getBytes());
            System.out.println("生产者发送消息:"+message);
        }
    }

}
