package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.entity.Shop;
import com.yi.common.response.ResponseResult;

public interface ShopService extends IService<Shop> {

    /**
     * 创建新的商店
     *
     * @param shop 前端返回数据封装成的 shop对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> createShop(Shop shop);

    /**
     * 查看店家旗下的店铺
     *
     * @param storekeeperId 店家 id
     * @param pageNumber    查询的当前页码
     * @param pageSize      分页大小
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> listShops(Integer pageNumber, Integer pageSize, String storekeeperId);

    /**
     * 获取指定店主旗下的所有店铺
     * @param storekeeperId 指定的店主 id
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> getAllShops(String storekeeperId);

    /**
     * 删除管理的店铺
     *
     * @param shop 店铺对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> deleteShop(Shop shop);

    /**
     * 更改店铺的信息
     *
     * @param shop 店铺对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> updateShop(Shop shop);


}
