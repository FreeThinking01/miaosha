package com.mooc.miaosha.kafka;

import com.mooc.miaosha.redis.RedisService;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

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
        // 2.异步发送 先打印的wait message，后打印发送编号，说明异步执行kafka存储消息后回调触发钩子函数，异步发送保证消息发送成功需要从回调函数中查看错误请求然后重发
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(MQConfig.MIAOSHA_TOPIC, msg);
        send.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onFailure(Throwable e) {
                log.info("[testASyncSend][发送编号：[{}] 发送异常]]", msg, e);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", msg, result);
            }
        });
        System.out.println("wait message");


    }


    public void sendMiaoshaMessageSync(MiaoshaMessage mm){
        String msg = RedisService.beanToString(mm);
        log.info("send message:"+msg);
        /*同步发送消息。在方法内部，也是调用 KafkaTemplate#send(topic, data) 方法，异步发送消息。
        不过，因为我们后面调用了 ListenableFuture 对象的 get() 方法，阻塞等待发送结果，从而实现同步的效果。同步可以保证消息可以消费成功*/
        try {
            RecordMetadata metadata = kafkaTemplate.send(MQConfig.MIAOSHA_TOPIC, msg).get().getRecordMetadata();
            System.out.printf("topic=%s, partition=%d, offset=%s",metadata.topic(), metadata.partition(), metadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
