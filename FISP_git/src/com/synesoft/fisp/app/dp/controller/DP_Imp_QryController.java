package com.synesoft.fisp.app.dp.controller;


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
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpImpQryForm;
import com.synesoft.fisp.app.dp.model.DpImpQryForm.DpImpQryFormDel;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.service.dp.DpImpCfgService;

/**
 * 数据导入映射-查询
 * @date 2013-11-11
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_Imp_Qry")
public class DP_Imp_QryController {

	private static final LogUtil log = new LogUtil(DP_Imp_QryController.class);

	@Autowired
	private DpImpCfgService dpImpCfgService;

	@ModelAttribute
	public DpImpQryForm setForm() {
		return new DpImpQryForm();
	}
	@RequestMapping("Init")
	public String init() {
		log.info("init...");
		return "dp/Dp_Imp_Qry";
	}

	@RequestMapping("Qry")
	public String search(DpImpQryForm form, @PageableDefaults Pageable pageable, Model model) {
		log.info("start search ...");
		
		// getting parameters from DpImpQryForm 
		DpImpCfg dpImpCfg = form.getDpImpCfg();
		
		// search records, display result on the page
		try {
			Page<DpImpCfg> page= dpImpCfgService.transQueryDpImpCfgPage(pageable, dpImpCfg);
			model.addAttribute("page", page);
		} catch (BusinessException e){
			log.info("[w.dp.0001] No data");
			model.addAttribute("infomsg", e.getResultMessages());
			return "dp/Dp_Imp_Qry";
		}

		log.info("searching information success, dispaly result on the page.");
		return "dp/Dp_Imp_Qry";
	}

	@RequestMapping("Del")
	public String del(@Validated({DpImpQryFormDel.class}) DpImpQryForm form, 
			BindingResult result, Model model) {
		log.info("start del ...");
		
		// validate parameter in DpImpQryForm
		if (result.hasErrors()) {
			log.error("[e.dp.imp.0001] ImpId cannot be empty");
			return "dp/Dp_Imp_Qry";
		}

		try {
			dpImpCfgService.transDel(form.getDpImpCfg());
		} catch (BusinessException e) {
			log.error("[e.dp.imp] Failed to delete the configuration information, forward to error page. ErrorMsg: " 
					+ e.getResultMessages());
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/DP_Imp_Qry/Qry";
		}
		
		log.info("[i.dp.imp.0003] Deleting the configuration information success. param[impid=" 
				+ form.getDpImpCfg().getImpId() + "]");
		model.addAttribute("successmsg", ResultMessages.success().add(
						ResultMessage.fromCode("i.dp.imp.0003", form.getDpImpCfg().getImpId())));
		
		return "forward:/DP_Imp_Qry/Qry";
	}
	
}
