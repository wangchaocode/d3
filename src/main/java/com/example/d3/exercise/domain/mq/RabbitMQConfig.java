package com.example.d3.exercise.domain.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/14 9:55
 * @vVersion 1.0
 */
@Data
@AllArgsConstructor
@ToString
public class RabbitMQConfig {
    /**
     *
     */
    private String host;
    private String user;
    private String pwd;
    private Integer port;

}
