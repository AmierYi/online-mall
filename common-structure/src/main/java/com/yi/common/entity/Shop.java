package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("test_shop")
public class Shop implements Serializable {

    /**
     * 店铺 id
     */
    @TableId(value = "shop_id",type = IdType.ASSIGN_UUID)
    private String shopId;

    /**
     * 店主 id
     */
    @TableField("storekeeper_id")
    private String storekeeperId;

    /**
     * 店主名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 店铺星级
     */
    @TableField("star_level")
    private String starLevel;

    /**
     * 店铺简介
     */
    @TableField("description")
    private String description;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;


    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 店铺封面图片
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 逻辑删除标志 0：删除  1：未删除
     */
    @TableField("is_delete")
    private Short isDelete;

}
