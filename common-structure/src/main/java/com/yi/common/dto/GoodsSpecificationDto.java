package com.yi.common.dto;

import com.yi.common.entity.GoodsSpecification;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsSpecificationDto extends GoodsSpecification implements Serializable {

    /**
     * 商品 id
     */
    private String goodsId;
}
