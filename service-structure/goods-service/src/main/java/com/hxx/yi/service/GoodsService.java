package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.Goods;
import com.yi.common.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface GoodsService extends IService<Goods> {


    /**
     * 获取对应店铺下的所有商品
     *
     * @param shopIdStr     店铺 id数组的拼接字符串
     * @param storekeeperId 店家 id
     * @param pageNumber    页码，默认为1
     * @param pageSize      每一页的记录数
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> listAllGoods(Integer pageNumber, Integer pageSize, String shopIdStr, String storekeeperId);

    /**
     * 获取旗下所有商品（不包含属性、规格和 sku）
     *
     * @param shopIdStr     店铺 id数组的拼接字符串
     * @param storekeeperId 店家 id
     * @return ResponseResult
     */
    ResponseResult<Object> getGoods(String shopIdStr, String storekeeperId);

    /**
     * 新增商品的方法
     *
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> addGoods(GoodsDto goodsDto);

    /**
     * 上传商品文件的方法
     *
     * @param file 文件
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> uploadFile(MultipartFile file);

    /**
     * 更新商品的方法
     *
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> updateGoods(GoodsDto goodsDto);

    /**
     * 逻辑删除商品的方法
     *
     * @param map 商品 id的键值对
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> logicRemoveGoodsByGoodsID(Map<String, String> map);
}
