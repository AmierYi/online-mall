package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.ShopCommodityMapper;
import com.hxx.yi.service.ShopCommodityService;
import com.yi.common.entity.Goods;
import com.yi.common.entity.Shop;
import com.yi.common.entity.ShopCommodity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShopCommodityServiceImpl extends ServiceImpl<ShopCommodityMapper, ShopCommodity> implements ShopCommodityService {

    private final ShopCommodityMapper shopCommodityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveShopCommodity(Shop shop, Goods goods) {


        Assert.notNull(shop,"参数为空");
        Assert.notNull(goods,"对象为空");

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
