package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsAttributeMapper;
import com.hxx.yi.service.GoodsAttributeService;
import com.yi.common.entity.GoodsAttribute;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

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
}
