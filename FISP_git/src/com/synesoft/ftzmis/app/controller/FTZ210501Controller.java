package com.synesoft.ftzmis.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZ210501Form;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrTmp;
import com.synesoft.ftzmis.domain.service.FTZ210501Service;

/**
 * @author hb_huang
 *
 * 2013-12-27 下午12:52:33
 */
@Controller
@RequestMapping(value="FTZ210501")
public class FTZ210501Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@ModelAttribute
	public FTZ210501Form setForm() {
		return new FTZ210501Form();
	}
	
	//账户信息查询
	@RequestMapping("Qry")
	public String query(Model model, FTZ210501Form form, @PageableDefaults Pageable pageable) {
		logger.info("账户信息查询开始...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setBranchId(form.getQuery_branchId());
		query_FtzActMstr.setAccountName(form.getQuery_accountName());
		query_FtzActMstr.setAccountNo(form.getQuery_accountNo());
		query_FtzActMstr.setSubAccountNo(form.getQuery_subAccountNo());
		query_FtzActMstr.setCurrency(form.getQuery_currency());
		query_FtzActMstr.setAccType(form.getQuery_accType());
		query_FtzActMstr.setAccStatus(form.getQuery_accStatus());
		
		//query page list
		Page<FtzActMstr> page = ftz210501Service.queryFtzActMstrPage(pageable, query_FtzActMstr);
		
		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息查询结束...");
			return "ftzmis/FTZ210501_Qry";
		}
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息查询结束...");
		return "ftzmis/FTZ210501_Qry";
	}
	
	//账户信息详情
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210501Form form) {
		logger.info("账户信息详情查询开始...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(form.getSelected_actNo());
		query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
		
		//query Detail
		FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
		
		if (null == result_FtzActMstr) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息详情查询结束...");
			return "ftzmis/FTZ210501_Qry_Dtl";
		}
		result_FtzActMstr.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getMakDatetime()));
		result_FtzActMstr.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getChkDatetime()));
		form.setFtzActMstr(result_FtzActMstr);
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息详情查询结束...");
		return "ftzmis/FTZ210501_Qry_Dtl";
	}
	
	//账户信息录入&修改查询
	@RequestMapping("InputQry")
	public String inputQry(Model model, FTZ210501Form form, @PageableDefaults Pageable pageable) {
		logger.info("账户信息录入&修改查询开始...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setBranchId(form.getQuery_branchId());
		query_FtzActMstr.setAccountName(form.getQuery_accountName());
		query_FtzActMstr.setAccountNo(form.getQuery_accountNo());
		query_FtzActMstr.setSubAccountNo(form.getQuery_subAccountNo());
		query_FtzActMstr.setCurrency(form.getQuery_currency());
		query_FtzActMstr.setAccType(form.getQuery_accType());
		
		//query page list
		Page<FtzActMstr> page = ftz210501Service.queryFtzActMstrPage(pageable, query_FtzActMstr);
		
		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息录入&修改查询结束...");
			return "ftzmis/FTZ210501_Input_Qry";
		}
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息录入&修改查询结束...");
		return "ftzmis/FTZ210501_Input_Qry";
	}
	
	//账户信息录入初始化
	@RequestMapping("InputInit")
	public String inputInit(Model model, FTZ210501Form form) {
		logger.info("账户信息录入初始化开始...");
		logger.info("账户信息录入初始化结束...");
		return "ftzmis/FTZ210501_Input_Add";
	}
	
	//账户信息录入提交
	@RequestMapping("Insert")
	public String insert(Model model, FTZ210501Form form) {
		logger.info("账户信息录入提交开始...");
		
		//页面校验
		FtzActMstrTmp ftzActMstrTmp = form.getFtzActMstrTmp();
		ResultMessages resultMessages = ResultMessages.error();
		
		//户名
		if ("".equals(ftzActMstrTmp.getAccountName()) || null== ftzActMstrTmp.getAccountName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0002");
			resultMessages.add(resultMessage);
		}
		
		//国民经济部门分类
		if ("".equals(ftzActMstrTmp.getDeptType()) || null== ftzActMstrTmp.getDeptType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0003");
			resultMessages.add(resultMessage);
		}
		
		//资产负债指标代码
		if ("".equals(ftzActMstrTmp.getBalanceCode()) || null== ftzActMstrTmp.getBalanceCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0004");
			resultMessages.add(resultMessage);
		}
		
		//主账号
		if ("".equals(ftzActMstrTmp.getAccountNo()) || null== ftzActMstrTmp.getAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0005");
			resultMessages.add(resultMessage);
		}
		
		//子账号
		if ("".equals(ftzActMstrTmp.getSubAccountNo()) || null== ftzActMstrTmp.getSubAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0006");
			resultMessages.add(resultMessage);
		}
		
		//货币
		if ("".equals(ftzActMstrTmp.getCurrency()) || null== ftzActMstrTmp.getCurrency()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0007");
			resultMessages.add(resultMessage);
		}
		
		//类别
		if ("".equals(ftzActMstrTmp.getAccType()) || null== ftzActMstrTmp.getAccType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0008");
			resultMessages.add(resultMessage);
		}
		
		//证件类型
		if ("".equals(ftzActMstrTmp.getDocumentType()) || null== ftzActMstrTmp.getDocumentType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0009");
			resultMessages.add(resultMessage);
		}
		
		//证件号码
		if ("".equals(ftzActMstrTmp.getDocumentNo()) || null== ftzActMstrTmp.getDocumentNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0010");
			resultMessages.add(resultMessage);
		}
		
		//客户类型
		if ("".equals(ftzActMstrTmp.getCustomType()) || null== ftzActMstrTmp.getCustomType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0022");
			resultMessages.add(resultMessage);
		}
		
		//开户机构代码
		if ("".equals(ftzActMstrTmp.getAccOrgCode()) || null== ftzActMstrTmp.getAccOrgCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0023");
			resultMessages.add(resultMessage);
		}
		
		//余额
		if ("".equals(ftzActMstrTmp.getBalance()) || null== ftzActMstrTmp.getBalance()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0024");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210501_Input_Add";
		}
		
		try {
			ftz210501Service.insertFtzActMstr(ftzActMstrTmp);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210501_Input_Add";
		}
		form.getFtzActMstrTmp().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzActMstrTmp().getMakDatetime()));
		form.getFtzActMstrTmp().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzActMstrTmp().getChkDatetime()));
		model.addAttribute("successmsg", ResultMessages.success().
				add(ResultMessage.fromCode("i.sm.0001")));
		logger.info("账户信息录入提交结束");
		return "ftzmis/FTZ210501_Input_Add";
	}
	
	//账户信息修改初始化
	@RequestMapping("InputUpd")
	public String inputUpd(Model model, FTZ210501Form form) {
		logger.info("账户信息修改初始化查询...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(form.getSelected_actNo());
		query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
		//query Detail
		FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
		
		if (null == result_FtzActMstr) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息修改初始化查询结束...");
			return "ftzmis/FTZ210501_Input_Qry";
		}
		result_FtzActMstr.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getMakDatetime()));
		result_FtzActMstr.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getChkDatetime()));
		form.setFtzActMstr(result_FtzActMstr);
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息修改初始化查询结束...");
		return "ftzmis/FTZ210501_Input_Upd";
	}
	
	//查询账户临时表
	@RequestMapping("QryActDtl")
	public @ResponseBody String qryActDtl(@RequestParam("accountNo") String selected_actNo,
			@RequestParam("subAccountNo") String selected_subActNo, Model model) {
		JSONObject json = new JSONObject();
		FtzActMstrTmp query_FtzActMstrTmp = new FtzActMstrTmp();
		query_FtzActMstrTmp.setAccountNo(selected_actNo);
		query_FtzActMstrTmp.setSubAccountNo(selected_subActNo);
		FtzActMstrTmp result_FtzActMstrTmp = ftz210501Service.queryFtzActMstrTmp(query_FtzActMstrTmp);
		if (null == result_FtzActMstrTmp) {
			json.put("dtlExist", false);
		} else {
			json.put("dtlExist", true);
		}
		return json.toString();
	}
	
	//账户信息修改提交
	@RequestMapping("Update")
	public String update(Model model, FTZ210501Form form) {
		logger.info("账户信息修改提交开始...");
		
		FtzActMstr ftzActMstr = form.getFtzActMstr();
		//页面校验
		ResultMessages resultMessages = ResultMessages.error();
		
		//户名
		if ("".equals(ftzActMstr.getAccountName()) || null== ftzActMstr.getAccountName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0002");
			resultMessages.add(resultMessage);
		}
		
		//国民经济部门分类
		if ("".equals(ftzActMstr.getDeptType()) || null== ftzActMstr.getDeptType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0003");
			resultMessages.add(resultMessage);
		}
		
		//资产负债指标代码
		if ("".equals(ftzActMstr.getBalanceCode()) || null== ftzActMstr.getBalanceCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0004");
			resultMessages.add(resultMessage);
		}
		
		//主账号
		if ("".equals(ftzActMstr.getAccountNo()) || null== ftzActMstr.getAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0005");
			resultMessages.add(resultMessage);
		}
		
		//子账号
		if ("".equals(ftzActMstr.getSubAccountNo()) || null== ftzActMstr.getSubAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0006");
			resultMessages.add(resultMessage);
		}
		
		//货币
		if ("".equals(ftzActMstr.getCurrency()) || null== ftzActMstr.getCurrency()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0007");
			resultMessages.add(resultMessage);
		}
		
		//类别
		if ("".equals(ftzActMstr.getAccType()) || null== ftzActMstr.getAccType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0008");
			resultMessages.add(resultMessage);
		}
		
		//证件类型
		if ("".equals(ftzActMstr.getDocumentType()) || null== ftzActMstr.getDocumentType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0009");
			resultMessages.add(resultMessage);
		}
		
		//证件号码
		if ("".equals(ftzActMstr.getDocumentNo()) || null== ftzActMstr.getDocumentNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0010");
			resultMessages.add(resultMessage);
		}
		
		//客户类型
		if ("".equals(ftzActMstr.getCustomType()) || null== ftzActMstr.getCustomType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0022");
			resultMessages.add(resultMessage);
		}
		
		//开户机构代码
		if ("".equals(ftzActMstr.getAccOrgCode()) || null== ftzActMstr.getAccOrgCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0023");
			resultMessages.add(resultMessage);
		}
		
		//余额
		if ("".equals(ftzActMstr.getBalance()) || null== ftzActMstr.getBalance()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210501.0024");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210501_Input_Upd";
		}
		
		form.getFtzActMstr().setMakUserId(ContextConst.getCurrentUser().getUserid());
		form.getFtzActMstr().setMakDatetime(DateUtil.getNowInputDateTime());
		form.getFtzActMstr().setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(
				form.getFtzActMstr().getChkDatetime()));
		try {
			ftz210501Service.updateFtzActMstr(form.getFtzActMstr());
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210501_Input_Upd";
		}
		form.getFtzActMstr().setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form
				.getFtzActMstr().getMakDatetime()));
		form.getFtzActMstr().setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				form.getFtzActMstr().getChkDatetime()));
		model.addAttribute("successmsg", ResultMessages.success().add("i.sm.0002"));
		model.addAttribute("updFlag", "1");
		logger.info("账户信息修改提交结束...");
		return "ftzmis/FTZ210501_Input_Upd";
	}
	
	//账户信息删除
	@RequestMapping("Delete")
	public String delete(Model model, FTZ210501Form form) {
		logger.info("账户信息删除开始...");
		
		FtzActMstr delete_FtzActMstr = new FtzActMstr();
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(form.getSelected_actNo());
		query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
		delete_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
		try {
			ftz210501Service.deleteFtzActMstr(delete_FtzActMstr);
		} catch (BusinessException e) {
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210501_Input_Qry";
		}
		model.addAttribute(ResultMessages.success().add("i.sm.0003"));
		logger.info("账户信息删除结束...");
		return "forward:/FTZ210501/InputQry";
	}
	
	//账户信息录入&修改详情查询
	@RequestMapping("InputDtl")
	public String inputDtl(Model model, FTZ210501Form form) {
		logger.info("账户信息录入&修改详情查询开始...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(form.getSelected_actNo());
		query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
		
		//query Detail
		FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
		
		if (null == result_FtzActMstr) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息录入&修改详情查询结束...");
			return "ftzmis/FTZ210501_Input_Dtl";
		}
		result_FtzActMstr.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getMakDatetime()));
		result_FtzActMstr.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getChkDatetime()));
		form.setFtzActMstr(result_FtzActMstr);
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息录入&修改详情查询结束...");
		return "ftzmis/FTZ210501_Input_Dtl";
	}
	
	//审核查询列表
	@RequestMapping("AuthQry")
	public String authQry(Model model, FTZ210501Form form, @PageableDefaults Pageable pageable) {
		logger.info("审核查询列表开始...");
		
		FtzActMstrTmp query_FtzActMstrTmp = new FtzActMstrTmp();
		query_FtzActMstrTmp.setBranchId(form.getQuery_branchId());
		query_FtzActMstrTmp.setAccountName(form.getQuery_accountName());
		query_FtzActMstrTmp.setAccountNo(form.getQuery_accountNo());
		query_FtzActMstrTmp.setSubAccountNo(form.getQuery_subAccountNo());
		query_FtzActMstrTmp.setCurrency(form.getQuery_currency());
		query_FtzActMstrTmp.setAccType(form.getQuery_accType());
		query_FtzActMstrTmp.setAccStatus(form.getQuery_accStatus());
		
		//query page list
		Page<FtzActMstrTmp> page = ftz210501Service.
			queryFtzActMstrTmpPage(pageable, query_FtzActMstrTmp);
		
		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息审核查询结束...");
			return "ftzmis/FTZ210501_Auth_Qry";
		}
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("审核查询列表结束...");
		return "ftzmis/FTZ210501_Auth_Qry";
	}
	
	//账户信息审核详情
	@RequestMapping("AuthDtl")
	public String authDtl(Model model, FTZ210501Form form) {
		logger.info("账户信息审核详情查询开始...");
		
		FtzActMstrTmp query_FtzActMstrTmp = new FtzActMstrTmp();
		query_FtzActMstrTmp.setAccountNo(form.getSelected_actNo());
		query_FtzActMstrTmp.setSubAccountNo(form.getSelected_subActNo());
		
		//query Detail
		FtzActMstrTmp result_FtzActMstrTmp = ftz210501Service.queryFtzActMstrTmp(query_FtzActMstrTmp);
		
		if (null == result_FtzActMstrTmp) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("账户信息审核详情查询结束...");
			return "ftzmis/FTZ210501_Qry_Dtl";
		}
		result_FtzActMstrTmp.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstrTmp.getMakDatetime()));
		result_FtzActMstrTmp.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstrTmp.getChkDatetime()));
		result_FtzActMstrTmp.setChkAddWord("");
		form.setFtzActMstrTmp(result_FtzActMstrTmp);
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		logger.info("账户信息审核详情查询结束...");
		return "ftzmis/FTZ210501_Auth_Qry_Dtl";
	}
	
	//审核通过
	@RequestMapping("AuthSubmit")
	public String authSubmit(Model model, FTZ210501Form form) {
		logger.info("审核通过开始...");
		FtzActMstrTmp query_FtzActMstrTmp = new FtzActMstrTmp();
		query_FtzActMstrTmp.setAccountNo(form.getFtzActMstrTmp().getAccountNo());
		query_FtzActMstrTmp.setSubAccountNo(form.getFtzActMstrTmp().getSubAccountNo());
		FtzActMstrTmp result_FtzActMstrTmp = ftz210501Service.queryFtzActMstrTmp(query_FtzActMstrTmp);
		result_FtzActMstrTmp.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstrTmp.getMakDatetime()));
		result_FtzActMstrTmp.setChkAddWord(form.getFtzActMstrTmp().getChkAddWord());
		if ("1".equals(form.getAuthStat())) {
			try {
				ftz210501Service.authFtzActMstr(form.getFtzActMstrTmp());
			} catch (BusinessException e) {
				model.addAttribute("errmsg", e.getResultMessages());
				return "ftzmis/FTZ210501_Auth_Qry_Dtl";
			}
			//处理审核时间和审核员
			FtzActMstr query_FtzActMstr = new FtzActMstr();
			query_FtzActMstr.setAccountNo(form.getFtzActMstrTmp().getAccountNo());
			query_FtzActMstr.setSubAccountNo(form.getFtzActMstrTmp().getSubAccountNo());
			FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
			if (null != result_FtzActMstr) {
				result_FtzActMstrTmp.setChkUserId(result_FtzActMstr.getChkUserId());
				result_FtzActMstrTmp.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
						result_FtzActMstr.getChkDatetime()));
			}
			form.setFtzActMstrTmp(result_FtzActMstrTmp);
			model.addAttribute("authFlag","1");
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210501.0019"));
		} else if ("0".equals(form.getAuthStat())) {
			try {
				ftz210501Service.refuseFtzActMstr(form.getFtzActMstrTmp());
			} catch (BusinessException e) {
				model.addAttribute("errmsg", e.getResultMessages());
				return "ftzmis/FTZ210501_Auth_Qry_Dtl";
			}
			//处理审核时间和审核员
			FtzActMstr query_FtzActMstr = new FtzActMstr();
			query_FtzActMstr.setAccountNo(form.getFtzActMstrTmp().getAccountNo());
			query_FtzActMstr.setSubAccountNo(form.getFtzActMstrTmp().getSubAccountNo());
			FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
			if (null != result_FtzActMstr) {
				result_FtzActMstrTmp.setChkUserId(result_FtzActMstr.getChkUserId());
				result_FtzActMstrTmp.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
						result_FtzActMstr.getChkDatetime()));
			}
			
			form.setFtzActMstrTmp(result_FtzActMstrTmp);
			model.addAttribute("authFlag","1");
			model.addAttribute(ResultMessages.error().add("i.ftzmis.210501.0020"));
		} else {
			model.addAttribute(ResultMessages.error().add("e.ftzmis.210501.0021"));
		}
		
		logger.info("审核通过结束...");
		return "ftzmis/FTZ210501_Auth_Qry_Dtl";
	}
	
	@Resource
	protected FTZ210501Service ftz210501Service;
}
