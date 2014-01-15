package com.synesoft.fisp.app.dp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.domain.model.DmLoanAmount;
import com.synesoft.fisp.domain.service.dp.DmLoanAmountService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "dp01")
public class DP_01_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_01_03Controller.class);

	@ModelAttribute
	public LoanAmountForm setInfoUpForm() {
		return new LoanAmountForm();
	}
	
	@RequestMapping("03/detailSearch")
	public String detailSearch(LoanAmountForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount=dmLoanAmountService.transQueryDmLoanAmount(loanAmount);
		form.setLoanAmount(loanAmount);
		return "dp/DP_01_03";
	}
	
	@Autowired
	private DmLoanAmountService dmLoanAmountService;

}
