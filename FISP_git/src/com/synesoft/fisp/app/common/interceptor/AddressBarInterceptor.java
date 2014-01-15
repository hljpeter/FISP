
package com.synesoft.fisp.app.common.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;

/**
 * @author zhongHubo
 * @date 2013年7月19日 16:27:19
 * @version 1.0
 * @Description 地址栏拦截器(防盗链)
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class AddressBarInterceptor extends HandlerInterceptorAdapter {

	private List<String> allowUrls = new ArrayList<String>();

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		boolean result = false;
		
		// 判断是否需要防盗链
		if(CommonUtil.filterChain != null) {
			
			if("true".equals(CommonUtil.filterChain)) {
				result = adresBarFilterChain(request, response);
			} else if("false".equals(CommonUtil.filterChain)) {
				// 不需要过滤，让当前请求正常访问
				result = true;
			}
			
		} else {
			// 不需要过滤，让当前请求正常访问
			result = true;
		}
		
		if (!result) {
			//引用页为空时，且密钥校验失败，说明是直接从地址栏上输入或非法链接
			response.sendRedirect(request.getContextPath() + "/WEB-INF/views/notFoundError.jsp");
		}
		
		return result;
	}

	/**
	 * 防盗链处理
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean adresBarFilterChain(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		boolean result = false;
		
		String requestUrl = request.getRequestURI();
		for (String url : allowUrls) {
			if (requestUrl.endsWith(url)) {
				result = true;
				return result;
			}
		}
		
		//防止浏览器地址栏上输入
		String referer = request.getHeader("referer");
		
		if (referer==null) {
			
			String cipherContent = request.getParameter("encrypContent");
			if (StringUtil.isNotEmpty(cipherContent)) {
			
				// 解密
				String deEncrypContent = CommonUtil.decryptor(cipherContent);
				
				if(StringUtil.isNotTrimEmpty(deEncrypContent)) {
					// 为啥链接里需要使用密文，原因是：IE不支持弹出窗口和模式窗口的referer
					// 所以合法的弹出窗口和模式窗口会带密文请求
					
					String[] params = deEncrypContent.split(CommonConst.comb_unseperator);
					if(params != null && params.length >= 2) {
						String param_time = params[0];
						String param_user = params[1];
						
						String[] map_time = param_time.split(CommonConst.equal_seperator);
						String[] map_user = param_user.split(CommonConst.equal_seperator);
						
						if(map_time != null && map_user != null && map_time.length >= 2 && map_user.length >= 2) {
							
							if("encrypTime".equals(map_time[0]) && "encrypUser".equals(map_user[0])) {
								// 获取当前用户
								UserInf userInfo = ContextConst.getCurrentUser();
								String userId = map_user[1];
								if(StringUtil.isNotTrimEmpty(userId) && userId.equals(userInfo.getUserid())) {
									
									result = true;
								}
							}
						}
					}
				}
			}
			
		} else {
			result = true;
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("abcdefg");
		list.add("1234567");
		if(list.contains("1234567")) {
			System.out.println(list.indexOf("1234567"));
		}
	}

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

}
