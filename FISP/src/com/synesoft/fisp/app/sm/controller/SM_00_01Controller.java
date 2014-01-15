package com.synesoft.fisp.app.sm.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.sm.model.MenuForm;
import com.synesoft.fisp.domain.service.sm.MenuFuncService;

/**
 * @author zhongHubo
 * @date 2013骞�鏈�2鏃�14:02:49
 * @version 1.0
 * @Description 鑿滃崟Controller
 * @System TIPS
 * @Company 涓婃捣鎭╂姊暟鎹檵鎭掕蒋浠舵湁闄愬叕鍙�
 */
@Controller
@RequestMapping(value = "sm00")
public class SM_00_01Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(SM_00_01Controller.class);

	@ModelAttribute
	public MenuForm setMenuForm() {
		return new MenuForm();
	}
	
	/**
	 * 涓嶄娇鐢ㄦā鏉挎柟寮忚烦杞�
	 * @param request
	 * @param response
	 */
	@RequestMapping("01/init")
	public void init(MenuForm menuForm, Model model, HttpServletRequest request,HttpServletResponse response) {
		try {
			// 鑾峰彇宸茶閫変腑鐨勬墍鏈夎彍鍗旾d
			request.setAttribute("menuId", menuForm.getMenuId());
			request.getRequestDispatcher("/WEB-INF/views/sm/SM_00_01.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 浣跨敤妯℃澘鏂瑰紡璺宠浆
	 */
//	@RequestMapping("01/init")
//	public String init(MenuForm menuForm, Model model) {
//		return "sm/SM_00_01";
//	}

	/**
	 * 杩斿洖Json绫诲瀷鐨勮彍鍗�
	 * @return
	 */
	@RequestMapping("01/search")
	public void search(MenuForm menuForm, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("start search ...");
		
		// 鑾峰彇褰撳墠琚�涓殑鑿滃崟Id
		String menuId = menuForm.getMenuId();
		String menuStr = menuFuncService.queryList(menuId);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(menuStr);
			response.getWriter().close();
		} catch (IOException e) {
		}
		logger.info("end search ...");
	}
	
	@Autowired
	private MenuFuncService menuFuncService;

}
