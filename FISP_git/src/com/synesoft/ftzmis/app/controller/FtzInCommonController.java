/**
 * 
 */
package com.synesoft.ftzmis.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FtzInCommonForm;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrHistory;
import com.synesoft.ftzmis.domain.service.FTZ210501Service;
import com.synesoft.ftzmis.domain.service.FTZCMAccountQryService;
import com.synesoft.ftzmis.domain.service.FTZCommonService;

/**
 * @author Peter
 * @date 2014-1-26 下午5:46:39
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("FTZINCOM")
public class FtzInCommonController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public FtzInCommonForm setUpForm() {
		return new FtzInCommonForm();
	}
	@RequestMapping("BalanceCheckRe")
	public String ReBalanceCheck(FtzInCommonForm form, @PageableDefaults Pageable pageable, Model model){
		logger.info("重新校验开始");
		String submitDate = DateUtil.getFormatDateRemoveSprit(form
				.getCheck_SubmitDate());
		String accountNo = form.getCheck_AccountNo();
		String subAccountNo = form.getCheck_SubAccountNo();
		
		Map balanceResult = ftzCommonServ.BalanceCheck(submitDate, accountNo,
				subAccountNo);
		if (!(Boolean) balanceResult.get("check_result")) {
			model.addAttribute("checkFlag", false);
			logger.error("校验失败");
			return "ftzmis/FTZINCommon_Balance_Validation";
		}
		form.setBalance_result(((Boolean) balanceResult.get("balance_result")).toString());
		form.setBalance_current((BigDecimal) balanceResult
				.get("balance_current"));
		form.setBalance_enter((BigDecimal) balanceResult.get("balance_enter"));
		form.setBalance_enterflushes((BigDecimal) balanceResult
				.get("balance_enterflushes"));
		form.setBalance_expend((BigDecimal) balanceResult.get("balance_expend"));
		form.setBalance_expendflushes((BigDecimal) balanceResult
				.get("balance_expendflushes"));

		logger.info("重新校验结束");
		return "ftzmis/FTZINCommon_Balance_Validation";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("BalanceCheck")
	public String BalanceCheck(Model model, FtzInCommonForm form) {
		logger.info("余额校验开始");
		String submitDate = DateUtil.getFormatDateRemoveSprit(form
				.getCheck_SubmitDate());
		String accountNo = form.getCheck_AccountNo();
		String subAccountNo = form.getCheck_SubAccountNo();
		if (null == accountNo || "".equals(accountNo)) {
			model.addAttribute("checkFlag", false);
			logger.error("账号为空");
			return "ftzmis/FTZINCommon_Balance";
		}
		if (null == subAccountNo || "".equals(subAccountNo)) {
			model.addAttribute("checkFlag", false);
			logger.error("子账号为空");
			return "ftzmis/FTZINCommon_Balance";
		}
		if (null == submitDate || "".equals(submitDate)) {
			model.addAttribute("checkFlag", false);
			logger.error("申报日期为空");
			return "ftzmis/FTZINCommon_Balance";
		}

		// 查询当前工作日期，与传入的申报日期对比
		String workDate = procCommonServ.queryWorkDate();
		// 相等 - 查询账号主表
		if (workDate.equals(submitDate)) {
			FtzActMstr query_ftzActMstr = new FtzActMstr();
			query_ftzActMstr.setAccountNo(accountNo);
			query_ftzActMstr.setSubAccountNo(subAccountNo);
			FtzActMstr ftzActMstr = ftzcmactQryServ
					.queryFtzActMstr(query_ftzActMstr);
			if (null == ftzActMstr) {
				model.addAttribute("checkFlag", false);
				logger.error("账号不存在");
				return "ftzmis/FTZINCommon_Balance";
			}
			form.setFtzActMstr(ftzActMstr);
		}
		// 否则查询账号历史表
		else {

		}

		// 调用余额校验方法
		Map balanceResult = ftzCommonServ.BalanceCheck(submitDate, accountNo,
				subAccountNo);

		if (!(Boolean) balanceResult.get("check_result")) {
			model.addAttribute("checkFlag", false);
			logger.error("校验失败");
			return "ftzmis/FTZINCommon_Balance";
		}
		form.setBalance_result(((Boolean) balanceResult.get("balance_result")).toString());
		form.setBalance_current((BigDecimal) balanceResult
				.get("balance_current"));
		form.setBalance_enter((BigDecimal) balanceResult.get("balance_enter"));
		form.setBalance_enterflushes((BigDecimal) balanceResult
				.get("balance_enterflushes"));
		form.setBalance_expend((BigDecimal) balanceResult.get("balance_expend"));
		form.setBalance_expendflushes((BigDecimal) balanceResult
				.get("balance_expendflushes"));

		logger.info("余额校验结束");
		return "ftzmis/FTZINCommon_Balance";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("AccountBalanceCheck")
	public String AccountBalanceCheck(Model model, FtzInCommonForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("账户余额校验开始");
		String submitDate = DateUtil.getFormatDateRemoveSprit(form
				.getCheck_SubmitDate());
		// 查询当前工作日期，与传入的申报日期对比
		String workDate = procCommonServ.queryWorkDate();
		// 相等 - 查询账号主表
		if (workDate.equals(submitDate)) {
			FtzActMstr query_ftzActMstr = new FtzActMstr();
			Page page = ftzcmactQryServ.queryFtzActMstrPage(pageable, query_ftzActMstr);
			if(null != page && page.getContent().size()>0){
				List<FtzActMstr> ftzActMstrs = page.getContent();
				for(FtzActMstr ftzActMstr :ftzActMstrs){
					// 转换账号校验时间
					
				}
			}
			model.addAttribute("page", page);
		}
		// 否则查询账号历史表
		else {

		}

		return "ftzmis/FTZINCommon_Balance";
	}
	@RequestMapping("AddQry")
	public String queryAdd(Model model, FtzInCommonForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("余额校验查询开始...");
		ResultMessages resultMessages = ResultMessages.error();
		
		//申报日期
		if ("".equals(form.getQuery_submitDate_start()) || null== form.getQuery_submitDate_start()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZINCommon_Balance_Qry";
		}
		
		FtzActMstr query_ftzActMst = new FtzActMstr();
		String submitDate = DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start());		

		// 查询当前工作日期，与传入的申报日期对比
		String workDate = procCommonServ.queryWorkDate();
		// 相等 - 查询账号主表
		if (workDate.equals(submitDate)) {	
			query_ftzActMst.setBranchId(form.getQuery_branchId());//机构号
			query_ftzActMst.setSubmitDate(submitDate);//申报日期
			query_ftzActMst.setAccountNo(form.getQuery_accountNo());//账号
			query_ftzActMst.setSubAccountNo(form.getQuery_subAccountNo());//子账号
			query_ftzActMst.setCurrency(form.getQuery_currency());//货币		
			
			Page<FtzActMstr> page = ftz210501Service.queryFtzActMstrPage(pageable, query_ftzActMst);

			if (null != page && page.getContent().size() > 0) {
				List<FtzActMstr> ftzActMstrs = page.getContent();
				for (FtzActMstr ftzActMstr : ftzActMstrs) {
					ftzActMstr.setSubmitDate(DateUtil
							.getFormatDateAddSprit(ftzActMstr.getSubmitDate()));
					ftzActMstr.setCheckDatetime(DateUtil
							.getFormatDateTimeAddSpritAndColon(ftzActMstr
									.getChkDatetime()));
					
				}
				model.addAttribute("page", page);
			}  else {
				model.addAttribute(ResultMessages.info().add("w.sm.0001"));
				logger.error("查询无记录！");
				logger.info("余额校验查询结束...");
				return "ftzmis/FTZINCommon_Balance_Qry";
			}			
			model.addAttribute("page", page);
		} else {// 否则查询账号历史表
			FtzActMstrHistory query_FtzActMstrHistory = new FtzActMstrHistory();
			
			query_FtzActMstrHistory.setBranchId(form.getQuery_branchId());//机构号
			query_FtzActMstrHistory.setSubmitDate(submitDate);//申报日期
			query_FtzActMstrHistory.setAccountNo(form.getQuery_accountNo());//账号
			query_FtzActMstrHistory.setSubAccountNo(form.getQuery_subAccountNo());//子账号
			query_FtzActMstrHistory.setCurrency(form.getQuery_currency());//货币		
			
			FtzActMstrHistory result_FtzActMstrHistory = ftzCommonServ.queryFtzActMstrHistory(query_FtzActMstrHistory);
			
			if (null == result_FtzActMstrHistory) {
				model.addAttribute(ResultMessages.info().add("ftzin.balance.validation.001"));
			}	
		//	result_FtzActMstrHistory.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
		//			result_FtzActMstrHistory.getMakDatetime()));
		//	result_FtzActMstrHistory.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
		//	result_FtzActMstrHistory.getChkDatetime()));
		//	form.setFtzActMstr(result_FtzActMstrHistory);
			form.setSelected_actNo("");
			form.setSelected_subActNo("");
			
			logger.info("余额校验查询结束...");
			return "ftzmis/FTZINCommon_Balance_Qry";
		}	
		logger.info("余额校验查询结束...");
		return "ftzmis/FTZINCommon_Balance_Qry";
	}
	@RequestMapping("BalanceCheckVa")	//查看明细
	public String BalanceCheckQryVa(Model model, FtzInCommonForm form) {		
		
			logger.info("余额校验查询开始...");
			
			FtzActMstr query_FtzActMstr = new FtzActMstr();
			query_FtzActMstr.setAccountNo(form.getSelected_actNo());
			query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
			
			//query Detail
			FtzActMstr result_FtzActMstr = ftz210501Service.queryFtzActMstr(query_FtzActMstr);
			
			if (null == result_FtzActMstr) {
				model.addAttribute(ResultMessages.info().add("w.sm.0001"));
				logger.error("查询无记录！");
				logger.info("余额校验查询结束...");
				return "ftzmis/FTZINCommon_Balance_Validation";
			}
			result_FtzActMstr.setSubmitDate(DateUtil
					.getFormatDateAddSprit(result_FtzActMstr.getSubmitDate()));
			result_FtzActMstr.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
					result_FtzActMstr.getMakDatetime()));
			result_FtzActMstr.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
					result_FtzActMstr.getChkDatetime()));
			form.setFtzActMstr(result_FtzActMstr);
			logger.info("余额校验查询结束...");			
			return "ftzmis/FTZINCommon_Balance_Validation";
		
	
	}
	

	@Resource
	private ProcCommonService procCommonServ;

	@Resource
	protected FTZCMAccountQryService ftzcmactQryServ;

	@Resource
	protected FTZCommonService ftzCommonServ;
	
	@Resource
	protected FTZ210501Service ftz210501Service;
}
