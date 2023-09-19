package com.example.d3.exercise.domain.mq.one;

import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.ConfirmationCallback;
import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author wangchao
 * @Description: 生产者
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
public class MqProducer1 {
    private static Channel channel=MqUtils.getConnection();
    private static boolean persistFlag=false;
    /**
     * 发布确认开启
     */
    private static boolean beSurePublishFlag=false;
    private static boolean beSureBatchFlag=false;
    private static boolean beSureSyncFlag=false;

    public static void main(String[] args) throws IOException{

        initFlag(true,true,false,true);
        /**
         * 平常 1563
         * 单个确认 很慢
         * 批量确认： 51488
         * 异步确认：
         */
        init();
        consume2();
    }


    /**
     * 1 控制台输入消息
     * @throws IOException
     */
    public static void consume() throws IOException {
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",MqUtils.DEFAULT_QUEUE_NAME,(persistFlag?
                    MessageProperties.PERSISTENT_TEXT_PLAIN:null),message.getBytes());
            System.out.println("消息发送完毕");
        }
    }

    /**
     * 2 批量发消息
     * @throws IOException
     */
    public static void consume2() throws IOException {
        Instant now=Instant.now();
        String queueName=getQName();
        int batchSize=100;
        int outStandingMessageCount=0;
        System.out.println("当前队列名称："+queueName);

        ConcurrentSkipListMap<Long,String> outComfirms  =new ConcurrentSkipListMap<>();
        // 异步
        if(getSync()){
            // 确认
            ConfirmCallback cback=( sequenceNumber,  multiple)->{
                if(multiple){
                    ConcurrentNavigableMap<Long,String> naiMap=outComfirms.headMap(sequenceNumber,
                            true);
                    naiMap.clear();
                    System.out.println("返回当前《=当前序列号的未确认消息,sequenceNumber:"+sequenceNumber);
                }else{
                    outComfirms.remove(sequenceNumber);
                }
            };
            //取消
            ConfirmCallback nback=( sequenceNumber,  multiple)->{
                String message=outComfirms.get(sequenceNumber);
                System.out.println("发布的消息："+message+"未被确认，序列号："+sequenceNumber);
            };
            // 监听成功和失败
            channel.addConfirmListener(cback,nback);
            System.out.println("监听成功和失败。。。");
        }


        for (int i = 0; i < 100000; i++) {
            String msg="msge哈哈哈"+i;

            if(getSync()){

                outComfirms.put(channel.getNextPublishSeqNo(),msg);
            }

            channel.basicPublish("",queueName,null,msg.getBytes());
            outStandingMessageCount++;

            // 单个确认
            if (getSingle()){
                try {
                    boolean flag=channel.waitForConfirms();
                    System.out.println("单个发布结果："+(flag?"成功":"失败"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 批量确认
            if (getBatch()){
                if(batchSize==outStandingMessageCount){
                    System.out.println("等待1..."+i);
                    try {
                        channel.waitForConfirms();
                        outStandingMessageCount=0;
                        System.out.println("这里开始批量发布..."+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(getBatch() && outStandingMessageCount>0){
            try {
                channel.waitForConfirms();
                System.out.println("看看还有没有漏掉的...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("时间："+(Instant.now().toEpochMilli()-now.toEpochMilli()) );
    }
    /**
     * 开启各种组合开关
     * @param pFlag
     * @param bsp
     * @param bsb
     * @param bss
     */
    private static void initFlag(boolean pFlag,boolean bsp
            ,boolean bsb,
                                 boolean bss) {
        persistFlag=pFlag;
        beSurePublishFlag=bsp;
        beSureBatchFlag=bsb;
        beSureSyncFlag=bss;
    }
    /**
     * 默认
     * @throws IOException
     */
    static void init() throws IOException {
        channel.queueDeclare(getQName(),persistFlag,false,false,null);
        if(beSurePublishFlag){
            channel.confirmSelect();
            System.out.println("开启确认发布");
        }
    }
    private static String getQName() {
        return beSurePublishFlag?MqUtils.RANDOM_QUEUE_NAME:MqUtils.DEFAULT_QUEUE_NAME;
    }

    private static boolean getBatch() {
        return beSurePublishFlag && beSureBatchFlag;
    }

    private static boolean getSingle() {
        return beSurePublishFlag && !beSureBatchFlag && !beSureSyncFlag;
    }

    private static boolean getSync() {
        return beSurePublishFlag && beSureSyncFlag;
    }

}
