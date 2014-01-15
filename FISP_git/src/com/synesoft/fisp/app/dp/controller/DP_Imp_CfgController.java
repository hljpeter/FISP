package com.synesoft.fisp.app.dp.controller;


import java.util.List;

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

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpImpCfgForm;
import com.synesoft.fisp.app.dp.model.DpImpCfgForm.DpImpCfgFormInit;
import com.synesoft.fisp.app.dp.model.DpImpCfgForm.DpImpCfgFormSubmit;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.vo.DpImpCfgDtlVO;
import com.synesoft.fisp.domain.service.dp.DpImpCfgService;

/**
 * 数据导入映射-映射字段配置
 * @date 2013-11-14
 * @author yyw
 * 
 */
@Controller
@RequestMapping(value = "DP_Imp_Cfg")
public class DP_Imp_CfgController {

	private static final LogUtil log = new LogUtil(DP_Imp_CfgController.class);

	@Autowired
	private DpImpCfgService dpImpCfgService;

	@ModelAttribute
	public DpImpCfgForm setForm() {
		return new DpImpCfgForm();
	}

	@RequestMapping("Init")
	public String init(@Validated({DpImpCfgFormInit.class}) DpImpCfgForm form, 
			BindingResult result, Model model) {
		log.info("init...");

		// validate parameter in DpImpCfgForm
		if (result.hasErrors()) {
			log.error("[e.w.dp.imp] The required fields cannot be empty");
			return "dp/Dp_Imp_Cfg";
		}

		try {
			log.info("searching the detail infomation");
			
			initFormData(form);
		} catch (BusinessException e) {
			log.error("Failed to delete record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Imp_Cfg";
		}
		
		log.info("searching the detail information success, display it on the page");
		return "dp/Dp_Imp_Cfg";

	}

	@RequestMapping("SubmitCfg")
	public String submitcfg(@Validated({DpImpCfgFormSubmit.class}) DpImpCfgForm form, BindingResult result, 
			Model model) {
		log.info("start submitcfg ...");

		List<DpImpCfgDtlVO> list = form.getcList();
		
		try {
			// validate parameter in DpImpCfgForm
			if (result.hasErrors()) {
				log.error("[e.w.dp.imp] The required fields cannot be empty");
				initFormData(form);
				return "dp/Dp_Imp_Cfg";
			}
	
			// getting parameters from DpImpCfgForm 
			DpImpCfg dpImpCfg = form.getDpImpCfg();
			
			dpImpCfgService.transAddDtl(dpImpCfg.getImpId(), list);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to add record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			
			initFormData(form);
			form.setcList(list);
			
			return "dp/Dp_Imp_Cfg";
		}

		log.info("[i.dp.imp.0026] config success. result[num=" + (list == null? 0:list.size()) + "]");
		model.addAttribute("successmsg", ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.imp.0026", (list == null? 0:list.size()))));

		return "forward:/DP_Imp_Cfg/Init";
	}
	
	private DpImpCfgForm initFormData(DpImpCfgForm form) {
		try {
			List<Object> list = dpImpCfgService.transQueryDetail(form.getDpImpCfg().getImpId());
			
			// getting object
			DpImpCfg dpImpCfg = (DpImpCfg) list.get(0);
			@SuppressWarnings("unchecked")
			List<DpImpCfgDtlVO> cList = (List<DpImpCfgDtlVO>) list.get(1);
			@SuppressWarnings("unchecked")
			List<DpFileDtl> fList = (List<DpFileDtl>) list.get(2);
			@SuppressWarnings("unchecked")
			List<DpTableDtl> tList = (List<DpTableDtl>) list.get(3);
			@SuppressWarnings("unchecked")
			List<DpMapDict> mList = (List<DpMapDict>) list.get(4);
	
			form.setDpImpCfg(dpImpCfg);
			form.setcList(cList);
			form.setfList(fList);
			form.settList(tList);
			form.setmList(mList);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return form;
	}
}
