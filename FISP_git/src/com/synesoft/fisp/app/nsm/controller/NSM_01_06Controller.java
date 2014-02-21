package com.synesoft.fisp.app.nsm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "nsm01")
public class NSM_01_06Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(NSM_01_06Controller.class);
	
	@ModelAttribute
	public OrgInfTmpForm setInfoUpForm() {
		return new OrgInfTmpForm();
	}

	@RequestMapping("06/auth")
	public String auth(OrgInfTmpForm form,
			BindingResult result, Model model){
		logger.info("start auth ...");
		if (result.hasErrors()) {
			return "sm1/SM_01_06";
		}
		try {
			orgInfMaintenanceService.transAuth(form.getOrgInfTmp());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_01_06";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0004")));
		return "sm1/SM_01_06";
	}
	
	@RequestMapping("06/reject")
	public String reject(OrgInfTmpForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		if (result.hasErrors()) {
			return "sm1/SM_01_06";
		}
		try {
			orgInfMaintenanceService.transRejct(form.getOrgInfTmp());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_01_06";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0005")));
		return "sm1/SM_01_06";
	}
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
