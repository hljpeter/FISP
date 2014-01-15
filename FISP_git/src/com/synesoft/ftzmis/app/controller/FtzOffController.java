package com.synesoft.ftzmis.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZOFFForm;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.service.FTZ210302Service;

/**
 * @author 李峰
 * @date 2013-12-31 下午3:16:23
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Controller
@RequestMapping("FTZOFF")
public class FtzOffController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FTZ210302Service ftz210302Service;
	
	@RequestMapping("Qry")
	public String query(Model model, FTZOFFForm form,
			@PageableDefaults Pageable pageable){
		// trans form to queryObject
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(form.getQuery_workDate_start());
		query_FtzOffMsgCtl.setRsv2(form.getQuery_workDate_end());
		query_FtzOffMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzOffMsgCtl.setMsgNo(form.getQuery_msgNo());
		
		//Query
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210302Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("表外查询结束...");
			return "ftzmis/FTZOFF_Qry";
		}
				
		return "ftzmis/FTZOFF_Qry";
	}
	
	@RequestMapping("AuthQry")
	public String authQuery(Model model, FTZOFFForm form,
			@PageableDefaults Pageable pageable){
		// trans form to queryObject
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(form.getQuery_workDate_start());
		query_FtzOffMsgCtl.setRsv2(form.getQuery_workDate_end());
		query_FtzOffMsgCtl.setMsgStatuss(new String[] {
				CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED,
				CommonConst.FTZ_MSG_STATUS_INPUTING,
				CommonConst.FTZ_MSG_STATUS_AUTH_FAIL});
		query_FtzOffMsgCtl.setMsgNo(form.getQuery_msgNo());
		
		//Query
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210302Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("表外查询结束...");
			return "ftzmis/FTZOFF_Auth_Qry";
		}
				
		return "ftzmis/FTZOFF_Auth_Qry";
	}
	
	/**
	 * 查看明细 分发
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("QryRedirect")
	public String queryRedirect(Model model, FTZOFFForm form) {
		if (CommonConst.MSG_NO_210301.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210301/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210302.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210302/QryDtl?selected_msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210303.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210303/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210304.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210304/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210305.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210305/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210306.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210306/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210307.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210307/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210308.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210308/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210309.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210309/Qry/DtlMsg/Init?FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210310.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210310/QryDtl?selected_msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210311.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210311/QryDtl?selected_msgId="+ form.getSelected_msgId();
		} else {
			return null;
		}

	}
	
	/**
	 * 查询待审核的
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("QryRedirectAuth")
	public String queryRedirectAuth(Model model, FTZOFFForm form) {
		if (CommonConst.MSG_NO_210301.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210301/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210302.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210302/QryAuthDtl?selected_msgId="+ form.getSelected_msgId()+"&unAuthFlag=1";
		} else if (CommonConst.MSG_NO_210303.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210303/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210304.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210304/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210305.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210305/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210306.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210306/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210307.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210307/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210308.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210308/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210309.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210309/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210310.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210310/QryAuthDtl?selected_msgId="+form.getSelected_msgId()+"&unAuthFlag=1";
		} else if (CommonConst.MSG_NO_210311.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210311/QryAuthDtl?selected_msgId="+form.getSelected_msgId()+"&unAuthFlag=1";
		} else {
			return null;
		}

	}
	
	/**
	 * 审核全部
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping("QryRedirectAuthAll")
	public String queryRedirectAuthAll(Model model, FTZOFFForm form) {
		if (CommonConst.MSG_NO_210301.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210301/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210302.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210302/QryAuthDtl?selected_msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210303.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210303/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210304.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210304/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210305.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210305/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210306.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210306/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210307.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210307/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210308.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210308/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210309.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210309/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210310.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210310/QryAuthDtl?selected_msgId="+form.getSelected_msgId();
		} else if (CommonConst.MSG_NO_210311.equals(form.getSelected_msgNo())) {
			return "redirect:/FTZ210311/QryAuthDtl?selected_msgId="+form.getSelected_msgId();
		} else {
			return null;
		}

	}
}
