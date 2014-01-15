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
import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInfTmp;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm01")
public class SM_01_04Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_01_04Controller.class);
	
	@ModelAttribute
	public OrgInfTmpForm setInfoUpForm() {
		return new OrgInfTmpForm();
	}
	@RequestMapping("04/init")
	public String init() {
		logger.info("init...");
		return "sm/SM_01_04";
	}

	@RequestMapping("04/search")
	public String search(OrgInfTmpForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		OrgInfTmp orgInfTmp=listForm.getOrgInfTmp();
		if(orgInfTmp==null){
			orgInfTmp=new OrgInfTmp();
		}
		orgInfTmp.setOpttype(listForm.getOpttype());
		orgInfTmp.setOrgid(StringUtil.trim(orgInfTmp.getOrgid()));
		Page<OrgInfTmp> page= orgInfMaintenanceService.transQueryOrgInfTmpList(pageable,orgInfTmp);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.sm.0001")));
		}
		return "sm/SM_01_04";
	}
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
