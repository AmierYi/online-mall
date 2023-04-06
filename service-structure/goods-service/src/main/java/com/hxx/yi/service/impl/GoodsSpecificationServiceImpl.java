package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsSpecificationMapper;
import com.hxx.yi.service.GoodsSpecificationService;
import com.yi.common.dto.GoodsSpecificationDto;
import com.yi.common.entity.GoodsSpecification;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodsSpecificationServiceImpl extends ServiceImpl<GoodsSpecificationMapper, GoodsSpecification> implements GoodsSpecificationService {

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> saveGoodsSpecificationService(List<Map<String, String>> goodsSpecificationList) {

        if (goodsSpecificationList == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        ArrayList<GoodsSpecification> list = new ArrayList<>();
        for (Map<String, String> map : goodsSpecificationList) {
            GoodsSpecification goodsSpecification = new GoodsSpecification();

            goodsSpecification.setGoodsId(map.get("goodsId"));
            goodsSpecification.setSpecificationName(map.get("specificationName"));
            goodsSpecification.setSpecificationValue(map.get("values"));
            goodsSpecification.setIsDelete((short) 1);
            goodsSpecification.setCreateTime(new DateTime());
            goodsSpecification.setUpdateTime(new DateTime());

            list.add(goodsSpecification);
        }


        boolean flag = this.saveBatch(list);

        if(!flag) {
            return ResponseResult.error("添加规格失败!!");
        }

        return ResponseResult.success(list);
    }
}
