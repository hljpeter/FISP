package com.synesoft.pisa.app.sheet.contorller; 

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

import com.synesoft.pisa.app.draft.contorller.Draft_Paper_InputContorller;
import com.synesoft.pisa.app.sheet.model.Draft_Sheet_Form;
import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.model.ExpSheetInfo;
import com.synesoft.pisa.domain.service.sheet.SheetService;





/** 
 *
 * description: 表单查询
 * @author wjl 
 * @version 2013-12-17
 */
@Controller
@RequestMapping(value= "Draft_Sheet")
public class Draft_Sheet_QueryContorller {
	private static final Logger logger = LoggerFactory
			.getLogger(Draft_Paper_InputContorller.class);
	
	@Autowired
	private SheetService sheetService;
	
	@ModelAttribute
	public Draft_Sheet_Form setUpForm() {
		return new Draft_Sheet_Form();
	}
	
	@RequestMapping("Qry")
	public String search(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("srart search ...");
		ExpSheetInfo sheetNo = new ExpSheetInfo();
		if(null!=form.getSheetNo()){
			sheetNo.setSheetNo(form.getSheetNo());
		}
		Page<ExpSheetInfo> page  = sheetService.querySheetList(pageable, sheetNo);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Sheet_Quy";		
	}
	@RequestMapping("Detil")
	public String detailSearch(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailSearch...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
		if(null!=form.getSheetNo()){
			sheetList.setSheetNo(form.getSheetNo());
			sheetList.setSubNo(form.getSubNo());
			sheetList.setAreaType(form.getAreaType());
			sheetList.setBatNo(form.getBatNo());
			sheetList.setAreaCode(form.getAreaCode());	
		}
		Page<ExpSheetDtl> page = sheetService.querySheetDetilList(pageable, sheetList);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Sheet_Quy_Dtl";		
	}
}
 