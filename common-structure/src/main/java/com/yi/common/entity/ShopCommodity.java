package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("test_shop_commodity")
@Data
public class ShopCommodity {

    /**
     * 店铺商品id
     */
    @TableId(value = "shop_commodity_id",type = IdType.ASSIGN_UUID)
    private String shopCommodityId;

    /**
     * '店铺id'
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * '商品id'
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * '商品名称'
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 逻辑删除标志 0：删除  1：未删除
     */
    @TableField("is_delete")
    private Short isDelete;

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
