package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsSpecificationService;
import com.yi.common.dto.GoodsSpecificationDto;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class GoodsSpecificationController {

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    /**
     * 处理保存商品规格
     * @param goodsSpecificationList 商品规格的 dto对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/save")
    public ResponseResult<Object> saveSpecification(@RequestBody List<Map<String, String>> goodsSpecificationList){
        return goodsSpecificationService.saveGoodsSpecificationService(goodsSpecificationList);
    }
}
