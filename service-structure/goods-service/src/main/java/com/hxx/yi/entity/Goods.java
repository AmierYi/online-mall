package com.hxx.yi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("test_goods")
public class Goods implements Serializable {
    /**
     * 商品 id
     */
    @TableId(value = "goods_id",type= IdType.ASSIGN_ID)
    private String goodsId;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 商品关键字
     */
    @TableField("key_words")
    private String keyWords;

    /**
     * 商品图片的 url路径
     */
    @TableField("image_url")
    private String imageUrl;


    /**
     * 商品的图片画廊
     */
    @TableField("gallery")
    private String gallery;


    /**
     * 商品的计量单位
     */
    @TableField("uint")
    private String uint;


    /**
     * 商品标识价格
     */
    @TableField("price")
    private BigDecimal price;


    /**
     * 商品的上架标识	0：下架	1：上架
     */
    @TableField("is_on_sale")
    private Short isOnSale;

    /**
     * 是否为新品的标识位
     */
    @TableField("is_new")
    private Short isNew;

    /**
     * 商品的新品标识	0：该商品不是新品	1：该商品是新品
     */
    @TableField("is_hot")
    private Short isHot;

    /**
     * 商品的删除标识	0：该商品被删除	1：该商品没有被删除
     */
    @TableField("is_delete")
    private Short isDelete;

    /**
     * 商品的创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 商品的更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
