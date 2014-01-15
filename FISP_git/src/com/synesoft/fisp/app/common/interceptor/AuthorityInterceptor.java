
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
import com.synesoft.fisp.app.common.utils.AuthorityUtil;
import com.synesoft.fisp.app.common.utils.RequestUrlUtil;

/**
 * @author zhongHubo
 * @date 2013年12月3日 10:59:55
 * @version 1.0
 * @Description 权限控制拦截器
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

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
		
		// 校验权限
		result = authorityFilter(request, response);
		
		// 记录操作日志
		recordOperLog(result, request);
		
		if (!result) {
			// 当前用户没有此权限
			response.sendRedirect(request.getContextPath() + "/WEB-INF/views/authorityError.jsp");
		}
		
		return result;
	}

	/**
	 * 权限校验
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean authorityFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		boolean result = true;
		
		String requestUrl = request.getRequestURI();
		for (String url : allowUrls) {
			if (requestUrl.endsWith(url)) {
				result = true;
				return result;
			}
		}
		// 获取经过处理后的请求url
		String authority = getRequestUrl(request, requestUrl);
		// 判断权限
		result = AuthorityUtil.judgeUrlAuthority(authority);
			
		return result;
	}

	/**
	 * 获取经过处理后的请求url
	 * @param request
	 * @param requestUrl
	 * @return
	 */
	private String getRequestUrl(HttpServletRequest request, String requestUrl) {
		String authority = requestUrl;
		String projectName = request.getContextPath();
		// 判断url是否以projectName开头，若是：剪掉projectName
		if (authority.startsWith(projectName)) {
			authority = authority.substring(projectName.length());
		}
		// 判断剩下的url是否以/开头，若是：剪掉/
		if (authority.startsWith(CommonConst.SLASH)) {
			authority = authority.substring(CommonConst.SLASH.length());
		}
		return authority;
	}

	/**
	 * 记录操作日志
	 * @param result	当前权限验证是否通过
	 * @param request
	 */
	private void recordOperLog(boolean result, HttpServletRequest request) {
		try {
			String requestUrl = request.getRequestURI();
			for (String url : allowUrls) {
				if (requestUrl.endsWith(url)) {
					return;
				}
			}
			
			// 获取经过处理后的请求url
			String tmp_requestUrl = getRequestUrl(request, requestUrl);
			// 记录操作日志
			RequestUrlUtil.recordOperLog(result, tmp_requestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

}
