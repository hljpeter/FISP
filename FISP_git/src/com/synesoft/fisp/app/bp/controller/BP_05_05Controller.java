package com.synesoft.fisp.app.bp.controller;

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
public class BP_05_05Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_05_05Controller.class);
	
	@ModelAttribute
	public DepositBalanceForm setUpForm() {
		return new DepositBalanceForm();
	}

	@RequestMapping("05/detailSearch")
	public String detailSearch(DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance=dmDepositBalanceService.transQueryDmDepositBalance(depositBalance);
		form.setDepositBalance(depositBalance);
		return "bp/BP_05_05";
	}
	
	@RequestMapping("05/auth")
	public String auth(DepositBalanceForm form,
			BindingResult result, Model model){
		logger.info("start auth ...");
		if (result.hasErrors()) {
			return "bp/BP_05_05";
		}
		try {
			dmDepositBalanceService.transAuth(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_05_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0004")));
		return "bp/BP_05_05";
	}
	
	@RequestMapping("05/reject")
	public String reject(DepositBalanceForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		if (result.hasErrors()) {
			return "bp/BP_05_05";
		}
		try {
			dmDepositBalanceService.transReject(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_05_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0005")));
		return "bp/BP_05_05";
	}
	@RequestMapping("05/cancel")
	public String cancel(DepositBalanceForm form,
	BindingResult result, Model model) {
		logger.info("start reject ...");
		if (result.hasErrors()) {
			return "bp/BP_05_05";
		}
		try {
			dmDepositBalanceService.transCancel(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_05_05";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0005")));
		return "bp/BP_05_05";
	}
	
	@Autowired
	private DmDepositBalanceService dmDepositBalanceService;

}
