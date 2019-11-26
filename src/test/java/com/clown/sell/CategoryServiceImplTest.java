package com.clown.sell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.domain.ProductCategory;
import com.clown.sell.service.impl.CategoryServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    
    @Autowired
    private CategoryServiceImpl cs;
    
    @Test
    public void findOne() {
	ProductCategory pc = cs.findOne(1);
	assertEquals(new Integer("1"), pc.getCategoryId());
    }
    
    @Test
    public void saveTest() {
	ProductCategory pc = new ProductCategory();
	pc.setCategoryName("类目5");
	pc.setCategoryType(5);
	pc.setCreateTime(new Date());
	pc.setUpdateTime(new Date());
	ProductCategory pc2 = cs.save(pc);
	assertEquals(1, pc2);
    }
    
    @Test
    public void updateTest() {
	ProductCategory pc = cs.findOne(5);
	pc.setCategoryName("类目五");
	pc.setUpdateTime(new Date());
	ProductCategory pc2 = cs.save(pc);
	assertEquals(1, cs.save(pc));
    }
    
    @Test
    public void findByCategoryTypeInTest() {
	List<Integer> list = Arrays.asList(2,3,4);
	List<ProductCategory> pcList = cs.findByCategoryTypeIn(list);
	for (ProductCategory productCategory : pcList) {
	    System.out.println(productCategory);
	}
	assertNotEquals(null, pcList);
    }
}
