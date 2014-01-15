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
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.domain.model.RoleInfTmp;
import com.synesoft.fisp.domain.service.sm.RoleInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm02")
public class SM_02_04Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_02_04Controller.class);
	
	@ModelAttribute
	public RoleInfTmpForm setInfoUpForm() {
		return new RoleInfTmpForm();
	}
	@RequestMapping("04/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_02_04";
	}

	@RequestMapping("04/search")
	public String search(RoleInfTmpForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		RoleInfTmp roleInfTmp=listForm.getRoleInfTmp();
		if(roleInfTmp==null){
			roleInfTmp=new RoleInfTmp();
		}
		roleInfTmp.setOpttype(listForm.getOpttype());
		roleInfTmp.setRoleid(StringUtil.trim(roleInfTmp.getRoleid()));
		Page<RoleInfTmp> page= roleInfService.transQueryRoleInfTmpList(pageable,roleInfTmp);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm/SM_02_04";
	}
	
	@Autowired
	private RoleInfService roleInfService;

}
