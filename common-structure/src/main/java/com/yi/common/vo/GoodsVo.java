package com.yi.common.vo;

import com.yi.common.entity.Goods;
import com.yi.common.entity.GoodsAttribute;
import com.yi.common.entity.GoodsProduct;
import com.yi.common.entity.GoodsSpecification;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsVo extends Goods implements Serializable {

    /**
     * 商品的属性列表
     */
    private List<GoodsAttribute> goodsAttributeList;

    /**
     * 商品的规格列表
     */
    private List<GoodsSpecification> goodsSpecificationList;


    /**
     * 商品的产品列表
     */
    private List<GoodsProduct> goodsProductList;
}
