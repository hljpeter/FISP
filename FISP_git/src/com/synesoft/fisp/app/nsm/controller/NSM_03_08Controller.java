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

import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.app.sm.model.UserInfForm.SM_03_08_Check;
import com.synesoft.fisp.domain.service.sm.UserInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "nsm03")
public class NSM_03_08Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(NSM_03_08Controller.class);

	@ModelAttribute
	public UserInfForm setUpForm() {
		return new UserInfForm();
	}
	@RequestMapping("08/init")
	public String init() {
		logger.info("init...");
		return "sm1/SM_03_08";
	}

	@RequestMapping("08/unlock")
	public String unlock(@Validated({SM_03_08_Check.class}) UserInfForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		//获取零时表中角色功能
		if (result.hasErrors()) {
			return "sm1/SM_03_08";
		}
		try {
			userInfService.transSetStatusNormal(form, "02");
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_03_08";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0006")));
		return "sm1/SM_03_08";
	}
	@RequestMapping("08/unfreeze")
	public String unfreeze(@Validated({SM_03_08_Check.class}) UserInfForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		//获取零时表中角色功能
		if (result.hasErrors()) {
			return "sm1/SM_03_08";
		}
		try {
			userInfService.transSetStatusNormal(form, "03");
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_03_08";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0007")));
		return "sm1/SM_03_08";
	}
	
	@RequestMapping("08/passwordReset")
	public String passwordReset(@Validated({SM_03_08_Check.class}) UserInfForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		String pwd="";
		//获取零时表中角色功能
		if (result.hasErrors()) {
			return "sm1/SM_03_08";
		}
		try {
			pwd=userInfService.transPasswordReset(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm1/SM_03_08";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0007")));
		form.setNewPwd(pwd);
		model.addAttribute("form",form);
		return "sm1/SM_03_08";
	}

	@Autowired
	private UserInfService userInfService;

}
