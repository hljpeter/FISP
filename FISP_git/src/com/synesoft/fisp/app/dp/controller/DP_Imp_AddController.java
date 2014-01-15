package com.synesoft.fisp.app.dp.controller;


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
import com.synesoft.fisp.app.dp.model.DpImpAddForm;
import com.synesoft.fisp.app.dp.model.DpImpAddForm.DpImpAddFormAdd;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.service.dp.DpImpCfgService;

/**
 * 数据导入映射-新增
 * @date 2013-11-13
 * @author yyw
 * 
 */
@Controller
@RequestMapping(value = "DP_Imp_Add")
public class DP_Imp_AddController {

	private static final LogUtil log = new LogUtil(DP_Imp_AddController.class);

	@Autowired
	private DpImpCfgService dpImpCfgService;

	@ModelAttribute
	public DpImpAddForm setForm() {
		return new DpImpAddForm();
	}
	@RequestMapping("Init")
	public String init() {
		log.info("init...");
		return "dp/Dp_Imp_Add";
	}

	@RequestMapping("SubmitCfg")
	public String submitcfg(@Validated({DpImpAddFormAdd.class}) DpImpAddForm form, 
			BindingResult result, Model model) {
		log.info("start submitcfg ...");

		// validate parameter in DpImpAddForm
		if (result.hasErrors()) {
			log.error("[e.w.dp.imp] The required fields cannot be empty");
			return "dp/Dp_Imp_Add";
		}

		// getting parameters from DpImpAddForm 
		DpImpCfg dpImpCfg = form.getDpImpCfg();
		
		try {
			log.info("adding record");
			dpImpCfgService.transAdd(dpImpCfg);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to add record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Imp_Add";
		}

		log.info("[i.dp.imp.0012] adding record success. result[num=1]");
		model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.dp.imp.0012")));

		return "dp/Dp_Imp_Add";
	}
	
}
