package com.example.d3.exercise.domain.kafka;

import com.example.d3.tools.KafkaUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/26 14:18
 * @vVersion 1.0
 */
public class KafkaTest {
    public static void main(String[] args) {
        KafkaProducer<String,String> producer=KafkaUtils.getProducer();
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,"test"+i));
            System.out.println("kafka发送消息");
        }
        KafkaUtils.closeKafka();
    }
}
