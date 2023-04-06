package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hxx.yi.mapper.GoodsMapper;
import com.hxx.yi.service.GoodsService;
import com.hxx.yi.service.MinioUtilsService;
import com.hxx.yi.service.ShopCommodityService;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.Goods;
import com.yi.common.entity.Shop;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private MinioUtilsService minioUtilsService;

    @Autowired
    private ShopCommodityService shopCommodityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> addGoods(GoodsDto goodsDto) {

        if( goodsDto == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 获取商品dto对象中的店铺信息
        Shop shop = goodsDto.getShop();

        // 获取商品信息
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDto, goods);
        goods.setCreateTime(new DateTime());
        goods.setUpdateTime(new DateTime());
        goods.setIsOnSale((short) 0);
        goods.setIsHot((short) 0);
        goods.setIsNew((short) 0);
        goods.setIsDelete((short) 1);

        int insert = goodsMapper.insert(goods);

        if(insert <= 0) {
            return ResponseResult.error("添加商品失败!!");
        }

        // 更新店铺商品表
        shopCommodityService.saveShopCommodity(shop,goods);

        return ResponseResult.success(goods);
    }

    @Override
    public ResponseResult<Object> uploadFile(MultipartFile file) {
        if(file == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 获取上传文件的源文件名
        String originalFilename = file.getOriginalFilename();
        // 获取该文件的原格式
        int index = originalFilename.indexOf(".");
        String substring = originalFilename.substring(index);

        String imageName = IdUtil.randomUUID() + substring;
        String imageUrl = "";

        // 上传图片并且获取返回的url路径
        try {
            minioUtilsService.uploadFile("public", imageName, file.getInputStream());
            imageUrl = minioUtilsService.getPresignedObjectUrl("public", imageName);
            System.out.println(imageUrl);
        } catch (Exception e) {
            log.error("上传文件到minio时出现异常，信息:" + e);
            return ResponseResult.error("上传文件失败！请稍后重试。");
        }
        return ResponseResult.success(imageUrl);
    }
}
