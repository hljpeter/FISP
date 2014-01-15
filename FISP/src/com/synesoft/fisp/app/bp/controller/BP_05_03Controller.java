package com.synesoft.fisp.app.bp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.domain.model.DmDepositBalance;
import com.synesoft.fisp.domain.service.dp.DmDepositBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "bp05")
public class BP_05_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_05_03Controller.class);

	@ModelAttribute
	public DepositBalanceForm setInfoUpForm() {
		return new DepositBalanceForm();
	}
	
	@RequestMapping("03/detailSearch")
	public String detailSearch(DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance=dmDepositBalanceService.transQueryDmDepositBalance(depositBalance);
		form.setDepositBalance(depositBalance);
		return "bp/BP_05_03";
	}
	
	@Autowired
	private DmDepositBalanceService dmDepositBalanceService;

}
