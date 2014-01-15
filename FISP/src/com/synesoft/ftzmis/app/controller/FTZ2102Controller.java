package com.synesoft.ftzmis.app.controller;

import java.util.List;

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

import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZ210206Form;
import com.synesoft.ftzmis.app.model.FTZ2102Form;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210206Service;

//@Controller
//@RequestMapping(value = "FTZ2102")
//public class FTZ2102Controller {
//	public Logger logger = LoggerFactory.getLogger(getClass());
//
//	@ModelAttribute
//	public FTZ2102Form setUpForm() {
//		return new FTZ2102Form();
//	}
//
//	@RequestMapping("AuthQry")
//	public String queryAuth(Model model,  FTZ2102Form form,
//			@PageableDefaults Pageable pageable) {
//		logger.info("query...");
//
//		// trans form to queryObject
//		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
//		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
//		query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
//		query_FtzInMsgCtl.setAccountNo(form.getQuery_accountNo());
//		query_FtzInMsgCtl.setRsv1(form.getQuery_submitDate_start());
//		query_FtzInMsgCtl.setRsv2(form.getQuery_submitDate_end());
//		query_FtzInMsgCtl
//				.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
//		
//		
//
//		// query DpMppCfg page list
//		Page<FtzInMsgCtl> page = ftz210206Serv.queryFtzInMsgCtlPage(pageable,
//				query_FtzInMsgCtl);
//
//		if (page.getContent().size() > 0) {
//			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
//			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
//				FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
//				query_FtzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
//				List<FtzInTxnDtl> ftzInTxnDtls = ftz210206Serv
//						.queryFtzInTxnDtlList(query_FtzInTxnDtl);
//				int count = 0;
//				if (null == ftzInTxnDtls || ftzInTxnDtls.size() < 1) {
//					ftzInMsgCtl.setRsv1(ftzInMsgCtl.getTotalCount() + "");
//				} else {
//					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
//						if (ftzInTxnDtl.getChkStatus().equals(
//								CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
//							count++;
//						}
//					}
//					ftzInMsgCtl.setRsv1((ftzInMsgCtl.getTotalCount() - count)
//							+ "");
//				}
//				ftzInMsgCtl.setSubmitDate(DateUtil
//						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
//			}
//			model.addAttribute("page", page);
//			//form.setSelected_msgId("");
//		} else {
//			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
//			return "ftzmis/FTZ2102_Auth_Qry";
//		}
//
//		return "ftzmis/FTZ2102_Auth_Qry";
//	}
//	
//	
//	@RequestMapping("QryAuthRedirect")
//	public String queryAuthRedirect(Model model, FTZ210206Form form) {
//		if (CommonConst.MSG_NO_210101.equals(form.getSelected_msgNo())) {
//			return "redirect:/FTZ210201/QryAuthDtl?selected_msgId="
//					+ form.getSelected_msgId() + "&unAuthFlag="
//					+ form.getUnAuthFlag();
//		} else if (CommonConst.MSG_NO_210102.equals(form.getSelected_msgNo())) {
//			return "redirect:/FTZ210102/QryAuthDtl?selected_msgId="
//					+ form.getSelected_msgId() + "&unAuthFlag="
//					+ form.getUnAuthFlag();
//			
//		}
//		else if(CommonConst.MSG_NO_210206.equals(form.getSelected_msgNo()))
//		{
//			return "redirect:/FTZ210206/QryAuthDtl?selected_msgId="
//			+ form.getSelected_msgId() + "&unAuthFlag="
//			+ form.getUnAuthFlag();
//		}
//		else {
//			return null;
//		}
//
//	}
//	
//	@Resource
//	protected FTZ210206Service ftz210206Serv;
//
//	@Resource
//	protected NumberService numberService;
//}

