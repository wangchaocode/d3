package com.example.d3.exercise.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wangchao
 * @date 2023/8/17 08:07:57
 */
public interface IRunQuotaService {
    /**
     * 发布信息
     */
    void publish();

    /**
     * 观察
     */
    void observer();

    void register() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException;
}
