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
import com.synesoft.fisp.app.sm.model.UserInfForm;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.sm.UserInfService;
import com.synesoft.fisp.domain.service.sm.UserOrgInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm03")
public class SM_03_07Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_03_07Controller.class);

	@ModelAttribute
	public UserInfForm setUpForm() {
		return new UserInfForm();
	}
	@RequestMapping("07/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_03_07";
	}

	@RequestMapping("07/search")
	public String search(UserInfForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		String userId=StringUtil.trim(listForm.getUserid());
		String userName=StringUtil.trim(listForm.getUsername());
		UserInf userInf=listForm.getUserInf();
		if(userInf==null){
			userInf=new UserInf();
		}
		userInf.setUserid(userId);
		userInf.setUsername(userName);
		Page<UserInf> page= userInfService.transQueryUserInfList(pageable, userInf);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm/SM_03_07";
	}

	@Autowired
	private UserInfService userInfService;
	@Autowired
	private UserOrgInfService userOrgInfService;

}
