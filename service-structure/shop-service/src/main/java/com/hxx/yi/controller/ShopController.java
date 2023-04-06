package com.hxx.yi.controller;

import com.hxx.yi.service.ShopService;
import com.yi.common.entity.Shop;
import com.yi.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sp")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 创建新店铺的请求
     *
     * @param shop 前端传输的数据封装而成的 Shop对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/create")
    public ResponseResult<Object> createShop(@RequestBody Shop shop) {
        return shopService.createShop(shop);
    }

    @GetMapping("/list")
    public ResponseResult<Object> listShops(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(value = "id") String storekeeperId) {

        return shopService.listShops(pageNumber, pageSize, storekeeperId);
    }

    @GetMapping("/get")
    public ResponseResult<Object> getShops(@RequestParam(value = "storekeeperId") String storekeeperId) {
        return shopService.getAllShops(storekeeperId);
    }

    /**
     * 处理更新店铺请求
     *
     * @param shop 前端传来的信息封装而成的店铺对象
     * @return ResponseResult<Object>
     */
    @PutMapping("/update")
    public ResponseResult<Object> updateShop(@RequestBody Shop shop) {
        return shopService.updateShop(shop);
    }

    /**
     * @param shop 前端传来的信息封装而成的店铺对象
     * @return ResponseResult<Object>
     */
    @DeleteMapping("/delete")
    public ResponseResult<Object> deleteShop(@RequestBody Shop shop) {
        return shopService.deleteShop(shop);
    }
}
