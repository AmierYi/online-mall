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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_category")
public class Category implements Serializable {

    /**
     * 分类id
     */
    @TableId(value = "category_id",type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 分类的关键字
     */
    @TableField("key_words")
    private String keyWords;

    /**
     * 分类的展示图片
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 分类的等级    0：根节点   1：一级分类...
     */
    @TableField("category_level")
    private Integer categoryLevel;

    /**
     * 父分类的id
     */
    @TableField("parent_category_id")
    private Integer parentCategoryId;

    /**
     * 删除标志 1：未删除   0：已删除
     */
    @TableField("is_delete")
    private Short isDelete;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 子分类集合
     */
    @TableField(exist = false)
    private List<Category> categoryChildrenList;
}
