package com.bester.platform.platformchain.controller;

import com.bester.platform.platformchain.common.CommonResult;
import com.bester.platform.platformchain.constant.RedisKeys;
import com.bester.platform.platformchain.enums.HttpStatus;
import com.bester.platform.platformchain.service.RedisClientService;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqiang
 */
@RestController
public class FindPageController {

    @Resource
    private RedisClientService redisClientService;

    /**
     * 返回发现页购物按钮的商品列表
     *
     * @return
     */
    @GetMapping("/find/commodity")
    public CommonResult getCommodityList() {
        List<Object> shoppingList = redisClientService.lRange(RedisKeys.FIND_PAGE_SHOPPING_KEY, 0L, 10L);
        if (CollectionUtils.isEmpty(shoppingList)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        Map<String, List<Object>> result = Maps.newHashMap();
        result.put(RedisKeys.FIND_PAGE_SHOPPING_KEY, shoppingList);
        return CommonResult.success(result);
    }

}
