package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsAttributeDto;
import com.yi.common.dto.GoodsDto;
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
    ResponseResult<Object> saveAttribute(List<Map<String, String>> goodsAttributeJsonList);

    /**
     * 根据商品 id获取其属性值
     *
     * @param goodsId 商品 id
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> getAttributeByGoodsId(String goodsId);

    /**
     * 根据属性 id修改属性的方法
     *
     * @param goodsAttributeDto 属性的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> updateAttributeById(GoodsAttributeDto goodsAttributeDto);

    /**
     * 根据商品id逻辑删除对应的属性
     *
     * @param map 商品和属性的 id键值对
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> logicRemoveAttributeById(Map<String, String> map);
}
