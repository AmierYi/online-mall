package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.GoodsProductMapper;
import com.hxx.yi.service.GoodsProductService;
import com.hxx.yi.service.MinioUtilsService;
import com.yi.common.dto.GoodsProductDto;
import com.yi.common.entity.GoodsProduct;
import com.yi.common.entity.GoodsSpecification;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsProductServiceImpl extends ServiceImpl<GoodsProductMapper, GoodsProduct> implements GoodsProductService {

    private final GoodsProductMapper goodsProductMapper;
    private final MinioUtilsService minioUtilsService;
    public GoodsProductServiceImpl(GoodsProductMapper goodsProductMapper,MinioUtilsService minioUtilsService) {
        this.goodsProductMapper = goodsProductMapper;
        this.minioUtilsService = minioUtilsService;
    }

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

            goodsProduct.setImageUrl(goodsProductDto.getImageUrl());
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

    @Override
    public ResponseResult<Object> saveProductImage(MultipartFile file) {

        if(file.isEmpty()) {
            return ResponseResult.error("图片有误！");
        }
        // 获取源文件名
        String originalFilename = file.getOriginalFilename();

        // 获取源文件格式
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(lastIndexOf);

        String imageName = IdUtil.randomUUID() + substring;
        String imageUrl;

        // 上传图片并且获取返回的url路径
        try {
            minioUtilsService.uploadFile("public", imageName, file.getInputStream());
            imageUrl = minioUtilsService.getPresignedObjectUrl("public", imageName);
        } catch (Exception e) {
            log.error("上传文件到minio时出现异常，信息:" + e);
            return ResponseResult.error("上传文件失败！请稍后重试。");
        }

        return ResponseResult.success(imageUrl);
    }

    @Override
    public ResponseResult<Object> getProductByGoodsId(String goodsId) {

        // 参数校验
        if(StrUtil.isBlank(goodsId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        List<GoodsProduct> goodsProducts =
                goodsProductMapper.selectList(
                        Wrappers.<GoodsProduct>lambdaQuery()
                                .eq(GoodsProduct::getGoodsId, goodsId)
                                .eq(GoodsProduct::getIsDelete,(short) 1));

        if(goodsProducts.size() <= 0) {
            return ResponseResult.error("出现了意料之外的错误，没有查询出任何数据！");
        }
        return ResponseResult.success(goodsProducts);
    }

    @Override
    public ResponseResult<Object> updateProductById(GoodsProductDto goodsProductDto) {

        if(goodsProductDto == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        GoodsProduct goodsProduct = new GoodsProduct();
        BeanUtils.copyProperties(goodsProductDto,goodsProduct);
        goodsProduct.setUpdateTime(new DateTime());

        int i = goodsProductMapper.updateById(goodsProduct);
        if(i <= 0) {
            return ResponseResult.error("受到影响的记录为0");
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> logicRemoveSpecification(Map<String, String> map) {

        String goodsId = map.get("goodsId");
        String productId = map.get("productId");
        if (StrUtil.isBlank(goodsId) && StrUtil.isBlank(productId)) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        UpdateWrapper<GoodsProduct> goodsProductUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<GoodsProduct> wrapper;
        if (productId != null) {
            wrapper = goodsProductUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("product_id", productId);
        } else {
            wrapper = goodsProductUpdateWrapper
                    .set("is_delete", (short) 0)
                    .eq("goods_id",goodsId);
        }

        int update = goodsProductMapper.update(null, wrapper);
        if(update <= 0) {
            return ResponseResult.build(200,"逻辑删除失败");
        }
        return ResponseResult.success();
    }
}
