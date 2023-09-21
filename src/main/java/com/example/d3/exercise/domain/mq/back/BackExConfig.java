package com.example.d3.exercise.domain.mq.back;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
/**
 * @author wangchao
 * @Description:
 * @date 2023/9/21 15:04
 * @vVersion 1.0
 */
@Configuration
public class BackExConfig {
    public static final String CONFIRM_EXCHANGE_NAME = "confirm1.exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm1.queue";
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    /**
     * 声明交换机、 确认的是直接，备份是扇出，全广播
     * @return
     */

    @Bean("confirmEx")
    public DirectExchange confirmEx(){
        return (DirectExchange) ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    @Bean("backupEx")
    public FanoutExchange backEx(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean("confirmQ")
    public Queue confirmQ(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    @Bean("warningQ")
    public Queue warningQ(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean("backQ")
    public Queue backQ(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    /**
     * 绑定队列到交换机
     * @param qa
     * @param ex
     * @return
     */
    @Bean
    public Binding queueBingC(@Qualifier("confirmQ") Queue qa,@Qualifier("confirmEx") DirectExchange ex){
        return BindingBuilder.bind(qa).to(ex).with("key1");
    }

    @Bean
    public Binding queueBingW(@Qualifier("warningQ") Queue qa,@Qualifier("backupEx") FanoutExchange ex){
        return BindingBuilder.bind(qa).to(ex);
    }

    @Bean
    public Binding queueBingB(@Qualifier("backQ") Queue qa,@Qualifier("backupEx") FanoutExchange ex){
        return BindingBuilder.bind(qa).to(ex);
    }

}
