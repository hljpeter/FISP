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

import com.synesoft.fisp.app.dp.model.LoanAmountCheckForm;
import com.synesoft.fisp.domain.model.DmLoanAmountCheck;
import com.synesoft.fisp.domain.service.dp.DmLoanAmountCheckService;



@Controller
@RequestMapping(value = "dp08")
public class DP_08_01Controller {

	private static final Logger logger = LoggerFactory
	.getLogger(DP_08_01Controller.class);
	
	@ModelAttribute
	public LoanAmountCheckForm setUpForm() {
		return new LoanAmountCheckForm();
}
	
	@RequestMapping("01/init")
	public String init(@PageableDefaults Pageable pageable,Model model) {
			logger.info("init...");
			DmLoanAmountCheck loanAmountCheck=new DmLoanAmountCheck();
			loanAmountCheck.setCensusMonth("201309");
			Page<DmLoanAmountCheck> page= dmLoanAmountCheckService.QueryDmLoanAmountCheckQueryList(pageable, loanAmountCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}
			return "dp/DP_08_01";
	}

	@RequestMapping("01/search")
	public String search(LoanAmountCheckForm listForm,@PageableDefaults Pageable pageable, Model model) {
			logger.info("start search ...");
			DmLoanAmountCheck loanAmountCheck=new DmLoanAmountCheck();
			listForm.setCensusMonth("201309"); //TODO ERROR-NEEDS TO MODIFY 
			loanAmountCheck.setCensusMonth(listForm.getCensusMonth().replace("-", ""));
			
			Page<DmLoanAmountCheck> page= dmLoanAmountCheckService.QueryDmLoanAmountCheckQueryList(pageable, loanAmountCheck);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.sm.0001")));
			}
			return "dp/DP_08_01";
	}
	
	@Autowired
	private DmLoanAmountCheckService dmLoanAmountCheckService;
}
