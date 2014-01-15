package com.synesoft.fisp.app.bp.controller;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.LoanBalanceForm;
import com.synesoft.fisp.app.dp.model.LoanBalanceForm.DP_04_02_Modify;
import com.synesoft.fisp.domain.model.DmLoanBalance;
import com.synesoft.fisp.domain.service.dp.DmLoanBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "bp06")
public class BP_06_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_06_02Controller.class);

	@ModelAttribute
	public LoanBalanceForm setInfoUpForm() {
		return new LoanBalanceForm();
	}
	
	@RequestMapping("02/search")
	public String search(LoanBalanceForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "bp/BP_06_02";
		}
		form=setValue(form);
		return "bp/BP_06_02";
	}

	@RequestMapping("02/modify")
	public String modify(@Validated({DP_04_02_Modify.class}) LoanBalanceForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		if (result.hasErrors()) {
			return "bp/BP_06_02";
		}
		BigDecimal rate=new BigDecimal(form.getInterestRate().replace("%", ""));
		rate=rate.divide(new BigDecimal("100"));
		form.setInterestRate(String.valueOf(rate));
		try {
			dmLoanBalanceService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_06_02";
		}
		
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0002")));
		return "bp/BP_06_02";
	}

	public LoanBalanceForm setValue(LoanBalanceForm form){
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance=dmLoanBalanceService.transQueryDmLoanBalance(loanBalance);
		form.setLoanBalance(loanBalance);
		form.setWorkdate(DateUtil.formatStringToDatePattern(loanBalance.getWorkDate()));
		form.setCustomerType(loanBalance.getCustomerType());
		form.setLoanIndustryType(loanBalance.getLoanIndustryType());
		form.setLoanIouCode(loanBalance.getLoanIouCode());
		form.setProductType(loanBalance.getProductType());
		form.setLoanActualInvest(loanBalance.getLoanActualInvest());
		form.setLoanOriginationDate(DateUtil.formatStringToDatePattern(loanBalance.getLoanOriginationDate()));
		form.setLoanMaturityDate(DateUtil.formatStringToDatePattern(loanBalance.getLoanMaturityDate()));
		form.setCurrency(loanBalance.getCurrency());
		form.setLoanIouBalance(String.valueOf(loanBalance.getLoanIouBalance()));
		form.setInterestRateFix(loanBalance.getInterestRateFix());
		form.setInterestRate(String.valueOf(loanBalance.getInterestRate()));
		form.setLoanGuaranteeKind(loanBalance.getLoanGuaranteeKind());
		form.setLoanQuality(loanBalance.getLoanQuality());
		form.setStatus(loanBalance.getStatus());
		form.setExtensionEdate(DateUtil.formatStringToDatePattern(loanBalance.getExtensionEdate()));
		return form;
	}
	@Autowired
	private DmLoanBalanceService dmLoanBalanceService;

}
