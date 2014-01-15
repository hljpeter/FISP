package com.synesoft.fisp.app.bm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.fisp.domain.service.bm.SysDataDictService;


/**
 * 
 * @author michelle.wang
 * 
 */

@Controller
@RequestMapping("BM_Data_Dtl")
public class BM_Data_DtlController {
	private static final Logger logger = LoggerFactory
			.getLogger(BM_Data_DtlController.class);

	@ModelAttribute
	public Bm_Data_QryForm setForm() {
		return new Bm_Data_QryForm();
	}
	@RequestMapping("Init")
	public String detailSearch(Bm_Data_QryForm form,
			BindingResult result, Model model) {
		logger.info("detailSearch...");
		SysDataDict dataDict=form.getSysDataDict();
		dataDict=sysDataDictService.querySysDataDictByKey(dataDict);
		form.setSysDataDict(dataDict);
		System.out.println(dataDict.getMetaDesc()+dataDict.getMetaLan());
		return "bm/BM_Data_Dtl";
	}
	
	@Autowired
	private SysDataDictService sysDataDictService;

}
