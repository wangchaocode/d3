package com.example.d3.exercise.domain.kafka;

import com.example.d3.tools.ReadProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/26 17:09
 * @vVersion 1.0
 */
public class KafkaCustomer1 {
    public static void main(String[] args) {
        Properties properties=ReadProperties.getKafkaProperties();
        properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"my-group-id");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer kafkaCustomer=new KafkaConsumer(properties);
        while(true){
            ConsumerRecords<String,String>  records=kafkaCustomer.poll(Long.MAX_VALUE);
            for(ConsumerRecord<String,String> consumerRecord:records){
                System.out.println("获取到消息..."+consumerRecord);
            }
        }
    }
}
