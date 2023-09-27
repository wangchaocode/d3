package com.example.d3.exercise.domain.kafka;

import com.example.d3.tools.KafkaUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/26 14:18
 * @vVersion 1.0
 */
public class KafkaTest {
    private static boolean cbFlag=true;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        KafkaProducer<String,String> producer=KafkaUtils.getProducer();
        // 开启事务
        producer.initTransactions();
        producer.beginTransaction();
        try {
            for (int i = 0; i < 5; i++) {
                //            test01(i,producer);
                //            testGet(i,producer);
                // abf 分别打到1.2.0的分区
                //            ProducerRecord p=getFixPartition(i,null,"f");
                ProducerRecord p = getFixPartition(i, null, "");
                testP1(i, producer, p);
                System.out.println("kafka发送消息");
            }
            int i=1/0;
            producer.commitTransaction();
            System.out.println("提交事务");
        }catch (Exception e){
            producer.abortTransaction();
            System.out.println("异常："+e.getMessage());
        }

        KafkaUtils.closeKafka();
    }

    private static ProducerRecord getFixPartition(int i,Integer partition,String key) {
        String msg="test-callback";//+ (i<3?"-ju3mp-":"")+i;
        if(partition !=null) return  new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,partition,key,"test-callback"+i);
        else if (!"".equals(key) )return new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,key,msg);
        else
            return new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,msg);
    }

    /**
     * 全部打到1分区
     * @param i
     * @param producer
     * @throws InterruptedException
     */
    private static void testP1(int i, KafkaProducer<String, String> producer,ProducerRecord producerRecord) throws InterruptedException {
            producer.send(
                    producerRecord,
                    (RecordMetadata metadata, Exception exception)->{
                        if(null ==exception){
                            System.out.println("主题:"+metadata.topic()+",分区："+
                                    metadata.partition());
                        }else{
                            System.out.println("异常："+exception.getMessage());
                        }
                    });
            Thread.sleep(1000);
    }

    private static void testGet(int i, KafkaProducer<String, String> producer) throws InterruptedException, ExecutionException {
        System.out.println("同步发送");
        producer.send(
                    new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,"同步发送，test"+i)
            ).get();

    }

    private static void test01(int i, KafkaProducer<String, String> producer) throws InterruptedException {
        if(!cbFlag){
            producer.send(
                    new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,"test"+i)
            );
        }else{
            producer.send(
                    new ProducerRecord<>(KafkaUtils.KAFKA_TOPIC_FIRST,"test-callback"+i),
                    (RecordMetadata metadata, Exception exception)->{
                        if(null ==exception){
                            System.out.println("主题:"+metadata.topic()+",分区："+
                                    metadata.partition());
                        }else{
                            System.out.println("异常："+exception.getMessage());
                        }
                    });
            Thread.sleep(2000);
        }
    }
}
