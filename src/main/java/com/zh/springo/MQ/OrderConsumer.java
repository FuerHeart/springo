package com.zh.springo.MQ;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zh.springo.mapper.OrdersMapper;
import com.zh.springo.redisConfig.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RocketMQMessageListener(topic = "AIR_SYSTEM", selectorExpression = "order", consumerGroup = "order-group")
@Slf4j
public class OrderConsumer implements RocketMQListener<Map<String, Object>> {

    private final Logger logger = Logger.getLogger("OrderConsumer");

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public void onMessage(Map<String, Object> s) {//这里最好得是个对象 因为前面已经把身份证过滤出来了 直接放进来就行 用Map把
        //发送定时任务 消息是订单的id 收到定时后去数据库查询付款状态 未完成就取消 完成就当做无事发生
        logger.info(s.toString());
        Long orderNo = Long.valueOf(String.valueOf(s.get("orderNo")));
        List<String> idCards = (List<String>) s.get("idCards");
        String redisKey = String.valueOf(s.get("redisKey"));
        int status = ordersMapper.retrieveOrderStatus(orderNo);
        if (status != 1) {
            //状态等于未付款就取消订单
            //还要在redis的已购客户里取消掉这次的乘客
            logger.info(String.valueOf(orderNo));
            ordersMapper.deleteById(orderNo);
            //去除对应key中的value
            for (String idCard : idCards) {
                redisUtil.setRemove(redisKey, idCard);
            }
        }
    }
}
