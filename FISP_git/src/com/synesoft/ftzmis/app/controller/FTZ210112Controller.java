package com.synesoft.ftzmis.app.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.xmlproc.GenerateXml;
import com.synesoft.ftzmis.app.common.xmlproc.MsgHead;
import com.synesoft.ftzmis.app.model.FTZ210101Form;
import com.synesoft.ftzmis.app.model.FTZ210112Form;
import com.synesoft.ftzmis.app.model.FTZ210112Form.FTZ210112FormAddDtlDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210112Service;

@Controller
@RequestMapping(value = "FTZ210112")
public class FTZ210112Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public FTZ210112Form setUpForm() {
		return new FTZ210112Form();
	}
	//录入完成
	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210112Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210112Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null != ftzInTxnDtls && ftzInTxnDtls.size() > 0) {
			for (FtzInTxnDtl dtl : ftzInTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(dtl
						.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
							"e.ftzmis.210101.0030"));
					return "forward:/FTZ210112/AddQry";
				}
			}
		}
		int i = ftz210112Serv.updateFtzInMsgCtlForSubmit(ftzInMsgCtl);

		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210112/AddQry";
		}

		model.addAttribute(ResultMessages.success().add("i.ftzmis.210301.0009"));
		return "forward:/FTZ210112/AddQry";
	}
	
	@RequestMapping("AuthDtlSubmitResu")
	public String AuthDtlDtlSubmit(Model model, FTZ210112Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getSelected_msgId());
		ftzInTxnDtl.setSeqNo("1");


		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(form.getFtzInTxnDtl().getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210112/QryAuthDtl?selected_msgId="
					+ form.getSelected_msgId();
		}
		ftzInTxnDtl.setChkUserId(userInfo.getUserid());
		ftzInTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
		ftzInTxnDtl.setChkAddWord(form.getFtzInTxnDtl().getChkAddWord());

		ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		int i = ftz210112Serv.updateFtzInTxnDtlSelective(ftzInTxnDtl);
	
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210501.0021"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210210.0010"));
			form.setAuthFinishFlag("1");
		}
		return "forward:/FTZ210112/QryAuthDtl";
	}
	
	@RequestMapping("AuthDtlSubmit")
	public String AuthDtlSubmit(Model model, FTZ210112Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210112Serv.queryFtzInMsgCtl(query_FtzInMsgCtl);
		UserInf userInfo = ContextConst.getCurrentUser();
		if(userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())){
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210112/QryAuthDtl";
		}
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210112/QryAuthDtl";
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210112Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
			int count_unAuth = 0;
			int count_authFail = 0;
			StringBuffer sb_unAuth = new StringBuffer();
			StringBuffer sb_authFail = new StringBuffer();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				String chkStatus = ftzInTxnDtl.getChkStatus();
				if (chkStatus
						.equals(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED)) {
					count_unAuth++;
					sb_unAuth.append(ftzInTxnDtl.getSeqNo().toString() + ",");
					ftzInTxnDtl.setChkUserId(userInfo.getUserid());
					ftzInTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
					ftzInTxnDtl.setChkAddWord(form.getFtzInTxnDtl().getChkAddWord());
					ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
					int j  = ftz210112Serv.updateFtzInTxnDtlSelective(ftzInTxnDtl);
				}
				if (chkStatus.equals(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
					count_authFail++;
					sb_authFail.append(ftzInTxnDtl.getSeqNo().toString() + ",");
				}
			}

			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			if (count_authFail > 0) {
				update_FtzInMsgCtl
						.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			} else {
				update_FtzInMsgCtl
						.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			}

			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl.setMsgNo(form.getSelected_msgNo());
			
			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setRsv2(update_FtzInMsgCtl.getChkDatetime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210112Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210301.0008"));
			} else {
				if(update_FtzInMsgCtl.getMsgStatus().equals(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)){
					generateXml.writeXml(update_FtzInMsgCtl.getMsgNo(),update_FtzInMsgCtl.getMsgId());
				}
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210112/QryAuthDtl";
			}
	//	}

		return "ftzmis/FTZ210112_Auth_Qry_Dtl";
	}
	@Resource
	protected GenerateXml generateXml;
	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210112Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "ftzmis/FTZ210112_Auth_Qry_Dtl";
		} else {
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
			query_FtzInTxnDtl.setSeqNo("1");
			query_FtzInTxnDtl = ftz210112Serv.queryFtzInTxnDtl(query_FtzInTxnDtl);//查询明细信息放入form
			form.setFtzInTxnDtl(query_FtzInTxnDtl);
			
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			// 查询待审核数据
			if ("1".equals(form.getUnAuthFlag())) {
				query_FtzInTxnDtl
						.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
				Page<FtzInTxnDtl> page = ftz210112Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
						ftzInTxnDtl.setMakDatetime(DateUtil
								.getFormatDateTimeAddSpritAndColon(ftzInTxnDtl
										.getMakDatetime()));
						ftzInTxnDtl.setChkDatetime(DateUtil
								.getFormatDateTimeAddSpritAndColon(ftzInTxnDtl
										.getChkDatetime()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}
			// 查询全部数据
			else {
				Page<FtzInTxnDtl> page = ftz210112Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
						ftzInTxnDtl.setMakDatetime(DateUtil
								.getFormatDateTimeAddSpritAndColon(ftzInTxnDtl
										.getMakDatetime()));
						ftzInTxnDtl.setChkDatetime(DateUtil
								.getFormatDateTimeAddSpritAndColon(ftzInTxnDtl
										.getChkDatetime()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}
			query_FtzInTxnDtl.setMakDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(query_FtzInTxnDtl
							.getMakDatetime()));
			query_FtzInTxnDtl.setChkDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(query_FtzInTxnDtl
							.getChkDatetime()));
			return "ftzmis/FTZ210112_Auth_Qry_Dtl";
		}
	}

	//修改批量
	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备录入批量录入更新初始化开始...");
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		
		
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210112Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("各项准备录入批量录入更新初始化结束...");
			return "ftzmis/FTZ210112_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		form.setFtzInMsgCtl(ftzInMsgCtl);//查询表头信息放入FORM
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo("1");
		query_FtzInTxnDtl = ftz210112Serv.queryFtzInTxnDtl(query_FtzInTxnDtl);//查询明细信息放入form
		form.setFtzInTxnDtl(query_FtzInTxnDtl);
		form.getFtzInMsgCtl().setBalanceCode(form.getFtzInMsgCtl().getBalanceCode().trim());

		form.setSelected_msgId("");		// 清空页面列表选择Key
		form.setSelected_seqNo(null);
		logger.info("各项准备录入批量录入更新初始化结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}
	//批量删除
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210112Form form) {
		logger.info("各项准备删除开始...");
		FtzInMsgCtl del_FtzInMsgCtl = new FtzInMsgCtl();
		del_FtzInMsgCtl.setMsgId(form.getSelected_msgId());

		int i = ftz210112Serv.deleteFtzInMsgCtl(del_FtzInMsgCtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210210.0006"));
			form.setSelected_msgId("");
			logger.info("各项准备批量删除结束...");
			return "forward:/FTZ210112/AddQry";
		}

		logger.info("各项准备批量删除结束...");
		return "forward:/FTZ210112/AddQry";
	}
	@RequestMapping("AddQry")
	public String queryAdd(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备录入查询开始...");
		
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzInMsgCtl.setAccountName(form.getQuery_accountName());
		query_FtzInMsgCtl.setAccountNo(form.getQuery_accountNo());
		query_FtzInMsgCtl.setSubAccountNo(form.getQuery_subAccountNo());
		query_FtzInMsgCtl.setRsv1(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start()));
		query_FtzInMsgCtl.setRsv2(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_end()));
		query_FtzInMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210112);

		// query DpMppCfg page list
		Page<FtzInMsgCtl> page = ftz210112Serv.queryFtzInMsgCtlPageInput(
				pageable, query_FtzInMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("各项准备录入查询结束...");
			return "ftzmis/FTZ210112_Input_Qry";
		}
		logger.info("各项准备录入查询结束...");
		return "ftzmis/FTZ210112_Input_Qry";
	}

	//新增 批量
	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210112Form form) {
		form.setInput_flag("add");
		model.addAttribute("pageUrl", "/FTZ210112/AddDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}
	//提交批量
	@RequestMapping("UptDtlSubmit")
	public String UptDtlSubmit(Model model, FTZ210112Form form, BindingResult result) {
		FtzInMsgCtl update_FtzInMsgCtl = form.getFtzInMsgCtl();
		FtzInTxnDtl update_FtzInTxnDtl = form.getFtzInTxnDtl();
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();
		// 申请日期
		if (null == update_FtzInMsgCtl.getSubmitDate()
				|| "".equals(update_FtzInMsgCtl.getSubmitDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		// 主账号
		if (null == update_FtzInMsgCtl.getAccountNo()
				|| "".equals(update_FtzInMsgCtl.getAccountNo().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0002");
			resultMessages.add(resultMessage);
		}

		// 日终余额
		if (null == update_FtzInMsgCtl.getBalance()
				|| update_FtzInMsgCtl.getBalance().compareTo(BigDecimal.ZERO) != 1) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0010");
			resultMessages.add(resultMessage);
		}
//		// 资产负债指标代码
		if (null == update_FtzInMsgCtl.getBalanceCode()
				|| "".equals(update_FtzInMsgCtl.getBalanceCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0007");
			resultMessages.add(resultMessage);
		}
		// 出/入账标志
		if (null == update_FtzInTxnDtl.getCdFlag()
				|| "".equals(update_FtzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}
		if ("3".equals(update_FtzInTxnDtl.getCdFlag().trim())
				|| "4".equals(update_FtzInTxnDtl.getCdFlag().trim())) {
			{
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210112.0002");
				resultMessages.add(resultMessage);
			}
		}
		// 金额
		if (null == update_FtzInTxnDtl.getAmount()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210112.0001");
			resultMessages.add(resultMessage);
		}

//		// 开户机构代码
		if (null == update_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(update_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0003");
			resultMessages.add(resultMessage);
		}
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
		update_FtzInMsgCtl.setRsv1(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInMsgCtl
						.getMakDatetime()));
		update_FtzInMsgCtl.setRsv2(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInMsgCtl
						.getChkDatetime()));
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInMsgCtl.getSubmitDate()));
		int i = ftz210112Serv.updateFtzInMsgCtl(update_FtzInMsgCtl,null);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		}
		
		update_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		update_FtzInTxnDtl.setSeqNo("1");
		update_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
		update_FtzInTxnDtl.setRsv1(DateUtil.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl.getMakDatetime()));
		update_FtzInTxnDtl.setRsv2(DateUtil.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl.getChkDatetime()));
		update_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int j = ftz210112Serv.updateFtzInTxnDtlSelective(update_FtzInTxnDtl);
		if (j < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.ftzmis.210301.0001"));
		}
		
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}
	//批量明细
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210112Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("各项准备批量查询开始...");
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210112Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			logger.error("查询无记录！");
			logger.info("各项准备批量查询结束...");
			return "ftzmis/FTZ210112_Qry";
		} else {
			// 有数据则进行数据转换，查询明细数据
			result_FtzInMsgCtl.setSubmitDate(DateUtil
					.getFormatDateAddSprit(result_FtzInMsgCtl.getSubmitDate()));
			result_FtzInMsgCtl.setSndDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
							.getSndDatetime()));
			result_FtzInMsgCtl.setAckDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(result_FtzInMsgCtl
							.getAckDatetime()));
			result_FtzInMsgCtl.setBalanceCode(result_FtzInMsgCtl.getBalanceCode().trim());
			form.setFtzInMsgCtl(result_FtzInMsgCtl);
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			query_FtzInTxnDtl.setSeqNo("1");
			query_FtzInTxnDtl = ftz210112Serv.queryFtzInTxnDtl(query_FtzInTxnDtl);//查询明细信息放入form
		
			form.setFtzInTxnDtl(query_FtzInTxnDtl);
			logger.info("各项准备批量查询结束...");
			return "ftzmis/FTZ210112_Qry";
		}
	}
	//录入
	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model,@Validated({ FTZ210112FormAddDtlDtl.class })FTZ210112Form form, BindingResult result) {
		logger.info("各项准备录入批量录入开始...");

		// 获取录入信息
		FtzInMsgCtl insert_FtzInMsgCtl = form.getFtzInMsgCtl();
		FtzInTxnDtl issert_FtzInTxnDtl = new FtzInTxnDtl();
		issert_FtzInTxnDtl = form.getFtzInTxnDtl();
		
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();
		// 申请日期
		if (null == insert_FtzInMsgCtl.getSubmitDate()
				|| "".equals(insert_FtzInMsgCtl.getSubmitDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		// 主账号
		if (null == insert_FtzInMsgCtl.getAccountNo()
				|| "".equals(insert_FtzInMsgCtl.getAccountNo().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210112.0003");
			resultMessages.add(resultMessage);
		}
//		// 货币
		if (null == insert_FtzInMsgCtl.getCurrency()
				|| "".equals(insert_FtzInMsgCtl.getCurrency().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0009");
			resultMessages.add(resultMessage);
		}
		// 日终余额
		if (null == insert_FtzInMsgCtl.getBalance()
				|| insert_FtzInMsgCtl.getBalance().compareTo(BigDecimal.ZERO) != 1) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0010");
			resultMessages.add(resultMessage);
		}
//		// 资产负债指标代码
		if (null == insert_FtzInMsgCtl.getBalanceCode()
				|| "".equals(insert_FtzInMsgCtl.getBalanceCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0007");
			resultMessages.add(resultMessage);
		}
		// 出/入账标志
		if (null == issert_FtzInTxnDtl.getCdFlag()
				|| "".equals(issert_FtzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}
		if ("3".equals(issert_FtzInTxnDtl.getCdFlag().trim())
				|| "4".equals(issert_FtzInTxnDtl.getCdFlag().trim())) {
			{
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210112.0002");
				resultMessages.add(resultMessage);
			}
		}
		
		// 金额
		if (null == issert_FtzInTxnDtl.getAmount()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0015");
			resultMessages.add(resultMessage);
		}

//		// 开户机构代码
		if (null == insert_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(insert_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0003");
			resultMessages.add(resultMessage);
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210112_Input_Dtl";
		}		

		insert_FtzInMsgCtl.setMsgId(numberService.getSysIDSequence(16));
		insert_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzInMsgCtl.getSubmitDate()));
		
		// 设置批量头信息
		MsgHead mh = MsgHead.getMsgHead();
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
		// 插入信息
		int j = ftz210112Serv.insertFtzInMsgCtl(insert_FtzInMsgCtl);
		if (j < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		}
		
		
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(insert_FtzInMsgCtl.getMsgId());
		issert_FtzInTxnDtl.setMsgId(insert_FtzInMsgCtl.getMsgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210112Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null == ftzInTxnDtls || ftzInTxnDtls.size() == 0) {
			issert_FtzInTxnDtl.setSeqNo("1");
		} else {
			FtzInTxnDtl ftzInTxnDtl_tmp = ftzInTxnDtls
					.get(ftzInTxnDtls.size() - 1);
			issert_FtzInTxnDtl.setSeqNo(ftzInTxnDtl_tmp.getSeqNo() + 1);
		}
		
		issert_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getTranDate()));
		issert_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getValueDate()));
		issert_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getOrgTranDate()));
		issert_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getExpireDate()));

		issert_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		issert_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateRemoveSprit(DateUtil.getNowInputDateTime().substring(0, 8)));
		issert_FtzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = 0;
		try{
			i = ftz210112Serv.insertFtzInTxnDtl(issert_FtzInTxnDtl);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			
		}
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Add.Msg.Ctl.Success"));
		}
		
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		logger.info("各项准备录入批量录入结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210112/UptDtlInit");
		return "ftzmis/FTZ210112_Input_Dtl";
	}
	
	@Resource
	protected FTZ210112Service ftz210112Serv;


	@Resource
	protected NumberService numberService;

}
