package com.synesoft.fisp.app.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.RoleInfForm;
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.RoleInfTmp;
import com.synesoft.fisp.domain.service.sm.MenuFuncService;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_05Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_05Controller.class);

	@RequestMapping("05/detailSearch_01")
	public String detailSearch_01(@ModelAttribute RoleInfForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch_01...");
		RoleInf roleInf=form.getRoleInf();
		roleInf=roleInfService.transQueryRoleInf(roleInf);
		roleInf.setMenulist(menuFuncService.makeMenuList(StringUtil.trim(roleInf.getRoleid())));
		form.setRoleInf(roleInf);
		form.setEditable("false");
		model.addAttribute("form", form);
		return "sm/SM_02_05";
	}

	@RequestMapping("05/detailSearch_04")
	public String detailSearch_04(@ModelAttribute RoleInfTmpForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch_04...");
		RoleInfTmp roleInfTmp = form.getRoleInfTmp();
		roleInfTmp = roleInfService.transQueryRoleInfTmp(roleInfTmp);
		String roleId = StringUtil.trim(roleInfTmp.getRoleid());
		form.setMeunlist(menuFuncService.makeMenuListTmp(roleId));
		form.setRoleInfTmp(roleInfTmp);
		model.addAttribute("form", form);
		return "sm/SM_02_06";
	}
	
	@Autowired
	private RoleInfService roleInfService;
	@Autowired
	public MenuFuncService menuFuncService;

}
