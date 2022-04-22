package com.mooc.miaosha.kafka;

import com.mooc.miaosha.domain.MiaoshaOrder;
import com.mooc.miaosha.domain.MiaoshaUser;
import com.mooc.miaosha.kafka.MiaoshaMessage;
import com.mooc.miaosha.redis.RedisService;
import com.mooc.miaosha.service.GoodsService;
import com.mooc.miaosha.service.MiaoshaService;
import com.mooc.miaosha.service.OrderService;
import com.mooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha
 * @author: FreeThinking
 * @create: 2022-04-21 20:12
 * @description: kafka 接收方
 **/
@Service
public class MQReceiver {


    private static Logger log = LoggerFactory.getLogger(com.mooc.miaosha.kafka.MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @KafkaListener(topics = MQConfig.MIAOSHA_TOPIC, groupId = MQConfig.MIAOSHA_GROUP)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }
}
