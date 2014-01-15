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

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.RoleInfForm;
import com.synesoft.fisp.app.sm.model.RoleInfForm.SM_02_03_Modify;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.service.sm.MenuFuncService;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_03Controller.class);

	@ModelAttribute
	public RoleInfForm setInfoUpForm() {
		return new RoleInfForm();
	}
	
	@RequestMapping("03/search")
	public String search(RoleInfForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "sm/SM_02_03";
		}
		String roleid=form.getRoleInf().getRoleid();
		RoleInf roleInf=new RoleInf();
		roleInf.setRoleid(roleid);
		roleInf=roleInfService.transQueryRoleInf(roleInf);
		form.setRoleInf(roleInf);
		form.setRolename(StringUtil.trim(roleInf.getRolename()));
		form.setMeunlist(menuFuncService.makeMenuList(roleid));
		model.addAttribute("form", form);
		return "sm/SM_02_03";
	}

	@RequestMapping("03/modify")
	public String modify(@Validated({SM_02_03_Modify.class}) RoleInfForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		//获取零时表中角色功能
		form.setMeunlist(form.getMeunlist());
		model.addAttribute("form", form);
		if (result.hasErrors()) {
			return "sm/SM_02_03";
		}
		try {
			roleInfService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "sm/SM_02_03";
		}
		form.setEditable("false");
		model.addAttribute("form", form);
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0002")));
		return "sm/SM_02_03";
	}

	@Autowired
	private RoleInfService roleInfService;

	@Autowired
	private MenuFuncService menuFuncService;
}
