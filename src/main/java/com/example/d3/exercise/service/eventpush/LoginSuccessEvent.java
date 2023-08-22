package com.example.d3.exercise.service.eventpush;

import com.example.d3.exercise.domain.UserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangchao
 * @Description: 测试发布信息。每个服务都可以去注册。
 * @date 2023/8/21 13:56
 * @vVersion 1.0
 */

public class LoginSuccessEvent extends ApplicationEvent {
    public LoginSuccessEvent(UserEntity source) {
        super(source);
    }
}
