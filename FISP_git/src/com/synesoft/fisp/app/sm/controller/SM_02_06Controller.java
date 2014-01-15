package com.synesoft.fisp.app.sm.controller;

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

import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_06Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_06Controller.class);
	
	@ModelAttribute
	public RoleInfTmpForm setInfoUpForm() {
		return new RoleInfTmpForm();
	}

	@RequestMapping("06/auth")
	public String auth(RoleInfTmpForm form,
			BindingResult result, Model model){
		logger.info("start auth ...");
		setMenuList(form,model);
		if (result.hasErrors()) {
			return "sm/SM_02_06";
		}
		try {
			roleInfService.transAuth(form.getRoleInfTmp());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_02_06";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0004")));
		return "sm/SM_02_06";
	}
	
	@RequestMapping("06/reject")
	public String reject(RoleInfTmpForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		setMenuList(form,model);
		if (result.hasErrors()) {
			return "sm/SM_02_06";
		}
		try {
			roleInfService.transRejct(form.getRoleInfTmp());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_02_06";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0005")));
		return "sm/SM_02_06";
	}
	public void setMenuList(RoleInfTmpForm form,Model model){
		form.setMeunlist(form.getMeunlist());
		model.addAttribute("form", form);
	}
	@Autowired
	private RoleInfService roleInfService;

}
