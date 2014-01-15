package com.synesoft.fisp.app.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.constants.UserConst;
import com.synesoft.fisp.app.common.utils.ContextUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.sm.UserInfService;

public class SyneSessionListener implements HttpSessionBindingListener, HttpSessionListener{

	//session列表
	private static Map<String, SessionUserInfo> sessionMap = new HashMap<String, SessionUserInfo>();

	private static final LogUtil log = new LogUtil(SyneSessionListener.class);

	public class SessionUserInfo {
		
		private String userid; // 用户id
		private String ip; // 用户登录IP
		private HttpSession session;

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public HttpSession getSession() {
			return session;
		}

		public void setSession(HttpSession session) {
			this.session = session;
		}
	}
	
	/**
	 * user login successfully
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		synchronized (SyneSessionListener.class) {
			String userid = ((UserInf)session.getAttribute(CommonConst.USER_SESSION)).getUserid().trim();
			if (sessionMap.containsKey(userid)) {
				sessionMap.remove(userid);
			}

			SessionUserInfo ssui = new SessionUserInfo();
			ssui.setUserid(userid);
			ssui.setIp(ContextConst.getCurrentIP());
			ssui.setSession(session);
			sessionMap.put(userid, ssui);
			
			log.debug("The count of login users is [" + sessionMap.keySet().size() + "]");
		}
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}
	
	/**
	 * the session will be invalidate
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session=event.getSession();
		synchronized (SyneSessionListener.class) {
			sessionDestoryHandle(session);
			
			String userid = ((UserInf)session.getAttribute(CommonConst.USER_SESSION)).getUserid().trim();
			sessionMap.remove(userid);
		}
	}
	
	/**
	 * 根据用户id登出系统
	 * @param userid
	 */
	public static void sessionInvalidate(String userid) {
		if (sessionMap.containsKey(userid)) {
			sessionMap.get(userid).getSession().invalidate();
		}else{
			//修改用户登录状态为"未登录"(考虑web application异常关闭)
			UserInfService userInfService = (UserInfService) ContextUtil.getCtx().getBean("userInfService");
			UserInf userInfo = userInfService.queryUser(userid);
			userInfo.setStatus(UserConst.STATUS_NOLOGIN);
			userInfService.updateUser(userInfo);
		}
	}
	
	/**
	 * 销毁session前处理
	 * @param session
	 */
	public static void sessionDestoryHandle(HttpSession session){
		UserInf userInfo = (UserInf) session.getAttribute(CommonConst.USER_SESSION);
		
		UserInfService userInfService = (UserInfService) ContextUtil.getCtx().getBean("userInfService");

		//修改用户登出信息
		userInfo.setStatus(UserConst.STATUS_NOLOGIN);
		
		String nowDateTime = DateUtil.getNow(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
		userInfo.setLogouttime(nowDateTime);
		userInfo.setLogoutuser(userInfo.getUserid());

		userInfService.updateUser(userInfo);
	}

	public static Map<String, SessionUserInfo> getSessionMap() {
		return sessionMap;
	}

}
