package com.synesoft.fisp.app.nsm.controller;

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

import com.synesoft.fisp.app.sm.model.OrgInfForm;
import com.synesoft.fisp.app.sm.model.OrgInfForm.SM_01_03_Modify;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "nsm01")
public class NSM_01_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(NSM_01_03Controller.class);

	@ModelAttribute
	public OrgInfForm setInfoUpForm() {
		return new OrgInfForm();
	}
	
	@RequestMapping("03/search")
	public String search(OrgInfForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "sm1/SM_01_03";
		}
		OrgInf orgInf=form.getOrgInf();
		orgInf=orgInfMaintenanceService.transQueryOrgInf(orgInf);
		form.setSuprorgid(orgInf.getSuprorgid());
		form.setBankid(orgInf.getBankid());
		form.setOrgname(orgInf.getOrgname());
		form.setOrgInf(orgInf);
		return "sm1/SM_01_03";
	}

	@RequestMapping("03/modify")
	public String modify(@Validated({SM_01_03_Modify.class}) OrgInfForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		if (result.hasErrors()) {
			return "sm1/SM_01_03";
		}
		try {
			orgInfMaintenanceService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_01_03";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0002")));
		return "sm1/SM_01_03";
	}

	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
