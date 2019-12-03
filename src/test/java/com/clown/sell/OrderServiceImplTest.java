package com.clown.sell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.domain.OrderDetail;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.enums.OrderStatusEnum;
import com.clown.sell.enums.PayStatusEnum;
import com.clown.sell.service.impl.OrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    
    private final String BUYER_OPENID = "110110";
    private final String ORDER_ID = "1575275691361104984";
    
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
	OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	System.out.println("[查询单个订单] orderDTO = " + orderDTO);
	assertEquals(ORDER_ID, orderDTO.getOrderId());
    }

    @Test
    public void testFindList() {
	PageRequest request = new PageRequest(0, 5);
	Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
	for (OrderDTO orderDTO : orderDTOPage) {
	    System.out.println(orderDTO);
	}
	Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void testCancle() {
	OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	OrderDTO result = orderService.cancle(orderDTO);
	Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void testFinish() {
	OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	OrderDTO result = orderService.finish(orderDTO);
	Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void testPaid() {
	OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	OrderDTO result = orderService.paid(orderDTO);
	Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

}
