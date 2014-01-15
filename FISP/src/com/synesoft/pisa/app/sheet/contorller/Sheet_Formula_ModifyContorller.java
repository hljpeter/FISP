package com.synesoft.pisa.app.sheet.contorller;

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

import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form;
import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form.Sheet_Formula_Modify;
import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.service.sheet.Sheet_FormulaService;


@Controller
@RequestMapping(value = "Sheet_Formula_Modify")
public class Sheet_Formula_ModifyContorller {

	private static final Logger logger = LoggerFactory
			.getLogger(Sheet_Formula_ModifyContorller.class);

	@ModelAttribute
	public Sheet_Formula_Form setUpForm() {
		return new Sheet_Formula_Form();
	}
	
	@RequestMapping("Init")
	public String init(Sheet_Formula_Form form,
			BindingResult result, Model model){
		logger.info("start Init ...");
		if (result.hasErrors()) {
			return "pisa/Sheet_Formula_Modify";
		}
		ExpSheetFormula expSheetFormula=form.getExpSheetFormula();
		expSheetFormula=sheet_FormulaService.transExpSheetFormula(expSheetFormula);
		form.setExpSheetFormula(expSheetFormula);
		form.setSheetNo(expSheetFormula.getSheetNo());
		form.setFormulaName(expSheetFormula.getFormulaName());
		form.setFormulaArea(expSheetFormula.getFormulaArea());
		return "pisa/Sheet_Formula_Modify"; 
	}
	
	@RequestMapping("Modify")
	public String modify(@Validated({Sheet_Formula_Modify.class})Sheet_Formula_Form form,
			BindingResult result, Model model) {
		logger.info("start Modify ...");
		if (result.hasErrors()) {
			return "pisa/Sheet_Formula_Modify";
		}
		try {
			sheet_FormulaService.transUpdate(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "pisa/Sheet_Formula_Modify";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0002")));
		return "pisa/Sheet_Formula_Modify";
	}
	@Autowired
	private Sheet_FormulaService sheet_FormulaService;
}
