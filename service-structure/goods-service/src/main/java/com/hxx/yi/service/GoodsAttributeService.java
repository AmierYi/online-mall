package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsAttributeDto;
import com.yi.common.entity.GoodsAttribute;
import com.yi.common.response.ResponseResult;

import java.util.List;
import java.util.Map;

public interface GoodsAttributeService extends IService<GoodsAttribute> {

    /**
     * 保存商品属性的方法
     *
     * @param goodsAttributeJsonList 商品属性的 Json数组转换成的 List对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveAttribute(List<Map<String,String>> goodsAttributeJsonList);
}
