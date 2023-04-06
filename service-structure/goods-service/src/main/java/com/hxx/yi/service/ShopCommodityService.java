package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.entity.Goods;
import com.yi.common.entity.Shop;
import com.yi.common.entity.ShopCommodity;

public interface ShopCommodityService extends IService<ShopCommodity> {

    /**
     * 保存店铺商品记录
     *
     * @param shop  店铺对象
     * @param goods 商品对象
     * @return Integer：数据库中收到影响的行数
     */
    Integer saveShopCommodity(Shop shop, Goods goods);
}
