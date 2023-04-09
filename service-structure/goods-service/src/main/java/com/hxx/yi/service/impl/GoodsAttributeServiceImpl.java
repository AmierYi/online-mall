package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsAttributeMapper;
import com.hxx.yi.service.GoodsAttributeService;
import com.yi.common.dto.GoodsAttributeDto;
import com.yi.common.entity.GoodsAttribute;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements GoodsAttributeService {

    // 构造器注入法，注入GoodsAttributeMapper对象
    private final GoodsAttributeMapper goodsAttributeMapper;
    public GoodsAttributeServiceImpl(GoodsAttributeMapper goodsAttributeMapper) {
        this.goodsAttributeMapper = goodsAttributeMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> saveAttribute(List<Map<String,String>> goodsAttributeJsonList) {

        if (goodsAttributeJsonList == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        ArrayList<GoodsAttribute> goodsAttributeArrayList = new ArrayList<>();

        for (Map<String, String> attrMap : goodsAttributeJsonList) {
            GoodsAttribute goodsAttribute = new GoodsAttribute();

            goodsAttribute.setAttributeName( attrMap.get("attributeName"));
            goodsAttribute.setGoodsId(attrMap.get("goodsId"));
            goodsAttribute.setAttributeValue(attrMap.get("value"));
            goodsAttribute.setCreateTime(new DateTime());
            goodsAttribute.setUpdateTime(new DateTime());
            goodsAttribute.setIsDelete((short) 1);

            // 把解析出来的商品属性对象添加到list中
            goodsAttributeArrayList.add(goodsAttribute);
        }
        boolean flag = this.saveBatch(goodsAttributeArrayList);
        if(!flag) {
            return ResponseResult.error("添加属性失败！！");
        }
        return ResponseResult.success(goodsAttributeArrayList);
    }

    @Override
    public ResponseResult<Object> getAttributeByGoodsId(String goodsId) {
        // 参数校验
        if(StrUtil.isBlank(goodsId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        List<GoodsAttribute> goodsAttributes =
                goodsAttributeMapper.selectList(
                        Wrappers.<GoodsAttribute>lambdaQuery()
                                .eq(GoodsAttribute::getGoodsId, goodsId)
                                .eq(GoodsAttribute::getIsDelete,(short) 1));

        if(goodsAttributes.size() <= 0) {
            return ResponseResult.error("出现了意料之外的错误，没有查询出任何数据！");
        }
        return ResponseResult.success(goodsAttributes);
    }

    @Override
    public ResponseResult<Object> updateAttributeById(GoodsAttributeDto goodsAttributeDto) {
        // 参数校验
        if(goodsAttributeDto == null || StrUtil.isBlank(goodsAttributeDto.getAttributeId())) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        GoodsAttribute goodsAttribute = new GoodsAttribute();
        BeanUtils.copyProperties(goodsAttributeDto,goodsAttribute);
        goodsAttribute.setUpdateTime(new DateTime());

        int update =goodsAttributeMapper.updateById(goodsAttribute);

        if(update <= 0) {
            return ResponseResult.error("受到影响的行数为0");
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> logicRemoveAttributeById(Map<String,String> map) {

        String goodsId = map.get("goodsId");
        String attributeId = map.get("attributeId");
        // 参数校验
        if(StrUtil.isBlank(goodsId) && StrUtil.isBlank(attributeId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        UpdateWrapper<GoodsAttribute> goodsAttributeUpdateWrapper = new UpdateWrapper<>();

        UpdateWrapper<GoodsAttribute> updateWrapper;
        if(attributeId == null) {
            // 逻辑删除指定商品下的所有属性
            updateWrapper = goodsAttributeUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("goods_id", goodsId);
        } else {
            // 逻辑删除指定的属性
            updateWrapper = goodsAttributeUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("attribute_id", attributeId);
        }
        int update = goodsAttributeMapper.update(null, updateWrapper);

        if(update <= 0) {
            return ResponseResult.build(200,"逻辑删除失败");
        }

        return ResponseResult.success();
    }
}
