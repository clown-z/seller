package com.clown.sell;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.domain.OrderDetail;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.service.impl.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    
    private final String BUYER_OPENID = "110110";
    
    @Test
    public void testCreate() {
	OrderDTO orderDTO = new OrderDTO();
	orderDTO.setBuyerName("roc");
	orderDTO.setBuyerAddress("my Address");
	orderDTO.setBuyerPhone("12345678910");
	orderDTO.setBuyerOpenid(BUYER_OPENID);
	
	//购物车
	List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
	
	OrderDetail o1 = new OrderDetail();
	o1.setProductId("000002");
	o1.setProductQuantity(1);
	orderDetailsList.add(o1);
	
	OrderDetail o2 = new OrderDetail();
	o2.setProductId("000004");
	o2.setProductQuantity(2);
	orderDetailsList.add(o2);
	
	orderDTO.setOrderDetailsList(orderDetailsList);
	OrderDTO result = orderService.create(orderDTO);
	
	log.info("[创建订单] result={}", result);
    }

    @Test
    public void testFindOne() {
	fail("尚未实现");
    }

    @Test
    public void testFindList() {
	fail("尚未实现");
    }

    @Test
    public void testCancle() {
	fail("尚未实现");
    }

    @Test
    public void testFinish() {
	fail("尚未实现");
    }

    @Test
    public void testPaid() {
	fail("尚未实现");
    }

}
