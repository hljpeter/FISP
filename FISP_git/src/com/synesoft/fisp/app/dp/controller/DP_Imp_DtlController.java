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

import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.common.utils.LogUtil;
import com.synesoft.fisp.app.dp.model.DpImpDtlForm;
import com.synesoft.fisp.app.dp.model.DpImpDtlForm.DpImpDtlFormInit;
import com.synesoft.fisp.domain.model.DpFileDtl;
import com.synesoft.fisp.domain.model.DpImpCfg;
import com.synesoft.fisp.domain.model.DpImpCfgDtl;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.service.dp.DpImpCfgService;

/**
 * 数据导入映射-详细
 * @date 2013-11-13
 * @author yyw
 * 
 */

@Controller
@RequestMapping(value = "DP_Imp_Dtl")
public class DP_Imp_DtlController {

	private static final LogUtil log = new LogUtil(DP_Imp_DtlController.class);

	@Autowired
	private DpImpCfgService dpImpCfgService;

	@ModelAttribute
	public DpImpDtlForm setForm() {
		return new DpImpDtlForm();
	}
	@RequestMapping("Init")
	public String init(@Validated({DpImpDtlFormInit.class}) DpImpDtlForm form, 
			BindingResult result, Model model) {
		log.info("init...");

		// validate parameter in DpImpDtlForm
		if (result.hasErrors()) {
			log.error("[e.dp.imp.0001] ImpId cannot be blank");
			return "dp/Dp_Imp_Qry";
		}

		try {
			log.info("searching the detail infomation");
			List<Object> list = dpImpCfgService.transQueryDetail(form.getDpImpCfg().getImpId());
			
			// getting object
			DpImpCfg dpImpCfg = (DpImpCfg) list.get(0);
			dpImpCfg.setCreateTime(DateUtil.getFormatdateAddSplit(dpImpCfg.getCreateTime()));
			dpImpCfg.setUpdateTime(DateUtil.getFormatdateAddSplit(dpImpCfg.getUpdateTime()));
			@SuppressWarnings("unchecked")
			List<DpImpCfgDtl> cList = (List<DpImpCfgDtl>) list.get(1);
			@SuppressWarnings("unchecked")
			List<DpFileDtl> fList = (List<DpFileDtl>) list.get(2);
			@SuppressWarnings("unchecked")
			List<DpTableDtl> tList = (List<DpTableDtl>) list.get(3);

			form.setDpImpCfg(dpImpCfg);
			form.setcList(cList);
			form.setfList(fList);
			form.settList(tList);
			
		} catch (BusinessException e) {
			log.error("Failed to delete record, forward to error page");
			model.addAttribute("errmsg", e.getResultMessages());
			return "dp/Dp_Imp_Dtl";
		}
		
		log.info("searching the detail information success, display it on the page");
		return "dp/Dp_Imp_Dtl";
	}
	

}
