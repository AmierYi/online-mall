package com.hxx.yi.controller;

import com.hxx.yi.service.CategoryService;
import com.yi.common.entity.Category;
import com.yi.common.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有商品分类的请求
     * @return ResponseResult<List<Category>>
     */
    @GetMapping("/list")
    public ResponseResult<List<Category>> getCategory(){
        return categoryService.getAllCategory();
    }
}
