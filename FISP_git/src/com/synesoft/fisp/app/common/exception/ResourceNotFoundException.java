
package com.synesoft.fisp.app.common.exception;

/**
 * @author zhongHubo
 * @date 2013年7月19日 16:35:53
 * @version 1.0
 * @Description 请求未找到异常
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	
	public ResourceNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ResourceNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return null;
	}

}
