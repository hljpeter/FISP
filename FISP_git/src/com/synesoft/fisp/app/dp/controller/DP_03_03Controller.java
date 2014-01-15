package com.synesoft.fisp.app.dp.controller;


import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value = "dp03")
public class DP_03_03Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_03_03Controller.class);

	@ModelAttribute
	public LoanBalanceForm setUpForm() {
		return new LoanBalanceForm();
	}
	@RequestMapping("03/init")
	public String init() {
		logger.info("init...");
		return "dp/DP_03_03";
	}

	@RequestMapping("03/search")
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
//		if(listForm.getLoanIouBalance()!=null&&!listForm.getLoanIouBalance().equals("")){
//			loanBalance.setLoanIouBalance(new BigDecimal(listForm.getLoanIouBalance().replace(",", "")));
//		}
		Page<DmLoanBalance> page= dmLoanBalanceService.transQueryDmLoanBalanceQueryList(pageable, loanBalance);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "dp/DP_03_03";
	}
	
	@RequestMapping("03/detailSearch")
	public String detailSearch(LoanBalanceForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmLoanBalance loanBalance=form.getLoanBalance();
		loanBalance=dmLoanBalanceService.transQueryDmLoanBalance(loanBalance);
		form.setLoanBalance(loanBalance);
		return "dp/DP_04_07";
	}

	@Autowired
	private DmLoanBalanceService dmLoanBalanceService;

}
