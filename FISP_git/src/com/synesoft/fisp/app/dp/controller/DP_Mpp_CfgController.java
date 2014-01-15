package com.synesoft.fisp.app.dp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.DP_Mpp_CfgForm;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.DpMppCfgDtl;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;
import com.synesoft.fisp.domain.model.vo.DpTableDtlVO;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Mpp_Cfg")
public class DP_Mpp_CfgController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_CfgController.class);

	@ModelAttribute
	public DP_Mpp_CfgForm setUpForm() {
		return new DP_Mpp_CfgForm();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("Init")
	public String init(Model model, @Validated DP_Mpp_CfgForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Mpp_Cfg";
		}

		DpMppCfg query_dpMppCfg = new DpMppCfg();
		query_dpMppCfg.setMppId(form.getCdg_MppId());
		DpMppCfg result_DpMppCfg = dp_Mpp_Service
				.queryDpMppCfgByMppId(query_dpMppCfg);
		if (null == result_DpMppCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0005"));
			return "dp/DP_Mpp_Cfg";
		}
		DpTable query_dpTable = new DpTable();
		query_dpTable.setTableName(result_DpMppCfg.getDestTable());
		DpMppCfgDtl query_DpMppCfgDtl = new DpMppCfgDtl();
		query_DpMppCfgDtl.setMppId(query_dpMppCfg.getMppId());
		List<DpTableDtlVO> dpTableDtlVOs = dp_Mpp_Service
				.queryDpTableDtlsByTableName(query_dpTable);
		List<DpMppCfgDtl> dpMppCfgDtls = dp_Mpp_Service
				.queryDpMppCfgDtlsByMppId(query_DpMppCfgDtl);

		for (DpTableDtlVO dpTableDtlVO : dpTableDtlVOs) {
			for (DpMppCfgDtl dpMppCfgDtl : dpMppCfgDtls) {
				if (dpMppCfgDtl.getDestColName().equals(
						dpTableDtlVO.getDestColName())) {
					dpTableDtlVO.setColFormula_key(dpMppCfgDtl.getColFormula());
					dpTableDtlVO.setComments(dpMppCfgDtl.getComments());
					dpTableDtlVO.setUkFlag(dpMppCfgDtl.getUkFlag());
					dpTableDtlVO.setDupProcType(dpMppCfgDtl.getDupProcType());
					DpExprParaval query_DpExprParaval = new DpExprParaval();
					query_DpExprParaval.setSeqNo(dpMppCfgDtl.getColFormula());

					DpExprParaval dpExprParaval = dp_Mpp_Service
							.queryDpExprParavalBySeqNo(query_DpExprParaval);

					if (null != dpExprParaval
							&& CommonConst.paramType_str.equals(dpExprParaval.getParamType())) {
						dpTableDtlVO.setColFormula_flag("1");
					}
					// set dpExprParaval value to form.getDpMppCfg.getprocvalue
					Map map = CommonUtil.analyticalExpression(dpMppCfgDtl.getColFormula());
					if(null!=map && !map.isEmpty()){
						dpTableDtlVO.setColFormula(((DpExprParavalVO) map.get(CommonUtil.expr_method)).getAnalyValue());
					}
					
				}
			}
		}

		form.setDpMppCfg(result_DpMppCfg);
		form.setDpTableDtlVOs(dpTableDtlVOs);

		return "dp/DP_Mpp_Cfg";
	}

	@RequestMapping("SubmitCfgDtl")
	public String submitCfgDtl(Model model, DP_Mpp_CfgForm form) {
		logger.info("SubmitCfgDtl...");

		List<DpTableDtlVO> dpTableDtlVOs = form.getDpTableDtlVOs();
		List<DpMppCfgDtl> dpMppCfgDtls = new ArrayList<DpMppCfgDtl>();

		DpMppCfgDtl del_dpMppCfgDtl = new DpMppCfgDtl();
		del_dpMppCfgDtl.setMppId(form.getDpMppCfg().getMppId());
		dp_Mpp_Service.deleteDpMppCfgDtlByMppId(del_dpMppCfgDtl);

		List<String> strings = new ArrayList<String>();
		for (DpTableDtlVO dpTableDtlVO : dpTableDtlVOs) {
			if (null != dpTableDtlVO.getColFormula()
					&& !dpTableDtlVO.getColFormula().trim().equals("")) {
				DpMppCfgDtl dpMppCfgDtl_tmp = new DpMppCfgDtl();
				dpMppCfgDtl_tmp.setMppDtlId(numberService.getSysIDSequence(32));
				dpMppCfgDtl_tmp.setMppId(form.getDpMppCfg().getMppId());
				dpMppCfgDtl_tmp.setDestColName(dpTableDtlVO.getDestColName());
				dpMppCfgDtl_tmp.setUkFlag(dpTableDtlVO.getUkFlag());
				dpMppCfgDtl_tmp.setDupProcType(dpTableDtlVO.getDupProcType());
				dpMppCfgDtl_tmp.setComments(dpTableDtlVO.getComments());
				strings.add(dpTableDtlVO.getColFormula());
				if ("1".equals(dpTableDtlVO.getColFormula_flag())) {
					
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						insert_DpExprParaval.setSeqNo(numberService
								.getSysIDSequence(32));
						insert_DpExprParaval.setBranchId(form.getDpMppCfg()
								.getBranchId().trim());
						insert_DpExprParaval.setCreateTime(DateUtil
								.getNow("yyyyMMddHHmmss"));
						UserInf userInfo = ContextConst.getCurrentUser();
						insert_DpExprParaval
								.setCreateUser(userInfo.getUserid());
						insert_DpExprParaval.setDpCfgId(form.getDpMppCfg()
								.getMppId());
						insert_DpExprParaval.setMapType("2");
						insert_DpExprParaval.setParamType(CommonConst.paramType_str);
						insert_DpExprParaval.setParamValue(dpTableDtlVO
								.getColFormula());
						insert_DpExprParaval.setUseFlag("0");
						dp_Mpp_Service
								.insertDpExprParaval(insert_DpExprParaval);

						dpTableDtlVO.setColFormula(insert_DpExprParaval
								.getSeqNo());
						dpMppCfgDtl_tmp.setColFormula(dpTableDtlVO.getColFormula());
				} else {
					dpMppCfgDtl_tmp.setColFormula(dpTableDtlVO.getColFormula_key());
					dpTableDtlVO.setColFormula(dpTableDtlVO.getColFormula_key());
				}

				dpMppCfgDtls.add(dpMppCfgDtl_tmp);
			}else{
				strings.add(null);
			}
		}

		int i = dp_Mpp_Service.insertDpMppCfgDtl(dpMppCfgDtls);

		if (i == 1) {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0003"));
			DpExprTmp dpExprTmp = new DpExprTmp();
			UserInf userInfo = ContextConst.getCurrentUser();
			dpExprTmp.setUserId(userInfo.getUserid());
			dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);
			for(int j =0;j<dpTableDtlVOs.size();j++){
				DpTableDtlVO tableDtlVO = dpTableDtlVOs.get(j);
				tableDtlVO.setColFormula_key(tableDtlVO.getColFormula());
				tableDtlVO.setColFormula(strings.get(j));
			}
			return "dp/DP_Mpp_Cfg";
		} else {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0006"));
			return "dp/DP_Mpp_Cfg";
		}

	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

	@Resource
	protected NumberService numberService;

}
