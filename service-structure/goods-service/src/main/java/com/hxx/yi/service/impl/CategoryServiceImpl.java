package com.hxx.yi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.CategoryMapper;
import com.hxx.yi.service.CategoryService;
import com.yi.common.entity.Category;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult<List<Category>> getAllCategory() {
        // 获取所有的商品分类记录
        List<Category> categories = categoryMapper.selectList(null);
        List<Category> categoryList = categories.stream()
                .filter(
                        category -> category.getParentCategoryId() == 0)
                .peek(
                        item -> item.setCategoryChildrenList(findAllCategoryChild(item, categories)))
                .collect(Collectors.toList());
        return ResponseResult.success(categoryList);
    }

    /**
     * 查找出某一个分类的所有子分类
     *
     * @param category   需要获取子分类的分类对象
     * @param categories 分类的 list集合（可以看做是一棵树，树里面就是所有的分类对象）
     * @return List<Category> 该分类的子分类集合
     */
    public List<Category> findAllCategoryChild(Category category, List<Category> categories) {
        return categories.stream()
                .filter(
                        item -> item.getParentCategoryId().equals(category.getCategoryId()))
                .peek(
                        childrenCategory -> findAllCategoryChild(childrenCategory, categories))
                .collect(Collectors.toList());
    }
}
