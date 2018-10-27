package com.ssm.weixin.service;

import java.io.InputStream;


/**
 * 微信公众账号业务处理接口-针对微信过来的信息处理
 * 
 * @author xiepeixiong
 *
 */
public interface WeixinPublicService {
	
	/**
	 * 接受和回复微信消息
	 */
	public String saveReceiveAndReplyMsg(InputStream in) throws Exception;
	
}
