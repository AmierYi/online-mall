package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsSpecificationMapper;
import com.hxx.yi.service.GoodsSpecificationService;
import com.yi.common.dto.GoodsSpecificationDto;
import com.yi.common.entity.GoodsSpecification;
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
public class GoodsSpecificationServiceImpl extends ServiceImpl<GoodsSpecificationMapper, GoodsSpecification> implements GoodsSpecificationService {


    private final GoodsSpecificationMapper goodsSpecificationMapper;

    public GoodsSpecificationServiceImpl(GoodsSpecificationMapper goodsSpecificationMapper) {
        this.goodsSpecificationMapper = goodsSpecificationMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> saveGoodsSpecification(List<Map<String, String>> goodsSpecificationList) {

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

        if (!flag) {
            return ResponseResult.error("添加规格失败!!");
        }

        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult<Object> getSpecificationByGoodsId(String goodsId) {

        // 参数校验
        if (StrUtil.isBlank(goodsId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        List<GoodsSpecification> goodsSpecifications =
                goodsSpecificationMapper.selectList(
                        Wrappers.<GoodsSpecification>lambdaQuery().eq(GoodsSpecification::getGoodsId, goodsId)
                                .eq(GoodsSpecification::getIsDelete, (short) 1));

        if (goodsSpecifications.size() <= 0) {
            return ResponseResult.error("出现了意料之外的错误，没有查询出任何数据！");
        }
        return ResponseResult.success(goodsSpecifications);
    }

    @Override
    public ResponseResult<Object> updateSpecificationById(GoodsSpecificationDto goodsSpecificationDto) {

        if (goodsSpecificationDto == null || StrUtil.isBlank(goodsSpecificationDto.getSpecificationId())) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }


        GoodsSpecification goodsSpecification = new GoodsSpecification();
        BeanUtils.copyProperties(goodsSpecificationDto, goodsSpecification);
        goodsSpecification.setUpdateTime(new DateTime());

        int i = goodsSpecificationMapper.updateById(goodsSpecification);
        if (i <= 0) {
            return ResponseResult.error("0条数据受到影响");
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> logicRemoveSpecificationById(Map<String, String> map) {

        String goodsId = map.get("goodsId");
        String specificationId = map.get("specificationId");
        if (StrUtil.isBlank(goodsId) && StrUtil.isBlank(specificationId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        UpdateWrapper<GoodsSpecification> goodsSpecificationUpdateWrapper = new UpdateWrapper<>();

        UpdateWrapper<GoodsSpecification> wrapper;
        if (specificationId != null) {
            wrapper = goodsSpecificationUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("specification_id", specificationId);
        } else {
            wrapper = goodsSpecificationUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("goods_id",goodsId);
        }

        int update = goodsSpecificationMapper.update(null, wrapper);
        if(update <= 0) {
            return ResponseResult.build(200,"逻辑删除失败");
        }
        return ResponseResult.success();
    }
}
