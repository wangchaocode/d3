package com.example.d3.exercise.domain.mq.comfirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/21 14:17
 * @vVersion 1.0
 */
@Component
@Slf4j
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id=null != correlationData?correlationData.getId():"error";
        if(ack){
            log.info("交换机接收到ID为：{}的消息",id);
        }else{
            log.info("交换机接还未收到ID为：{}的消息，原因：{}",id,cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息被回退,getExchange：{}，getRoutingKey：{},getReplyText：{},getReplyCode:{},getMessage：{}"+returned.getRoutingKey(),
                returned.getReplyText(),returned.getReplyCode(),
                returned.getMessage(),returned.getExchange());
    }
}
