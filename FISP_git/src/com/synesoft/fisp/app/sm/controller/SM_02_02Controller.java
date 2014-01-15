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

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm.SM_02_02_Add;
import com.synesoft.fisp.domain.model.RoleInfTmp;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_02Controller.class);
	@ModelAttribute
	public RoleInfTmpForm setInfoUpForm() {
		return new RoleInfTmpForm();
	}
	@RequestMapping("02/init")
	public String init(RoleInfTmpForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		RoleInfTmp roleInfTmp = new RoleInfTmp();
		roleInfTmp.setCreateorg(ContextConst.getCurrentUser().getLoginorg());
		form.setRoleInfTmp(roleInfTmp);
		form.setRoleid("");
		form.setRolename("");
		form.setMeunlist("");
		form.setEditable("true");
		model.addAttribute("form", form);
		return "sm/SM_02_02";
	}

	@RequestMapping("02/add")
	public String add(@Validated({SM_02_02_Add.class}) RoleInfTmpForm form,
			BindingResult result, Model model) {
		logger.info("start add ...");
		//获取零时表中角色功能
		form.getRoleInfTmp().setCreateorg(ContextConst.getCurrentUser().getLoginorg());
		form.setMeunlist(form.getMeunlist());
		model.addAttribute("form", form);
		if (result.hasErrors()) {
			return "sm/SM_02_02";
		}
		try {
			roleInfService.transAdd(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_02_02";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		form.setEditable("false");
		model.addAttribute("form", form);
		return "sm/SM_02_02";
	}

	@Autowired
	private RoleInfService roleInfService;

}
