package com.synesoft.fisp.app.bm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.bm.model.Bm_Data_AddForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_AddForm.Bm_Dict_Add;
import com.synesoft.fisp.domain.service.bm.SysDataDictService;


/**
 * 
 * @author ljch
 * 
 */

@Controller
@RequestMapping("BM_Data_Add")
public class BM_Data_AddController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Data_AddController.class);
	@ModelAttribute
	public Bm_Data_AddForm setForm() {
		return new Bm_Data_AddForm();
	}
	@RequestMapping("Init")
	public String init() {
		logger.info("init...");
		return "bm/BM_Data_Add";
	}

	@RequestMapping("Add")
	public String add(@Validated({Bm_Dict_Add.class}) Bm_Data_AddForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		if (result.hasErrors()) {
			System.out.println("testtsetstetse");
			return "bm/BM_Data_Add";
		}
		try {
			sysDataDictService.insertSysDataDict(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bm/BM_Data_Add";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		return "bm/BM_Data_Add";
	}

	@Autowired
	private SysDataDictService sysDataDictService;

}
