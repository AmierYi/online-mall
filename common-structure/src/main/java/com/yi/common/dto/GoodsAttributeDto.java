package com.yi.common.dto;

import com.yi.common.entity.GoodsAttribute;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsAttributeDto extends GoodsAttribute implements Serializable {

    /**
     * 商品 id
     */
    private String goodsId;
}
