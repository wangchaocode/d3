package com.example.d3.exercise.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/22 10:15
 * @vVersion 1.0
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        log.trace("trace 日志.....");
        log.debug("debug 日志.....");
        //SpringBoot底层默认的日志级别 info
        log.info("info 日志..... 参数a:{} b:{}");
        log.warn("warn 日志...");
        log.error("error 日志.isTraceEnabled..{}",log.isTraceEnabled());
        log.error("error 日志.isDebugEnabled..{}",log.isDebugEnabled());
        log.error("error 日志..isInfoEnabled.{}",log.isInfoEnabled());
        log.error("error 日志.isWarnEnabled..{}",log.isWarnEnabled());

    }
}
