package com.synesoft.fisp.app.sm.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.sm.model.Sm_Date_QryForm;
import com.synesoft.fisp.domain.service.sm.HolidayMaintainService;

/**
 * @author 李敬昌
 * @date 2013年11月12日 10:51:22
 * @version 1.0
 * @Description 节假日信息查询
 * @System FISP
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */

@Controller
@RequestMapping("SM_Date_Qry")
public class SM_Date_QryController {
	
	private static final Logger logger = LoggerFactory.getLogger(SM_Date_QryController.class);

	@ModelAttribute
	public Sm_Date_QryForm setDateForm() {
		return new Sm_Date_QryForm();
	}
	
	/**
	 * 初始化节假日界面
	 * @param request
	 * @param response
	 */
	@RequestMapping("Init")
	public String init() {
		logger.info("init...");
		return "sm/SM_Date_Qry";
	}
	
	

	/**
	 * 查询节假日
	 * @return
	 */
	@RequestMapping("Qry")
	public String search(Sm_Date_QryForm smDateForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
	
		Page<Sm_Date_QryForm> page= holidayService.transQueryHolidayList(pageable, smDateForm);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		System.out.println(smDateForm.getFloorYear()+"----------"+smDateForm.getUpperYear());
		System.out.println("记录条数"+page.getContent().size());
		return "sm/SM_Date_Qry";
	}
	
	@Autowired
	private HolidayMaintainService holidayService;

}
