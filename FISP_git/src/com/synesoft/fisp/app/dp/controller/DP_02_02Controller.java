package com.synesoft.fisp.app.dp.controller;


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
import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.app.dp.model.DepositBalanceForm.DP_02_02_Modify;
import com.synesoft.fisp.domain.model.DmDepositBalance;
import com.synesoft.fisp.domain.service.dp.DmDepositBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "dp02")
public class DP_02_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_02_02Controller.class);

	@ModelAttribute
	public DepositBalanceForm setInfoUpForm() {
		return new DepositBalanceForm();
	}
	
	@RequestMapping("02/search")
	public String search(DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_02_02";
		}
		form=setValue(form);
		return "dp/DP_02_02";
	}

	@RequestMapping("02/modify")
	public String modify(@Validated({DP_02_02_Modify.class}) DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		if (result.hasErrors()) {
			return "dp/DP_02_02";
		}
		BigDecimal rate=new BigDecimal(form.getInterestRate().replace("%", ""));
		rate=rate.divide(new BigDecimal("100"));
		form.setInterestRate(String.valueOf(rate));
		try {
			dmDepositBalanceService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/DP_02_02";
		}
		
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0002")));
		return "dp/DP_02_02";
	}

	public DepositBalanceForm setValue(DepositBalanceForm form){
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance=dmDepositBalanceService.transQueryDmDepositBalance(depositBalance);
		depositBalance.setWorkDate(DateUtil.formatStringToDatePattern(depositBalance.getWorkDate()));
		form.setDepositBalance(depositBalance);
		form.setProductType(depositBalance.getProductType());
		form.setDepositAgreementSdate(DateUtil.formatStringToDatePattern(depositBalance.getDepositAgreementSdate()));
		form.setDepositAgreementEdate(DateUtil.formatStringToDatePattern(depositBalance.getDepositAgreementEdate()));
		form.setInterestRateFix(depositBalance.getInterestRateFix());
		form.setDepositBalanceDis(String.valueOf(depositBalance.getDepositBalance()));
		form.setInterestRate(String.valueOf(depositBalance.getInterestRate()));
		return form;
	}
	@Autowired
	private DmDepositBalanceService dmDepositBalanceService;

}
