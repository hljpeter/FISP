package com.synesoft.fisp.app.nsm.controller;

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
import com.synesoft.fisp.app.sm.model.UserInfTmpForm;
import com.synesoft.fisp.domain.model.UserInfTmp;
import com.synesoft.fisp.domain.service.sm.UserInfService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "nsm03")
public class NSM_03_04Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(NSM_03_04Controller.class);
	
	@ModelAttribute
	public UserInfTmpForm setInfoUpForm() {
		return new UserInfTmpForm();
	}
	@RequestMapping("04/init")
	public String init() {
		logger.info("init...");
		return "sm1/SM_03_04";
	}

	@RequestMapping("04/search")
	public String search(UserInfTmpForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		UserInfTmp userInfTmp=listForm.getUserInfTmp();
		if(userInfTmp==null){
			userInfTmp=new UserInfTmp();
		}
		userInfTmp.setOpttype(listForm.getOpttype());
		userInfTmp.setUserid(StringUtil.trim(userInfTmp.getUserid()));
		Page<UserInfTmp> page= userInfService.transQueryUserInfTmpList(pageable,userInfTmp);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm1/SM_03_04";
	}
	
	@Autowired
	private UserInfService userInfService;

}
