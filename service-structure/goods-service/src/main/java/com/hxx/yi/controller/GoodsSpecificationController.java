package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsSpecificationService;
import com.yi.common.dto.GoodsSpecificationDto;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class GoodsSpecificationController {

    private final GoodsSpecificationService goodsSpecificationService;
    public GoodsSpecificationController(GoodsSpecificationService goodsSpecificationService) {
        this.goodsSpecificationService = goodsSpecificationService;
    }

    /**
     * 处理保存商品规格
     * @param goodsSpecificationList 商品规格的 dto对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/save")
    public ResponseResult<Object> saveSpecification(@RequestBody List<Map<String, String>> goodsSpecificationList){
        return goodsSpecificationService.saveGoodsSpecification(goodsSpecificationList);
    }

    /**
     * 处理更新商品规格的请求
     * @param goodsSpecificationDto 商品规格的 dto对象
     * @return ResponseResult<Object>
     */
    @PutMapping("/update")
    public ResponseResult<Object> updateSpecification(@RequestBody GoodsSpecificationDto goodsSpecificationDto){
        return goodsSpecificationService.updateSpecificationById(goodsSpecificationDto);
    }

    /**
     * 处理逻辑删除商品规格的请求
     * @param map 商品和规格的 id键值对
     * @return ResponseResult<Object>
     */
    @PutMapping("/remove")
    public ResponseResult<Object> logicRemoveSpecification(@RequestBody Map<String, String> map){
        return goodsSpecificationService.logicRemoveSpecificationById(map);
    }
}
