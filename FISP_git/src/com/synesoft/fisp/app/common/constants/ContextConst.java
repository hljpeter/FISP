package com.synesoft.fisp.app.common.constants;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.synesoft.fisp.app.common.model.SessionForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.TipsConn;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.UserOrgInf;

/**
 * @author zhongHubo
 * @date 2013年7月9日 13:46:49
 * @version 1.0
 * @Description Context常量类
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class ContextConst {

	/**
	 * 获取当前用户
	 * @return	当前用户
	 */
	public static UserInf getCurrentUser() {
	    UserInf userInf = null;
	    
	    // 获取用户信息
	    Object obj = ContextConst.getAttribute(CommonConst.USER_SESSION);
	    if(obj != null && obj instanceof UserInf) {
	    	userInf = (UserInf) obj;
	    }
	    
	    return userInf;
    }
	
	/**
	 * 获取当前用户的上一次登录的信息
	 * @return	当前用户的上一次登录的信息
	 */
	public static UserInf getUserLastLogin() {
	    UserInf userInf = null;
	    
	    // 获取用户信息
	    Object obj = ContextConst.getAttribute(CommonConst.USER_OLD_SESSION);
	    if(obj != null && obj instanceof UserInf) {
	    	userInf = (UserInf) obj;
	    }
	    
	    return userInf;
    }
	
	/**
	 * 获取用户导航菜单信息
	 * @return
	 */
	public static String getMenuList() {
		String  menulist="";
		Object obj= ContextConst.getAttribute(CommonConst.MENU_SESSION);
	    if(obj != null && obj instanceof String) {
	    	menulist  = (String) obj;
	    }
	    
	    return menulist;
    }
	
	/**
	 * 获取Left菜单信息
	 * @return
	 */
	public static String getLeftMenuList() {
		String  menulist="";
		Object obj= ContextConst.getAttribute(CommonConst.LEFT_MENU_SESSION);
	    if(obj != null && obj instanceof String) {
	    	menulist  = (String) obj;
	    }
	    
	    return menulist;
    }

	/**
	 * 获取Map
	 * @author yyw updated
	 * @return
	 */
	public static String getMenu(String menuId) {
		return "123";
	}
	
	/**
	 * 获取当前用户的机构信息
	 * @return 机构信息
	 */
	public static OrgInf getOrgInfByUser(){
		OrgInf orgInf=null;
		
		// 获取用户机构信息
	    Object obj = ContextConst.getAttribute(CommonConst.ORG_SESSION);
	    if(obj != null && obj instanceof OrgInf) {
	    	orgInf = (OrgInf) obj;
	    }
	    return orgInf;
	}
	
	/**
	 * 获取当前用户的TipsConn信息
	 * @return TipsConn
	 */
	public static TipsConn getTipsConn(){
		TipsConn tipsConn=null;
		
		// 获取当前用户的TipsConn信息
	    Object obj = ContextConst.getAttribute(CommonConst.TIPS_CONN_SESSION);
	    if(obj != null && obj instanceof TipsConn) {
	    	tipsConn = (TipsConn) obj;
	    }
	    return tipsConn;
	}
	
	/**
	 * 获取当前用户的所有可用机构信息
	 * @return List<UserOrgInf>
	 */
	@SuppressWarnings("unchecked")
	public static List<UserOrgInf> getUserOrgList(){
		List<UserOrgInf> list = null;
		
		// 获取用户机构信息
	    Object obj = ContextConst.getAttribute(CommonConst.USERORGLIST_SESSION);
	    if(obj != null && obj instanceof List) {
	    	list = (List<UserOrgInf>) obj;
	    }
	    return list;
	}
	
	/**
	 * 获取当前用户的IP地址
	 * @return
	 */
	public static String getCurrentIP() {
		String ip = "";

		HttpServletRequest request = ContextConst.getRequest();

		ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip != null && "0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		// 如果IP为127.0.0.1，则获取本机IP
		if(ip != null && "127.0.0.1".equals(ip)) {
			ip = ContextConst.getHostIP(ip);
		}
		
		return ip;
    }
	
	/**
	 * 获取本机的IP
	 * @return
	 */
	public static String getHostIP(String ip) {  
        InetAddress addr = null;
        String ip_new = ip;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(addr != null) {
			ip_new=addr.getHostAddress().toString();
		}
        
        return ip_new;
	}  

	
	/**
	 * 获取contextPath
	 * @return
	 */
	public static String getContextPath() {
		HttpServletRequest request = ContextConst.getRequest();
		if(request != null) {
			return getRequest().getContextPath();
		} else {
			return "";
		}
		
	}
	
	/**
	 * 从Session中获取对象
	 * @param name	对象名
	 * @return
	 */
	public static Object getAttribute(String name) {
		Object obj = null;
		
		HttpSession session = ContextConst.getSession();
		if(session != null) {
			obj = session.getAttribute(name);
		}
		
		return obj;
	}
	
	/**
	 * 从Session中移除对象
	 * @param name	对象名
	 * @return
	 */
	public static void removeAttribute(String name) {
		
		HttpSession session = ContextConst.getSession();
		if(session != null) {
			session.setAttribute(name, null);
			session.removeAttribute(name);
		}
		
	}
	
	/**
	 * 从Session中设置对象
	 * @param name	对象名
	 * @param value	对象值
	 * @return
	 */
	public static void setAttribute(String name, Object value) {
		
		HttpSession session = ContextConst.getSession();
		if(session != null) {
			session.setAttribute(name, value);
		}
		
	}
	
	/**
	 * 获取session
	 * @return	session
	 */
	public static HttpSession getSession() {
	    HttpSession session = ContextConst.getRequest().getSession();
	    
	    return session;
    }
	
	/**
	 * 获取request
	 * @return	request
	 */
	public static HttpServletRequest getRequest() {
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    
	    return request;
    }
	
	/**
	 * 获取Locale
	 * @return	String 数组 ：当前语言如：zh_CN ,当前国家，当前语言
	 */
	public static String[] getLocal() {
		Locale local = (Locale) ContextConst.getAttribute(SessionForm.LOCAL);
		String [] str = {local.toString(),local.getCountry(),local.getLanguage()};
	    return str;
    }
}
