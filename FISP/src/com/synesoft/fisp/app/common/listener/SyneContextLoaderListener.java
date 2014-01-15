package com.synesoft.fisp.app.common.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.synesoft.jsynframe.batch.batmonitor.BatchMonitor;
import cn.com.synesoft.jsynframe.util.ExceptionBase;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.context.MemoryContextHolder;
import com.synesoft.fisp.app.common.context.MemoryContextHolder.MemoryResourceType;
import com.synesoft.fisp.app.common.utils.ContextUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.SysParam;
import com.synesoft.fisp.domain.model.TipsAcctInf;
import com.synesoft.fisp.domain.model.TipsBaCInf;

/**
 * Web App启动事件
 * 
 */
public class SyneContextLoaderListener extends ContextLoaderListener {
	
	private static final LogUtil log = new LogUtil(SyneContextLoaderListener.class); 
	
	private BatchMonitor monitor;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);

//		shutdownBatMonitor();// 关闭批量框架
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		ContextUtil.setSctx(context);
		ContextUtil.setCtx(ctx);

		initMemoryResourceAll(event.getServletContext());// 初始化内存数据

//		startBatMonitor();// 启动批量框架
	}
	
	/**
	 * app启动初始化内存数据
	 * @param sc
	 */
	private void initMemoryResourceAll(ServletContext sc){
		WebApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(sc);
		
		QueryDAO queryDAO = ctx.getBean(QueryDAO.class);
		
		List<TipsBaCInf> listTipsBaCInf = queryDAO.executeForObjectList(Table.TIPS_BACINF + "." + SQLMap.SELECT_LIST, new TipsBaCInf());
		MemoryContextHolder.initMemoryResource(MemoryResourceType.TipsBaCInf, listTipsBaCInf);
		
		
		List<TipsAcctInf> listTipsAcctInf = queryDAO.executeForObjectList(Table.TIPS_ACCTINF + "." + SQLMap.SELECT_LIST, new TipsAcctInf());
		MemoryContextHolder.initMemoryResource(MemoryResourceType.TipsAcctInf, listTipsAcctInf);
		
		
		List<OrgInf> listOrgInf = queryDAO.executeForObjectList(Table.ORGINF + "." + SQLMap.SELECT_LIST, new OrgInf());
		MemoryContextHolder.initOrgInf(listOrgInf);

		// 初始化系统参数信息
		List<SysParam> listSysParam = queryDAO.executeForObjectList(Table.SYS_PARAM + "." + SQLMap.SELECT_LIST, new SysParam());
		MemoryContextHolder.initMemoryResource(MemoryResourceType.SYS_PARAM, listSysParam);

	}
	
	/**
	 * 启动批量框架
	 */
	private void startBatMonitor() {
		try {
			log.info("开始启动批量框架!");
			monitor = new BatchMonitor();
			monitor.init();
			monitor.run();
			log.info("启动批量框架成功!");
		} catch (ExceptionBase e) {
			log.error("启动批量框架失败:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void shutdownBatMonitor(){
		if (monitor!=null){
			try {
				log.info("开始关闭批量框架!");
				monitor.shutdown();
				log.info("关闭批量框架成功!");
			} catch (ExceptionBase e) {
				log.error("关闭批量框架失败:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}
