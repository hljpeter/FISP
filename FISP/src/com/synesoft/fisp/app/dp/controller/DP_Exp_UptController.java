package com.synesoft.fisp.app.dp.controller;

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
import com.synesoft.fisp.app.dp.model.DP_Exp_UptForm;
import com.synesoft.fisp.app.dp.model.DP_Exp_UptForm.DP_Exp_Upt_Init;
import com.synesoft.fisp.app.dp.model.DP_Exp_UptForm.DP_Exp_Upt_Submit;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Exp_Service;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Exp_Upt")
public class DP_Exp_UptController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Exp_UptController.class);

	@ModelAttribute
	public DP_Exp_UptForm setUpForm() {
		return new DP_Exp_UptForm();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("Init")
	public String init(Model model,
			@Validated({ DP_Exp_Upt_Init.class }) DP_Exp_UptForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Exp_Upt";
		}

		DpExpCfg query_DpExpCfg = new DpExpCfg();
		query_DpExpCfg.setExpId(form.getMod_ExpId());
		DpExpCfg result_DpExpCfg = dp_Exp_Service
				.queryDpExpCfgByExpId(query_DpExpCfg);
		if (null == result_DpExpCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0003"));
			return "dp/DP_Exp_Upt";
		}
		
		DpExprParaval query_DpExprParaval = new DpExprParaval();
		query_DpExprParaval.setSeqNo(result_DpExpCfg.getTableFilter());

		DpExprParaval dpExprParaval = dp_Mpp_Service
				.queryDpExprParavalBySeqNo(query_DpExprParaval);

		if (null != dpExprParaval && CommonConst.paramType_str.equals(dpExprParaval.getParamType())) {
			form.setCustomize_flag("1");
		}
		// set dpExprParaval value to form.getDpMppCfg.getprocvalue
		Map map = CommonUtil.analyticalExpression(result_DpExpCfg.getTableFilter());
		if(null!=map && !map.isEmpty()){
			result_DpExpCfg.setTableFilter_key(result_DpExpCfg.getTableFilter());
			result_DpExpCfg.setTableFilter(((DpExprParavalVO) map.get(CommonUtil.expr_method)).getAnalyValue());
		}
		
		form.setDpExpCfg(result_DpExpCfg);
		
		return "dp/DP_Exp_Upt";
	}

	@RequestMapping("SubmitCfg")
	public String Upt(Model model,
			@Validated({ DP_Exp_Upt_Submit.class }) DP_Exp_UptForm form,
			BindingResult result) {

		logger.info("SubmitCfg ...");
		if (result.hasErrors()) {
			return "dp/DP_Exp_Upt";
		}

		if (!form.getDpExpCfg().getFileType().equals("xml")) {
			if (null == form.getDpExpCfg().getTableName()
					|| "".equals(form.getDpExpCfg().getTableName())) {
				model.addAttribute(ResultMessages.error().add("e.ifc.1008"));
				return "dp/DP_Exp_Upt";
			}
		}
		if (!form.getDpExpCfg().getFileType().equals("xml")) {
			if (null == form.getDpExpCfg().getTableFilter()
					|| "".equals(form.getDpExpCfg().getTableFilter())) {
				model.addAttribute(ResultMessages.error().add("e.dp.valid.003"));
				return "dp/DP_Exp_Upt";
			}
		}

		// 检查是否有业务主键记录存在
		DpExpCfg check_DpExpCfg = new DpExpCfg();
		check_DpExpCfg.setBranchId(form.getDpExpCfg().getBranchId().trim());
		check_DpExpCfg.setBatchNo(form.getDpExpCfg().getBatchNo());
		check_DpExpCfg.setProjId(form.getDpExpCfg().getProjId().trim());
		check_DpExpCfg.setSeqNo(form.getDpExpCfg().getSeqNo());
		check_DpExpCfg.setExpId(form.getDpExpCfg().getExpId().trim());

		int count_BizKeys = dp_Exp_Service
				.queryDpExpCfgCountByBizKeys(check_DpExpCfg);
		if (count_BizKeys > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0001"));
			return "dp/DP_Exp_Upt";
		}

		DpExpCfg check_DpExpCfg1 = new DpExpCfg();
		check_DpExpCfg1.setBranchId(form.getDpExpCfg().getBranchId());
		check_DpExpCfg1.setBatchNo(form.getDpExpCfg().getBatchNo());
		check_DpExpCfg1.setProjId(form.getDpExpCfg().getProjId());
		check_DpExpCfg1.setFileId(form.getDpExpCfg().getFileId());
		check_DpExpCfg1.setTableName(form.getDpExpCfg().getTableName());
		check_DpExpCfg1.setExpId(form.getDpExpCfg().getExpId());

		int count_BizInfo = dp_Exp_Service
				.queryDpExpCfgCountByBizInfo(check_DpExpCfg1);
		if (count_BizInfo > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0002"));
			return "dp/DP_Exp_Upt";
		}

		DpExpCfg update_DpExpCfg = form.getDpExpCfg();
		update_DpExpCfg.setBranchId(update_DpExpCfg.getBranchId().trim());
		UserInf userInfo = ContextConst.getCurrentUser();
		if (null == update_DpExpCfg.getTableName()) {
			update_DpExpCfg.setTableName("");
		}
		if (null == update_DpExpCfg.getTableFilter()) {
			update_DpExpCfg.setTableFilter("");
		}
		update_DpExpCfg.setRsv1(update_DpExpCfg.getUpdateUser());
		update_DpExpCfg.setRsv2(update_DpExpCfg.getUpdateTime());
		update_DpExpCfg.setUpdateUser(userInfo.getUserid());
		update_DpExpCfg.setUpdateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		
		String tableFilter = update_DpExpCfg.getTableFilter();
		if ("1".equals(form.getCustomize_flag())) {
			DpExprParaval insert_DpExprParaval = new DpExprParaval();
			insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
			insert_DpExprParaval.setBranchId(form.getDpExpCfg().getBranchId()
					.trim());
			insert_DpExprParaval.setCreateTime(DateUtil
					.getNow("yyyyMMddHHmmss"));
			insert_DpExprParaval.setCreateUser(userInfo.getUserid());
			insert_DpExprParaval.setDpCfgId(form.getDpExpCfg().getExpId());
			insert_DpExprParaval.setMapType("3");
			insert_DpExprParaval.setParamType(CommonConst.paramType_str);
			insert_DpExprParaval.setParamValue(form.getDpExpCfg().getTableFilter());
			insert_DpExprParaval.setUseFlag("0");
			dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);

			update_DpExpCfg.setTableFilter(insert_DpExprParaval.getSeqNo());
		} else {
			update_DpExpCfg.setTableFilter(form.getDpExpCfg().getTableFilter_key());
		}
		
		int count_update = dp_Exp_Service.updateDpExpCfgByExpId(update_DpExpCfg);
		if (count_update < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			return "dp/DP_Exp_Upt";
		}
		
		DpExprTmp dpExprTmp = new DpExprTmp();
		dpExprTmp.setUserId(userInfo.getUserid());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);
		
		form.getDpExpCfg().setTableFilter_key(update_DpExpCfg.getTableFilter());
		form.getDpExpCfg().setTableFilter(tableFilter);

		model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		return "dp/DP_Exp_Upt";
	}

	
	@Resource
	private DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	private DP_Exp_Service dp_Exp_Service;
	
	@Resource
	protected NumberService numberService;

}
