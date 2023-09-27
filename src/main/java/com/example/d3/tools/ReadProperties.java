package com.example.d3.tools;

import com.example.d3.exercise.domain.mq.RabbitMQConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * @author wangchao
 * @Description:rabbitMq工具类
 * @date 2023/9/14 9:43
 * @vVersion 1.0
 */
public class ReadProperties {



    static Properties getFiles(String fileName){
        InputStream is= ReadProperties.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties=new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
    static RabbitMQConfig getRabbitMqConf(){
        Properties p=getFiles("rabbitmq.properties");
        return new RabbitMQConfig(p.getProperty("host"),p.getProperty("user"),p.getProperty("pwd"),
                Integer.parseInt(p.getProperty("port")));
    }
    public static Properties getKafkaProperties(){
        return getFiles("kafka.properties");
    }
    public static KafkaProducer getKafkaProducer(){
        Properties p=getFiles("kafka.properties");
        // 优化
        p.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
        p.put(ProducerConfig.LINGER_MS_CONFIG,1);
        p.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);
        p.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");

        // 添加ack应答
        p.put(ProducerConfig.ACKS_CONFIG,"all");
        // 重试次数retries，默认是int 最大值，2147483647
        p.put(ProducerConfig.RETRIES_CONFIG,3);

        // 放入事务
        p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString());
        KafkaProducer<String,String> kafkaProducer=new KafkaProducer<String, String>(p);
        return kafkaProducer;
    }

    public static void main(String[] args) {
        System.out.println(getRabbitMqConf().toString());
    }
}
