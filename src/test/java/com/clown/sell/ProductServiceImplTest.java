package com.clown.sell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.domain.ProductInfo;
import com.clown.sell.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    
    @Autowired
    private ProductServiceImpl psi;

    @Test
    public void save() throws Exception{
	ProductInfo pi = new ProductInfo();
	pi.setProductId("000004");
	pi.setProductName("商品四");
	pi.setProductPrice(new BigDecimal(44.44));
	pi.setProductStock(15);
	pi.setProductDescription("商品四描述");
	pi.setProductIcon("商品四图");
	pi.setProductStatus(0);
	pi.setCategoryType(3);
	ProductInfo pi2 = psi.save(pi);
	System.out.println(pi2);
    }
    
    @Test
    public void findAll() throws Exception{
	@SuppressWarnings("deprecation")
	PageRequest request = new PageRequest(0, 5);
	Page<ProductInfo> pg = psi.findAll(request);
	//System.out.println(pg.getTotalElements());
	for (ProductInfo productInfo : pg) {
	    System.out.println(productInfo);
	}
    }
    
    @Test
    public void findUpAll() throws Exception{
	List<ProductInfo> list = psi.findUpAll();
	assertNotEquals(0, list.size());
    }
    @Test
    public void findOne() throws Exception{
	ProductInfo pi = psi.findOne("000002");
	assertEquals("000002", pi.getProductId());
	System.out.println(pi);
    }
    
    @Test
    public void decreaseStock() throws Exception{
	ProductInfo pi = psi.findOne("000002");
	assertEquals("000002", pi.getProductId());
	pi.setProductStock(22);;
	psi.save(pi);
	System.out.println(pi);
    }
    
    
}
