package com.clown.sell;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dao.ProductCategoryDao;
import com.clown.sell.domain.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao pcDao;
    
    @Test
    
    public void findOne() {
	ProductCategory pc = pcDao.getOne(1);
	System.out.println(pc + " " +pcDao.count());
    }
    
    @Test
    
    public void save() {
	ProductCategory pc = new ProductCategory();
	pc.setCategoryName("类目三");
	pc.setCategoryType(3);
	pc.setCreateTime(new Date());
	pc.setUpdateTime(new Date());
	ProductCategory p2 = pcDao.save(pc);
	System.out.println(p2);
	
    }
    @Test  
    public void updateTest() {
	ProductCategory pc = pcDao.getOne(2);
	pc.setCategoryName("类目二");
	pc.setCategoryType(2);
	pc.setCreateTime(pc.getCreateTime());
	pc.setUpdateTime(new Date());
	ProductCategory p2 = pcDao.save(pc);
	System.out.println(p2);
	
    }
    
    @Test
    public void findByCategoryTypeIn() {
	List<Integer> list = Arrays.asList(1,2,3);
	List<ProductCategory> result = pcDao.findByCategoryTypeIn(list);
	for (ProductCategory productCategory : result) {
	    System.out.println(productCategory);
	}
	
	Assert.assertNotEquals(0, result);
    }
}
