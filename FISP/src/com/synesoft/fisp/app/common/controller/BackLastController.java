/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @file 	BackLastController.java
 * @author 	Jon
 * @date 	2013-5-9
 * @description TODO
 * @tag 	1.0.0
 * 
 */
@Controller
@RequestMapping(value = "cm")
public class BackLastController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("backLastUrl")
	public String backLastUrl(HttpServletRequest request,
			@RequestParam("refererUrl") String refererUrl){
		String lastUrl = "";
		String requestUri = request.getContextPath();
		lastUrl = refererUrl.replaceAll(requestUri, "");
		logger.debug("lastUrl:--------------"+lastUrl);
		return "redirect:" + lastUrl;
	}
	
}
