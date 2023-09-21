package com.example.d3.exercise.config;

import com.example.d3.tools.MqUtils;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangchao
 * @Description: 定义交换机和队列
 * @date 2023/9/20 9:50
 * @vVersion 1.0
 */
@Configuration
public class RabbitMqConfig {
    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String DEAD_LETTER_QUEUE = "QD";


    /**
     * 声明交换机
     * @return
     */
    @Bean("xEx")
    public DirectExchange xEx(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yEx")
    public DirectExchange yEx(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    /**
     * 声明队列
     * @return
     */
    @Bean("QA")
    public Queue qA(){
        return QueueBuilder.durable(QUEUE_A).withArguments(
                MqUtils.getInitMap(Y_DEAD_LETTER_EXCHANGE,"YD",0,10000)
        ).build();
    }
    @Bean("QB")
    public Queue qB(){
        return QueueBuilder.durable(QUEUE_B).withArguments(
                MqUtils.getInitMap(Y_DEAD_LETTER_EXCHANGE,"YD",0,40000)
        ).build();
    }
    @Bean("QC")
    public Queue qC(){
        return QueueBuilder.durable(QUEUE_C).withArguments(
                MqUtils.getInitMap(Y_DEAD_LETTER_EXCHANGE,"YC",0,0)
        ).build();
    }

    @Bean("QD")
    public Queue qD(){
        return new Queue(DEAD_LETTER_QUEUE);
    }

/**
 * 队列绑定到交换机
 */
    @Bean
    public Binding qBingX(@Qualifier("QA") Queue qA,@Qualifier("xEx") DirectExchange xEx){
        return BindingBuilder.bind(qA).to(xEx).with("XA");
    }
    @Bean
    public Binding qBingB2X(@Qualifier("QB") Queue qA,@Qualifier("xEx") DirectExchange xEx){
        return BindingBuilder.bind(qA).to(xEx).with("XB");
    }

    @Bean
    public Binding qBingC2X(@Qualifier("QC") Queue qA,@Qualifier("xEx") DirectExchange xEx){
        return BindingBuilder.bind(qA).to(xEx).with("XC");
    }


    @Bean
    public Binding qBingD2Y(@Qualifier("QD") Queue qA,@Qualifier("yEx") DirectExchange xEx){
        return BindingBuilder.bind(qA).to(xEx).with("YD");
    }

}
