package com.synesoft.fisp.app.common.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("contextUtil")
public class ContextUtil implements ApplicationContextAware {

	private static ApplicationContext  ctx;
	
	private static ServletContext sctx;

	public static ServletContext getSctx() {
		return sctx;
	}

	public static void setSctx(ServletContext sctx) {
		ContextUtil.sctx = sctx;
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

	public static void setCtx(ApplicationContext ctx) {
		ContextUtil.ctx = ctx;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ContextUtil.setCtx(arg0);
	}
	
}
