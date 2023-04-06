package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.Goods;
import com.yi.common.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;


public interface GoodsService extends IService<Goods> {


    /**
     * 新增商品的方法
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> addGoods( GoodsDto goodsDto);

    /**
     * 上传商品文件的方法
     * @param file 文件
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> uploadFile(MultipartFile file);
}
