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

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.DP_Exp_AddForm;
import com.synesoft.fisp.app.dp.model.DP_Exp_AddForm.DP_Exp_Add;
import com.synesoft.fisp.domain.model.DpExpCfg;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Exp_Service;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("DP_Exp_Add")
public class DP_Exp_AddController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Exp_AddController.class);

	@ModelAttribute
	public DP_Exp_AddForm setUpForm() {
		return new DP_Exp_AddForm();
	}

	@RequestMapping("Init")
	public String init(Model model, DP_Exp_AddForm form) {
		return "dp/DP_Exp_Add";
	}

	@RequestMapping("SubmitCfg")
	public String Add(Model model,
			@Validated({ DP_Exp_Add.class }) DP_Exp_AddForm form,
			BindingResult result) {
		logger.info("SubmitCfg ...");
		if (result.hasErrors()) {
			return "dp/DP_Exp_Add";
		}

		if (!form.getDpExpCfg().getFileType().equals("xml")) {
			if (null == form.getDpExpCfg().getTableName()
					|| "".equals(form.getDpExpCfg().getTableName())) {
				model.addAttribute(ResultMessages.error().add("e.ifc.1008"));
				return "dp/DP_Exp_Add";
			}
		}
		if (!form.getDpExpCfg().getFileType().equals("xml")) {
			if (null == form.getDpExpCfg().getTableFilter()
					|| "".equals(form.getDpExpCfg().getTableFilter())) {
				model.addAttribute(ResultMessages.error().add("e.dp.valid.003"));
				return "dp/DP_Exp_Add";
			}
		}

		// 检查是否有业务主键记录存在
		DpExpCfg check_DpExpCfg = new DpExpCfg();
		check_DpExpCfg.setBranchId(form.getDpExpCfg().getBranchId());
		check_DpExpCfg.setBatchNo(form.getDpExpCfg().getBatchNo());
		check_DpExpCfg.setProjId(form.getDpExpCfg().getProjId());
		check_DpExpCfg.setSeqNo(form.getDpExpCfg().getSeqNo());

		int count_BizKeys = dp_Exp_Service
				.queryDpExpCfgCountByBizKeys(check_DpExpCfg);
		if (count_BizKeys > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0001"));
			return "dp/DP_Exp_Add";
		}

		DpExpCfg check_DpExpCfg1 = new DpExpCfg();
		check_DpExpCfg1.setBranchId(form.getDpExpCfg().getBranchId());
		check_DpExpCfg1.setBatchNo(form.getDpExpCfg().getBatchNo());
		check_DpExpCfg1.setProjId(form.getDpExpCfg().getProjId());
		check_DpExpCfg1.setFileId(form.getDpExpCfg().getFileId());
		check_DpExpCfg1.setTableName(form.getDpExpCfg().getTableName());

		int count_BizInfo = dp_Exp_Service
				.queryDpExpCfgCountByBizInfo(check_DpExpCfg1);
		if (count_BizInfo > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.exp.0002"));
			return "dp/DP_Exp_Add";
		}

		DpExpCfg insert_DpExpCfg = form.getDpExpCfg();
		insert_DpExpCfg.setExpId(numberService.getSysIDSequence(32));
		UserInf userInfo = ContextConst.getCurrentUser();
		insert_DpExpCfg.setCreateUser(userInfo.getUserid());
		insert_DpExpCfg.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpExpCfg.setUpdateUser(userInfo.getUserid());
		insert_DpExpCfg.setUpdateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpExpCfg.setBranchId(insert_DpExpCfg.getBranchId().trim());

		String tableFilter = insert_DpExpCfg.getTableFilter();
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

			insert_DpExpCfg.setTableFilter(insert_DpExprParaval.getSeqNo());
		} else {
			insert_DpExpCfg.setTableFilter(form.getDpExpCfg().getTableFilter_key());
		}

		int count_insert = dp_Exp_Service.insertDpExpCfg(insert_DpExpCfg);
		if (count_insert < 1) {
			model.addAttribute(ResultMessages.error().add("e.al.2002"));
			return "dp/DP_Exp_Add";
		}
		
		form.getDpExpCfg().setTableFilter_key(insert_DpExpCfg.getTableFilter());
		form.getDpExpCfg().setTableFilter(tableFilter);

		DpExprTmp dpExprTmp = new DpExprTmp();
		dpExprTmp.setUserId(userInfo.getUserid());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);
		
		List<DpExprParaval> dpExprParavals = CommonUtil.getAllDepBySeqNo(form.getDpExpCfg().getTableFilter_key());
		for(DpExprParaval dpExprParaval : dpExprParavals){
			dpExprParaval.setDpCfgId(insert_DpExpCfg.getExpId());
			dpExprParaval.setBranchId(insert_DpExpCfg.getBranchId());
			dp_Mpp_Service.updateDpExprParaval(dpExprParaval);
		}

		model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		return "dp/DP_Exp_Add";
	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

	@Resource
	private DP_Exp_Service dp_Exp_Service;

	@Resource
	protected NumberService numberService;

}
