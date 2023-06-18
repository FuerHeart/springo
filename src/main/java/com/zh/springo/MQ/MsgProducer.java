package com.zh.springo.MQ;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MsgProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final static String topic = "AIR_SYSTEM";

    private final static int delayLevel = 2;//rocketMQ默认延迟有18个级别 1s - 2h 16就是30分钟

    public <T> SendResult sendDelayMsg(String tag, T message) {
        //防止重复任务1、 redis 2、 session
        Message<T> build = MessageBuilder.withPayload(message).build();
        return rocketMQTemplate.syncSend(topic + ":" + tag, build, 2000, delayLevel);
    }

}
