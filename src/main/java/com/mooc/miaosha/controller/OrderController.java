package com.mooc.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mooc.miaosha.domain.MiaoshaUser;
import com.mooc.miaosha.domain.OrderInfo;
import com.mooc.miaosha.redis.RedisService;
import com.mooc.miaosha.result.CodeMsg;
import com.mooc.miaosha.result.Result;
import com.mooc.miaosha.service.GoodsService;
import com.mooc.miaosha.service.MiaoshaUserService;
import com.mooc.miaosha.service.OrderService;
import com.mooc.miaosha.vo.GoodsVo;
import com.mooc.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model,MiaoshaUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
