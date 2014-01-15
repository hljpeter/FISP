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
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.DP_Mpp_AddForm;
import com.synesoft.fisp.app.dp.model.DP_Mpp_AddForm.DP_Mpp_Add;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpMapDict;
import com.synesoft.fisp.domain.model.DpMppCfg;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;
import com.synesoft.fisp.domain.service.sm.OrgInfMaintenanceService;

@Controller
@RequestMapping("DP_Mpp_Add")
public class DP_Mpp_AddController {

	private static final Logger logger = LoggerFactory
			.getLogger(DP_Mpp_AddController.class);

	@ModelAttribute
	public DP_Mpp_AddForm setUpForm() {
		return new DP_Mpp_AddForm();
	}
	
	@RequestMapping("SQLEditInit")
	public String SQLEditInit(Model model,@RequestParam("srcSQL") String srcSQL){
		logger.info("SQLEditInit...");
		List<DpMapDict> dpMapDicts = dp_Mpp_Service.query0001DpMapDicts();
		model.addAttribute("list", dpMapDicts);
		String srcSQL_decryptor = CommonUtil.decryptor(srcSQL);
		model.addAttribute("srcSQL", srcSQL_decryptor);
		return "dp/DP_Mpp_SQLEdit";
	}

	@RequestMapping("Init")
	public String init(Model model) {
		logger.info("init...");
		return "dp/DP_Mpp_Add";
	}

	@RequestMapping("SubmitCfg")
	public String Add(Model model,
			@Validated({ DP_Mpp_Add.class }) DP_Mpp_AddForm form,
			BindingResult result) {
		logger.info("SubmitCfg ...");
		if (result.hasErrors()) {
			return "dp/DP_Mpp_Add";
		}

		if (form.getDpMppCfg().getProcType().equals("3")) {
			if (null == form.getDpMppCfg().getDestTable()
					|| "".equals(form.getDpMppCfg().getDestTable())) {
				model.addAttribute(ResultMessages.error().add("e.de.2010"));
				return "dp/DP_Mpp_Add";
			}
		}
		if (form.getDpMppCfg().getProcType().equals("3")) {
			if (null == form.getDpMppCfg().getSrcTable()
					|| "".equals(form.getDpMppCfg().getSrcTable())) {
				model.addAttribute(ResultMessages.error().add("e.de.2011"));
				return "dp/DP_Mpp_Add";
			}
		}

		// 检查是否有业务主键记录存在
		DpMppCfg check_dpMppCfg = new DpMppCfg();
		check_dpMppCfg.setBranchId(form.getDpMppCfg().getBranchId().trim());
		check_dpMppCfg.setBatchNo(form.getDpMppCfg().getBatchNo());
		check_dpMppCfg.setProjId(form.getDpMppCfg().getProjId());
		check_dpMppCfg.setSeqNo(form.getDpMppCfg().getSeqNo());

		int count_BizKeys = dp_Mpp_Service
				.queryDpMppCfgCountByBizKeys(check_dpMppCfg);
		if (count_BizKeys > 0) {
			model.addAttribute(ResultMessages.error().add("e.dp.mpp.0003"));
			return "dp/DP_Mpp_Add";
		}
		
		if(form.getDpMppCfg().getProcType().equals("3")){
			DpMppCfg check_dpMppCfg1 = new DpMppCfg();
			check_dpMppCfg1.setBranchId(form.getDpMppCfg().getBranchId().trim());
			check_dpMppCfg1.setBatchNo(form.getDpMppCfg().getBatchNo());
			check_dpMppCfg1.setProjId(form.getDpMppCfg().getProjId());
			check_dpMppCfg1.setDestTable(form.getDpMppCfg().getDestTable());
			check_dpMppCfg1.setSrcTable(form.getDpMppCfg().getSrcTable());

			int count_BizInfo = dp_Mpp_Service
					.queryDpMppCfgCountByBizInfo(check_dpMppCfg1);
			if (count_BizInfo > 0) {
				model.addAttribute(ResultMessages.error().add("e.dp.mpp.0004"));
				return "dp/DP_Mpp_Add";
			}
		}

		DpMppCfg insert_DpMppCfg = form.getDpMppCfg();
		insert_DpMppCfg.setMppId(numberService.getSysIDSequence(32));
		UserInf userInfo = ContextConst.getCurrentUser();
		insert_DpMppCfg.setCreateUser(userInfo.getUserid());
		insert_DpMppCfg.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpMppCfg.setUpdateUser(userInfo.getUserid());
		insert_DpMppCfg.setUpdateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpMppCfg.setBranchId(insert_DpMppCfg.getBranchId().trim());
		String procCfg = insert_DpMppCfg.getProcCfg();
		if("3".equals(form.getDpMppCfg().getProcType())){
			if("1".equals(form.getCustomize_flag())){
				DpExprParaval insert_DpExprParaval = new DpExprParaval();
				insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
				insert_DpExprParaval.setBranchId(form.getDpMppCfg().getBranchId().trim());
				insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
				insert_DpExprParaval.setCreateUser(userInfo.getUserid());
				insert_DpExprParaval.setDpCfgId(form.getDpMppCfg().getMppId());
				insert_DpExprParaval.setMapType("2");
				insert_DpExprParaval.setParamType(CommonConst.paramType_str);
				insert_DpExprParaval.setParamValue(insert_DpMppCfg.getProcCfg());
				insert_DpExprParaval.setUseFlag("0");
				dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
				
				insert_DpMppCfg.setProcCfg(insert_DpExprParaval.getSeqNo());
			}else{
				insert_DpMppCfg.setProcCfg(form.getDpMppCfg().getProcCfg_key());
			}
			
		}
		
		int count_insert = dp_Mpp_Service
				.insertDpMppCfgByMppId(insert_DpMppCfg);
		if (count_insert < 1) {
			model.addAttribute(ResultMessages.error().add("e.al.2002"));
			return "dp/DP_Mpp_Add";
		}
		if("3".equals(form.getDpMppCfg().getProcType())){
			form.getDpMppCfg().setProcCfg_key(insert_DpMppCfg.getProcCfg());
			form.getDpMppCfg().setProcCfg(procCfg);
		}
		
		List<DpExprParaval> dpExprParavals = CommonUtil.getAllDepBySeqNo(form.getDpMppCfg().getProcCfg_key());
		for(DpExprParaval dpExprParaval : dpExprParavals){
			dpExprParaval.setDpCfgId(insert_DpMppCfg.getMppId());
			dpExprParaval.setBranchId(insert_DpMppCfg.getBranchId());
			dp_Mpp_Service.updateDpExprParaval(dpExprParaval);
		}
		
		DpExprTmp dpExprTmp = new DpExprTmp();
		dpExprTmp.setUserId(userInfo.getUserid());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);

		model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		return "dp/DP_Mpp_Add";
	}


	@Resource
	private DP_Mpp_Service dp_Mpp_Service;

	@Resource
	protected NumberService numberService;

	@Resource
	private OrgInfMaintenanceService orgInfMaintenanceService;

}
