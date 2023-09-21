package com.example.d3.exercise.domain.mq.one.dead;

import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;


/**
 * @author wangchao
 * @Description: 死信队列，先启动消费2，让交换机和队列生成，然后关闭
 * 再启动生产者，进行生产，关闭，看页面有10条正常队列，10s后进入死信队列
 * 启动1，可以进行消费
 * @date 2023/9/19 16:54
 * @vVersion 1.0
 */
public class DeadProducer {
    private static final String NORMAL_EXCHANGE = MqUtils.NORMAL_EXCHANGE_NAME;
    public static void main(String[] args) throws Exception{
        Channel channel= MqUtils.getConnection();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        //设置消息的 TTL 时间
        AMQP.BasicProperties properties = new
                AMQP.BasicProperties().builder().expiration("10000").build();
        //该信息是用作演示队列个数限制
        for (int i = 1; i <11 ; i++) {
            String message="info"+i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties,
                    message.getBytes());
            System.out.println("生产者发送消息:"+message);
        }
    }

}
