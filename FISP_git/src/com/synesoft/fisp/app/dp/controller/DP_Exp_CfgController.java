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
import com.synesoft.fisp.app.dp.model.DP_Exp_CfgForm;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExpCfgDtl;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpFile;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;
import com.synesoft.fisp.domain.model.vo.DpFileDtlVO;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Exp_Service;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Exp_Cfg")
public class DP_Exp_CfgController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DP_Exp_CfgController.class);

	@ModelAttribute
	public DP_Exp_CfgForm setUpForm() {
		return new DP_Exp_CfgForm();
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("Init")
	public String init(Model model, @Validated DP_Exp_CfgForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Exp_Cfg";
		}

		DpExpCfg query_DpExpCfg = new DpExpCfg();
		query_DpExpCfg.setExpId(form.getCfg_ExpId());
		DpExpCfg result_DpExpCfg = dp_Exp_Service.queryDpExpCfgByExpId(query_DpExpCfg);
		if (null == result_DpExpCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0003"));
			return "dp/DP_Exp_Cfg";
		}
		DpFile query_DpFile = new DpFile();
		query_DpFile.setFileName(result_DpExpCfg.getFileName());
		DpExpCfgDtl query_DpExpCfgDtl = new DpExpCfgDtl();
		query_DpExpCfgDtl.setExpId(form.getCfg_ExpId());
		List<DpFileDtlVO> dpFileDtlVOs = dp_Exp_Service.queryDpFileDtlsByFileName(query_DpFile);
		List<DpExpCfgDtl> dpExpCfgDtls = dp_Exp_Service.queryDpExpCfgDtlsByExpId(query_DpExpCfgDtl);

		for (DpFileDtlVO dpFileDtlVO : dpFileDtlVOs) {
			for (DpExpCfgDtl dpExpCfgDtl : dpExpCfgDtls) {
				if (dpExpCfgDtl.getFieldName().equals(
						dpFileDtlVO.getFieldName())) {
					dpFileDtlVO.setFieldFormula_key(dpExpCfgDtl.getFieldFormula());
					dpFileDtlVO.setComments(dpExpCfgDtl.getComments());
					DpExprParaval query_DpExprParaval = new DpExprParaval();
					query_DpExprParaval.setSeqNo(dpExpCfgDtl.getFieldFormula());

					DpExprParaval dpExprParaval = dp_Mpp_Service
							.queryDpExprParavalBySeqNo(query_DpExprParaval);

					if (null != dpExprParaval
							&& CommonConst.paramType_str.equals(dpExprParaval.getParamType())) {
						dpFileDtlVO.setFieldFormula("1");
					}
					// set dpExprParaval value to form.getDpMppCfg.getprocvalue
					Map map = CommonUtil.analyticalExpression(dpExpCfgDtl.getFieldFormula());
					if(null!=map && !map.isEmpty()){
						dpFileDtlVO.setFieldFormula(((DpExprParavalVO) map.get(CommonUtil.expr_method)).getAnalyValue());
					}
				}
			}
		}

		form.setDpExpCfg(result_DpExpCfg);
		form.setDpFileDtlVOs(dpFileDtlVOs);
		
		return "dp/DP_Exp_Cfg";
	}
	
	@RequestMapping("SubmitCfgDtl")
	public String submitCfgDtl(Model model, DP_Exp_CfgForm form) {
		logger.info("SubmitCfgDtl...");

		List<DpFileDtlVO> dpFileDtlVOs = form.getDpFileDtlVOs();
		List<DpExpCfgDtl> dpExpCfgDtls = new ArrayList<DpExpCfgDtl>();
		
		DpExpCfgDtl del_DpExpCfgDtl = new DpExpCfgDtl();
		del_DpExpCfgDtl.setExpId(form.getCfg_ExpId());
		dp_Exp_Service.deleteDpExpCfgDtlByExpId(del_DpExpCfgDtl);

		List<String> strings = new ArrayList<String>();
		for (DpFileDtlVO dpFileDtlVO : dpFileDtlVOs) {
			if (null != dpFileDtlVO.getFieldFormula()
					&& !dpFileDtlVO.getFieldFormula().trim().equals("")) {
				DpExpCfgDtl dpExpCfgDtl_tmp = new DpExpCfgDtl();
				dpExpCfgDtl_tmp.setExpId(form.getCfg_ExpId());
				dpExpCfgDtl_tmp.setExpDtlId(numberService.getSysIDSequence(32));
				dpExpCfgDtl_tmp.setFieldName(dpFileDtlVO.getFieldName());
				dpExpCfgDtl_tmp.setFieldFormula(dpFileDtlVO.getFieldFormula());
				dpExpCfgDtl_tmp.setSeqNo(dpFileDtlVO.getSeqNo());
				dpExpCfgDtl_tmp.setComments(dpFileDtlVO.getComments());
				
				strings.add(dpFileDtlVO.getFieldFormula());
				if ("1".equals(dpFileDtlVO.getFieldFormula_flag())) {
					
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						insert_DpExprParaval.setSeqNo(numberService
								.getSysIDSequence(32));
						insert_DpExprParaval.setBranchId(form.getDpExpCfg().getBranchId().trim());
						insert_DpExprParaval.setCreateTime(DateUtil
								.getNow("yyyyMMddHHmmss"));
						UserInf userInfo = ContextConst.getCurrentUser();
						insert_DpExprParaval
								.setCreateUser(userInfo.getUserid());
						insert_DpExprParaval.setDpCfgId(form.getDpExpCfg()
								.getExpId());
						insert_DpExprParaval.setMapType("3");
						insert_DpExprParaval.setParamType(CommonConst.paramType_str);
						insert_DpExprParaval.setParamValue(dpFileDtlVO
								.getFieldFormula());
						insert_DpExprParaval.setUseFlag("0");
						dp_Mpp_Service
								.insertDpExprParaval(insert_DpExprParaval);

						dpFileDtlVO.setFieldFormula(insert_DpExprParaval
								.getSeqNo());
						dpExpCfgDtl_tmp.setFieldFormula(dpFileDtlVO.getFieldFormula());
				} else {
					dpExpCfgDtl_tmp.setFieldFormula(dpFileDtlVO.getFieldFormula_key());
					dpFileDtlVO.setFieldFormula(dpFileDtlVO.getFieldFormula_key());
				}
				dpExpCfgDtls.add(dpExpCfgDtl_tmp);
			}else{
				strings.add(null);
			}
		}

		int i = dp_Exp_Service.insertDpExpCfgDtl(dpExpCfgDtls);

		if (i == 1) {
			model.addAttribute(ResultMessages.success().add("i.dp.exp.0001"));
			DpExprTmp dpExprTmp = new DpExprTmp();
			UserInf userInfo = ContextConst.getCurrentUser();
			dpExprTmp.setUserId(userInfo.getUserid());
			dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);
			for(int j =0;j<dpFileDtlVOs.size();j++){
				DpFileDtlVO dpFileDtlVO = dpFileDtlVOs.get(j);
				dpFileDtlVO.setFieldFormula_key(dpFileDtlVO.getFieldFormula());
				dpFileDtlVO.setFieldFormula(strings.get(j));
			}
			
			return "dp/DP_Exp_Cfg";
		} else {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0006"));
			return "dp/DP_Exp_Cfg";
		}

	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	private DP_Exp_Service dp_Exp_Service;

	@Resource
	protected NumberService numberService;

}
