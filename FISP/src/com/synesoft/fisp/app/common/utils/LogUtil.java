package com.synesoft.fisp.app.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhongHubo
 * @date 2013年10月28日 16:51:22
 * @version 1.0
 * @Description 日志打印公共类
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class LogUtil {

	private Log log = null;
	
	public LogUtil(Class<?> clazz){
		log = LogFactory.getLog(clazz);
	}
	
	public void debug(Object arg0){
		if(log.isDebugEnabled()){
			log.debug(arg0);
		}
	}
	
	public void debug(Object arg0,Throwable arg1){
		if(log.isDebugEnabled()){
			log.debug(arg0,arg1);
		}
	}
	
	public void info(Object arg0){
		if(log.isInfoEnabled()){
			log.info(arg0);
		}
	}
	
	public void info(Object arg0,Throwable arg1){
		if(log.isInfoEnabled()){
			log.info(arg0,arg1);
		}
	}
	
	public void warn(Object arg0){
		if(log.isWarnEnabled()){
			log.warn(arg0);
		}
	}
	
	public void warn(Object arg0,Throwable arg1){
		if(log.isWarnEnabled()){
			log.warn(arg0,arg1);
		}
	}
	
	public void error(Object arg0){
		if(log.isErrorEnabled()){
			log.error(arg0);
		}
	}
	
	public void error(Object arg0,Throwable arg1){
		if(log.isErrorEnabled()){
			log.error(arg0,arg1);
		}
	}
	
	public void fatal(Object arg0){
		if(log.isFatalEnabled()){
			log.fatal(arg0);
		}
	}
	
	public void fatal(Object arg0,Throwable arg1){
		if(log.isFatalEnabled()){
			log.fatal(arg0,arg1);
		}
	}
	
}
