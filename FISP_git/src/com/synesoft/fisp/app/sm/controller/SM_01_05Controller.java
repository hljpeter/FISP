package com.synesoft.fisp.app.sm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.sm.model.OrgInfForm;
import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.OrgInfTmp;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "sm01")
public class SM_01_05Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(SM_01_05Controller.class);

	@RequestMapping("05/detailSearch_01")
	public String detailSearch_01(@ModelAttribute OrgInfForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch_01...");
		OrgInf orgInf=form.getOrgInf();
		orgInf=orgInfMaintenanceService.transQueryOrgInf(orgInf);
		form.setOrgInf(orgInf);
		return "sm/SM_01_05";
	}
	@RequestMapping("05/detailSearch_04")
	public String detailSearch_04(@ModelAttribute OrgInfTmpForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch_04...");
		OrgInfTmp orgInfTmp=form.getOrgInfTmp();
		orgInfTmp=orgInfMaintenanceService.transQueryOrgInfTmp(orgInfTmp);
		form.setOrgInfTmp(orgInfTmp);
		return "sm/SM_01_06";
	}
	
	@Autowired
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
