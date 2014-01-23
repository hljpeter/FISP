/**
 * 
 */
package com.synesoft.ftzmis.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgHead;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.util.Validator;
import com.synesoft.ftzmis.app.model.FTZ210112Form;
import com.synesoft.ftzmis.app.model.FTZ210112Form.FTZ210112FormAddDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210211Service;

/**
	
 */

@Controller
@RequestMapping(value = "FTZ210112")
public class FTZ210112Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());
	public static final String CD_FLAG_3 = "3";//出账业务冲正
	public static final String CD_FLAG_4 = "4";//入账业务冲正

	@ModelAttribute
	public FTZ210112Form setUpForm() {
		return new FTZ210112Form();
	}

	@RequestMapping("QryDtl")
	/**
	 * 查询库存现金批量明细
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210211_Qry_Dtl
	 */
	public String queryDtl(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备批量查询开始...");
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210211Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			logger.info("查询无记录！");
			logger.info("库存现金批量查询结束...");
			return "ftzmis/FTZ210112_Qry_Dtl";
		} 
		// 有数据则进行数据转换，查询明细数据
		result_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateAddSprit(result_FtzInMsgCtl.getSubmitDate()));
		result_FtzInMsgCtl.setSndDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
						.getSndDatetime()));
		result_FtzInMsgCtl.setAckDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
						.getAckDatetime()));
		if(result_FtzInMsgCtl.getBalanceCode() != null){
			result_FtzInMsgCtl.setBalanceCode(result_FtzInMsgCtl.getBalanceCode().trim());
		}
		form.setFtzInMsgCtl(result_FtzInMsgCtl);
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210211Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if(ftzInTxnDtls != null && !ftzInTxnDtls.isEmpty() && ftzInTxnDtls.size() > 0){
			FtzInTxnDtl result_FtzInTxnDtl = ftzInTxnDtls.get(0);
			result_FtzInTxnDtl.setMakDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getMakDatetime()));
			result_FtzInTxnDtl.setChkDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getChkDatetime()));
			form.setFtzInTxnDtl(result_FtzInTxnDtl);
		}
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("各项准备批量查询结束...");
		return "ftzmis/FTZ210112_Qry_Dtl";
	}

	/**
	 * 删除库存现金批量信息
	 * @param model
	 * @param form
	 * @return forward:/FTZ210211/AddQry
	 */
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210112Form form) {
		logger.info("各项准备查询批量删除开始...");
		FtzInMsgCtl del_FtzInMsgCtl = new FtzInMsgCtl();
		del_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		//删除库存现金批量信息
		int i = ftz210211Serv.deleteFtzInMsgCtl(del_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
			logger.info("库存现金查询批量删除结束...");
			return "forward:/FTZ210112/AddQry";
		} 
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0006"));
		form.setSelected_msgId("");
		logger.info("各项准备查询批量删除结束...");
		return "forward:/FTZ210112/AddQry";
	}

	/**
	 * 
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210211_Input_Qry
	 */
	@RequestMapping("AddQry")
	public String queryAdd(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备库存现金查询开始...");
		// trans form to queryObject
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
		if(form.getQuery_branchId() != null){
			query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
		}
		query_FtzInMsgCtl.setAccountNo(form.getQuery_accountNo());
		query_FtzInMsgCtl.setRsv1(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start()));
		query_FtzInMsgCtl.setRsv2(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_end()));
		query_FtzInMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210112);

		// 查询批量列表信息
		Page<FtzInMsgCtl> page = ftz210211Serv.queryFtzInMsgCtlPageInput(
				pageable, query_FtzInMsgCtl);

		if (0 == page.getContent().size()) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("库存现金查询结束...");
			return "ftzmis/FTZ210112_Input_Qry";
		}
		List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
		for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
			ftzInMsgCtl.setSubmitDate(DateUtil
					.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
		}
		model.addAttribute("page", page);
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		return "ftzmis/FTZ210112_Input_Qry";
	}

	/**
	 * 新增批量页面初始信息
	 * @param model
	 * @param form
	 * @return ftzmis/FTZ210211_Input_Dtl
	 */
	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210112Form form) {
		form.setInput_flag("add");
		model.addAttribute("pageUrl", "/FTZ210112/AddDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}

	/**
	 * 库存现金批量录入
	 * @param model
	 * @param form
	 * @param result
	 * @return ftzmis/FTZ210211_Input_Dtl
	 */
	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model,
			@Validated({ FTZ210112FormAddDtl.class }) FTZ210112Form form,
			BindingResult result) {
		logger.info("各项准备批量录入开始...");
		if (result.hasErrors()) {
			return "ftzmis/FTZ210112_Input_Dtl";
		}
		// 获取录入信息
		FtzInMsgCtl insert_FtzInMsgCtl = form.getFtzInMsgCtl();
		FtzInTxnDtl insert_FtzInTxnDtl = form.getFtzInTxnDtl();

		// 开始校验
		ResultMessages resultMessages = validDtl(insert_FtzInMsgCtl,insert_FtzInTxnDtl);
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210112_Input_Dtl";
		}

		insert_FtzInMsgCtl.setMsgId(numberService.getSysIDSequence(16));
		insert_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzInMsgCtl.getSubmitDate()));

		// 设置批量头信息
		FtzMsgHead mh = FtzMsgHead.getMsgHead();
		insert_FtzInMsgCtl.setVer(mh.getVER());
		insert_FtzInMsgCtl.setSrc(mh.getSRC());
		insert_FtzInMsgCtl.setDes(mh.getDES());
		insert_FtzInMsgCtl.setApp(mh.getAPP());
		insert_FtzInMsgCtl.setWorkDate(mh.getWorkDate());
		insert_FtzInMsgCtl.setEditFlag(mh.getEditFlag());
		insert_FtzInMsgCtl.setReserve(mh.getReserve());

		UserInf userInfo = ContextConst.getCurrentUser();
		insert_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		insert_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzInMsgCtl.setTotalCount(0);
		insert_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		insert_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210112);
		
		
		insert_FtzInTxnDtl.setMsgId(insert_FtzInMsgCtl.getMsgId());
		insert_FtzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		insert_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		insert_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		
		// 插入批量头信息和交易明细信息
		int retMsgCtl = ftz210211Serv.insertFtzInMsgCtlAndTxnDtl(insert_FtzInMsgCtl,insert_FtzInTxnDtl);
		if (retMsgCtl < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
			form.getFtzInMsgCtl().setSubmitDate(
					DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
							.getSubmitDate()));
			return "ftzmis/FTZ210112_Input_Dtl";
		}
		model.addAttribute(ResultMessages.success().add(
				"i.ftzmis.210210.0001"));
		logger.info("各项准备录入结束...");
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		form.getFtzInMsgCtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInMsgCtl()
						.getMakDatetime()));
		form.getFtzInMsgCtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInMsgCtl()
						.getMakDatetime()));
		return "ftzmis/FTZ210112_Input_Dtl";
	}

	/**
	 * 更新库存现金批量明细
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210211_Input_Dtl
	 */
	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备录入批量录入更新初始化开始...");
		// 检查批量是否存在
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210211Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("各项准备录入批量录入更新初始化结束...");
			return "ftzmis/FTZ210112_Input_Upt";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		if(ftzInMsgCtl.getBalanceCode() != null){
			ftzInMsgCtl.setBalanceCode(ftzInMsgCtl.getBalanceCode().trim());
		}
		
		//查询交易明细
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210211Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if(ftzInTxnDtls != null && !ftzInTxnDtls.isEmpty() && ftzInTxnDtls.size() > 0){
			FtzInTxnDtl result_FtzInTxnDtl = ftzInTxnDtls.get(0);
			result_FtzInTxnDtl.setMakDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getMakDatetime()));
			result_FtzInTxnDtl.setChkDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getChkDatetime()));
			form.setFtzInTxnDtl(result_FtzInTxnDtl);
		}
		form.setFtzInMsgCtl(ftzInMsgCtl);
		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("各项准备录入批量录入更新初始化结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}

	/**
	 * 更新库存现金批量
	 * @param model
	 * @param form
	 * @param result
	 * @param pageable
	 * @return ftzmis/FTZ210211_Input_Dtl
	 */
	@RequestMapping("UptDtlSubmit")
	public String UptDtlSubmit(Model model,
			@Validated({ FTZ210112FormAddDtl.class }) FTZ210112Form form,
			BindingResult result, @PageableDefaults Pageable pageable) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210112_Input_Dtl";
		}
		FtzInMsgCtl update_FtzInMsgCtl = form.getFtzInMsgCtl();
		FtzInTxnDtl update_FtzInTxnDtl = form.getFtzInTxnDtl();

		// 开始校验
		ResultMessages resultMessages = validDtl(update_FtzInMsgCtl,update_FtzInTxnDtl);
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			// 清空页面列表选择Key
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
			return "ftzmis/FTZ210112_Input_Dtl";
		}
		
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInMsgCtl.getSubmitDate()));
		
		update_FtzInTxnDtl.setMsgId(update_FtzInMsgCtl.getMsgId());
		update_FtzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		update_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		update_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl
						.getChkDatetime()));
		update_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		
		int i = ftz210211Serv.updateFtzInMsgCtlAndTxnDtl(update_FtzInMsgCtl,update_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		}else{
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0002"));
		}
		update_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(update_FtzInTxnDtl
						.getMakDatetime()));
		update_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(update_FtzInTxnDtl
						.getChkDatetime()));
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		return "ftzmis/FTZ210112_Input_Dtl";
	}

	/**
	 * 提交批量信息
	 * @param model
	 * @param form
	 * @return forward:/FTZ210211/AddQry
	 */
	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210112Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(form.getSelected_msgId());
		int i = ftz210211Serv.updateFtzInMsgCtlForSubmit(ftzInMsgCtl);
		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210112/AddQry";
		}
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0007"));
		return "forward:/FTZ210112/AddQry";
	}

	/**
	 * 查询审核信息
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210211_Auth_Qry_Dtl
	 */
	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210211Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "ftzmis/FTZ210112_Auth_Qry_Dtl";
		} 
		if(result_FtzInMsgCtl.getBalanceCode() != null){
			result_FtzInMsgCtl.setBalanceCode(result_FtzInMsgCtl.getBalanceCode().trim());
		}
		result_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateAddSprit(result_FtzInMsgCtl.getSubmitDate()));
		result_FtzInMsgCtl.setSndDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
						.getSndDatetime()));
		result_FtzInMsgCtl.setAckDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
						.getAckDatetime()));
		form.setFtzInMsgCtl(result_FtzInMsgCtl);
		//查询交易明细
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210211Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (ftzInTxnDtls != null && !ftzInTxnDtls.isEmpty()
				&& ftzInTxnDtls.size() > 0) {
			FtzInTxnDtl result_FtzInTxnDtl = ftzInTxnDtls.get(0);
			result_FtzInTxnDtl.setMakDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getMakDatetime()));
			result_FtzInTxnDtl.setChkDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
							.getChkDatetime()));
			form.setFtzInTxnDtl(result_FtzInTxnDtl);
		}
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		return "ftzmis/FTZ210112_Auth_Qry_Dtl";
	}

	/**
	 * 批量信息审核
	 * @param model
	 * @param form
	 * @return forward:/FTZ210211/QryAuthDtl
	 */
	@RequestMapping("AuthDtlSubmit")
	@Transactional
	public String AuthDtlSubmit(Model model, FTZ210112Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210211Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210112/QryAuthDtl";
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210112/QryAuthDtl";
		}
		FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
		update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
		if ("1".equals(form.getAuthStat())) {
			update_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		} else if ("0".equals(form.getAuthStat())) {
			update_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}
		update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
		FtzInTxnDtl query_ftzInTxnDtl = new FtzInTxnDtl();
		query_ftzInTxnDtl.setMsgId(update_FtzInMsgCtl.getMsgId());
		query_ftzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		FtzInTxnDtl update_ftzInTxnDtl = ftz210211Serv.queryFtzInTxnDtl(query_ftzInTxnDtl);
		update_ftzInTxnDtl.setChkUserId(userInfo.getUserid());
		update_ftzInTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
		update_ftzInTxnDtl.setChkAddWord(form.getFtzInTxnDtl().getChkAddWord());
		if ("1".equals(form.getAuthStat())) {
			update_ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		} else if ("0".equals(form.getAuthStat())) {
			update_ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}
		int i = ftz210211Serv.updateFtzInMsgCtlAndTxnDtl(update_FtzInMsgCtl,update_ftzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
			return "ftzmis/FTZ210112_Auth_Qry_Dtl";
		} 
		if("1".equals(form.getAuthStat())){
			//提交报文信息
			ftzMsgProcService.submitMsg(result_FtzInMsgCtl.getMsgNo(),result_FtzInMsgCtl.getMsgId());
		}
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0010"));
		form.setAuthFinishFlag("1");
		return "forward:/FTZ210112/QryAuthDtl";
	}

	/**
	 * 效验输入
	 * @param ftzInTxnDtl
	 * @return ResultMessages
	 */
	private ResultMessages validDtl(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl) {
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();
		// 申请日期
		if (null == ftzInMsgCtl.getSubmitDate()
				|| "".equals(ftzInMsgCtl.getSubmitDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		// 账号
		if (null == ftzInMsgCtl.getAccountNo()
				|| "".equals(ftzInMsgCtl.getAccountNo().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0033");
			resultMessages.add(resultMessage);
		}

		// 资产负债指标代码
		if (null == ftzInMsgCtl.getBalanceCode()
				|| "".equals(ftzInMsgCtl.getBalanceCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0007");
			resultMessages.add(resultMessage);
		}

		// 货币
		if (null == ftzInMsgCtl.getCurrency()
				|| "".equals(ftzInMsgCtl.getCurrency().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0009");
			resultMessages.add(resultMessage);
		}

		// 日终余额
		if (null == ftzInMsgCtl.getBalance()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0010");
			resultMessages.add(resultMessage);
		}

		// 开户机构代码
		if (null == ftzInMsgCtl.getAccOrgCode()
				|| "".equals(ftzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210303.0002");
			resultMessages.add(resultMessage);
		}

		// 出/入账标志
		if (null == ftzInTxnDtl.getCdFlag()
				|| "".equals(ftzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}

		// 出账/入账效验
		if (CD_FLAG_3.equals(ftzInTxnDtl.getCdFlag())
				|| CD_FLAG_4.equals(ftzInTxnDtl.getCdFlag())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210112.0002");
			resultMessages.add(resultMessage);
		}

		// 轧差金额
		if (null == ftzInTxnDtl.getAmount()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210112.0001");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckAmount(ftzInTxnDtl.getAmount())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210211.0001");
				resultMessages.add(resultMessage);
			}	
		}
		return resultMessages;
	}
	
	@Autowired
	protected FTZ210211Service ftz210211Serv;

	@Autowired
	protected NumberService numberService;

	@Autowired
	private FtzMsgProcService ftzMsgProcService;
}
