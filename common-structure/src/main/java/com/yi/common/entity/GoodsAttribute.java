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

@TableName("test_goods_attribute")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAttribute implements Serializable {

    /**
     * 商品参数id
     */
    @TableId(value = "attribute_id",type = IdType.ASSIGN_UUID)
    private String attributeId;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 商品参数名
     */
    @TableField("attribute_name")
    private String attributeName;

    /**
     * 商品参数值
     */
    @TableField("attribute_value")
    private String attributeValue;

    /**
     * 商品参数的删除标识。0：该商品参数被删除	1：该商品参数没有被删除
     */
    @TableField("is_delete")
    private Short isDelete;

    /**
     * 商品参数的创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 商品参数的更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
