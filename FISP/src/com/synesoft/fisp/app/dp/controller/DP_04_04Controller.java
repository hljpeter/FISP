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

import com.synesoft.fisp.app.dp.model.LoanBalanceForm;
import com.synesoft.fisp.domain.model.DmLoanBalance;
import com.synesoft.fisp.domain.service.dp.DmLoanBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "dp04")
public class DP_04_04Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_04_04Controller.class);

	@ModelAttribute
	public LoanBalanceForm setUpForm() {
		return new LoanBalanceForm();
	}
	@RequestMapping("04/init")
	public String init() {
		logger.info("init...");
		return "dp/DP_04_04";
	}

	@RequestMapping("04/search")
	public String search(LoanBalanceForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		DmLoanBalance loanBalance=listForm.getLoanBalance();
		if(loanBalance==null){
			loanBalance=new DmLoanBalance();
		}
		if(loanBalance.getWorkDate()!=null&&!loanBalance.getWorkDate().equals("")){
			loanBalance.setWorkDate(loanBalance.getWorkDate().replace("-", ""));
		}
		Page<DmLoanBalance> page= dmLoanBalanceService.transQueryDmLoanBalanceAuthList(pageable, loanBalance);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "dp/DP_04_04";
	}

	@Autowired
	private DmLoanBalanceService dmLoanBalanceService;

}
