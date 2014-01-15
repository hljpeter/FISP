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

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.app.dp.model.LoanAmountForm.DP_01_02_Modify;
import com.synesoft.fisp.domain.service.dp.DmLoanAmountService;

@Controller
@RequestMapping(value = "dp01")
public class DP_01_06Controller {
	private static final Logger logger = LoggerFactory
	.getLogger(DP_01_06Controller.class);

		@ModelAttribute
		public LoanAmountForm setInfoUpForm() {
		       return new LoanAmountForm();
		}
		
		@RequestMapping("06/init")
		public String search(LoanAmountForm form,
					BindingResult result, Model model) {
				logger.info("init...");
				form.getLoanAmount().setBranch(ContextConst.getOrgInfByUser().getBankid());
				form.setWorkdate(DateUtil.formatStringToDatePattern(ContextConst.getTipsConn().getCursysdate()));
				form.getLoanAmount().setRsv1("H");
				return "dp/DP_01_06";
		}
		
		@RequestMapping("06/add")
		public String add(@Validated({DP_01_02_Modify.class}) LoanAmountForm form,
				BindingResult result, Model model) {
			logger.info("start modify ...");
			if (result.hasErrors()) {
				return "dp/DP_01_06";
			}
			BigDecimal rate=new BigDecimal(form.getInterestRate().replace("%", ""));
			rate=rate.divide(new BigDecimal("100"));
			form.setInterestRate(String.valueOf(rate));
			try {
				dmLoanAmountService.transAdd(form);
			} catch (BusinessException e) {
				model.addAttribute("errmsg", e.getResultMessages());
				return "dp/DP_01_06";
			}
			
			model.addAttribute(
					"successmsg",
					ResultMessages.success().add(
							ResultMessage.fromCode("i.dp.0006")));
			return "dp/DP_01_06";
		}
			
		@Autowired
		private DmLoanAmountService dmLoanAmountService;
}
