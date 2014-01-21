package com.synesoft.ftzmis.app.common.msgproc.ibmMQ;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import oracle.net.aso.p;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.synesoft.dataproc.common.util.AppCtxUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;

public class MQMsgProc {
	
	private static LogUtil log = new LogUtil(MQMsgProc.class);
	
	// 定义队列管理器和队列的名称
	private static String queueManagerName;
	private static String sendQueueName;
	private static String receiveQueueName;
	private static MQQueueManager qMgr;
	static {
		/* MQEnvironment中包含控制MQQueueManager对象中的环境的构成的静态变量，
		 * MQEnvironment的值的设定会在MQQueueManager的构造函数加载的时候起作用，
		 * 因此必须在建立MQQueueManager对象之前设定MQEnvironment中的值.
		 */
		MQEnvironment.hostname = MQConstant.hostname;
		MQEnvironment.channel = MQConstant.channel;
		MQEnvironment.CCSID = MQConstant.CCSID;
		MQEnvironment.port = MQConstant.port;
		MQEnvironment.userID = MQConstant.userID;
		MQEnvironment.password = MQConstant.password;
		
		queueManagerName = MQConstant.queueManagerName;
		sendQueueName = MQConstant.sendQueueName;
		receiveQueueName = MQConstant.receiveQueueName;
		try {
			/* 定义并初始化队列管理器对象并连接
			 MQQueueManager可以被多线程共享，但是从MQ获取信息的时候是同步的，
			 任何时候只有一个线程可以和MQ通信。
			 */
			qMgr = new MQQueueManager(queueManagerName);
			
		} catch (MQException e) {
			log.error("初使化MQ出错,原因:"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 往MQ发送消息
	 * 
	 * @param message
	 * @return
	 */
	public static boolean sendMessage(String message, String errInfo) {
		boolean result = true;
		try {
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			// 连接队列
			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = new MQQueueManager(queueManagerName);
			}
			MQQueue mqQueue = qMgr.accessQueue(sendQueueName, openOptions);
			// 定义一个简单的消息
			MQMessage mqMessage = new MQMessage();
			
			// 将数据放入消息缓冲区
			mqMessage.encoding = MQConstant.CCSID;
			mqMessage.characterSet = MQConstant.CCSID;
			mqMessage.format = MQC.MQFMT_STRING;
			mqMessage.writeString(message);
			// 设置写入消息的属性（默认属性）
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			// 将消息写入队列
			mqQueue.put(mqMessage, pmo);
			mqQueue.close();
		} catch (MQException ex) {
			result = false;
			errInfo = "A WebSphere MQ error occurred : Completion code " + ex.completionCode + " Reason code " + ex.reasonCode;
			log.error("A WebSphere MQ error occurred : Completion code " + ex.completionCode + " Reason code " + ex.reasonCode);
		} catch (IOException ex) {
			result = false;
			errInfo = "An error occurred whilst writing to the message buffer: " + ex.getMessage();
			log.error("An error occurred whilst writing to the message buffer: " + ex.getMessage());
		} catch (Exception ex) {
			result = false;
			errInfo = "发送报文信息出错,原因:" + ex.getMessage();
			log.error("发送报文信息出错,原因:" + ex.getMessage());
		} finally {
			try {
				qMgr.disconnect();
			} catch (MQException e) {
				result = false;
				errInfo = "关闭MQ管理器出错,原因:" + e.getMessage();
				log.error("关闭MQ管理器出错,原因:" + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 从队列中去获取消息，如果队列中没有消息，就会发生异常，不过没有关系，有TRY...CATCH，如果是第三方程序调用方法，如果无返回则说明无消息
	 * 第三方可以将该方法放于一个无限循环的while(true){...}之中，不需要设置等待，因为在该方法内部在没有消息的时候会自动等待。
	 * @return
	 */
	public static String getMessage() {
		String message = null;
		try {
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;

			MQGetMessageOptions gmo = new MQGetMessageOptions();
			// 在同步点控制下获取消息
			gmo.options = gmo.options + MQC.MQGMO_SYNCPOINT;
			// 如果在队列上没有消息则等待
			gmo.options = gmo.options + MQC.MQGMO_WAIT;
			// 如果队列管理器停顿则失败
			gmo.options = gmo.options + MQC.MQGMO_FAIL_IF_QUIESCING;
			// 设置等待的毫秒时间限制
			gmo.waitInterval = 1000;
			// 连接队列
			if (qMgr == null || !qMgr.isConnected()) {
				qMgr = new MQQueueManager(queueManagerName);
			}

			MQQueue mqQueue = qMgr.accessQueue(receiveQueueName, openOptions);
			MQMessage mqMessage = new MQMessage();
			
			try {
				// 从队列中取出消息
				mqQueue.get(mqMessage, gmo);
				mqMessage.encoding = MQConstant.CCSID;
				mqMessage.characterSet = MQConstant.CCSID;
				mqMessage.format = MQC.MQFMT_STRING;
				message = mqMessage.readStringOfByteLength(mqMessage.getMessageLength()); 
				log.info("收到MQ报文:"+message);
			} catch (MQException ex) {
				log.debug("MQ接收队列信息为空!" + ex.completionCode + " Reason code " + ex.reasonCode);
			}
			
			mqQueue.close();
		} catch (MQException ex) {
			log.error("A WebSphere MQ error occurred : Completion code " + ex.completionCode + " Reason code " + ex.reasonCode);
		} catch (IOException ex) {
			log.error("An error occurred whilst writing to the message buffer: " + ex.getMessage());
		} catch (Exception ex) {
			log.error("接收信息出错,原因:" + ex.getMessage());
		} 
		finally {
			try {
				qMgr.disconnect();
			} catch (MQException e) {
				log.error("关闭MQ管理器出错,原因:" + e.getMessage());
			}
		}
		return message;
	}
	
	/**
	 * 从文件读取报文信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer readToBuffer(String filePath) throws IOException{
		StringBuffer sb = new StringBuffer();
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            sb.append(line); // 将读到的内容添加到 buffer 中
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
        
        return sb; 
    }

	public static void main(String args[]) {
		System.out.println("begin");
//		try {
//			sendMessage(readToBuffer("D:\\FTZ_MSG\\FTZ_210101_0000000000021519.xml").toString(), "");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println(readToBuffer("D:\\FTZ_MSG\\FTZ_210101_0000000000021519.xml").toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.out.println(getMessage());
		System.out.println("end");
	}
}