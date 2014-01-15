package com.synesoft.fisp.app.dp.controller;


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

import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.domain.model.DmDepositBalance;
import com.synesoft.fisp.domain.service.dp.DmDepositBalanceService;

/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping(value = "dp03")
public class DP_03_02Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(DP_03_02Controller.class);

	@ModelAttribute
	public DepositBalanceForm setUpForm() {
		return new DepositBalanceForm();
	}
	@RequestMapping("02/init")
	public String init() {
		logger.info("init...");
		return "dp/DP_03_02";
	}

	@RequestMapping("02/search")
	public String search(DepositBalanceForm listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		DmDepositBalance depositBalance=listForm.getDepositBalance();
		if(depositBalance==null){
			depositBalance=new DmDepositBalance();
		}
		if(depositBalance.getWorkDate()!=null&&!depositBalance.getWorkDate().equals("")){
			depositBalance.setWorkDate(depositBalance.getWorkDate().replace("-", ""));
		}
		Page<DmDepositBalance> page= dmDepositBalanceService.transQueryDmDepositBalanceQueryList(pageable, depositBalance);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "dp/DP_03_02";
	}

	@RequestMapping("02/detailSearch")
	public String detailSearch(DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		DmDepositBalance depositBalance=form.getDepositBalance();
		depositBalance=dmDepositBalanceService.transQueryDmDepositBalance(depositBalance);
		form.setDepositBalance(depositBalance);
		return "dp/DP_02_07";
	}
	
	@Autowired
	private DmDepositBalanceService dmDepositBalanceService;

}
