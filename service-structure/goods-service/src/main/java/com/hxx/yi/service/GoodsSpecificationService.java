package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsSpecificationDto;
import com.yi.common.entity.GoodsSpecification;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface GoodsSpecificationService extends IService<GoodsSpecification> {

    /**
     * 保存商品规格的方法
     *
     * @param goodsSpecificationList 商品规格的 list对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveGoodsSpecification(List<Map<String, String>> goodsSpecificationList);

    /**
     * 根据商品 id获取其规格的方法
     *
     * @param goodsId 商品 id
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> getSpecificationByGoodsId(String goodsId);

    /**
     * 根据规格 id更该属性规格的方法
     *
     * @param goodsSpecificationDto 商品规格的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> updateSpecificationById(GoodsSpecificationDto goodsSpecificationDto);

    /**
     * 逻辑删除商品规格的方法
     *
     * @param map 商品和规格的 id键值对
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> logicRemoveSpecificationById(Map<String, String> map);
}
