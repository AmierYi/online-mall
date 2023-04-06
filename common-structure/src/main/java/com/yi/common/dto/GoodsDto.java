package com.yi.common.dto;

import com.yi.common.entity.Goods;
import com.yi.common.entity.Shop;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDto extends Goods implements Serializable {

    /**
     * 店铺对象
     */
    private Shop shop;
}
