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
import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.domain.model.DmLoanAmount;
import com.synesoft.fisp.domain.service.dp.DmLoanAmountService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "dp03")
public class DP_03_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_03_01Controller.class);

	@ModelAttribute
	public LoanAmountForm setUpForm() {
		return new LoanAmountForm();
	}
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "dp/DP_03_01";
	}

	@RequestMapping("01/search")
	public String search(LoanAmountForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		DmLoanAmount loanAmount=listForm.getLoanAmount();
		if(loanAmount==null){
			loanAmount=new DmLoanAmount();
		}
		if(loanAmount.getWorkDate()!=null&&!loanAmount.getWorkDate().equals("")){
			loanAmount.setWorkDate(loanAmount.getWorkDate().replace("-", ""));
		}
//		if(listForm.getLoanIouAmount()!=null&&!listForm.getLoanIouAmount().equals("")){
//			loanAmount.setLoanIouAmount(new BigDecimal(listForm.getLoanIouAmount().replace(",", "")));
//		}
		Page<DmLoanAmount> page= dmLoanAmountService.transQueryDmLoanAmountQueryList(pageable, loanAmount);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "dp/DP_03_01";
	}
	
	@RequestMapping("01/detailSearch")
	public String detailSearch(LoanAmountForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmLoanAmount loanAmount=form.getLoanAmount();
		loanAmount=dmLoanAmountService.transQueryDmLoanAmount(loanAmount);
		form.setLoanAmount(loanAmount);
		return "dp/DP_01_07";
	}

	@Autowired
	private DmLoanAmountService dmLoanAmountService;

}
