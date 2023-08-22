package com.example.d3.exercise.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangchao
 * @date 2023/8/17 08:12:36
 */
@Slf4j
public class LongDistance extends ParentQuota {
    @Override
    void technologyQuota() {
        log.info("长距离跑技术指标");
    }

    @Override
    void phisicalQuota() {
        log.info("长距离跑【体能】指标");
    }
}
