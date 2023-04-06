package com.hxx.yi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxx.yi.mapper.StorekeeperMapper;
import com.hxx.yi.service.StorekeeperService;
import com.hxx.yi.utils.AppJwtUtil;
import com.yi.common.entity.Storekeeper;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class StorekeeperServiceImpl extends ServiceImpl<StorekeeperMapper, Storekeeper> implements StorekeeperService {

    @Autowired
    private StorekeeperMapper storekeeperMapper;

    @Override
    public ResponseResult<Object> register(Storekeeper storekeeper) {

        // 1. 查询该用户名是否已经被注册
        Storekeeper dbStorekeeper = storekeeperMapper.selectOne(
                Wrappers.<Storekeeper>lambdaQuery().eq(Storekeeper::getUsername, storekeeper.getUsername()));

        if (dbStorekeeper != null) { //使用用户名查询出数据库记录，说明该用户名已经被注册，直接返回
            return ResponseResult.build(500, "该用户名已被注册");
        }

        // 2. 用户名没有被注册
        String salt = createSalt();
        // 2.1 使用MD5算法处理盐加工后的字符串
        String newPassword = DigestUtil.md5Hex(storekeeper.getPassword() + salt);

        dbStorekeeper = new Storekeeper();
        BeanUtils.copyProperties(storekeeper,dbStorekeeper);
        dbStorekeeper.setPassword(newPassword);
        dbStorekeeper.setSalt(salt);
        dbStorekeeper.setStatus(Short.parseShort("1"));
        dbStorekeeper.setCreateTime(DateUtil.date(System.currentTimeMillis()));

        // 3. 保存到数据库中
        storekeeperMapper.insert(dbStorekeeper);
        return ResponseResult.build(200,"注册成功");
    }

    @Override
    public ResponseResult<Object> login(Storekeeper storekeeper) {
        // 1. 查询该用户名是否已经被注册
        Storekeeper dbStorekeeper = storekeeperMapper.selectOne(
                Wrappers.<Storekeeper>lambdaQuery().eq(Storekeeper::getUsername, storekeeper.getUsername()));
        if (dbStorekeeper == null) { //说明该用户不存在，直接返回
            return ResponseResult.build(500, "用户名密码错误");
        }

        // 用户名正确，开始比较密码。首先获取存在数据库中的密码和盐
        String dbPassword = dbStorekeeper.getPassword();
        String salt = dbStorekeeper.getSalt();

        if(!dbPassword.equals(DigestUtil.md5Hex(storekeeper.getPassword() + salt))) {
            // 密码不一致，直接返回错误信息
            return ResponseResult.build(500, "用户名密码错误");
        }

        // 用户名密码正确，返回数据
        dbStorekeeper.setPassword("");
        dbStorekeeper.setSalt("");

        Map<String,Object> responseData  = new HashMap<>();
        responseData.put("token", AppJwtUtil.getToken(dbStorekeeper.getStorekeeperId()));

        responseData.put("storekeeper",dbStorekeeper);
        return ResponseResult.build(200,"登录成功",responseData);
    }

    /**
     * 辅助方法：生成加密所需的盐
     *
     * @return 生成的盐字符串
     */
    private String createSalt() {
        Random random = new Random();
        //生成一个长度20的随机字符串，也就是所谓的盐
        StringBuilder stringBuilder = new StringBuilder(20);
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890").toCharArray();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append(random.nextInt(chars.length));
        }
        // 生成盐并且返回
        return stringBuilder.toString();
    }
}
