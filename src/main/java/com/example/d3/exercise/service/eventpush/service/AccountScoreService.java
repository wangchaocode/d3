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
@Order(20)
@Service
public class AccountScoreService extends ParentService {

    @Override
    void doMethod(UserEntity userEntity) {
        userEntity.getRemarkMap().put(this.getClass().getName(),userEntity.getUsername()+"加了一分");
    }
}
