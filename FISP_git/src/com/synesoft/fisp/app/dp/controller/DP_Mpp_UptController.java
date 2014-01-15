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
import com.synesoft.fisp.app.dp.model.DP_Mpp_UptForm;
import com.synesoft.fisp.app.dp.model.DP_Mpp_UptForm.DP_Mpp_Upt_Init;
import com.synesoft.fisp.app.dp.model.DP_Mpp_UptForm.DP_Mpp_Upt_Submit;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprParavalVO;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Mpp_Upt")
public class DP_Mpp_UptController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_UptController.class);

	@ModelAttribute
	public DP_Mpp_UptForm setUpForm() {
		return new DP_Mpp_UptForm();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("Init")
	public String init(Model model,
			@Validated({ DP_Mpp_Upt_Init.class }) DP_Mpp_UptForm form,
			BindingResult result) {
		logger.info("init...");
		if (result.hasErrors()) {
			return "dp/DP_Mpp_Upt";
		}

		DpMppCfg query_dpMppCfg = new DpMppCfg();
		query_dpMppCfg.setMppId(form.getMod_MppId());
		DpMppCfg result_DpMppCfg = dp_Mpp_Service
				.queryDpMppCfgByMppId(query_dpMppCfg);
		if (null == result_DpMppCfg) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0005"));
			return "dp/DP_Mpp_Upt";
		}

		DpExprParaval query_DpExprParaval = new DpExprParaval();
		query_DpExprParaval.setSeqNo(result_DpMppCfg.getProcCfg());

		DpExprParaval dpExprParaval = dp_Mpp_Service
				.queryDpExprParavalBySeqNo(query_DpExprParaval);

		if (null != dpExprParaval && CommonConst.paramType_str.equals(dpExprParaval.getParamType())) {
			form.setCustomize_flag("1");
		}
		// set dpExprParaval value to form.getDpMppCfg.getprocvalue
		Map map = CommonUtil.analyticalExpression(result_DpMppCfg.getProcCfg());
		if(null != map && !map.isEmpty()){
			result_DpMppCfg.setProcCfg_key(result_DpMppCfg.getProcCfg());
			result_DpMppCfg.setProcCfg(((DpExprParavalVO) map.get(CommonUtil.expr_method)).getAnalyValue());
		}
		form.setDpMppCfg(result_DpMppCfg);
		return "dp/DP_Mpp_Upt";
	}

	@RequestMapping("SubmitCfg")
	public String Upt(Model model,
			@Validated({ DP_Mpp_Upt_Submit.class }) DP_Mpp_UptForm form,
			BindingResult result) {

		logger.info("SubmitCfg ...");
		if (result.hasErrors()) {
			return "dp/DP_Mpp_Upt";
		}

		if (form.getDpMppCfg().getProcType().equals("3")) {
			if (null == form.getDpMppCfg().getDestTable()
					|| "".equals(form.getDpMppCfg().getDestTable())) {
				model.addAttribute(ResultMessages.error().add("e.de.2010"));
				return "dp/DP_Mpp_Upt";
			}
		}
		if (form.getDpMppCfg().getProcType().equals("3")) {
			if (null == form.getDpMppCfg().getSrcTable()
					|| "".equals(form.getDpMppCfg().getSrcTable())) {
				model.addAttribute(ResultMessages.error().add("e.de.2011"));
				return "dp/DP_Mpp_Upt";
			}
		}

		// 检查是否有业务主键记录存在
		DpMppCfg check_dpMppCfg = new DpMppCfg();
		check_dpMppCfg.setBranchId(form.getDpMppCfg().getBranchId());
		check_dpMppCfg.setBatchNo(form.getDpMppCfg().getBatchNo());
		check_dpMppCfg.setProjId(form.getDpMppCfg().getProjId());
		check_dpMppCfg.setSeqNo(form.getDpMppCfg().getSeqNo());
		check_dpMppCfg.setMppId(form.getDpMppCfg().getMppId());

		int count_BizKeys = dp_Mpp_Service
				.queryDpMppCfgCountByBizKeys(check_dpMppCfg);
		if (count_BizKeys > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0003"));
			return "dp/DP_Mpp_Upt";
		}

		if (form.getDpMppCfg().getProcType().equals("3")) {
			DpMppCfg check_dpMppCfg1 = new DpMppCfg();
			check_dpMppCfg1.setBranchId(form.getDpMppCfg().getBranchId());
			check_dpMppCfg1.setBatchNo(form.getDpMppCfg().getBatchNo());
			check_dpMppCfg1.setProjId(form.getDpMppCfg().getProjId());
			check_dpMppCfg1.setDestTable(form.getDpMppCfg().getDestTable());
			check_dpMppCfg1.setSrcTable(form.getDpMppCfg().getSrcTable());
			check_dpMppCfg1.setMppId(form.getDpMppCfg().getMppId());

			int count_BizInfo = dp_Mpp_Service
					.queryDpMppCfgCountByBizInfo(check_dpMppCfg1);
			if (count_BizInfo > 0) {
				model.addAttribute(ResultMessages.error().add("e.dp.mpp.0004"));
				return "dp/DP_Mpp_Upt";
			}
		}

		DpMppCfg update_DpMppCfg = form.getDpMppCfg();
		update_DpMppCfg.setBranchId(update_DpMppCfg.getBranchId().trim());
		UserInf userInfo = ContextConst.getCurrentUser();
		if (null == update_DpMppCfg.getSrcTable()) {
			update_DpMppCfg.setSrcTable("");
		}
		if (null == update_DpMppCfg.getDestTable()) {
			update_DpMppCfg.setDestTable("");
		}
		update_DpMppCfg.setRsv1(update_DpMppCfg.getUpdateUser());
		update_DpMppCfg.setRsv2(update_DpMppCfg.getUpdateTime());
		update_DpMppCfg.setUpdateUser(userInfo.getUserid());
		update_DpMppCfg.setUpdateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		String procCfg = update_DpMppCfg.getProcCfg();
		if ("3".equals(form.getDpMppCfg().getProcType())) {

			if ("1".equals(form.getCustomize_flag())) {

				DpExprParaval insert_DpExprParaval = new DpExprParaval();
				insert_DpExprParaval.setSeqNo(numberService
						.getSysIDSequence(32));
				insert_DpExprParaval.setBranchId(form.getDpMppCfg()
						.getBranchId().trim());
				insert_DpExprParaval.setCreateTime(DateUtil
						.getNow("yyyyMMddHHmmss"));
				insert_DpExprParaval.setCreateUser(userInfo.getUserid());
				insert_DpExprParaval.setDpCfgId(form.getDpMppCfg().getMppId());
				insert_DpExprParaval.setMapType("2");
				insert_DpExprParaval.setParamType(CommonConst.paramType_str);
				insert_DpExprParaval
						.setParamValue(update_DpMppCfg.getProcCfg());
				insert_DpExprParaval.setUseFlag("0");
				dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);

				update_DpMppCfg.setProcCfg(insert_DpExprParaval.getSeqNo());
			} else {
				update_DpMppCfg.setProcCfg(update_DpMppCfg.getProcCfg_key());
			}
		}

		int count_update = dp_Mpp_Service
				.updateDpMppCfgByMppId(update_DpMppCfg);
		if (count_update < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			return "dp/DP_Mpp_Upt";
		}

		if ("3".equals(form.getDpMppCfg().getProcType())) {
			form.getDpMppCfg().setProcCfg_key(update_DpMppCfg.getProcCfg());
			form.getDpMppCfg().setProcCfg(procCfg);
		}

		DpExprTmp dpExprTmp = new DpExprTmp();
		dpExprTmp.setUserId(userInfo.getUserid());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);
		model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		return "dp/DP_Mpp_Upt";
	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

	@Resource
	protected NumberService numberService;

}
