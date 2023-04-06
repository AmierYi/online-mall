package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsProductMapper;
import com.hxx.yi.service.GoodsProductService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.entity.GoodsProduct;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class GoodsProductServiceImpl extends ServiceImpl<GoodsProductMapper, GoodsProduct> implements GoodsProductService {

    @Autowired
    private GoodsProductMapper goodsProductMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> saveProduct(GoodsProductDto[] goodsProductDtos) {

        if(goodsProductDtos == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        ArrayList<GoodsProduct> list = new ArrayList<>();
        for (GoodsProductDto goodsProductDto : goodsProductDtos) {
            GoodsProduct goodsProduct = new GoodsProduct();
            BeanUtils.copyProperties(goodsProductDto,goodsProduct);

            goodsProduct.setImageUrl("");
            goodsProduct.setCreateTime(new DateTime());
            goodsProduct.setUpdateTime(new DateTime());
            goodsProduct.setIsDelete((short) 1);

            list.add(goodsProduct);
        }


        boolean flag = this.saveBatch(list);

        if(!flag) {
            return ResponseResult.error("添加商品失败!!");
        }

        return ResponseResult.success(list);
    }
}
