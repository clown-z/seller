/**
 * 
 */
package com.clown.sell;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dto.OrderDTO;
import com.clown.sell.service.OrderService;
import com.clown.sell.service.impl.PushMessageImpl;

/**
 * @author Clown
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessageService;
    
    @Autowired
    private OrderService orderService;
    
    /**
     * {@link com.clown.sell.service.impl.PushMessageImpl#orderStatus(com.clown.sell.dto.OrderDTO)} 的测试方法。
     */
    @Test
    public void testOrderStatus() throws Exception {
	OrderDTO orderDTO = orderService.findOne("1575356332276147216");
	pushMessageService.orderStatus(orderDTO);
    }

}
