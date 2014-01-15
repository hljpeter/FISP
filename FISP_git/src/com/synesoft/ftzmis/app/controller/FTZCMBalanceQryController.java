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

import com.synesoft.ftzmis.app.model.FTZCMBalanceQryForm;
import com.synesoft.ftzmis.domain.model.FtzBalanceCode;
import com.synesoft.ftzmis.domain.service.FTZCMBalanceService;


/**
 *  资产负债指标代码查询
 * @author NB19
 *
 */
@Controller
@RequestMapping(value = "FTZCMBalanceQry")
public class FTZCMBalanceQryController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZCMBalanceService ftzcmbalanceservice;
	@ModelAttribute
	public FTZCMBalanceQryForm setUpForm() {
		return new FTZCMBalanceQryForm();
	}

	@RequestMapping("Qry")
	public String query(Model model, FTZCMBalanceQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("资产负债指标代码查询...");
		
		FtzBalanceCode ftzBalanceCode = new FtzBalanceCode();		
		ftzBalanceCode.setBalanceCode(form.getBalanceCode()); //设置查询条件
		ftzBalanceCode.setOneTypeName(form.getOneTypeName());
		ftzBalanceCode.setTwoTypeName(form.getTwoTypeName());
		ftzBalanceCode.setThreeTypeName(form.getThreeTypeName());
		ftzBalanceCode.setFourTypeName(form.getFourTypeName());
		Page<FtzBalanceCode> page = ftzcmbalanceservice.queryBalancePage(pageable,ftzBalanceCode);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("资产负债指标代码查询！");
			logger.info("资产负债指标代码查询结束...");
			return "ftzmis/FTZCMBalance_Qry";
		}
		logger.info("资产负债指标代码查询结束...");
		return "ftzmis/FTZCMBalance_Qry";
	}
	
}
