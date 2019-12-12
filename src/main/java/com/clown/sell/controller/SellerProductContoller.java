package com.clown.sell.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.clown.sell.domain.ProductCategory;
import com.clown.sell.domain.ProductInfo;
import com.clown.sell.enums.ResultEnum;
import com.clown.sell.from.ProductFrom;
import com.clown.sell.service.CategoryService;
import com.clown.sell.service.ProductInfoService;
import com.clown.sell.util.KeyUtil;

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
    
    @Autowired
    private CategoryService categoryService;
    
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
    
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, 
	    Map<String, Object> map) {
	try {
	    productService.onSale(productId);
	} catch (Exception e) {
	    map.put("msg", e.getMessage());
	    map.put("url", "/sell/seller/product/list");
	    return new ModelAndView("common/error", map);
	}
	map.put("msg", ResultEnum.PRODUCT_ON_SALE_SUCCESS.getMessage());
	map.put("url", "/sell/seller/product/list");
	return new ModelAndView("common/success", map);
    }
    
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, 
	    Map<String, Object> map) {
	try {
	    productService.offSale(productId);
	} catch (Exception e) {
	    map.put("msg", e.getMessage());
	    map.put("url", "/sell/seller/product/list");
	    return new ModelAndView("common/error", map);
	}
	map.put("msg", ResultEnum.PRODUCT_OFF_SALE_SUCCESS.getMessage());
	map.put("url", "/sell/seller/product/list");
	return new ModelAndView("common/success", map);
    }
    
    /**
     * 
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, 
	    Map<String, Object> map) {
	if (!StringUtils.isEmpty(productId)) {
	    ProductInfo productInfo = productService.findOne(productId);
	    map.put("productInfo", productInfo);
	}
	
	//查询所有的类目
	List<ProductCategory> categoryList = categoryService.findAll();
	map.put("categoryList", categoryList);
	
	return new ModelAndView("product/index", map);
    }
    
    /**
     * 保存/更新
     * @param productFrom
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductFrom productFrom, BindingResult bindingResult, Map<String, Object> map) {
	if (bindingResult.hasErrors()) {
	    map.put("msg", bindingResult.getFieldError().getDefaultMessage());
	    map.put("url", "/sell/seller/product/index");
	    return new ModelAndView("common/error", map);
	}
	
	ProductInfo productInfo = new ProductInfo();	
	try {
	    Date CREATE_TIME = new Date();
	    Date UPDATE_TIME = new Date();
	    //如果productId为空，说明是新增
	    if (!StringUtils.isEmpty(productFrom.getProductId())) {
		productInfo = productService.findOne(productFrom.getProductId());
		CREATE_TIME = productInfo.getCreateTime();
	    }else {
		productFrom.setProductId(KeyUtil.genUniqueKey());
		
	    }
	    
	    BeanUtils.copyProperties(productFrom, productInfo);
	    productInfo.setCreateTime(CREATE_TIME);
	    productInfo.setUpdateTime(UPDATE_TIME);
	    System.out.println(productInfo.toString());
	    productService.save(productInfo);
	} catch (Exception e) {
	    map.put("msg", e.getMessage());
	    map.put("url", "/sell/seller/product/index");
	    return new ModelAndView("common/error", map);
	}
	
	map.put("url", "/sell/seller/product/list");
	return new ModelAndView("common/success", map);
    }
}
