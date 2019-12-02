package com.clown.sell;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dao.ProductInfoDao;
import com.clown.sell.domain.ProductInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoTest {

    @Resource
    private ProductInfoDao piDao;
    
    @Test
    public void save() {
	ProductInfo pi = new ProductInfo();
	pi.setProductId("000002");
	pi.setProductName("商品二");
	pi.setProductPrice(new BigDecimal(3.2));
	pi.setProductStock(2);
	pi.setProductDescription("商品二描述");
	pi.setProductIcon("商品二图");
	pi.setProductStatus(0);
	pi.setCategoryType(1);
	ProductInfo pi2 = piDao.save(pi);
	System.out.println(pi2);
    }
    
    @Test
    public void findOne() {
	ProductInfo pi = piDao.getOne("000002");
	assertNotNull(pi.getProductId());
    }
    
    @Test
    public void findByProductStatus() {
	List<ProductInfo> result = piDao.findByProductStatus(0);
	for (ProductInfo productInfo : result) {
	    System.out.println(productInfo);
	}
	assertNotEquals(0, result.size());
    }
}
