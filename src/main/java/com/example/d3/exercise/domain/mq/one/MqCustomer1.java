package com.example.d3.exercise.domain.mq.one;

import com.example.d3.exercise.domain.mq.ExchangeNum;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wangchao
 * @Description: 生产者 靠输入，或者循环
 * 默认交换机，固定队列
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
public class MqCustomer1 extends MqParent {


    private static String qName="";

    public static void main(String[] args) throws IOException{
        setModle("t");
        String[] arr= new String[]{"warn"};
        if(getExchangeType().contains("topic")){
            arr =new String[]{"*.*.fox"};
        }
         qName=init(MqUtils.getConnection(),true
                 ,arr, getRamdomQ());
        System.out.println("qName:"+qName);
        consume(false,false);
    }

    private static void init() {

    }

    /**
     * 基础版
     * @throws IOException
     * @param handMovement 手动应答标记
     */
    public static void consume(boolean handMovement,boolean beSure) throws IOException {
        Channel channel=MqUtils.getConnection();
        System.out.println("c2等待接收消息...");

        channel.basicConsume(qName,!handMovement,
                (s, delivery) -> {
                    String msg=new String(delivery.getBody());
                    System.out.println("接到消息："+msg);
                    if(handMovement){
                        System.out.println("设置应答模式");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),!handMovement);
                    }
                    if(beSure){
                        try {
                            if (channel.waitForConfirms()){
                                System.out.println("确认成功");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                },
                (cancel
                )->{
                    System.out.println("被中断");
                }
        );
    }
}
