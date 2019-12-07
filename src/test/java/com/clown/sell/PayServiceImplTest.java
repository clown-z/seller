package com.clown.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dto.OrderDTO;
import com.clown.sell.service.OrderService;
import com.clown.sell.service.PayService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void create() throws Exception {
	OrderDTO orderDTO = orderService.findOne("1575275691361104984");
	payService.create(orderDTO);
    }
}
