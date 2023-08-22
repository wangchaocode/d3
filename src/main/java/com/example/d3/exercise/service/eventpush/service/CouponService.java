package com.example.d3.exercise.service.eventpush.service;

import com.example.d3.exercise.domain.UserEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/21 14:05
 * @vVersion 1.0
 */
@Order(19)
@Service
public class CouponService extends ParentService {

    @Override
    void doMethod(UserEntity userEntity) {
         double randomRmb=Math.random()*10;
         String msg=userEntity.getUsername()+"获取到随机券"+randomRmb+"元";
         userEntity.getRemarkMap().put(this.getClass().getName(),msg);
    }
}
