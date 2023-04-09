package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_goods_product")
public class GoodsProduct implements Serializable {

    /**
     * 产品id
     */
    @TableId(value = "product_id",type = IdType.ASSIGN_UUID)
    private String productId;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 产品的分类id
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 产品规格值列表，采用JSON数组格式
     */
    @TableField("specifications")
    private String specifications;

    /**
     * 产品的价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 产品示意图
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 产品库存量
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 产品规格的删除标识。0：该产品被删除	1：该产品没有被删除
     */
    @TableField("is_delete")
    private Short isDelete;

    /**
     * 产品的创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 产品的更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
