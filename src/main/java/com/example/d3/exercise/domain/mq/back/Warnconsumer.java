package com.example.d3.exercise.domain.mq.back;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * @author wangchao
 * @Description:
 * @date 2023/9/21 15:22
 * @vVersion 1.0
 */
@Slf4j
@Component
public class Warnconsumer {

    @RabbitListener(queues = BackExConfig.CONFIRM_QUEUE_NAME)
    public void warnCosumerC(Message msg){
        log.info("收到确认消息，成功：{}",new String(msg.getBody()));
    }

    @RabbitListener(queues = BackExConfig.WARNING_QUEUE_NAME)
    public void warnCosumer(Message msg){
        log.info("监听到报警：{}",new String(msg.getBody()));
    }
    @RabbitListener(queues = BackExConfig.BACKUP_QUEUE_NAME)
    public void warnCosumerB(Message msg){
        log.info("监听到备份队列信息，需要马上处理：{}",new String(msg.getBody()));
    }

}
