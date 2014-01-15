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
import com.synesoft.fisp.app.dp.model.DpImpUptForm;
import com.synesoft.fisp.app.dp.model.DpImpUptForm.DpImpUptFormInit;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.service.dp.DpImpCfgService;

/**
 * 数据导入映射-修改
 * @date 2013-11-13
 * @author yyw
 * 
 */
@Controller
@RequestMapping(value = "DP_Imp_Upt")
public class DP_Imp_UptController {

	private static final LogUtil log = new LogUtil(DP_Imp_UptController.class);

	@Autowired
	private DpImpCfgService dpImpCfgService;

	@ModelAttribute
	public DpImpUptForm setForm() {
		return new DpImpUptForm();
	}
	@RequestMapping("Init")
	public String init(@Validated({DpImpUptFormInit.class}) DpImpUptForm form, 
			BindingResult result, Model model) {
		log.info("init...");

		// validate parameter in DpImpUptForm
		if (result.hasErrors()) {
			log.error("[e.w.dp.imp] The required fields cannot be empty");
			return "dp/Dp_Imp_Upt";
		}

		try {
			DpImpCfg dpImpCfg = dpImpCfgService.transQueryDpImpCfg(form.getDpImpCfg().getImpId());
			
			form.setDpImpCfg(dpImpCfg);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to init record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Imp_Upt";
		}

		log.info("Init the configuration information success, display it on the page");
		return "dp/Dp_Imp_Upt";
	}

	@RequestMapping("SubmitCfg")
	public String submitcfg(@Validated({DpImpUptFormInit.class}) DpImpUptForm form, 
			BindingResult result, Model model) {
		log.info("start submitcfg ...");

		// validate parameter in DpImpUptForm
		if (result.hasErrors()) {
			log.error("[e.w.dp.imp] The required fields cannot be empty");
			return "dp/Dp_Imp_Upt";
		}

		// getting parameters from DpImpUptForm 
		DpImpCfg dpImpCfg = form.getDpImpCfg();
		
		try {
			log.info("updating record");
			dpImpCfgService.transUpt(dpImpCfg);
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to update record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Imp_Upt";
		}

		log.info("[i.dp.imp.0022] updating record success. result[num=1]");
		model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.dp.imp.0022")));

		return "dp/Dp_Imp_Upt";
	}
	
}
