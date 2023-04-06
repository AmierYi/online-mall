package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.entity.GoodsSpecification;
import com.yi.common.response.ResponseResult;

import java.util.List;
import java.util.Map;

public interface GoodsSpecificationService extends IService<GoodsSpecification> {

    /**
     * 保存商品规格的方法
     * @param goodsSpecificationList 商品规格的 list对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveGoodsSpecificationService(List<Map<String, String>> goodsSpecificationList);
}
