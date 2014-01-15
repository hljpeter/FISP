package com.synesoft.fisp.app.bp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.dp.model.LoanBalanceForm;
import com.synesoft.fisp.domain.model.DmLoanBalance;
import com.synesoft.fisp.domain.service.dp.DmLoanBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "bp06")
public class BP_06_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_06_03Controller.class);

	@ModelAttribute
	public LoanBalanceForm setInfoUpForm() {
		return new LoanBalanceForm();
	}
	
	@RequestMapping("03/detailSearch")
	public String detailSearch_01(LoanBalanceForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance=dmLoanBalanceService.transQueryDmLoanBalance(loanBalance);
		form.setLoanBalance(loanBalance);
		return "bp/BP_06_03";
	}
	
	@Autowired
	private DmLoanBalanceService dmLoanBalanceService;

}
