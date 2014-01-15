package com.synesoft.fisp.app.bp.controller;


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
public class BP_05_01Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(BP_05_01Controller.class);

	@ModelAttribute
	public DepositBalanceForm setUpForm() {
		return new DepositBalanceForm();
	}
	@RequestMapping("01/init")
	public String init() {
		logger.info("init...");
		return "bp/BP_05_01";
	}

	@RequestMapping("01/search")
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
		Page<DmDepositBalance> page= dmDepositBalanceService.transQueryDmDepositBalanceInputList(pageable, depositBalance);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "bp/BP_05_01";
	}

	@RequestMapping("01/del")
	public String del(DepositBalanceForm form,
			BindingResult result, Model model) {
		logger.info("start del ...");
		if (result.hasErrors()) {
			return "bp/BP_05_01";
		}
		try {
			dmDepositBalanceService.transDel(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bp/BP_05_01";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.0003")));
		return "forward:/bp05/01/search";
	}
	
	@Autowired
	private DmDepositBalanceService dmDepositBalanceService;

}
