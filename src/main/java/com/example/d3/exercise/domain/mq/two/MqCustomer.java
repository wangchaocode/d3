package com.example.d3.exercise.domain.mq.two;

import com.example.d3.exercise.config.domain.User;
import com.example.d3.lock.synctest.SynchronizedExample;
import com.example.d3.lock.synctest.mysql.UserUpdateService;
import com.example.d3.tools.MqUtils;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangchao
 * @Description: 生产者
 * @date 2023/9/14 10:42
 * @vVersion 1.0
 */
@Component
public class MqCustomer extends SynchronizedExample {

    private static UserUpdateService updateService;

    @Autowired
    public void setUpdateService(UserUpdateService updateService) {
        this.updateService = updateService;
    }

    @Override
    public void doSomeThing(int i) {
        try {
            consume(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void consume(int i) throws IOException {
        Channel channel=MqUtils.getConnection();
        System.out.println(this.hashCode()+"等待接收消息");
        // 开5个线程
        channel.basicConsume(MqUtils.DEFAULT_QUEUE_NAME,true,
                (s, delivery) -> {
                    String msg=new String(delivery.getBody());
                    User user=new User(null,msg,i);
                    System.out.println("接到消息："+msg);
                    updateService.addUser(user);
                },
                (cancel
                )->{
                    System.out.println("被中断");
                }
        );
    }
}
