package com.example.d3.exercise.domain.kafka.mypartition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/26 15:54
 * @vVersion 1.0
 */
public class MyPartitionCallBack implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String msgValue=value.toString();
        int partition;
        if(msgValue.contains("jump")){
            partition=0;
        }else{
            partition=1;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
