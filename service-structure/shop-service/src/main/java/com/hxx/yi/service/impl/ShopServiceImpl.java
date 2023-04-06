package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.ShopMapper;
import com.hxx.yi.service.ShopService;
import com.yi.common.entity.Shop;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import com.yi.common.utils.StorekeeperThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ResponseResult<Object> createShop(Shop shop) {

        // 参数值校验
        if (null == shop || null == shop.getStorekeeperId()) {
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 防止手动输入参数造假了，为他人创建店铺
        if (!shop.getStorekeeperId().equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.build(ResponseEnum.OPERATION_FORBIDDEN);
        }

        // 创建插入对象，并且赋值
        Shop s = new Shop();
        BeanUtils.copyProperties(shop, s);
        s.setStarLevel("一星");
        s.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        s.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        s.setIsDelete(Short.parseShort("1"));
        shopMapper.insert(s);

        return ResponseResult.build(200, "创建商店成功");
    }

    @Override
    public ResponseResult<Object> listShops(Integer pageNumber, Integer pageSize, String storekeeperId) {

        // 参数校验
        if (StrUtil.isBlank(storekeeperId)) {
            return ResponseResult.build(4001, "错误的店家id");
        }

        // 判断是否是违规查询
        if (!storekeeperId.equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.build(ResponseEnum.OPERATION_FORBIDDEN);
        }

        // 定制查询
        Page<Shop> shopPage = shopMapper.selectPage(new Page<>(pageNumber, pageSize),
                Wrappers.<Shop>lambdaQuery().
                        eq(Shop::getStorekeeperId, storekeeperId));

        return ResponseResult.success(shopPage);
    }

    @Override
    public ResponseResult<Object> getAllShops(String storekeeperId) {

        // 参数校验
        if (StrUtil.isBlank(storekeeperId)) {
            return ResponseResult.build(4001, "错误的店家id");
        }

        // 判断是否是违规查询
        if (!storekeeperId.equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.build(ResponseEnum.OPERATION_FORBIDDEN);
        }


        List<Shop> shops = shopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getStorekeeperId, storekeeperId));
        return ResponseResult.success(shops);
    }

    @Override
    public ResponseResult<Object> deleteShop(Shop shop) {
        if (shop == null || StrUtil.isBlank(shop.getShopId())) {
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 判断是否是违规更改
        if (!shop.getStorekeeperId().equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.build(ResponseEnum.OPERATION_FORBIDDEN);
        }

        int effectRow = shopMapper.deleteById(shop.getShopId());
        return effectRow > 0 ?
                ResponseResult.build(200, "删除成功", shop) : ResponseResult.error("删除失败");
    }

    @Override
    public ResponseResult<Object> updateShop(Shop shop) {
        if (shop == null || StrUtil.isBlank(shop.getShopId())) {
            return ResponseResult.build(ResponseEnum.ERROR_PARAM);
        }

        // 判断是否是违规更改
        if (!shop.getStorekeeperId().equals(StorekeeperThreadLocal.get().getStorekeeperId())) {
            return ResponseResult.build(ResponseEnum.OPERATION_FORBIDDEN);
        }

        int effectRow = shopMapper.updateById(shop);
        return effectRow > 0 ?
                ResponseResult.build(200, "更改成功", shop) : ResponseResult.error("删除失败");
    }
}
