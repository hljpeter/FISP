package com.synesoft.fisp.app.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.synesoft.fisp.app.common.utils.CommonUtil;

/**
 * @author zhongHubo
 * @date 2013年11月26日 16:08:40
 * @version 1.0
 * @Description Ajax对字符串加密
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("encryp")
public class EncrytorController {

	@RequestMapping("getEncrytor")
	public @ResponseBody void getEncrytor(@RequestParam("content") String content, Model model, HttpServletResponse response) {
		String encrytor = CommonUtil.encrytor(content);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(encrytor);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
