package com.synesoft.fisp.app.dp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.dp.model.DP_Exp_CfgForm;
import com.synesoft.fisp.app.dp.model.DP_Exp_DtlForm;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;
import com.synesoft.fisp.domain.service.dp.DP_Exp_Service;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

@Controller
@RequestMapping("DP_Exp_Dtl")
public class DP_Exp_DtlController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DP_Exp_DtlController.class);

	@ModelAttribute
	public DP_Exp_CfgForm setUpForm() {
		return new DP_Exp_CfgForm();
	}
	
	@RequestMapping("Init")
	public String init(Model model, @Validated DP_Exp_DtlForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Exp_Dtl";
		}

		DpExpCfg query_DpExpCfg = new DpExpCfg();
		query_DpExpCfg.setExpId(form.getDtl_ExpId());
		DpExpCfg result_DpExpCfg = dp_Exp_Service.queryDpExpCfgByExpId(query_DpExpCfg);
		if (null == result_DpExpCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0003"));
			return "dp/DP_Exp_Dtl";
		}
		OrgInf query_orgInf = new OrgInf();
		query_orgInf.setOrgid(result_DpExpCfg.getBranchId());
		OrgInf result_OrgInf = orgInfMaintenanceService.transQueryOrgInf(query_orgInf);
		model.addAttribute("orgName",result_OrgInf.getOrgname());
		DpFile query_DpFile = new DpFile();
		query_DpFile.setFileName(result_DpExpCfg.getFileName());
		DpExpCfgDtl query_DpExpCfgDtl = new DpExpCfgDtl();
		query_DpExpCfgDtl.setExpId(form.getDtl_ExpId());
		List<DpFileDtlVO> dpFileDtlVOs = dp_Exp_Service.queryDpFileDtlsByFileName(query_DpFile);
		List<DpExpCfgDtl> dpExpCfgDtls = dp_Exp_Service.queryDpExpCfgDtlsByExpId(query_DpExpCfgDtl);

		for (DpFileDtlVO dpFileDtlVO : dpFileDtlVOs) {
			for (DpExpCfgDtl dpExpCfgDtl : dpExpCfgDtls) {
				if (dpExpCfgDtl.getFieldName().equals(
						dpFileDtlVO.getFieldName())) {
					dpFileDtlVO.setFieldFormula(dpExpCfgDtl.getFieldFormula());
					dpFileDtlVO.setComments(dpExpCfgDtl.getComments());
				}
			}
		}

		form.setDpExpCfg(result_DpExpCfg);
		form.setDpFileDtlVOs(dpFileDtlVOs);

		return "dp/DP_Exp_Dtl";
	}
	
	@Resource
	private DP_Exp_Service dp_Exp_Service;

	@Resource
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
