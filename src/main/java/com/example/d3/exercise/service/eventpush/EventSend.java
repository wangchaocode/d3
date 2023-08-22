package com.example.d3.exercise.service.eventpush;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author wangchao
 * @Description:
 * @date 2023/8/21 13:57
 * @vVersion 1.0
 */
@Service
public class EventSend implements ApplicationEventPublisherAware {
    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher=applicationEventPublisher;
    }
    public void sentEvent(ApplicationEvent event){
        this.applicationEventPublisher.publishEvent(event);
    }
}
