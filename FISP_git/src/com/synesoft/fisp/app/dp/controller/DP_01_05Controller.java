package com.synesoft.fisp.app.dp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

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
public class DP_01_05Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_01_05Controller.class);
	
	@ModelAttribute
	public LoanAmountForm setUpForm() {
		return new LoanAmountForm();
	}

	@RequestMapping("05/detailSearch")
	public String detailSearch(LoanAmountForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount=dmLoanAmountService.transQueryDmLoanAmount(loanAmount);
		form.setLoanAmount(loanAmount);
		return "dp/DP_01_05";
	}
	
	@RequestMapping("05/auth")
	public String auth(LoanAmountForm form,
			BindingResult result, Model model){
		logger.info("start auth ...");
		if (result.hasErrors()) {
			return "dp/DP_01_05";
		}
		try {
			dmLoanAmountService.transAuth(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/DP_01_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0004")));
		return "dp/DP_01_05";
	}
	
	@RequestMapping("05/reject")
	public String reject(LoanAmountForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		if (result.hasErrors()) {
			return "dp/DP_01_05";
		}
		try {
			dmLoanAmountService.transReject(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/DP_01_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0005")));
		return "dp/DP_01_05";
	}
	@RequestMapping("05/cancel")
	public String cancel(LoanAmountForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		if (result.hasErrors()) {
			return "dp/DP_01_05";
		}
		try {
			dmLoanAmountService.transCancel(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/DP_01_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0005")));
		return "dp/DP_01_05";
	}
	
	@Autowired
	private DmLoanAmountService dmLoanAmountService;

}
