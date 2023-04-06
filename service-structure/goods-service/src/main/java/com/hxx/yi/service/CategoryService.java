package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yi.common.entity.Category;
import com.yi.common.response.ResponseResult;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 返回所有的商品分类
     *
     * @return List<Category> 商品分裂的 List集合
     */
    ResponseResult<List<Category>> getAllCategory();
}
