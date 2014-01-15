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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.pisa.app.common.constants.CommonConst;
import com.synesoft.pisa.app.draft.contorller.Draft_Paper_InputContorller;
import com.synesoft.pisa.app.sheet.model.Draft_Sheet_Form;
import com.synesoft.pisa.app.sheet.model.Draft_Sheet_Form.Draft_Sheet_Form_Update;
import com.synesoft.pisa.domain.model.ExpSheetDtl;
import com.synesoft.pisa.domain.service.sheet.ItemService;


/** 
 *
 * description: 指标查询
 * @author wjl 
 * @version 2013-12-17
 */
@Controller
@RequestMapping(value= "Draft_Item")
public class Draft_Item_QueryContorller {
	private static final Logger logger = LoggerFactory
			.getLogger(Draft_Paper_InputContorller.class);
	
	@Autowired
	private ItemService itemService;
	
	@ModelAttribute
	public Draft_Sheet_Form setUpForm() {
		return new Draft_Sheet_Form();
	}
	
	@RequestMapping("Qry")
	public String search(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("srart search ...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
				sheetList.setItemNo(form.getItemNo());
				sheetList.setItemName(form.getItemName());
				sheetList.setSubNo(form.getSubNo());
				sheetList.setAreaType(form.getAreaType());
				sheetList.setAreaCode(form.getAreaCode());	

			Page<ExpSheetDtl> page = itemService.queryItemList(pageable, sheetList);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.dp.0001")));
			}
			return "pisa/BQ_Item_Quy";		
		}
	@RequestMapping("Detil")
	public String detailSearch(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailSearch...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
			sheetList.setItemNo(form.getItemNo());
			sheetList.setAreaCode(form.getAreaCode());
			sheetList.setSheetNo(form.getSheetNo());
		Page<ExpSheetDtl> page = itemService.queryItemDetilList(pageable, sheetList);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Item_Quy_Dtl";		
	}
	
	@RequestMapping("QryEdit")
	public String searchEdit(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("srart search ...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
				sheetList.setItemNo(form.getItemNo());
				sheetList.setItemName(form.getItemName());
				sheetList.setSubNo(form.getSubNo());
				sheetList.setAreaType(form.getAreaType());
				sheetList.setAreaCode(form.getAreaCode());	
				sheetList.setDimNo("01");
			Page<ExpSheetDtl> page = itemService.queryItemList(pageable, sheetList);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.dp.0001")));
			}
			return "pisa/BQ_Item_Quy_Edit";		
		}
	
	@RequestMapping("DetilEdit")
	public String detailSearchEdit(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailSearch...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
			sheetList.setItemNo(form.getItemNo());
			sheetList.setSheetNo(form.getSheetNo());
		Page<ExpSheetDtl> page = itemService.queryItemDetilList(pageable, sheetList);
		if(page.getContent().size()>0){
			model.addAttribute("page", page);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Item_Quy_Dtl_Edit";		
	}
	
	@RequestMapping("DetilEditPre")
	public String detailSearchEditPre(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailSearch...");
			ExpSheetDtl sheetDtl = new ExpSheetDtl();
			sheetDtl.setSeqNo(form.getSeqNo());
			sheetDtl.setSheetNo(form.getSheetNo());
		ExpSheetDtl expSheetDtl = itemService.queryItemDetil(sheetDtl);
		if(expSheetDtl != null){
			form.setExpSheetDtl(expSheetDtl);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Item_Quy_Dtl_EditPre";		
	}
	
	@RequestMapping("DetilEditSave")
	public String detailEditSave(Model model,@Validated({Draft_Sheet_Form_Update.class})Draft_Sheet_Form form,
			BindingResult result) {
			logger.info("detail update...");
		if (result.hasErrors()) {
			return "pisa/BQ_Item_Quy_Dtl_EditPre";
		}
		ExpSheetDtl dtl = form.getExpSheetDtl();
		dtl.setStatus(CommonConst.INDEX_DATA_STATUS_KEYIN);
		UserInf userInfo = ContextConst.getCurrentUser();
		dtl.setMakUserId(userInfo.getUserid());
		dtl.setMakDatetime(DateUtil.getNowInputDateTime());
		int i =	itemService.updateSheetDtl(dtl);
		if (i < 0) {
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("e.sysrunner.0003")));
		} else {
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("i.sm.0002")));
		}
		return "pisa/BQ_Item_Quy_Dtl_EditPre";
	}
	
	/****审核***************/
	@RequestMapping("QryAudit")
	public String searchAudit(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("srart search ...");
			ExpSheetDtl sheetList = new ExpSheetDtl();
			sheetList.setItemNo(form.getItemNo());
			sheetList.setItemName(form.getItemName());
			sheetList.setSubNo(form.getSubNo());
			sheetList.setAreaType(form.getAreaType());
			sheetList.setAreaCode(form.getAreaCode());
			sheetList.setStatus(CommonConst.INDEX_DATA_STATUS_KEYIN);
			Page<ExpSheetDtl> page = itemService.queryItemList(pageable, sheetList);
			if(page.getContent().size()>0){
				model.addAttribute("page", page);
			}else{
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("w.dp.0001")));
			}
			return "pisa/BQ_Item_Quy_Audit";		
		}
	
	@RequestMapping("DetilAudit")
	public String detailSearchAudit(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailSearch...");
			ExpSheetDtl sheetDtl = new ExpSheetDtl();
			sheetDtl.setSeqNo(form.getSeqNo());
			sheetDtl.setSheetNo(form.getSheetNo());
		ExpSheetDtl expSheetDtl = itemService.queryItemDetil(sheetDtl);
		if(expSheetDtl != null){
			form.setExpSheetDtl(expSheetDtl);
		}else{
			model.addAttribute(
					"infomsg",
					ResultMessages.info().add(
							ResultMessage.fromCode("w.dp.0001")));
		}
		return "pisa/BQ_Item_Quy_Dtl_Audit";		
	}
	
	@RequestMapping("Audit")
	public String detailAudit(Draft_Sheet_Form form,
			@PageableDefaults Pageable pageable, Model model) {
			logger.info("detailAudit...");
			ExpSheetDtl dtl = form.getExpSheetDtl();
			dtl.setStatus(CommonConst.INDEX_DATA_STATUS_AUDIT);
			UserInf userInfo = ContextConst.getCurrentUser();
			dtl.setChkUserId(userInfo.getUserid());
			dtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i =	itemService.auditSheetDtl(dtl);
			if (i < 0) {
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("e.ftzmis.210301.0008")));
			} else {
				model.addAttribute(
						"infomsg",
						ResultMessages.info().add(
								ResultMessage.fromCode("i.dp.0004")));
			}
		return "pisa/BQ_Item_Quy_Dtl_Audit";		
	}
}
 