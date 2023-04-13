package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.CouponMapper;
import com.hxx.yi.service.CouponService;
import com.yi.common.dto.CouponDto;
import com.yi.common.entity.Coupon;
import com.yi.common.entity.Storekeeper;
import com.yi.common.enums.ResponseEnum;
import com.yi.common.response.ResponseResult;
import com.yi.common.utils.StorekeeperThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    @Override
    public ResponseResult<Object> listCoupon(Integer pageNumber, Integer pageSize, String storekeeperId) {

        // 参数校验
        if (StrUtil.isBlank(storekeeperId)) {
            return ResponseResult.build(4001, "错误的店家id");
        }
        Storekeeper storekeeper = StorekeeperThreadLocal.get();
        if (!storekeeperId.equals(storekeeper.getStorekeeperId())) {
            return ResponseResult.error(ResponseEnum.OPERATION_FORBIDDEN);
        }

        Page<Coupon> couponPage = couponMapper.selectPage(
                new Page<>(pageNumber, pageSize),
                Wrappers.<Coupon>lambdaQuery()
                        .eq(Coupon::getStorekeeperId, storekeeperId)
                        .eq(Coupon::getIsDeleted, (short) 1));

        // TODO 把优惠券添加到 Redis中

        return ResponseResult.success(couponPage);
    }

    @Override
    public ResponseResult<Object> saveCoupon(CouponDto couponDto) {
        // 参数校验
        if (couponDto == null) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDto, coupon);
        coupon.setCreateTime(new DateTime());
        coupon.setUpdateTime(new DateTime());
        coupon.setIsDeleted((short) 1);

        int insert = couponMapper.insert(coupon);
        if (insert <= 0) {
            return ResponseResult.error("添加优惠券失败");
        }
        return ResponseResult.success("添加优惠券成功");
    }

    @Override
    public ResponseResult<Object> editCoupon(CouponDto couponDto) {
        // 参数校验
        if (couponDto == null) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDto, coupon);
        coupon.setUpdateTime(new DateTime());

        int i = couponMapper.updateById(coupon);
        if (i <= 0) {
            return ResponseResult.error("修改失败!");
        }

        // TODO 更新 Redis中的优惠券信息
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> removeCoupon(CouponDto couponDto) {
        // 参数校验
        if (couponDto == null) {
            return ResponseResult.error(ResponseEnum.ERROR_PARAM);
        }

        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponDto, coupon);
        coupon.setIsDeleted((short) 0);
        int i = couponMapper.updateById(coupon);
        if (i <= 0) {
            return ResponseResult.error("删除失败!");
        }

        // TODO 删除 Redis中的优惠券信息
        return ResponseResult.success();
    }
}
