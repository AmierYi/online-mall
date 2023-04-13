package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hxx.yi.mapper.*;
import com.hxx.yi.service.*;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.Goods;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import com.yi.common.utils.StorekeeperThreadLocal;
import com.yi.common.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    private final GoodsMapper goodsMapper;
    private final MinioUtilsService minioUtilsService;


    public GoodsServiceImpl(GoodsMapper goodsMapper, MinioUtilsService minioUtilsService) {
        this.goodsMapper = goodsMapper;
        this.minioUtilsService = minioUtilsService;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> addGoods(GoodsDto goodsDto) {

        if (goodsDto == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 获取商品信息
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDto, goods);
        goods.setShopId(goodsDto.getShop().getShopId());
        goods.setCategoryId(goodsDto.getCategoryId());
        goods.setCreateTime(new DateTime());
        goods.setUpdateTime(new DateTime());
        goods.setIsOnSale((short) 0);
        goods.setIsHot((short) 0);
        goods.setIsNew((short) 0);
        goods.setIsDelete((short) 1);

        int insert = goodsMapper.insert(goods);

        if (insert <= 0) {
            return ResponseResult.error("添加商品失败!!");
        }


        return ResponseResult.success(goods);
    }

    @Override
    public ResponseResult<Object> uploadFile(MultipartFile file) {
        if (file == null) {
            log.error("参数出现了空");
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 获取上传文件的源文件名
        String originalFilename = file.getOriginalFilename();
        // 获取该文件的原格式
        assert originalFilename != null;
        int index = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(index);

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
    public ResponseResult<Object> listAllGoods(Integer pageNumber, Integer pageSize, String shopIdStr, String storekeeperId) {
        // 参数校验
        if (StrUtil.isBlank(shopIdStr) || StrUtil.isBlank(storekeeperId)) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        // 客户端登录的id和服务器保存的id不一致，违规操作。直接返回
        if (!storekeeperId.equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.error(ResponseEnum.OPERATION_FORBIDDEN);
        }

        String[] ids = shopIdStr.split(",");

        // 查询出该店家旗下商店的所有商品
        Page<GoodsVo> goodsVoPage = new Page<>(pageNumber, pageSize);
        List<Goods> goodsList = goodsMapper.selectList(
                Wrappers.<Goods>lambdaQuery()
                        .in(Goods::getShopId, ids)
                        .eq(Goods::getIsDelete, (short) 1));
        // 结果小于等于0，返回错误信息
        if (goodsList.size() == 0) {
            return ResponseResult.build(200, "暂无数据");
        }

        // 创建返回的vo集合
        List<GoodsVo> goodsVoList = goodsList.stream().map(item -> {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(item, goodsVo);
            return goodsVo;
        }).collect(Collectors.toList());

        goodsVoPage.setRecords(goodsVoList);
        goodsVoPage.setTotal(goodsVoList.size());

        if (goodsVoList.size() < pageSize) {
            goodsVoPage.setPages(1);
        } else {
            goodsVoPage.setPages(goodsVoList.size() % pageSize + 1);
        }

        return ResponseResult.success(goodsVoPage);
    }

    @Override
    public ResponseResult<Object> getGoods(String shopIdStr, String storekeeperId) {
        // 参数校验
        if (StrUtil.isBlank(shopIdStr) || StrUtil.isBlank(storekeeperId)) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }
        // 客户端登录的id和服务器保存的id不一致，违规操作。直接返回
        if (!storekeeperId.equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.error(ResponseEnum.OPERATION_FORBIDDEN);
        }

        String[] ids = shopIdStr.split(",");

        List<Goods> goods = goodsMapper.selectList(
                Wrappers.<Goods>lambdaQuery()
                        .in(Goods::getShopId, ids)
                        .eq(Goods::getIsDelete, (short) 1));
        return ResponseResult.success(goods);
    }

    @Override
    public ResponseResult<Object> updateGoods(GoodsDto goodsDto) {
        if (goodsDto == null) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDto, goods);
        goods.setUpdateTime(new DateTime());
        int i = goodsMapper.updateById(goods);

        if (i <= 0) {
            return ResponseResult.error("0条数据受到影响");
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> logicRemoveGoodsByGoodsID(Map<String, String> map) {
        String goodsId = map.get("goodsId");
        if (StrUtil.isBlank(goodsId)) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        UpdateWrapper<Goods> goodsUpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper<Goods> wrapper = goodsUpdateWrapper.set("is_delete", (short) 0).eq("goods_id", goodsId);
        int i = goodsMapper.update(null, wrapper);
        if (i <= 0) {
            return ResponseResult.error("逻辑删除失败");
        }
        return ResponseResult.success();
    }
}
