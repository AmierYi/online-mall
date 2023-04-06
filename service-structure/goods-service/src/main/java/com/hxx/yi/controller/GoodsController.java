package com.hxx.yi.controller;

import com.hxx.yi.mapper.GoodsMapper;
import com.hxx.yi.service.GoodsService;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.Goods;
import com.yi.common.response.ResponseResult;
import com.yi.common.utils.StorekeeperThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public ResponseResult listAllGoods() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("storekeeperId", StorekeeperThreadLocal.get().getStorekeeperId());
        List<Goods> goods = goodsMapper.selectList(null);
        map.put("good", goods);
        return ResponseResult.success(map);
    }

    /**
     * 处理新增商品的请求
     *
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/saveGoods")
    public ResponseResult<Object> saveGoods(@RequestBody GoodsDto goodsDto) {
        return goodsService.addGoods(goodsDto);
    }



    /**
     * 处理商品页面图片上传的请求
     *
     * @param file 上传的图片对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/upload")
    public ResponseResult<Object> upload(MultipartFile file) {
        return goodsService.uploadFile(file);
    }

}
