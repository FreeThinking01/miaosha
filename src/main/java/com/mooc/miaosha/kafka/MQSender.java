package com.mooc.miaosha.kafka;

import com.mooc.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha
 * @author: FreeThinking
 * @create: 2022-04-21 20:12
 * @description:
 **/
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMiaoshaMessage(MiaoshaMessage mm){
        String msg = RedisService.beanToString(mm);
        log.info("send message:"+msg);
        kafkaTemplate.send(MQConfig.MIAOSHA_TOPIC,msg);
    }

}
