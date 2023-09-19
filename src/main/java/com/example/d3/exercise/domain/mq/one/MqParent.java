package com.example.d3.exercise.domain.mq.one;

import com.example.d3.exercise.domain.mq.ExchangeNum;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;
import lombok.Data;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/19 10:33
 * @vVersion 1.0
 */
@Data
public class MqParent {
    private static  String EXCHANGE_TYPE ="" ;
    private static  String EXCHANGE_NAME = "";
    public static String init(Channel channel, boolean changeFlag,
                              String[] key, String q1) throws IOException {
        String qName=q1;
        channel.queueDeclare(qName,true,true,false,null);
        if(changeFlag){
            channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);
            if (null !=key && key.length>0) {
                for (String s : key) {
                    System.out.println("绑定key:"+s);
                    channel.queueBind(qName, EXCHANGE_NAME, s);
                }
            }else{
                channel.queueBind(qName,EXCHANGE_NAME,"");
            }

        }else{
            channel.queueDeclare(qName,true,false,false,null);
        }
        return qName;
    }

    public static String getExchangeType() {
        return EXCHANGE_TYPE;
    }

    public static void setExchangeType(String exchangeType) {
        EXCHANGE_TYPE = exchangeType;
    }

    public static String getExchangeName() {
        return EXCHANGE_NAME;
    }

    public static void setExchangeName(String exchangeName) {
        EXCHANGE_NAME = exchangeName;
    }

    public static void setModle(String type){
        switch (type){
            case "f":
                setExchangeType(ExchangeNum.fanout.name());
                setExchangeName(MqUtils.DEFAULT_EXCHANGE_NAME_FANOUT);
                break;
            case "t":
                setExchangeType(ExchangeNum.topic.name());
                setExchangeName(MqUtils.DEFAULT_EXCHANGE_NAME_TOPIC);
                break;
            case "d":
                setExchangeType(ExchangeNum.direct.name());
                setExchangeName(MqUtils.DEFAULT_EXCHANGE_NAME_DIRECT);
                break;
            case "h":
                setExchangeType(ExchangeNum.headers.name());
                setExchangeName(MqUtils.DEFAULT_EXCHANGE_NAME_HEADER);
                break;

        }
        System.out.println("现在模式和名称"+getExchangeName()+"，"+getExchangeType());
    }
    public static String getRamdomQ(){
        return UUID.randomUUID().toString();
    }
}
