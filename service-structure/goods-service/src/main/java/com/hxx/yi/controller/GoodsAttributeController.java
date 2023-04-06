package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsAttributeService;
import com.yi.common.dto.GoodsAttributeDto;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attribute")
public class GoodsAttributeController {

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    /**
     * 处理保存商品属性的请求
     * @param goodsAttributeJsonList 商品属性的 Json数组转换成的 List对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/save")
    public ResponseResult<Object> addAttribute(@RequestBody List<Map<String,String>> goodsAttributeJsonList){
        return goodsAttributeService.saveAttribute(goodsAttributeJsonList);
    }
}
