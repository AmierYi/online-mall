package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.entity.GoodsProduct;
import com.yi.common.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface GoodsProductService extends IService<GoodsProduct> {

    /**
     * 保存 sku的方法
     *
     * @param goodsProductDtos 前端传入的 sku数组
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveProduct(GoodsProductDto[] goodsProductDtos);

    /**
     * 保存 sku图片的方法
     *
     * @param file sku 图片的二进制对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> saveProductImage(MultipartFile file);

    /**
     * 根据商品 id获取其对应的 sku
     *
     * @param goodsId 商品 id
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> getProductByGoodsId(String goodsId);

    /**
     * 根据 sku id来修改 sku
     *
     * @param goodsProductDto sku的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> updateProductById(GoodsProductDto goodsProductDto);

    /**
     * 逻辑删除 sku
     *
     * @param map 商品和规格的 id键值对
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> logicRemoveSpecification(Map<String, String> map);
}
