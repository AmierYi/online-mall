package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_goods_specification")
public class GoodsSpecification implements Serializable {

    /**
     * 商品属性id
     */
    @TableId(value = "specification_id", type = IdType.ASSIGN_UUID)
    private String specificationId;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 商品规格名
     */
    @TableField("specification_name")
    private String specificationName;

    /**
     * 商品规格值
     */
    @TableField("specification_value")
    private String specificationValue;

    /**
     * 商品规格的删除标识。0：该商品规格被删除	1：该商品规格没有被删除
     */
    @TableField("is_delete")
    private Short isDelete;

    /**+
     * 商品规格的创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 商品规格的更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
