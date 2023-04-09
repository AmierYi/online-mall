package com.hxx.yi.controller;

import com.hxx.yi.service.GoodsProductService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class GoodsProductController {


    private final GoodsProductService goodsProductService;

    public GoodsProductController(GoodsProductService goodsProductService) {
        this.goodsProductService = goodsProductService;
    }

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

    /**
     * 更新 sku的请求
     *
     * @param goodsProductDto sku的 dto对象
     * @return ResponseResult<Object>
     */
    @PutMapping("/update")
    public ResponseResult<Object> updateProduct(@RequestBody GoodsProductDto goodsProductDto) {
        return goodsProductService.updateProductById(goodsProductDto);
    }

    /**
     * 逻辑删除 sku的请求
     *
     * @param map 商品和 SKU的 id键值对
     * @return ResponseResult<Object>
     */
    @PutMapping("/remove")
    public ResponseResult<Object> logicRemoveProduct(@RequestBody Map<String, String> map) {
        return goodsProductService.logicRemoveSpecification(map);
    }

    /**
     * 保存 sku图片的请求
     *
     * @param image sku 图片的二进制对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/upload")
    public ResponseResult<Object> uploadProductImage(MultipartFile image) {
        return goodsProductService.saveProductImage(image);
    }


}
