package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.dto.CouponDto;
import com.yi.common.entity.Coupon;
import com.yi.common.response.ResponseResult;

public interface CouponService extends IService<Coupon> {

    /**
     * 获取所有的优惠券信息
     *
     * @param pageNumber    页码
     * @param pageSize      每一页的大小
     * @param storekeeperId 优惠券发布人 Id
     * @return ResponseResult
     */
    ResponseResult<Object> listCoupon(Integer pageNumber, Integer pageSize, String storekeeperId);

    /**
     * 新增优惠券的方法
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    ResponseResult<Object> saveCoupon(CouponDto couponDto);

    /**
     * 更改优惠券信息的方法
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    ResponseResult<Object> editCoupon(CouponDto couponDto);

    /**
     * 逻辑删除优惠券信息的方法
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    ResponseResult<Object> removeCoupon(CouponDto couponDto);
}
