package com.clown.sell.mybatis;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.domain.ProductCategory;
import com.clown.sell.domain.mapper.ProductCategoryMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryMapperTest {

    
    @Autowired
    private  ProductCategoryMapper productCategoryMapper;
    
    @Test
    public void insertByMap() {
	Map<String, Object> map = new HashMap<String, Object>();	
	map.put("category_name", "insertByMapTest");
	map.put("category_type", 9);
	map.put("createTime", new Date());
	map.put("updateTime",new Date());
	int result = productCategoryMapper.insertByMap(map);
	Assert.assertEquals(1, result);
    }
    
    @Test
    public void insertByObject() {
	ProductCategory category = new ProductCategory();
	category.setCategoryName("insertByMapTest");
	category.setCategoryType(11);
	category.setCreateTime(new Date());
	category.setUpdateTime(new Date());
	int result = productCategoryMapper.insertByObject(category);
	Assert.assertEquals(1, result);
    }

    @Test
    public void findByCategoryType() {
	ProductCategory result = productCategoryMapper.findByCategoryType(8);
	Assert.assertNotNull(result);
    }
    
    @Test
    public void findByCategoryName() {
	List<ProductCategory> result = productCategoryMapper.findByCategoryName("类目九");
	Assert.assertNotNull(result);
    }
    
    @Test
    public void updateByCategoryType() {
	int result = productCategoryMapper.updateByCategoryType("类目十一", 9);
	Assert.assertEquals(1, result);
    }
    
    @Test
    public void updateByCategoryObject() {
	ProductCategory category = new ProductCategory();
	category.setCategoryName("类目九");
	category.setCategoryType(8);
	category.setCreateTime(new Date());
	category.setUpdateTime(new Date());
	int result = productCategoryMapper.updateByCategoryObject(category);
	Assert.assertEquals(1, result);
    }
    
    @Test
    public void deleteByCategoryType() {
	int result = productCategoryMapper.deleteByCategoryType(11);
	Assert.assertEquals(1, result);
    }
    
    @Test
    public void selectByCategoryType() {
	ProductCategory result = productCategoryMapper.selectByCategoryType(8);
	Assert.assertNotNull(result);
    }
}
