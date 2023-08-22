package com.example.d3.exercise.domain;

import com.example.d3.exercise.service.IRunQuotaService;

/**
 * @author wangchao
 * @date 2023/8/17 08:10:05
 */
public abstract class ParentQuota {
    abstract void technologyQuota();
    abstract void phisicalQuota();
}
