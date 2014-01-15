package com.synesoft.fisp.app.sm.controller;

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

import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.app.sm.model.OrgInfTmpForm.SM_01_02_Add;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm01")
public class SM_01_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_01_02Controller.class);
	@ModelAttribute
	public OrgInfTmpForm setInfoUpForm() {
		return new OrgInfTmpForm();
	}
	@RequestMapping("02/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_01_02";
	}

	@RequestMapping("02/add")
	public String add(@Validated({SM_01_02_Add.class}) OrgInfTmpForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		if (result.hasErrors()) {
			return "sm/SM_01_02";
		}
		try {
			orgInfMaintenanceService.transAdd(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_01_02";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		return "sm/SM_01_02";
	}

	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
