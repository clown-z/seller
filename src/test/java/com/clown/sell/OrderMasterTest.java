package com.clown.sell;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dao.OrderMasterDao;
import com.clown.sell.domain.OrderMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterTest {

    @Autowired
    private OrderMasterDao mDao;
    
    private final String ONENID = "110110";
    
    @Test
    public void save() {
	OrderMaster orderMaster = new OrderMaster();
	orderMaster.setOrderId("123457");
	orderMaster.setBuyerName("clown");
	orderMaster.setBuyerAddress("buyer address");
	orderMaster.setBuyerPhone("12345678910");
	orderMaster.setBuyerOpenid(ONENID);
	orderMaster.setOrderAmount(new BigDecimal(22.3));
	orderMaster.setCreateTime(new Date());
	orderMaster.setUpdateTime(new Date());
	OrderMaster o2 = mDao.save(orderMaster);
	System.out.println(o2);
	assertNotNull(o2);
    }
    
    @Test
    public void one() {
	OrderMaster om = mDao.getOne("123456");
	System.out.println(om);
	assertNotNull(om);
    }
    
    @Test
    public void findByBuyerOpenid() {
	PageRequest request = new PageRequest(0, 2);
	Page<OrderMaster> result= mDao.findByBuyerOpenid(ONENID, request);
	System.out.println(result.getTotalElements());
	for (OrderMaster orderMaster : result) {
	    System.out.println(orderMaster);
	}
	assertNotEquals(0, result.getTotalElements());
    }
    
}


