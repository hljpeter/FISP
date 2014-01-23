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
import com.synesoft.ftzmis.app.model.FTZ210212Form;
import com.synesoft.ftzmis.app.model.FTZ210212Form.FTZ210212FormAddDtl;
import com.synesoft.ftzmis.app.model.FTZ210212Form.FTZ210212FormAddDtlDtl;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210212Service;

/**
 * @author Tangfire
 * @date 2014-01-11 下午13:16:39
 * @version 1.0
 * @description
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */

@Controller
@RequestMapping(value = "FTZ210212")
public class FTZ210212Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public FTZ210212Form setUpForm() {
		return new FTZ210212Form();
	}

	@RequestMapping("QryDtl")
	/**
	 * 查询外汇买卖批量明细
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210212_Qry_Dtl
	 */
	public String queryDtl(Model model, FTZ210212Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("外汇买卖批量查询开始...");
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			logger.info("查询无记录！");
			logger.info("外汇买卖批量查询结束...");
			return "ftzmis/FTZ210212_Qry_Dtl";
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
		Page<FtzInTxnDtl> page = ftz210212Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
		}
		logger.info("外汇买卖批量查询结束...");
		return "ftzmis/FTZ210212_Qry_Dtl";
	}

	/**
	 * 查询外汇买卖交易明细
	 * @param model
	 * @param form
	 * @return ftzmis/FTZ210212_Qry_Dtl_Dtl
	 */
	@RequestMapping("QryDtlDtl")
	public String queryDtlDtl(Model model, FTZ210212Form form) {
		logger.info("外汇买卖明细查询开始...");
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzInTxnDtl result_FtzInTxnDtl = ftz210212Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("查询无记录！");
			logger.info("外汇买卖明细查询结束...");
			return "ftzmis/FTZ210212_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getMakDatetime()));
		result_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getChkDatetime()));
		//查询对方银行或机构代码
		if (null != result_FtzInTxnDtl.getOppBankCode()
				|| !"".equals(result_FtzInTxnDtl.getOppBankCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzInTxnDtl.getOppBankCode());
			FtzBankCode result_FtzBankCode = ftz210212Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		logger.info("外汇买卖明细查询结束 ..");
		return "ftzmis/FTZ210212_Qry_Dtl_Dtl";
	}

	/**
	 * 删除外汇买卖批量信息
	 * @param model
	 * @param form
	 * @return forward:/FTZ210212/AddQry
	 */
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210212Form form) {
		logger.info("外汇买卖查询批量删除开始...");
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210212Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null != ftzInTxnDtls) {
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzInTxnDtl
						.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
							"e.ftzmis.210101.0035"));
					form.setSelected_msgId("");
					logger.info("单位存款查询批量删除结束...");
					return "forward:/FTZ210212/AddQry";
				}
			}
		}
		FtzInMsgCtl del_FtzInMsgCtl = new FtzInMsgCtl();
		del_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		//删除外汇买卖批量信息
		int i = ftz210212Serv.deleteFtzInMsgCtl(del_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
			logger.info("外汇买卖查询批量删除结束...");
			return "forward:/FTZ210212/AddQry";
		} 
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0006"));
		form.setSelected_msgId("");
		logger.info("外汇买卖查询批量删除结束...");
		return "forward:/FTZ210212/AddQry";
	}

	/**
	 * 查询外汇买卖批量列表信息
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210212_Input_Qry
	 */
	@RequestMapping("AddQry")
	public String queryAdd(Model model, FTZ210212Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("外汇买卖查询开始...");
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
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210212);

		// 查询批量列表信息
		Page<FtzInMsgCtl> page = ftz210212Serv.queryFtzInMsgCtlPageInput(
				pageable, query_FtzInMsgCtl);

		if (0 == page.getContent().size()) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("外汇买卖查询结束...");
			return "ftzmis/FTZ210212_Input_Qry";
		}
		List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
		for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
			ftzInMsgCtl.setSubmitDate(DateUtil
					.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
		}
		model.addAttribute("page", page);
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		return "ftzmis/FTZ210212_Input_Qry";
	}

	/**
	 * 新增批量页面初始信息
	 * @param model
	 * @param form
	 * @return ftzmis/FTZ210212_Input_Dtl
	 */
	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210212Form form) {
		form.setInput_flag("add");
		model.addAttribute("pageUrl", "/FTZ210212/AddDtlInit");
		return "ftzmis/FTZ210212_Input_Dtl";
	}

	/**
	 * 外汇买卖批量录入
	 * @param model
	 * @param form
	 * @param result
	 * @return ftzmis/FTZ210212_Input_Dtl
	 */
	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model,
			@Validated({ FTZ210212FormAddDtl.class }) FTZ210212Form form,
			BindingResult result) {
		logger.info("外汇买卖批量录入开始...");
		if (result.hasErrors()) {
			return "ftzmis/FTZ210212_Input_Dtl";
		}
		// 获取录入信息
		FtzInMsgCtl insert_FtzInMsgCtl = form.getFtzInMsgCtl();

		// 开始校验
		ResultMessages resultMessages = validMsgCtl(insert_FtzInMsgCtl);
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210212_Input_Dtl";
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
		insert_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210212);
		// 插入信息
		int i = ftz210212Serv.insertFtzInMsgCtl(insert_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210210.0001"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		logger.info("外汇买卖批量录入结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210212/UptDtlInit");
		return "ftzmis/FTZ210212_Input_Dtl";
	}

	@RequestMapping("DtlInitReflash")
	public String DtlInitReflash(Model model, FTZ210212Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			return "ftzmis/FTZ210212_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		form.setFtzInMsgCtl(ftzInMsgCtl);

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210212Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		return "ftzmis/FTZ210212_Input_Dtl";
	}

	/**
	 * 更新外汇买卖批量明细
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210212_Input_Dtl
	 */
	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210212Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("外汇买卖录入批量录入更新初始化开始...");
		// 检查批量是否存在
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("外汇买卖录入批量录入更新初始化结束...");
			return "ftzmis/FTZ210212_Input_Upt";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		if(ftzInMsgCtl.getBalanceCode() != null){
			ftzInMsgCtl.setBalanceCode(ftzInMsgCtl.getBalanceCode().trim());
		}
		form.setFtzInMsgCtl(ftzInMsgCtl);
		// 将查询数据放入form
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210212Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("外汇买卖录入批量录入更新初始化结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210212/UptDtlInit");
		return "ftzmis/FTZ210212_Input_Dtl";
	}

	/**
	 * 更新外汇买卖批量
	 * @param model
	 * @param form
	 * @param result
	 * @param pageable
	 * @return ftzmis/FTZ210212_Input_Dtl
	 */
	@RequestMapping("UptDtlSubmit")
	public String UptDtlSubmit(Model model,
			@Validated({ FTZ210212FormAddDtl.class }) FTZ210212Form form,
			BindingResult result, @PageableDefaults Pageable pageable) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210212_Input_Dtl";
		}
		FtzInMsgCtl update_FtzInMsgCtl = form.getFtzInMsgCtl();

		// 开始校验
		ResultMessages resultMessages = validMsgCtl(update_FtzInMsgCtl);
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			// 清空页面列表选择Key
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			model.addAttribute("pageUrl", "/FTZ210212/UptDtlInit");
			return "ftzmis/FTZ210212_Input_Dtl";
		}
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(update_FtzInMsgCtl.getMsgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210212Serv.queryFtzInTxnDtlPage(
				pageable, query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			}
			model.addAttribute("page", page);
		}

		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInMsgCtl.getSubmitDate()));
		int i = ftz210212Serv.updateFtzInMsgCtl(update_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			form.setFtzInMsgCtl(ftz210212Serv
					.queryFtzInMsgCtl(update_FtzInMsgCtl));
		}else{
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0002"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		model.addAttribute("pageUrl", "/FTZ210212/UptDtlInit");
		return "ftzmis/FTZ210212_Input_Dtl";
	}

	/**
	 * 提交批量信息
	 * @param model
	 * @param form
	 * @return forward:/FTZ210212/AddQry
	 */
	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210212Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(form.getSelected_msgId());

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210212Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null != ftzInTxnDtls && ftzInTxnDtls.size() > 0) {
			for (FtzInTxnDtl dtl : ftzInTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(dtl
						.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
							"e.ftzmis.210101.0030"));
					return "forward:/FTZ210212/AddQry";
				}
			}
		}
		int i = ftz210212Serv.updateFtzInMsgCtlForSubmit(ftzInMsgCtl);
		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210212/AddQry";
		}
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0007"));
		return "forward:/FTZ210212/AddQry";
	}

	/**
	 * 删除交易明细信息
	 * @param model
	 * @param form
	 * @return forward:/FTZ210212/UptDtlInit
	 */
	@RequestMapping("InputDtlDel")
	public String delDtlDtl(Model model, FTZ210212Form form) {
		FtzInTxnDtl del_FtzInTxnDtl = new FtzInTxnDtl();
		del_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		del_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		FtzInTxnDtl result_FtzInTxnDtl = ftz210212Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();
		if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(result_FtzInTxnDtl.getChkStatus())){
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210210.0002");
			resultMessages.add(resultMessage);
			model.addAttribute(resultMessages);
			return "forward:/FTZ210212/UptDtlInit";
		}

		int i = ftz210212Serv.deleteFtzInTxnDtl(del_FtzInTxnDtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_seqNo(null);
		} else {
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0005"));
			form.setSelected_seqNo(null);
			return "forward:/FTZ210212/UptDtlInit";
		}

		return "forward:/FTZ210212/UptDtlInit";
	}

	/**
	 * 新增明细画面初始信息
	 * @param model
	 * @param form
	 * @return ftzmis/FTZ210212_Input_Dtl_Dtl
	 */
	@RequestMapping("AddDtlDtlInit")
	public String AddDtlDtlInit(Model model, FTZ210212Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getSelected_msgId());
		form.setFtzInTxnDtl(ftzInTxnDtl);
		form.setSelected_msgId("");
		form.setInput_Dtl_flag("add");
		return "ftzmis/FTZ210212_Input_Dtl_Dtl";
	}

	/**
	 * 外汇买卖明细录入
	 * @param model
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping("AddDtlDtlSubmit")
	public String AddDtlDtlSubmit(Model model,
			@Validated({ FTZ210212FormAddDtlDtl.class }) FTZ210212Form form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}
		FtzInTxnDtl insert_FtzInTxnDtl = form.getFtzInTxnDtl();
		// 开始校验
		ResultMessages resultMessages = validTxnDtl(insert_FtzInTxnDtl);
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(insert_FtzInTxnDtl.getMsgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210212Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null == ftzInTxnDtls || ftzInTxnDtls.size() == 0) {
			insert_FtzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		} else {
			String seqNo = ftz210212Serv.queryTxnDtlMaxSeqNo(query_FtzInTxnDtl);
			insert_FtzInTxnDtl.setSeqNo(seqNo);
		}
		insert_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzInTxnDtl.getTranDate()));
		insert_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzInTxnDtl.getOrgTranDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		insert_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		insert_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = ftz210212Serv.insertFtzInTxnDtl(insert_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0003"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		form.setSelected_msgId(form.getFtzInTxnDtl().getMsgId());
		return "ftzmis/FTZ210212_Input_Dtl_Dtl";
	}

	/**
	 * 修改明细画面初始信息
	 * @param model
	 * @param form
	 * @param result
	 * @return ftzmis/FTZ210212_Input_Dtl_Dtl
	 */
	@RequestMapping("UptDtlDtlInit")
	public String UptDtlDtlInit(Model model, FTZ210212Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		form.setFather_makTime(result_FtzInMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzInMsgCtl.getChkDatetime());

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		FtzInTxnDtl result_FtzInTxnDtl = ftz210212Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getMakDatetime()));
		result_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getChkDatetime()));
		if (null != result_FtzInTxnDtl.getOppBankCode()
				|| !"".equals(result_FtzInTxnDtl.getOppBankCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzInTxnDtl.getOppBankCode());
			FtzBankCode result_FtzBankCode = ftz210212Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		form.setInput_Dtl_flag("upt");
		return "ftzmis/FTZ210212_Input_Dtl_Dtl";
	}

	/**
	 * 外汇买卖交易明细修改
	 * @param model
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping("UptDtlDtlSubmit")
	public String UptDtlDtlSubmit(Model model,
			@Validated({ FTZ210212FormAddDtlDtl.class }) FTZ210212Form form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}
		FtzInTxnDtl update_FtzInTxnDtl = form.getFtzInTxnDtl();
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(update_FtzInTxnDtl.getMsgId());
		query_FtzInTxnDtl.setSeqNo(update_FtzInTxnDtl.getSeqNo());
		FtzInTxnDtl result_FtzInTxnDtl = ftz210212Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);
		// 开始校验
		ResultMessages resultMessages = validTxnDtl(update_FtzInTxnDtl);
		if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(result_FtzInTxnDtl.getChkStatus())){
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210210.0001");
			resultMessages.add(resultMessage);
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if ((!form.getFather_makTime().equals(
				result_FtzInMsgCtl.getMakDatetime()) && null != result_FtzInMsgCtl
				.getMakDatetime())
				|| (!form.getFather_chkTime().equals(
						result_FtzInMsgCtl.getChkDatetime()) && null != result_FtzInMsgCtl
						.getChkDatetime())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0026"));
			return "ftzmis/FTZ210212_Input_Dtl_Dtl";
		}

		update_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getTranDate()));
		update_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getOrgTranDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		update_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl
						.getChkDatetime()));
		update_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = ftz210212Serv.updateFtzInTxnDtlSelective(update_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0004"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		return "ftzmis/FTZ210212_Input_Dtl_Dtl";
	}

	/**
	 * 查询审核信息
	 * @param model
	 * @param form
	 * @param pageable
	 * @return ftzmis/FTZ210212_Auth_Qry_Dtl
	 */
	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210212Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "ftzmis/FTZ210212_Auth_Qry_Dtl";
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
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询待审核数据
		if ("1".equals(form.getUnAuthFlag())) {
			query_FtzInTxnDtl
					.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		}
		Page<FtzInTxnDtl> page = ftz210212Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
		}
		return "ftzmis/FTZ210212_Auth_Qry_Dtl";
	}

	/**
	 * 批量信息审核
	 * @param model
	 * @param form
	 * @return forward:/FTZ210212/QryAuthDtl
	 */
	@RequestMapping("AuthDtlSubmit")
	@Transactional
	public String AuthDtlSubmit(Model model, FTZ210212Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210212/QryAuthDtl";
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210212/QryAuthDtl";
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210212Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null == ftzInTxnDtls || ftzInTxnDtls.isEmpty() || 0 == ftzInTxnDtls.size()) {
			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210212Serv.updateFtzInMsgCtl(update_FtzInMsgCtl);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210301.0008"));
			} else {
				//提交报文信息
				ftzMsgProcService.submitMsg(result_FtzInMsgCtl.getMsgNo(),result_FtzInMsgCtl.getMsgId());
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210212/QryAuthDtl";
			}
		} else {
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				String chkStatus = ftzInTxnDtl.getChkStatus();
				if (chkStatus.equals(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED)||
						chkStatus.equals(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
					model.addAttribute(ResultMessages.error().add(
							"i.ftzmis.210210.0009"));
					return "forward:/FTZ210212/QryAuthDtl";
				}
			}
			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setRsv2(update_FtzInMsgCtl.getChkDatetime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210212Serv.updateFtzInMsgCtl(update_FtzInMsgCtl);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210301.0008"));
			} else {
				//提交报文信息
				ftzMsgProcService.submitMsg(result_FtzInMsgCtl.getMsgNo(),result_FtzInMsgCtl.getMsgId());
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210210.0010"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210212/QryAuthDtl";
			}
		}

		return "ftzmis/FTZ210212_Auth_Qry_Dtl";
	}

	/**
	 * 查询审核交易明细信息
	 * @param model
	 * @param form
	 * @return ftzmis/FTZ210212_Auth_Qry_Dtl_Dtl
	 */
	@RequestMapping("QryAuthDtlDtl")
	public String queryAuthDtlDtl(Model model, FTZ210212Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		form.setFather_makTime(result_FtzInMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzInMsgCtl.getChkDatetime());

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		FtzInTxnDtl result_FtzInTxnDtl = ftz210212Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);
		if (null == result_FtzInTxnDtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210212_Auth_Qry_Dtl_Dtl";
		}
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getMakDatetime()));
		result_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getChkDatetime()));
		if (null != result_FtzInTxnDtl.getOppBankCode()
				|| !"".equals(result_FtzInTxnDtl.getOppBankCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzInTxnDtl.getOppBankCode());
			FtzBankCode result_FtzBankCode = ftz210212Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		return "ftzmis/FTZ210212_Auth_Qry_Dtl_Dtl";
	}

	/**
	 * 审核交易明细
	 * @param model
	 * @param form
	 * @return forward:/FTZ210212/QryAuthDtlDtl
	 */
	@RequestMapping("AuthDtlDtlSubmit")
	public String AuthDtlDtlSubmit(Model model, FTZ210212Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		ftzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210212Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if ((!form.getFather_makTime().equals(
				result_FtzInMsgCtl.getMakDatetime()) && null != result_FtzInMsgCtl
				.getMakDatetime())
				|| (!form.getFather_chkTime().equals(
						result_FtzInMsgCtl.getChkDatetime()) && null != result_FtzInMsgCtl
						.getChkDatetime())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0026"));
			return "forward:/FTZ210212/QryAuthDtlDtl?selected_msgId="
					+ form.getFtzInTxnDtl().getMsgId() + "&selected_seqNo="
					+ form.getFtzInTxnDtl().getSeqNo();
		}

		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(form.getFtzInTxnDtl().getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210212/QryAuthDtlDtl?selected_msgId="
					+ form.getFtzInTxnDtl().getMsgId() + "&selected_seqNo="
					+ form.getFtzInTxnDtl().getSeqNo();
		}
		ftzInTxnDtl.setChkUserId(userInfo.getUserid());
		ftzInTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
		ftzInTxnDtl.setChkAddWord(form.getFtzInTxnDtl().getChkAddWord());

		if ("1".equals(form.getAuthStat())) {
			ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		} else if ("0".equals(form.getAuthStat())) {
			ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}
		int i = ftz210212Serv.updateFtzInTxnDtlSelective(ftzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210210.0010"));
			form.setAuthFinishFlag("1");
		}

		return "forward:/FTZ210212/QryAuthDtlDtl?selected_msgId="
				+ form.getFtzInTxnDtl().getMsgId() + "&selected_seqNo="
				+ form.getFtzInTxnDtl().getSeqNo();
	}
	
	/**
	 * 效验批量输入
	 * @param ftzInTxnDtl
	 * @return ResultMessages
	 */
	private ResultMessages validMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
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

		// 所属机构代码
		if (null == ftzInMsgCtl.getAccOrgCode()
				|| "".equals(ftzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210303.0002");
			resultMessages.add(resultMessage);
		}
		return resultMessages;
	}
	
	/**
	 * 效验交易明细输入
	 * @param ftzInTxnDtl
	 * @return ResultMessages
	 */
	private ResultMessages validTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();

		// 出/入账标志
		if (null == ftzInTxnDtl.getCdFlag()
				|| "".equals(ftzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}
		// 当出入账标志为3或4时，本栏位为强制项，且栏位值小于等于记账日期
		if ("3".equals(ftzInTxnDtl.getCdFlag().trim())
				|| "4".equals(ftzInTxnDtl.getCdFlag().trim())) {
			if (null == ftzInTxnDtl.getOrgTranDate()
					|| "".equals(ftzInTxnDtl.getOrgTranDate().trim())
					|| ((null != ftzInTxnDtl.getTranDate()) && DateUtil
							.getFormatDateRemoveSprit(
									ftzInTxnDtl.getTranDate())
							.compareToIgnoreCase(
									DateUtil.getFormatDateRemoveSprit(ftzInTxnDtl
											.getOrgTranDate())) < 0)) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210101.0027");
				resultMessages.add(resultMessage);
			}
		}

		// 记帐日期
		if (null == ftzInTxnDtl.getTranDate()
				|| "".equals(ftzInTxnDtl.getTranDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0014");
			resultMessages.add(resultMessage);
		}

		// 外汇买卖类型
		if (null == ftzInTxnDtl.getExchangeType()
				|| "".equals(ftzInTxnDtl.getExchangeType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210311.0001");
			resultMessages.add(resultMessage);
		}

		// 买入币种
		if (null == ftzInTxnDtl.getBuyCurr()
				|| "".equals(ftzInTxnDtl.getBuyCurr().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0002");
			resultMessages.add(resultMessage);
		}

		// 卖出币种
		if (null == ftzInTxnDtl.getSellCurr()
				|| "".equals(ftzInTxnDtl.getSellCurr().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0003");
			resultMessages.add(resultMessage);
		}

		// 买入金额
		if (null == ftzInTxnDtl.getBuyAmt()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0004");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckAmount(ftzInTxnDtl.getBuyAmt())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210212.0001");
				resultMessages.add(resultMessage);
			}
		}

		// 卖出金额
		if (null == ftzInTxnDtl.getSellAmt()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0005");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckAmount(ftzInTxnDtl.getSellAmt())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210212.0003");
				resultMessages.add(resultMessage);
			}
		}

		// 买入牌价
		if (null == ftzInTxnDtl.getBuyRate()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0006");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckRate(ftzInTxnDtl.getBuyRate())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210212.0002");
				resultMessages.add(resultMessage);
			}
		}

		// 卖出牌价
		if (null == ftzInTxnDtl.getSellRate()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0007");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckRate(ftzInTxnDtl.getSellRate())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210212.0004");
				resultMessages.add(resultMessage);
			}
		}

		// 国别代码
		if (null == ftzInTxnDtl.getCountryCode()
				|| "".equals(ftzInTxnDtl.getCountryCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0016");
			resultMessages.add(resultMessage);
		}

		// 交易性质
		if (null == ftzInTxnDtl.getTranType()
				|| "".equals(ftzInTxnDtl.getTranType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0017");
			resultMessages.add(resultMessage);
		}

		// 对方账号
		if (null == ftzInTxnDtl.getOppAccount()
				|| "".equals(ftzInTxnDtl.getOppAccount().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0010");
			resultMessages.add(resultMessage);
		}

		// 对方户名
		if (null == ftzInTxnDtl.getOppName()
				|| "".equals(ftzInTxnDtl.getOppName().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0011");
			resultMessages.add(resultMessage);
		}
		return resultMessages;
	}

	@Autowired
	protected FTZ210212Service ftz210212Serv;

	@Autowired
	protected NumberService numberService;

	@Autowired
	private FtzMsgProcService ftzMsgProcService;
}
