package com.example.d3.exercise.domain.mq.three;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/20 11:31
 * @vVersion 1.0
 */
@Slf4j
@Component
public class BootCusumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel){
        log.info("当前时间：{},接收到死信消息：{}", Instant.now().toString(),new String(message.getBody()));
    }
}
