package com.clown.sell.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clown.sell.domain.ProductCategory;
import com.clown.sell.domain.ProductInfo;
import com.clown.sell.service.CategoryService;
import com.clown.sell.service.ProductInfoService;
import com.clown.sell.util.ResultVOUtil;
import com.clown.sell.vo.ProductInfoVO;
import com.clown.sell.vo.ProductVO;
import com.clown.sell.vo.ResultVO;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService  productInfoService;
    
    @Autowired
    private CategoryService categoryService;
    
    
    @GetMapping("/list")
    public ResultVO list() {
	//1.查询所有的上架商品
	List<ProductInfo> productInfoList = productInfoService.findUpAll();
	
	//2.查询类目（一次查询）
	/*
	 * List<Integer> categoryTyoeList = new ArrayList<Integer>(); 
	 * //传统方法
	 * for (ProductInfo productInfo : productInfoList) {
	 * 	categoryTyoeList.add(productInfo.getCategoryType()); 
	 * }
	 */
	//精简方法(java8, lambda)
	List<Integer> categoryTyoeList =  productInfoList.stream()
		.map(e -> e.getCategoryType())
		.collect(Collectors.toList());
	List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTyoeList);
	
	//3.数据拼接
	/**  创建resultVO的data数据存放对象productVOList. */
	List<ProductVO> productVOList = new ArrayList<ProductVO>();
	/**  遍历类目信息. */
	for (ProductCategory productCategory : productCategoryList) {
	    /**  创建productVO对象.储存商品 */
	    ProductVO productVO = new ProductVO();
	    /**  设置类目名. */
	    productVO.setCategoryName(productCategory.getCategoryName());
	    /**  设置类目类型. */
	    productVO.setCategoryType(productCategory.getCategoryType()); 
	    /**  创建productVO的foods数据存放对象productInfoVOList. */
	    List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
	    /**  遍历商品信息. */
	    for (ProductInfo productInfo : productInfoList) {
		/**  判断商品类目是否相同. */
		if(productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
		    /**  创建productInfoVO对象，储存商品详情. */
		    ProductInfoVO productInfoVO = new ProductInfoVO();
		    /**  复制productInfo信息到productInfoVO. */
		    System.out.println(productInfo);
		    BeanUtils.copyProperties(productInfo, productInfoVO);
		    /**  添加到productInfoVOList对象. */
		    productInfoVOList.add(productInfoVO);
		}
	    }
	    /**  添加商品到foods. */
	    productVO.setProductInfoVOList(productInfoVOList);
	    /**  添加到productVOList对象. */
	    productVOList.add(productVO);
	    
	}
	/**  创建resultVO对象 */
//	ResultVO resultVO = new ResultVO();
//	ProductVO productVO = new ProductVO();
//	ProductInfoVO productInfoVO = new ProductInfoVO();
//	/** 设置状态码*/
//	resultVO.setCode(0);
//	/** 设置提示消息*/
//	resultVO.setMsg("成功");
//	resultVO.setData(productVO);
//	productVO.setCategoryName("热榜");
//	productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
	/** 设置商品信息到data*/
//	resultVO.setData(productVOList);
	return ResultVOUtil.success(productVOList);
    }
}
