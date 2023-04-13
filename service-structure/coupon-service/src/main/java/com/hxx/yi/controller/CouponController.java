package com.hxx.yi.controller;

import com.hxx.yi.service.CouponService;
import com.yi.common.dto.CouponDto;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     *  处理获取所有的优惠券信息
     *
     * @param pageNumber    页码
     * @param pageSize      每一页的大小
     * @param storekeeperId 优惠券发布人 Id
     * @return ResponseResult
     */
    @GetMapping("/list")
    public ResponseResult<Object> listCoupon(@RequestParam(defaultValue = "1") Integer pageNumber,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam("storekeeperId") String storekeeperId) {
        return couponService.listCoupon(pageNumber,pageSize,storekeeperId);
    }

    /**
     * 处理新增优惠券的请求
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    @PostMapping("/save")
    public ResponseResult<Object> saveCoupon(@RequestBody CouponDto couponDto) {
        return couponService.saveCoupon(couponDto);
    }

    /**
     * 处理更改优惠券信息的请求
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    @PutMapping("/update")
    public ResponseResult<Object> editCoupon(@RequestBody CouponDto couponDto) {
        return couponService.editCoupon(couponDto);
    }

    /**
     * 处理逻辑删除优惠券信息的请求
     *
     * @param couponDto 优惠券的 dto对象
     * @return ResponseResult
     */
    @PutMapping("/remove")
    public ResponseResult<Object> removeCoupon(@RequestBody CouponDto couponDto) {
        return couponService.removeCoupon(couponDto);
    }
}
