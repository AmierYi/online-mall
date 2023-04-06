package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.entity.GoodsProduct;
import com.yi.common.response.ResponseResult;

public interface GoodsProductService extends IService<GoodsProduct> {

    /**
     * 保存 sku的方法
     * @param goodsProductDtos 前端传入的 sku数组
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveProduct(GoodsProductDto[] goodsProductDtos);
}
