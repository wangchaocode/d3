package com.example.d3.tools;

import com.example.d3.exercise.domain.mq.RabbitMQConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    static KafkaProducer getKafkaProducer(){
        Properties p=getFiles("kafka.properties");
        KafkaProducer<String,String> kafkaProducer=new KafkaProducer<String, String>(p);
        return kafkaProducer;
    }

    public static void main(String[] args) {
        System.out.println(getRabbitMqConf().toString());
    }
}
