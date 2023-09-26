package com.example.d3.tools;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/26 14:00
 * @vVersion 1.0
 */
public class KafkaUtils {
    public static KafkaProducer<String,String> producer=null;
    public static final String KAFKA_TOPIC_FIRST="first";
    public static KafkaProducer getProducer(){
        if(null == producer){
            producer=ReadProperties.getKafkaProducer();
            System.out.println("获取连接");
        }
        return producer;
    }
    public static void closeKafka(){
        if (null !=producer){
            producer.close();
            System.out.println("关闭连接");
        }
    }

}
