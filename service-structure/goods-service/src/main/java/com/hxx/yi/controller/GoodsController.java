package com.hxx.yi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxx.yi.service.*;
import com.yi.common.dto.GoodsDto;
import com.yi.common.entity.*;
import com.yi.common.response.ResponseResult;
import com.yi.common.vo.GoodsVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    private final GoodsService goodsService;
    private final ShopCommodityService shopCommodityService;
    private final GoodsAttributeService goodsAttributeService;
    private final GoodsSpecificationService goodsSpecificationService;
    private final GoodsProductService goodsProductService;

    public GoodsController(GoodsService goodsService, ShopCommodityService shopCommodityService,
                           GoodsAttributeService goodsAttributeService, GoodsSpecificationService goodsSpecificationService,
                           GoodsProductService goodsProductService) {

        this.goodsService = goodsService;
        this.shopCommodityService = shopCommodityService;
        this.goodsAttributeService = goodsAttributeService;
        this.goodsSpecificationService = goodsSpecificationService;
        this.goodsProductService = goodsProductService;
    }

    /**
     * 处理获取指定店主旗下所有商店的商品信息的请求
     *
     * @param shopIdStr     店铺 id
     * @param storekeeperId 店主 id
     * @param pageNumber    页码，默认为1
     * @param pageSize      每一页数据的大小
     * @return ResponseResult<Object>
     */
    @GetMapping("/list")
    public ResponseResult<Object> listAllGoods(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam("shopIdStr") String shopIdStr, @RequestParam("storekeeperId") String storekeeperId) {

        // 获取goodsVo集合
        ResponseResult<Object> responseResult = goodsService.listAllGoods(pageNumber, pageSize, shopIdStr, storekeeperId);
        if (!(responseResult.getData() instanceof Page)) {
            return responseResult;
        }
        Page<GoodsVo> goodsVoPage = (Page<GoodsVo>) responseResult.getData();
        List<GoodsVo> goodsVos = mergeGoodsVo(responseResult);
        goodsVoPage.setRecords(goodsVos);
        return ResponseResult.success(goodsVoPage);
    }

    /**
     * /**
     * 获取旗下所有商品（不包含属性、规格和 sku）
     *
     * @param shopIdStr     店铺 id数组的拼接字符串
     * @param storekeeperId 店家 id
     * @return ResponseResult
     */
    @GetMapping("/get")
    public ResponseResult<Object> getGoods(@RequestParam("shopIdStr") String shopIdStr,@RequestParam("storekeeperId") String storekeeperId) {
        return this.goodsService.getGoods(shopIdStr, storekeeperId);
    }


    /**
     * 处理新增商品的请求
     *
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    @PostMapping("/saveGoods")
    public ResponseResult<Object> saveGoods(@RequestBody GoodsDto goodsDto) {
        ResponseResult<Object> responseResult = goodsService.addGoods(goodsDto);
        Goods goods = (Goods) responseResult.getData();
        Shop shop = goodsDto.getShop();
        // 更新店铺商品表
        shopCommodityService.saveShopCommodity(shop, goods);
        return responseResult;
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

    /**
     * 处理更新商品信息的请求
     *
     * @param goodsDto 商品的 dto对象
     * @return ResponseResult<Object>
     */
    @PutMapping("/update")
    public ResponseResult<Object> updateGoods(@RequestBody GoodsDto goodsDto) {
        return goodsService.updateGoods(goodsDto);
    }

    /**
     * 逻辑删除商品的请求
     *
     * @param map 封装的 map
     * @return ResponseResult<Object>
     */
    @PutMapping("/remove")
    public ResponseResult<Object> logicRemoveGoods(@RequestBody Map<String, String> map) {
        ResponseResult<Object> responseResult = goodsService.logicRemoveGoodsByGoodsID(map);

        if (responseResult.getCode() != 200) {
            return responseResult;
        }
        goodsAttributeService.logicRemoveAttributeById(map);
        goodsSpecificationService.logicRemoveSpecificationById(map);
        goodsProductService.logicRemoveSpecification(map);
        return responseResult;
    }


    /**
     * 获取所有商品记录的辅助方法。通过商品集合获取其中元素对应的商品属性集合、商品规格集合和 sku集合。
     * 最后将这些集合合并到一个 List<GoodsVo>中返回。
     *
     * @param responseResult 获取商品集合的 ResponseResult对象
     * @return List<GoodsVo>
     */
    private List<GoodsVo> mergeGoodsVo(ResponseResult<Object> responseResult) {

        Page<GoodsVo> data = (Page<GoodsVo>) responseResult.getData();

        // 封装商品的属性集合
        List<GoodsVo> collect = data.getRecords().stream().peek(item -> {
            ResponseResult<Object> attributeByGoodsId = goodsAttributeService.getAttributeByGoodsId(item.getGoodsId());
            List<GoodsAttribute> attr = (List<GoodsAttribute>) attributeByGoodsId.getData();
            item.setGoodsAttributeList(attr);
        }).collect(Collectors.toList());

        // 封装商品的规格集合
        collect = collect.stream().peek(item -> {
            ResponseResult<Object> specificationByGoodsId = goodsSpecificationService.getSpecificationByGoodsId(item.getGoodsId());
            List<GoodsSpecification> specificationList = (List<GoodsSpecification>) specificationByGoodsId.getData();
            item.setGoodsSpecificationList(specificationList);
        }).collect(Collectors.toList());

        // 封装商品的sku集合
        collect = collect.stream().peek(item -> {
            ResponseResult<Object> productByGoodsId = goodsProductService.getProductByGoodsId(item.getGoodsId());
            List<GoodsProduct> productList = (List<GoodsProduct>) productByGoodsId.getData();
            item.setGoodsProductList(productList);
        }).collect(Collectors.toList());

        return collect;
    }

}
