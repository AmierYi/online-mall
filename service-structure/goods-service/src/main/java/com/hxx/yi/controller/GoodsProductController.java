package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsProductService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class GoodsProductController {

    @Autowired
    private GoodsProductService goodsProductService;

    /**
     * 处理保存 sku的请求
     *
     * @param goodsProductDtos sku的 dto对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/save")
    public ResponseResult<Object> saveProduct(@RequestBody GoodsProductDto[] goodsProductDtos) {
        return goodsProductService.saveProduct(goodsProductDtos);
    }
}
