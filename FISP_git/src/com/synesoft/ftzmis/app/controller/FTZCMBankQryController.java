package com.synesoft.ftzmis.app.controller;

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
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.ftzmis.app.model.FTZCMBankQryForm;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.service.FTZCMBankService;


/**
 *  银行代码查询
 * @author NB19
 *
 */
@Controller
@RequestMapping(value = "FTZCMBankQry")
public class FTZCMBankQryController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZCMBankService ftzcmbankservice;
	@ModelAttribute
	public FTZCMBankQryForm setUpForm() {
		return new FTZCMBankQryForm();
	}

	@RequestMapping("Qry")
	public String query(Model model, FTZCMBankQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("银行代码查询...");
		
		FtzBankCode ftzBankCode = new FtzBankCode();	
		ftzBankCode.setBankCode(form.getBankCode());
		ftzBankCode.setBankName(form.getBankName());

		Page<FtzBankCode> page = ftzcmbankservice.queryBankPage(pageable,ftzBankCode);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("银行代码查询结束...");
			return "ftzmis/FTZCMBank_Qry";
		}
		logger.info("银行代码查询结束...");
		return "ftzmis/FTZCMBank_Qry";
	}
	
}
