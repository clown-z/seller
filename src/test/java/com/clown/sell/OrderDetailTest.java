package com.clown.sell;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dao.OrderDetailDao;
import com.clown.sell.domain.OrderDetail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailTest {

    @Autowired
    private OrderDetailDao oDao;
    
    @Test
    public void save() {
	OrderDetail od = new OrderDetail();
	od.setDetailId("d000002");
	od.setOrderId("123456");
	od.setProductId("0000001");
	od.setProductName("商品一");
	od.setProductPrice(new BigDecimal(1.31));
	od.setProductQuantity(3);
	od.setProductIcon("htttp://xxx.jpg");
	od.setCreateTime(new Date());
	od.setUpdateTime(new Date());
	OrderDetail od2 = oDao.save(od);
	System.out.println(od2);
	assertNotNull(od2);
    }
    
    @Test
    public void findByOrderId() {
	List<OrderDetail> list= oDao.findByOrderId("123456");
	for (OrderDetail orderDetail : list) {
	    System.out.println(orderDetail);
	}
	assertNotEquals(0, list.size());
    }

}
