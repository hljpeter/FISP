package com.synesoft.fisp.app.bm.controller;

import java.math.BigDecimal;

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

import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_UpdForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_UpdForm.Bm_Dict_Modify;
import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.app.dp.model.DepositBalanceForm.DP_02_02_Modify;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.fisp.domain.service.bm.SysDataDictService;


/**
 * 
 * @author ljch
 * 
 */

@Controller
@RequestMapping("BM_Data_Upd")
public class BM_Data_UpdController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Data_UpdController.class);

	@ModelAttribute
	public Bm_Data_QryForm setForm() {
		return new Bm_Data_QryForm();
	}
	@RequestMapping("Init")
	public String init(Bm_Data_UpdForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysDataDict dataDict=form.getSysDataDict();
		dataDict=sysDataDictService.querySysDataDictByKey(dataDict);
		form.setSysDataDict(dataDict);
		form.setBeforeMetaName(dataDict.getMetaName());
		form.setBeforeMetaVal(dataDict.getMetaVal());	
		form.setMetaName(dataDict.getMetaName());
		form.setMetaVal(dataDict.getMetaVal());
		
		return "bm/BM_Data_Upd";
	}
	@RequestMapping("Upd")
	public String modify(@Validated({Bm_Dict_Modify.class}) Bm_Data_UpdForm form,
			BindingResult result, Model model) {
		logger.info("start modify ...");
		//System.out.println("***********"+form.getBeforeMetaName()+"  "+form.getMetaVal()+"  "+form.getMetaName());
		if (result.hasErrors()) {
			return "bm/BM_Data_Upd";
		}			
		try {
			sysDataDictService.updateSysDataDict(form);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "bm/BM_Data_Upd";
		}
		
		model.addAttribute(
				"successmsg",
				ResultMessages.success().add(
						ResultMessage.fromCode("i.bm.1001")));
		return "bm/BM_Data_Upd";
	}
	@Autowired
	private SysDataDictService sysDataDictService;

}
