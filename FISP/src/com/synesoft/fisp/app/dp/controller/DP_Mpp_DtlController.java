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

import com.synesoft.fisp.app.dp.model.DP_Mpp_DtlForm;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.DpMppCfgDtl;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

@Controller
@RequestMapping("DP_Mpp_Dtl")
public class DP_Mpp_DtlController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_DtlController.class);

	@ModelAttribute
	public DP_Mpp_DtlForm setUpForm() {
		return new DP_Mpp_DtlForm();
	}

	@RequestMapping("Init")
	public String init(Model model, @Validated DP_Mpp_DtlForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Mpp_Dtl";
		}

		DpMppCfg query_dpMppCfg = new DpMppCfg();
		query_dpMppCfg.setMppId(form.getDtl_MppId());
		DpMppCfg result_DpMppCfg = dp_Mpp_Service
				.queryDpMppCfgByMppId(query_dpMppCfg);
		if (null == result_DpMppCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0005"));
			return "dp/DP_Mpp_Dtl";
		}
		
		OrgInf query_orgInf = new OrgInf();
		query_orgInf.setOrgid(result_DpMppCfg.getBranchId());
		OrgInf result_OrgInf = orgInfMaintenanceService.transQueryOrgInf(query_orgInf);
		model.addAttribute("orgName",result_OrgInf.getOrgname());
		DpTable query_dpTable = new DpTable();
		query_dpTable.setTableName(result_DpMppCfg.getDestTable());
		DpMppCfgDtl query_DpMppCfgDtl = new DpMppCfgDtl();
		query_DpMppCfgDtl.setMppId(query_dpMppCfg.getMppId());
		List<DpTableDtlVO> dpTableDtlVOs = dp_Mpp_Service
				.queryDpTableDtlsByTableName(query_dpTable);
		List<DpMppCfgDtl> dpMppCfgDtls = dp_Mpp_Service
				.queryDpMppCfgDtlsByMppId(query_DpMppCfgDtl);

		for (DpTableDtlVO dpTableDtlVO : dpTableDtlVOs) {
			for(DpMppCfgDtl dpMppCfgDtl : dpMppCfgDtls){
				if(dpMppCfgDtl.getDestColName().equals(dpTableDtlVO.getDestColName())){
					dpTableDtlVO.setColFormula(dpMppCfgDtl.getColFormula());
					dpTableDtlVO.setComments(dpMppCfgDtl.getComments());
					dpTableDtlVO.setUkFlag(dpMppCfgDtl.getUkFlag());
					dpTableDtlVO.setDupProcType(dpMppCfgDtl.getDupProcType());
				}
			}
		}
		
		form.setDpMppCfg(result_DpMppCfg);
		form.setDpTableDtlVOs(dpTableDtlVOs);

		return "dp/DP_Mpp_Dtl";
	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	private OrgInfMaintenanceService orgInfMaintenanceService;
	
	
}
