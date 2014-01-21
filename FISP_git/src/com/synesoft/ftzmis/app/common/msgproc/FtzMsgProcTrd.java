package com.synesoft.ftzmis.app.common.msgproc;

import com.synesoft.dataproc.common.util.AppCtxUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.ftzmis.app.common.msgproc.ibmMQ.MQConstant;
import com.synesoft.ftzmis.app.common.msgproc.ibmMQ.MQMsgProc;

/**
 * 信息队列处理线程
 */
public class FtzMsgProcTrd implements Runnable {

	LogUtil log = new LogUtil(FtzMsgProcTrd.class);

	@Override
	public void run() {
		FtzMsgProcService ftzMsgProcService = (FtzMsgProcService) AppCtxUtil.getCtx().getBean("ftzMsgProcService");
		
		while (true) {
			// 处理待处理报文队列
			try {
				ftzMsgProcService.pendingMsgProc();
			} catch (Exception e) {
				log.error("处理报文信息异常:" + e.getMessage());
			}
			// 处理待处理报文队列
			try {
				String msg = MQMsgProc.getMessage();
				if (msg!=null && !"".equals(msg.trim())){
					ftzMsgProcService.RcvMsgFromMqProc(msg);
				}
			} catch (Exception e) {
				log.error("接收报文信息异常:" + e.getMessage());
			}

			try {
				Thread.sleep(MQConstant.monitorFrequency);
			} catch (InterruptedException e) {
				log.error("处理信息队列睡眠异常:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
