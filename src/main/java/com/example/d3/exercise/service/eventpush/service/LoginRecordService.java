package com.example.d3.exercise.service.eventpush.service;

import com.example.d3.exercise.domain.UserEntity;
import com.example.d3.exercise.service.eventpush.LoginSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/21 14:01
 * @vVersion 1.0
 */
@Order(1)
@Slf4j
@Service
public class LoginRecordService implements ApplicationListener<LoginSuccessEvent> {
    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        UserEntity userEntity= (UserEntity) event.getSource();
        addLoginRecord(userEntity);
    }

    private void addLoginRecord(UserEntity userEntity) {
        userEntity.getRemarkMap().put(this.getClass().getName(),userEntity.getUsername()+"登录成功！");
    }
}
