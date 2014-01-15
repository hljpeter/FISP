package com.synesoft.fisp.app.dp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.CommonConst;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.CommonUtil;
import com.synesoft.fisp.app.common.utils.DateUtil;
import com.synesoft.fisp.app.dp.model.Dp_ExpForm;
import com.synesoft.fisp.domain.model.DpExprMethod;
import com.synesoft.fisp.domain.model.DpExprMethodDtl;
import com.synesoft.fisp.domain.model.DpExprParaval;
import com.synesoft.fisp.domain.model.DpExprTmp;
import com.synesoft.fisp.domain.model.DpTable;
import com.synesoft.fisp.domain.model.DpTableDtl;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.model.vo.DpExprMethodDtlVO;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;

@Controller
@RequestMapping("Dp_Exp")
public class Dp_ExpController {

	private static final Logger logger = LoggerFactory
			.getLogger(Dp_ExpController.class);

	@ModelAttribute
	public Dp_ExpForm setUpForm() {
		return new Dp_ExpForm();
	}

	@RequestMapping("Init")
	public String init(Model model, @RequestParam("srcTable") String srcTable,
			@RequestParam("oldExpress") String oldExpress,
			@RequestParam("oldExpress_key") String oldExpress_key,
			@RequestParam("dpType") String dpType,
			@RequestParam("cfgId") String cfgId,
			@RequestParam("branchId") String branchId,
			Dp_ExpForm form) {
		logger.info("init...");

		List<DpExprMethod> dpExprMethods = dp_Mpp_Service
				.queryDpExprMethod(new DpExprMethod());

		List<List<DpExprMethodDtlVO>> dpExprMethodDtls = new ArrayList<List<DpExprMethodDtlVO>>();

		if (null != dpExprMethods && dpExprMethods.size() > 0) {
			for (DpExprMethod dpExprMethod_tmp : dpExprMethods) {
				DpExprMethodDtl query_DpExprMethodDtl = new DpExprMethodDtl();
				query_DpExprMethodDtl.setmId(dpExprMethod_tmp.getmId());
				List<DpExprMethodDtl> dpExprMethodDtl_tmpList = dp_Mpp_Service
						.queryDpExprMethodDtlByMId(query_DpExprMethodDtl);
				List<DpExprMethodDtlVO> dpExprMethodDtlVOs = new ArrayList<DpExprMethodDtlVO>();
				for (DpExprMethodDtl dpExprMethodDtl : dpExprMethodDtl_tmpList) {
					DpExprMethodDtlVO dpExprMethodDtlVO = new DpExprMethodDtlVO();
					dpExprMethodDtlVO.setMethodName(dpExprMethod_tmp
							.getMethodName());
					dpExprMethodDtlVO.setpId(dpExprMethodDtl.getpId());
					dpExprMethodDtlVO.setParamType(dpExprMethodDtl
							.getParamType());
					dpExprMethodDtlVO.setParamDesc(dpExprMethodDtl
							.getParamDesc());
					dpExprMethodDtlVOs.add(dpExprMethodDtlVO);
				}
				dpExprMethodDtls.add(dpExprMethodDtlVOs);
			}
		}

		UserInf userInfo = ContextConst.getCurrentUser();
		
		String sessionId = ContextConst.getSession().getId();
		DpExprTmp dpExprTmp = new DpExprTmp();

		dpExprTmp.setUserId(userInfo.getUserid().trim());
		dpExprTmp.setUserSession(sessionId);
		dpExprTmp.setSrcTable(srcTable);
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);

		List<DpExprTmp> dpExprTmps = dp_Mpp_Service
				.queryDpExprTmpByUserId(dpExprTmp);
		
		DpTable dpTable = new DpTable();
		dpTable.setTableName(srcTable.trim());

		List<DpTableDtl> dpTableDtls = dp_Mpp_Service
				.queryExprDpTableDtlsByTableName(dpTable);
		List<String> list = CommonUtil.getTypeMap();
		model.addAttribute("matchList", list);
		
		form.setDpExprMethodDtls(dpExprMethodDtls);
		form.setDpExprMethods(dpExprMethods);
		form.setDpExprTmps(dpExprTmps);
		form.setDpTableDtls(dpTableDtls);

		form.setSrcTable(srcTable);
		form.setOldExpress(oldExpress);
		form.setOldExpress_key(oldExpress_key);
		form.setDp_type(dpType);
		form.setCfg_Id(cfgId);
		form.setBranch_Id(branchId);

		return "dp/Dp_Exp";
	}

	@RequestMapping("CreateTmpExpress")
	public String createTmpExpress(Model model, Dp_ExpForm form) {

		logger.info("CreateTmpExpress...");

		String selected_mothedName = form.getSelected_mothedName();

		Map<String, List<String[]>> dpMethodParamVals = form
				.getDpMethodParamVals();

		List<String[]> mothedPrams = dpMethodParamVals.get(selected_mothedName);

		StringBuffer express_str = new StringBuffer();
		express_str.append(selected_mothedName + "(");
		List<String> strings = new ArrayList<String>();
		UserInf userInfo = ContextConst.getCurrentUser();
		if(null == mothedPrams){
			express_str.append(")");
			strings.add("");
			
		}else{
			for (int i = 0; i < mothedPrams.size(); i++) {
				if (i == mothedPrams.size() - 1) {
					if ("".equals(mothedPrams.get(i)[2])) {
						express_str.append("null");
						strings.add("null");
					} else {
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						//按参数类型进行参数表生成
						if(CommonConst.paramType_col.equals(mothedPrams.get(i)[1])){
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_col);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}else if(CommonConst.paramType_expr.equals(mothedPrams.get(i)[1])){
							strings.add(mothedPrams.get(i)[0]);
						}else{
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId("default");
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_str);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}
						express_str.append(mothedPrams.get(i)[2]);
					}
					express_str.append(")");
				}else{
					if ("".equals(mothedPrams.get(i)[2])) {
						express_str.append("null,");
						strings.add("null");
					} else {
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						//按参数类型进行参数表生成
						if(CommonConst.paramType_col.equals(mothedPrams.get(i)[1])){
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_col);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}else if(CommonConst.paramType_expr.equals(mothedPrams.get(i)[1])){
							strings.add(mothedPrams.get(i)[0]);
						}else{
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_str);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}
						express_str.append(mothedPrams.get(i)[2] + ",");
					}
				}
			}
		}
		
		
		DpExprParaval insert_DpExprParaval = new DpExprParaval();
		insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
		insert_DpExprParaval.setBranchId(form.getBranch_Id());
		insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpExprParaval.setCreateUser(userInfo.getUserid());
		insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
		insert_DpExprParaval.setMapType(form.getDp_type());
		insert_DpExprParaval.setParamType(CommonConst.paramType_expr);
		StringBuffer paramValue = new StringBuffer();
		paramValue.append(selected_mothedName + "(");
		for (int i = 0; i < strings.size(); i++) {
			if (i == strings.size() - 1) {
				paramValue.append(strings.get(i));
				paramValue.append(")");
			}else{
				paramValue.append(strings.get(i)+",");
			}
		}
		insert_DpExprParaval.setParamValue(paramValue.toString());
		insert_DpExprParaval.setUseFlag("0");
		dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);

		DpExprMethod query_dpExprMethod = new DpExprMethod();
		query_dpExprMethod.setMethodName(selected_mothedName);
		DpExprMethodDtl dpExprMethodDtl = dp_Mpp_Service
				.queryDpExprMethodRetuenType(query_dpExprMethod);

		DpExprTmp insert_DpExprTmp = new DpExprTmp();
		
		String sessionId = ContextConst.getSession().getId();
		insert_DpExprTmp.setUserSession(sessionId);
		insert_DpExprTmp.setUserId(userInfo.getUserid());
		insert_DpExprTmp.setExpressVal(express_str.toString());
		insert_DpExprTmp.setSrcTable(form.getSrcTable());
		insert_DpExprTmp.setRsv1(insert_DpExprParaval.getSeqNo()); 
		
		//判断临时表达式是否存在，存在则返回页面
		DpExprTmp tmp_DpExprTmp =dp_Mpp_Service.queryDpExprTmpByKey(insert_DpExprTmp);
		if(null!=tmp_DpExprTmp){
			model.addAttribute(ResultMessages.error().add("e.dp.valid.004"));
			List<DpExprMethod> dpExprMethods = dp_Mpp_Service
					.queryDpExprMethod(new DpExprMethod());

			List<List<DpExprMethodDtlVO>> dpExprMethodDtls = new ArrayList<List<DpExprMethodDtlVO>>();

			if (null != dpExprMethods && dpExprMethods.size() > 0) {
				for (DpExprMethod dpExprMethod_tmp : dpExprMethods) {
					DpExprMethodDtl query_DpExprMethodDtl = new DpExprMethodDtl();
					query_DpExprMethodDtl.setmId(dpExprMethod_tmp.getmId());
					List<DpExprMethodDtl> dpExprMethodDtl_tmpList = dp_Mpp_Service
							.queryDpExprMethodDtlByMId(query_DpExprMethodDtl);
					List<DpExprMethodDtlVO> dpExprMethodDtlVOs = new ArrayList<DpExprMethodDtlVO>();
					for (DpExprMethodDtl dpExprMethodDtl_tmp : dpExprMethodDtl_tmpList) {
						DpExprMethodDtlVO dpExprMethodDtlVO = new DpExprMethodDtlVO();
						dpExprMethodDtlVO.setMethodName(dpExprMethod_tmp
								.getMethodName());
						dpExprMethodDtlVO.setpId(dpExprMethodDtl_tmp.getpId());
						dpExprMethodDtlVO.setParamType(dpExprMethodDtl_tmp
								.getParamType());
						dpExprMethodDtlVO.setParamDesc(dpExprMethodDtl_tmp
								.getParamDesc());
						dpExprMethodDtlVOs.add(dpExprMethodDtlVO);
					}
					dpExprMethodDtls.add(dpExprMethodDtlVOs);
				}
			}


			DpExprTmp dpExprTmp = new DpExprTmp();

			dpExprTmp.setUserId(userInfo.getUserid().trim());
			dpExprTmp.setUserSession(sessionId);
			dpExprTmp.setSrcTable(form.getSrcTable());
			dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);

			List<DpExprTmp> dpExprTmps = dp_Mpp_Service
					.queryDpExprTmpByUserId(dpExprTmp);

			DpTable dpTable = new DpTable();
			dpTable.setTableName(form.getSrcTable());

			List<DpTableDtl> dpTableDtls = dp_Mpp_Service
					.queryExprDpTableDtlsByTableName(dpTable);
			
			List<String> list = CommonUtil.getTypeMap();
			model.addAttribute("matchList", list);

			form.setDpExprMethodDtls(dpExprMethodDtls);
			form.setDpExprMethods(dpExprMethods);
			form.setDpExprTmps(dpExprTmps);
			form.setDpTableDtls(dpTableDtls);
			

			return "dp/Dp_Exp";
		}
		
		//不存在则插入临时表达式表
		insert_DpExprTmp.setExpressReturn(dpExprMethodDtl.getParamType());
		dp_Mpp_Service.insertDpExprTmp(insert_DpExprTmp);
		
		List<DpExprMethod> dpExprMethods = dp_Mpp_Service
				.queryDpExprMethod(new DpExprMethod());

		List<List<DpExprMethodDtlVO>> dpExprMethodDtls = new ArrayList<List<DpExprMethodDtlVO>>();

		if (null != dpExprMethods && dpExprMethods.size() > 0) {
			for (DpExprMethod dpExprMethod_tmp : dpExprMethods) {
				DpExprMethodDtl query_DpExprMethodDtl = new DpExprMethodDtl();
				query_DpExprMethodDtl.setmId(dpExprMethod_tmp.getmId());
				List<DpExprMethodDtl> dpExprMethodDtl_tmpList = dp_Mpp_Service
						.queryDpExprMethodDtlByMId(query_DpExprMethodDtl);
				List<DpExprMethodDtlVO> dpExprMethodDtlVOs = new ArrayList<DpExprMethodDtlVO>();
				for (DpExprMethodDtl dpExprMethodDtl_tmp : dpExprMethodDtl_tmpList) {
					DpExprMethodDtlVO dpExprMethodDtlVO = new DpExprMethodDtlVO();
					dpExprMethodDtlVO.setMethodName(dpExprMethod_tmp
							.getMethodName());
					dpExprMethodDtlVO.setpId(dpExprMethodDtl_tmp.getpId());
					dpExprMethodDtlVO.setParamType(dpExprMethodDtl_tmp
							.getParamType());
					dpExprMethodDtlVO.setParamDesc(dpExprMethodDtl_tmp
							.getParamDesc());
					dpExprMethodDtlVOs.add(dpExprMethodDtlVO);
				}
				dpExprMethodDtls.add(dpExprMethodDtlVOs);
			}
		}


		DpExprTmp dpExprTmp = new DpExprTmp();

		dpExprTmp.setUserId(userInfo.getUserid().trim());
		dpExprTmp.setUserSession(sessionId);
		dpExprTmp.setSrcTable(form.getSrcTable());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);

		List<DpExprTmp> dpExprTmps = dp_Mpp_Service
				.queryDpExprTmpByUserId(dpExprTmp);

		DpTable dpTable = new DpTable();
		dpTable.setTableName(form.getSrcTable());

		List<DpTableDtl> dpTableDtls = dp_Mpp_Service
				.queryExprDpTableDtlsByTableName(dpTable);
		
		List<String> list = CommonUtil.getTypeMap();
		model.addAttribute("matchList", list);

		form.setDpExprMethodDtls(dpExprMethodDtls);
		form.setDpExprMethods(dpExprMethods);
		form.setDpExprTmps(dpExprTmps);
		form.setDpTableDtls(dpTableDtls);
		

		return "dp/Dp_Exp";
	}

	@RequestMapping("CreateExpress")
	public String createExpress(Model model, Dp_ExpForm form) {

		logger.info("CreateExpress...");
		String selected_mothedName = form.getSelected_mothedName();

		Map<String, List<String[]>> dpMethodParamVals = form
				.getDpMethodParamVals();

		List<String[]> mothedPrams = dpMethodParamVals.get(selected_mothedName);

		StringBuffer express_str = new StringBuffer();
		express_str.append(selected_mothedName + "(");
		List<String> strings = new ArrayList<String>();
		UserInf userInfo = ContextConst.getCurrentUser();
		if(null == mothedPrams){
			express_str.append(")");
			strings.add("");
		}else{
			for (int i = 0; i < mothedPrams.size(); i++) {
				if (i == mothedPrams.size() - 1) {
					if ("".equals(mothedPrams.get(i)[2])) {
						express_str.append("null");
						strings.add("null");
					} else {
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						//按参数类型进行参数表生成
						if(CommonConst.paramType_col.equals(mothedPrams.get(i)[1])){
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_col);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}else if(CommonConst.paramType_expr.equals(mothedPrams.get(i)[1])){
							strings.add(mothedPrams.get(i)[0]);
						}else{
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_str);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}
						express_str.append(mothedPrams.get(i)[2]);
					}
					express_str.append(")");
				}else{
					if ("".equals(mothedPrams.get(i)[2])) {
						express_str.append("null,");
						strings.add("null");
					} else {
						DpExprParaval insert_DpExprParaval = new DpExprParaval();
						//按参数类型进行参数表生成
						if(CommonConst.paramType_col.equals(mothedPrams.get(i)[1])){
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_col);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}else if(CommonConst.paramType_expr.equals(mothedPrams.get(i)[1])){
							strings.add(mothedPrams.get(i)[0]);
						}else{
							insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
							insert_DpExprParaval.setBranchId(form.getBranch_Id());
							insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
							insert_DpExprParaval.setCreateUser(userInfo.getUserid());
							insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
							insert_DpExprParaval.setMapType(form.getDp_type());
							insert_DpExprParaval.setParamType(CommonConst.paramType_str);
							insert_DpExprParaval.setParamValue(mothedPrams.get(i)[2]);
							insert_DpExprParaval.setUseFlag("0");
							dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
							strings.add(insert_DpExprParaval.getSeqNo());
						}
						express_str.append(mothedPrams.get(i)[2] + ",");
					}
				}
			}
		}
		
		DpExprParaval insert_DpExprParaval = new DpExprParaval();
		insert_DpExprParaval.setSeqNo(numberService.getSysIDSequence(32));
		insert_DpExprParaval.setBranchId(form.getBranch_Id());
		insert_DpExprParaval.setCreateTime(DateUtil.getNow("yyyyMMddHHmmss"));
		insert_DpExprParaval.setCreateUser(userInfo.getUserid());
		insert_DpExprParaval.setDpCfgId(form.getCfg_Id());
		insert_DpExprParaval.setMapType(form.getDp_type());
		insert_DpExprParaval.setParamType(CommonConst.paramType_expr);
		StringBuffer paramValue = new StringBuffer();
		paramValue.append(selected_mothedName + "(");
		for (int i = 0; i < strings.size(); i++) {
			if (i == strings.size() - 1) {
				paramValue.append(strings.get(i));
				paramValue.append(")");
			}else{
				paramValue.append(strings.get(i)+",");
			}
		}
		insert_DpExprParaval.setParamValue(paramValue.toString());
		insert_DpExprParaval.setUseFlag("0");
		dp_Mpp_Service.insertDpExprParaval(insert_DpExprParaval);
		
		form.setExpress(express_str.toString());
		form.setExpress_key(insert_DpExprParaval.getSeqNo());

		List<DpExprMethod> dpExprMethods = dp_Mpp_Service
				.queryDpExprMethod(new DpExprMethod());

		List<List<DpExprMethodDtlVO>> dpExprMethodDtls = new ArrayList<List<DpExprMethodDtlVO>>();

		if (null != dpExprMethods && dpExprMethods.size() > 0) {
			for (DpExprMethod dpExprMethod_tmp : dpExprMethods) {
				DpExprMethodDtl query_DpExprMethodDtl = new DpExprMethodDtl();
				query_DpExprMethodDtl.setmId(dpExprMethod_tmp.getmId());
				List<DpExprMethodDtl> dpExprMethodDtl_tmpList = dp_Mpp_Service
						.queryDpExprMethodDtlByMId(query_DpExprMethodDtl);
				List<DpExprMethodDtlVO> dpExprMethodDtlVOs = new ArrayList<DpExprMethodDtlVO>();
				for (DpExprMethodDtl dpExprMethodDtl : dpExprMethodDtl_tmpList) {
					DpExprMethodDtlVO dpExprMethodDtlVO = new DpExprMethodDtlVO();
					dpExprMethodDtlVO.setMethodName(dpExprMethod_tmp
							.getMethodName());
					dpExprMethodDtlVO.setpId(dpExprMethodDtl.getpId());
					dpExprMethodDtlVO.setParamType(dpExprMethodDtl
							.getParamType());
					dpExprMethodDtlVO.setParamDesc(dpExprMethodDtl
							.getParamDesc());
					dpExprMethodDtlVOs.add(dpExprMethodDtlVO);
				}
				dpExprMethodDtls.add(dpExprMethodDtlVOs);
			}
		}

		

		DpExprTmp dpExprTmp = new DpExprTmp();

		dpExprTmp.setUserId(userInfo.getUserid().trim());
		String sessionId = ContextConst.getSession().getId();
		dpExprTmp.setUserSession(sessionId);
		dpExprTmp.setSrcTable(form.getSrcTable());
		dp_Mpp_Service.deleteDpExprTmp(dpExprTmp);

		List<DpExprTmp> dpExprTmps = dp_Mpp_Service
				.queryDpExprTmpByUserId(dpExprTmp);

		DpTable dpTable = new DpTable();
		dpTable.setTableName(form.getSrcTable());

		List<DpTableDtl> dpTableDtls = dp_Mpp_Service
				.queryExprDpTableDtlsByTableName(dpTable);
		
		List<String> list = CommonUtil.getTypeMap();
		model.addAttribute("matchList", list);
		
		form.setDpExprMethodDtls(dpExprMethodDtls);
		form.setDpExprMethods(dpExprMethods);
		form.setDpExprTmps(dpExprTmps);
		form.setDpTableDtls(dpTableDtls);
		

		return "dp/Dp_Exp";
	}

	@Resource
	private DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	protected NumberService numberService;
}
