package com.mooc.miaosha.service;

import com.mooc.miaosha.dao.GoodsDao;
import com.mooc.miaosha.dao.OrderDao;
import com.mooc.miaosha.domain.MiaoshaOrder;
import com.mooc.miaosha.domain.MiaoshaUser;
import com.mooc.miaosha.domain.OrderInfo;
import com.mooc.miaosha.redis.OrderKey;
import com.mooc.miaosha.redis.RedisService;
import com.mooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long userId, long goodsId) {
    //  return orderDao.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getMiaoshaOrderByuidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteMiaoshaOrders();
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());

        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByuidGid,""+user.getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }
}
