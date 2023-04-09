package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsAttributeService;
import com.yi.common.dto.GoodsAttributeDto;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attribute")
public class GoodsAttributeController {


    private final GoodsAttributeService goodsAttributeService;

    public GoodsAttributeController(GoodsAttributeService goodsAttributeService) {
        this.goodsAttributeService = goodsAttributeService;
    }

    /**
     * 处理保存商品属性的请求
     *
     * @param goodsAttributeJsonList 商品属性的 Json数组转换成的 List对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/save")
    public ResponseResult<Object> addAttribute(@RequestBody List<Map<String, String>> goodsAttributeJsonList) {
        return goodsAttributeService.saveAttribute(goodsAttributeJsonList);
    }

    /**
     * 处理修改商品属性的请求
     *
     * @param goodsAttributeDto 商品属性的 dto独享
     * @return ResponseResult<Object>
     */
    @PutMapping("/update")
    public ResponseResult<Object> updateAttribute(@RequestBody GoodsAttributeDto goodsAttributeDto) {
        return goodsAttributeService.updateAttributeById(goodsAttributeDto);
    }

    /**
     * 处理逻辑删除商品属性的请求
     *
     * @param map 商品和属性的 id键值对
     * @return ResponseResult<Object>
     */
    @PutMapping("/remove")
    public ResponseResult<Object> logicRemoveAttribute(@RequestBody Map<String, String> map) {
        return goodsAttributeService.logicRemoveAttributeById(map);
    }
}
