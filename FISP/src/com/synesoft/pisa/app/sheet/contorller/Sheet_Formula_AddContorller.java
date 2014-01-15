package com.synesoft.pisa.app.sheet.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form;
import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form.Sheet_Formula_Add;
import com.synesoft.pisa.domain.service.sheet.Sheet_FormulaService;


@Controller
@RequestMapping(value = "Sheet_Formula_Add")
public class Sheet_Formula_AddContorller {

	private static final Logger logger = LoggerFactory
			.getLogger(Sheet_Formula_AddContorller.class);


	@RequestMapping("Init")
	public String init(){
		return "pisa/Sheet_Formula_Add"; 
	}
	
	@RequestMapping("Add")
	public String add(@Validated({Sheet_Formula_Add.class})Sheet_Formula_Form form,
			BindingResult result, Model model) {
		logger.info("start Add ...");
		if (result.hasErrors()) {
			return "pisa/Sheet_Formula_Add";
		}
		try {
			sheet_FormulaService.transAdd(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "pisa/Sheet_Formula_Add";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0001")));
		return "pisa/Sheet_Formula_Add";
	}
	@Autowired
	private Sheet_FormulaService sheet_FormulaService;
}
