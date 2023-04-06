package com.hxx.yi.service;

import com.baomidou.mybatisplus.extension.service.IService;


import com.yi.common.entity.Storekeeper;
import com.yi.common.response.ResponseResult;

public interface StorekeeperService extends IService<Storekeeper> {

    /**
     * 店主注册的方法
     *
     * @param storekeeper 前端注册页面数据封装成的 Storekeeper 对象
     * @return ResponseResult<Object>
     */
    ResponseResult<Object> register(Storekeeper storekeeper);

    /**
     * 店主登录的方法
     *
     * @param storekeeper 前端登录页面数据封装成的 Storekeeper 对象
     * @return ResponseResult<Storekeeper>
     */
    ResponseResult<Object> login(Storekeeper storekeeper);
}
