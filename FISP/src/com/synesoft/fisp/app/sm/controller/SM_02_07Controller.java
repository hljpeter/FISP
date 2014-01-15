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

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.sm.model.RoleInfForm;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_07Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_07Controller.class);

	@ModelAttribute
	public RoleInfForm setUpForm() {
		return new RoleInfForm();
	}
	@RequestMapping("07/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_02_07";
	}

	@RequestMapping("07/search")
	public String search(RoleInfForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		String roleId=StringUtil.trim(listForm.getRoleid());
		String roleName=StringUtil.trim(listForm.getRolename());
		RoleInf roleInf=listForm.getRoleInf();
		if(roleInf==null){
			roleInf=new RoleInf();
		}
		roleInf.setRoleid(roleId);
		roleInf.setRolename(roleName);
		
		Page<RoleInf> page= roleInfService.transQueryRoleInfList(pageable, roleInf);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm/SM_02_07";
	}
	@Autowired
	private RoleInfService roleInfService;

}
