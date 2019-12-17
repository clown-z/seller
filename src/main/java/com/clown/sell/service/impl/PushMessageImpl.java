package com.clown.sell.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clown.sell.config.WechatAccountConfig;
import com.clown.sell.dto.OrderDTO;
import com.clown.sell.service.PushMessage;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;
    
    @Autowired
    private WechatAccountConfig accountConfig;
    
    public void orderStatus(OrderDTO orderDTO) {
	WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
	templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
	templateMessage.setToUser("oFJZSwP2jK-aI1yocLv7E64ayn3k");//orderDTO.getBuyerOpenid()
	List<WxMpTemplateData> data = Arrays.asList(
		new WxMpTemplateData("first", "亲，请记得收货"),
		new WxMpTemplateData("keyword1", "微信点餐"),
		new WxMpTemplateData("keyword2", "188688123456"),
		new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
		new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()),
		new WxMpTemplateData("keyword5", "￥" + orderDTO.getOrderAmount()),
		new WxMpTemplateData("remark", "欢迎再次光临!")
		);
	templateMessage.setData(data);
	try {
	    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
	} catch (WxErrorException e) {
	    log.error("【微信模板消息】 发送失败， {}", e);
	}
    }

}
