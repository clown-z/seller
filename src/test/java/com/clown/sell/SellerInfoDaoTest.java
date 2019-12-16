package com.clown.sell;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clown.sell.dao.SellerInfoDao;
import com.clown.sell.domain.SellerInfo;
import com.clown.sell.util.KeyUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {
    
    @Autowired
    private SellerInfoDao sellerInfoDao;
    
    
    @Test
    public void save() {
	SellerInfo sellerinfo = new SellerInfo();
	sellerinfo.setId(KeyUtil.genUniqueKey());
	sellerinfo.setUsername("admin");
	sellerinfo.setPassword("123");
	sellerinfo.setOpenid("abc");
	sellerinfo.setCreateTime(new Date());
	sellerinfo.setUpdateTime(new Date());
	SellerInfo sl = sellerInfoDao.save(sellerinfo);	
	Assert.assertNotNull(sl);
    }
    
    
    @Test
    public void findByOpenid() throws Exception{
	SellerInfo sl = sellerInfoDao.findByOpenid("abc");
	System.out.println("sl: " + sl);
	Assert.assertEquals("abc", sl.getOpenid());
    }
}
