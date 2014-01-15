package com.synesoft.fisp.app.dp.controller;

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

import com.synesoft.fisp.app.dp.model.LoanBalanceCheckForm;
import com.synesoft.fisp.domain.model.DmLoanBalanceCheck;
import com.synesoft.fisp.domain.service.dp.DmLoanBalanceCheckService;



@Controller
@RequestMapping(value = "dp07")
public class DP_07_01Controller {
	private static final Logger logger = LoggerFactory
	.getLogger(DP_07_01Controller.class);
	
	@ModelAttribute
	public LoanBalanceCheckForm setUpForm() {
		return new LoanBalanceCheckForm();
}
	
	@RequestMapping("01/init")
	public String init(@PageableDefaults Pageable pageable,Model model) {
			logger.info("init...");
			DmLoanBalanceCheck loanBalanceCheck=new DmLoanBalanceCheck();
			loanBalanceCheck.setCensusMonth("201309");
			Page<DmLoanBalanceCheck> page= dmLoanBalanceCheckService.QueryDmLoanBalanceCheckQueryList(pageable, loanBalanceCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}
			return "dp/DP_07_01";
	}

	@RequestMapping("01/search")
	public String search(LoanBalanceCheckForm listForm,@PageableDefaults Pageable pageable, Model model) {
			logger.info("start search ...");
			DmLoanBalanceCheck loanBalanceCheck=new DmLoanBalanceCheck();
			listForm.setCensusMonth("201309"); //TODO ERROR-NEEDS TO MODIFY 
			loanBalanceCheck.setCensusMonth(listForm.getCensusMonth().replace("-", ""));
			
			Page<DmLoanBalanceCheck> page= dmLoanBalanceCheckService.QueryDmLoanBalanceCheckQueryList(pageable, loanBalanceCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.sm.0001")));
			}
			return "dp/DP_07_01";
	}
	

	
	@Autowired
	private DmLoanBalanceCheckService dmLoanBalanceCheckService;
}
