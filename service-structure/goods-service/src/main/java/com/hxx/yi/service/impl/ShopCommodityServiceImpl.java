package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.ShopCommodityMapper;
import com.hxx.yi.service.ShopCommodityService;
import com.yi.common.entity.Goods;
import com.yi.common.entity.Shop;
import com.yi.common.entity.ShopCommodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopCommodityServiceImpl extends ServiceImpl<ShopCommodityMapper, ShopCommodity> implements ShopCommodityService {

    @Autowired
    private ShopCommodityMapper shopCommodityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveShopCommodity(Shop shop, Goods goods) {

        ShopCommodity shopCommodity = new ShopCommodity();
        shopCommodity.setShopId(shop.getShopId());
        shopCommodity.setGoodsId(goods.getGoodsId());
        shopCommodity.setGoodsName(goods.getGoodsName());
        shopCommodity.setIsDelete((short) 1);
        shopCommodity.setCreateTime(new DateTime());
        shopCommodity.setUpdateTime(new DateTime());
        return shopCommodityMapper.insert(shopCommodity);
    }
}
