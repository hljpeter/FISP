package com.synesoft.ftzmis.app.common.msgproc.ibmMQ;


public class MQConstant {
	// MQ服务器的IP地址
	public static final String hostname = MQPropertiesUtil.getPropertiesValue("hostname");

	// 服务器连接的通道
	public static final String channel = MQPropertiesUtil.getPropertiesValue("channel");

	// MQ的队列管理器名称
	public static final String queueManagerName = MQPropertiesUtil.getPropertiesValue("queueManagerName");

	// 发送队列名称
	public static final String sendQueueName = MQPropertiesUtil.getPropertiesValue("sendQueueName");
	
	// 接收队列名称
	public static final String receiveQueueName = MQPropertiesUtil.getPropertiesValue("receiveQueueName");

	// 服务器MQ服务使用的编码1381代表GBK,1208代表UTF
	public static final int CCSID = Integer.valueOf(MQPropertiesUtil.getPropertiesValue("CCSID"));

	// MQ端口
	public static final int port = Integer.valueOf(MQPropertiesUtil.getPropertiesValue("port"));

	// MQ用户名
	public static final String userID = MQPropertiesUtil.getPropertiesValue("userID");

	// MQ密码
	public static final String password = MQPropertiesUtil.getPropertiesValue("password");
	
	// 队列监测频率(ms)
	public static final int monitorFrequency = Integer.valueOf(MQPropertiesUtil.getPropertiesValue("monitorFrequency"));
	
	// 重发次数
	public static final int resendCount = Integer.valueOf(MQPropertiesUtil.getPropertiesValue("resendCount"));
}
