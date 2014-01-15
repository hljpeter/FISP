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
import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.app.dp.model.LoanAmountForm.DP_01_02_Modify;
import com.synesoft.fisp.domain.model.DmLoanAmount;
import com.synesoft.fisp.domain.service.dp.DmLoanAmountService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "bp04")
public class BP_04_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_04_02Controller.class);

	@ModelAttribute
	public LoanAmountForm setInfoUpForm() {
		return new LoanAmountForm();
	}
	
	@RequestMapping("02/search")
	public String search(LoanAmountForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "bp/BP_04_02";
		}
		form=setValue(form);
		return "bp/BP_04_02";
	}

	@RequestMapping("02/modify")
	public String modify(@Validated({DP_01_02_Modify.class}) LoanAmountForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		if (result.hasErrors()) {
			return "bp/BP_04_02";
		}
		
		try {
			dmLoanAmountService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_04_02";
		}
		BigDecimal rate=new BigDecimal(form.getInterestRate().replace("%", ""));
		rate=rate.divide(new BigDecimal("100"));
		form.setInterestRate(String.valueOf(rate));
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0002")));
		return "bp/BP_04_02";
	}

	public LoanAmountForm setValue(LoanAmountForm form){
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount=dmLoanAmountService.transQueryDmLoanAmount(loanAmount);
		form.setLoanAmount(loanAmount);
		form.setWorkdate(DateUtil.formatStringToDatePattern(loanAmount.getWorkDate()));
		form.setCustomerType(loanAmount.getCustomerType());
		form.setLoanIndustryType(loanAmount.getLoanIndustryType());
		form.setLoanIouCode(loanAmount.getLoanIouCode());
		form.setProductType(loanAmount.getProductType());
		form.setLoanActualInvest(loanAmount.getLoanActualInvest());
		form.setLoanOriginationDate(DateUtil.formatStringToDatePattern(loanAmount.getLoanOriginationDate()));
		form.setLoanMaturityDate(DateUtil.formatStringToDatePattern(loanAmount.getLoanMaturityDate()));
		form.setCurrency(loanAmount.getCurrency());
		form.setLoanIouAmount(String.valueOf(loanAmount.getLoanIouAmount()));
		form.setInterestRateFix(loanAmount.getInterestRateFix());
		form.setInterestRate(String.valueOf(loanAmount.getInterestRate()));
		form.setLoanGuaranteeKind(loanAmount.getLoanGuaranteeKind());
		form.setStatus(loanAmount.getStatus());
		form.setLoanRecoverySign(loanAmount.getLoanRecoverySign());
		form.setEndDate(DateUtil.formatStringToDatePattern(loanAmount.getEndDate()));
		return form;
	}
	@Autowired
	private DmLoanAmountService dmLoanAmountService;

}
