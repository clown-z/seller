package com.clown.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clown.sell.service.SecKillService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillController {
    
    @Autowired 
    private SecKillService secKillService;
    
    /**
     * 查询秒杀活动特价商品的信息
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception {
	return secKillService.querySecKillProductInfo(productId);
    }
    
    /**
     * 秒杀,没有抢到获得"哎哟喂，xxxxxxxx", 抢到了会返回剩余库存量
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
	log.info("@skill request, productId: " + productId);
	secKillService.orderProductMockDiffUser(productId);
	return secKillService.querySecKillProductInfo(productId);
    }
}
