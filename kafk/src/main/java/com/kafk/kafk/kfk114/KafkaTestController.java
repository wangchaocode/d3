package com.kafk.kafk.kfk114;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/27 8:40
 * @vVersion 1.0
 */
@RestController
public class KafkaTestController {
    @Autowired
    private KafkaTemplate template;
    @GetMapping("/kafkaSend")
    public String producerMsg(String msg){
        ListenableFuture<SendResult> lt= template.send("first",msg);
        return "ok--?  :"+lt.completable();
    }
}
