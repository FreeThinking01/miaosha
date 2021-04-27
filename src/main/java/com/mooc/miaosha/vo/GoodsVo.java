package com.mooc.miaosha.vo;

import com.mooc.miaosha.domain.Goods;

import java.util.Date;

public class GoodsVo extends Goods {
    //GoodsVo将Goods信息和GoodsMiaosha(只有秒杀信息而无Goods信息)用于秒杀
    //继承Goods后再添加MiaoshaGoods信息
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Double miaoshaPrice;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }
}
