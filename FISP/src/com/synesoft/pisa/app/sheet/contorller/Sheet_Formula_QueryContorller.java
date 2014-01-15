package com.synesoft.pisa.app.sheet.contorller;

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

import com.synesoft.pisa.app.sheet.model.Sheet_Formula_Form;
import com.synesoft.pisa.domain.model.ExpSheetFormula;
import com.synesoft.pisa.domain.service.sheet.Sheet_FormulaService;


@Controller
@RequestMapping(value = "Sheet_Formula_Qry")
public class Sheet_Formula_QueryContorller {

	private static final Logger logger = LoggerFactory
			.getLogger(Sheet_Formula_QueryContorller.class);

	@ModelAttribute
	public Sheet_Formula_Form setUpForm() {
		return new Sheet_Formula_Form();
	}
	
	@RequestMapping("init")
	public String init(){
		return "pisa/Sheet_Formula_Query"; 
	}
	
	@RequestMapping("Qry")
	public String search(Sheet_Formula_Form listForm,
			@PageableDefaults Pageable pageable, Model model) {
		logger.info("start search ...");
		ExpSheetFormula expSheetFormula=listForm.getExpSheetFormula();
		if(expSheetFormula==null){
			expSheetFormula=new ExpSheetFormula();
		}
		expSheetFormula.setSheetNo(listForm.getSheetNo());
		expSheetFormula.setItemNo(listForm.getItemNo());
		expSheetFormula.setItemName(listForm.getItemName());
		expSheetFormula.setDimNo(listForm.getDimNo());
		expSheetFormula.setFormulaName(listForm.getFormulaName());
		Page<ExpSheetFormula> page= sheet_FormulaService.transQueryExpSheetFormulaList(pageable, expSheetFormula);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/Sheet_Formula_Query";
	}
	
	@RequestMapping("Detail")
	public String detail(Sheet_Formula_Form form,
			BindingResult result, Model model) {
		logger.info("start Detail ...");
		ExpSheetFormula expSheetFormula=form.getExpSheetFormula();
		expSheetFormula=sheet_FormulaService.transExpSheetFormula(expSheetFormula);
		form.setExpSheetFormula(expSheetFormula);
		return "pisa/Sheet_Formula_Dtl";
	}
	
	@RequestMapping("Delete")
	public String del(Sheet_Formula_Form form,
			BindingResult result, Model model) {
		logger.info("start Delete ...");
		if (result.hasErrors()) {
			return "forward:/Sheet_Formula_Qry/Qry";
		}
		try {
			sheet_FormulaService.transDel(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/Sheet_Formula_Qry/Qry";
		}
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.sm.0003")));
		return "forward:/Sheet_Formula_Qry/Qry";
	}
	
	@Autowired
	private Sheet_FormulaService sheet_FormulaService;
}
