package com.clown.sell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.clown.sell.domain.ProductInfo;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.service.ProductInfoService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

/**
 * 卖家端商品
 * @author Clown
 *
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductContoller {

    @Autowired
    private ProductInfoService productService;
    
    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
	    @RequestParam(value = "size", defaultValue = "10") Integer size, 
	    Map<String, Object> map) {
	PageRequest request = PageRequest.of(page - 1, size);
	Page<ProductInfo> productInfoPage = productService.findAll(request);
	
	map.put("productInfoPage", productInfoPage);
	map.put("currentPage", page);
	map.put("size", size);
	return new ModelAndView("product/list", map);
    }
}
