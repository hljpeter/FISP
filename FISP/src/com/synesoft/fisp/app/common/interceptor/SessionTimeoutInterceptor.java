/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.exception.SessionTimeoutException;

/**
 * @file SessionTimeoutInterceptor.java
 * @author Jon
 * @date 2013-4-26
 * @description Session Time out Interceptor
 * @tag 1.0.0
 * 
 */
public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

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
		String requestUrl = request.getRequestURI();
		for (String url : allowUrls) {
			if (requestUrl.endsWith(url)) {
				return true;
			}
		}
		
		Object session = WebUtils.getSessionAttribute(request, 
				CommonConst.USER_SESSION);
		if (session != null) {
			return true;
		} else {
			throw new SessionTimeoutException(" Session invalid. Please login again...");
//			return false;
		}
		
	}

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

}
