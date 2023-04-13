package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("test_coupon")
public class Coupon implements Serializable {

    /**
     * 优惠券id
     */
    @TableId(value = "coupon_id", type = IdType.ASSIGN_UUID)
    private String couponId;

    /**
     * 优惠的商品ID
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 优惠券发行人 Id
     */
    @TableField("storekeeper_id")
    private String storekeeperId;

    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;

    /**
     * 优惠券介绍，通常是显示优惠券使用限制文字
     */
    @TableField("description")
    private String description;

    /**
     * 优惠详情
     */
    @TableField("discount")
    private BigDecimal discount;

    /**
     * 发行总量
     */
    @TableField("total")
    private Integer total;

    /**
     * 优惠券状态
     */
    @TableField("status")
    private String status;

    /**
     * 优惠券类型
     */
    @TableField("type")
    private String type;

    /**
     * 优惠券起始有效时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 优惠券失效时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 逻辑删除标志 0：删除  1：未删除
     */
    @TableField("is_deleted")
    private Short isDeleted;

    /**
     * 登记时间
     */
    @TableField("create_time")
    private Date createTime;


    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
