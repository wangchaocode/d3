package com.example.d3.exercise.service.impl;

import com.example.d3.exercise.ObserverClass;
import com.example.d3.exercise.domain.ParentQuota;
import com.example.d3.exercise.service.IRunQuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author wangchao
 * @date 2023/8/17 08:16:29
 */
@Slf4j
@Service
public class QuotaImpl implements IRunQuotaService {
    @Override
    public void publish() {

    }

    @Override
    public void observer() {

    }

    @Override
    public void register() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(ParentQuota.class));
        // 将子类全部注册到观察者中
        Set<BeanDefinition> components = provider.findCandidateComponents("com.example.d3.exercise.domain");
        for (BeanDefinition component : components)
        {
            Class cls = Class.forName(component.getBeanClassName());
            // use class cls found
            log.info(cls.getName());
            Method[] methods=cls.getDeclaredMethods();
            for (Method m:methods){
                m.setAccessible(true);
                log.info("获取到当前方法："+m.getName());
                m.invoke(cls.newInstance());
            }

        }
    }
}
