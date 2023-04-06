package com.yi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("test_storekeeper")
public class Storekeeper implements Serializable {
    /**
     * 店主 id
     */
    @TableId(value = "storekeeper_id", type = IdType.ASSIGN_ID)
    private String storekeeperId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 昵称
     */
    @TableField("nike_name")
    private String nikeName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 加密盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 电话号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 状态	0：禁止	1：正常
     */
    @TableField("status")
    private Short status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
