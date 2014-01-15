package com.synesoft.ftzmis.app.controller;

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
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZCMAccountQryForm;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.service.FTZCMAccountQryService;

@Controller
@RequestMapping(value = "FTZCMAccountQry")
public class FTZCMAccountQryController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@ModelAttribute
	public FTZCMAccountQryForm setUpForm() {
		return new FTZCMAccountQryForm();
	}
	
	@RequestMapping("/QryInit")
	public String qryInit() {
		logger.info("Qry Init...");
		return "ftzmis/FTZCMAccount_Qry";
	}
	
	@RequestMapping("Qry")
	public String query(Model model, FTZCMAccountQryForm form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");
		
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setBranchId(form.getQuery_branchId());
		query_FtzActMstr.setAccountName(form.getQuery_accountName());
		query_FtzActMstr.setAccountNo(form.getQuery_accountNo());
		query_FtzActMstr.setSubAccountNo(form.getQuery_subAccountNo());
		query_FtzActMstr.setCurrency(form.getQuery_currency());
		query_FtzActMstr.setAccType(form.getQuery_accType());
		query_FtzActMstr.setAccStatus(form.getQuery_accStatus());
		
		//query page list
		Page<FtzActMstr> page = ftzcmactQryServ.queryFtzActMstrPage(pageable, query_FtzActMstr);
		
		if (page.getContent().size() > 0) {
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZCMAccount_Qry";
		}
		return "ftzmis/FTZCMAccount_Qry";
		
	}
	
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZCMAccountQryForm form) {
		logger.info("Query Detail...");
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(form.getSelected_actNo());
		query_FtzActMstr.setSubAccountNo(form.getSelected_subActNo());
		
		//query Detail
		FtzActMstr result_FtzActMstr = ftzcmactQryServ.queryFtzActMstr(query_FtzActMstr);
		
		if (null == result_FtzActMstr) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZCMAccount_Qry_Dtl";
		}
		result_FtzActMstr.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getMakDatetime()));
		result_FtzActMstr.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzActMstr.getChkDatetime()));
		form.setFtzActMstr(result_FtzActMstr);
		form.setSelected_actNo("");
		form.setSelected_subActNo("");
		return "ftzmis/FTZCMAccount_Qry_Dtl";
	}
	
	@Resource
	protected FTZCMAccountQryService ftzcmactQryServ;
}
