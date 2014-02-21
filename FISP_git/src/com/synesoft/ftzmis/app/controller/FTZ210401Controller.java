package com.synesoft.ftzmis.app.controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.dataproc.service.ProcCommonService;
import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.fisp.domain.service.dp.DP_Mpp_Service;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgHead;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.util.Validator;
import com.synesoft.ftzmis.app.model.FTZ210401Form;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210401Service;

@Controller
@RequestMapping(value="FTZ210401")
public class FTZ210401Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public FTZ210401Form setForm() {
		return new FTZ210401Form();
	}

	// 表外理财查询
	@RequestMapping("Qry")
	public String query(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("表外理财查询开始...");

		// trans form to queryObject
		// 转换查询条件
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzInMsgCtl.setRsv1(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start()));
		query_FtzInMsgCtl.setRsv2(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_end()));
		query_FtzInMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210401);
		
		// query DpMppCfg page list
		// 查询翻页数据
		Page<FtzInMsgCtl> page = ftz210401Serv.queryFtzInMsgCtlPage(pageable,
				query_FtzInMsgCtl);
		// 若有数据则放入model
		if (page.getContent().size() > 0) {
			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			// 没有数据则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("表外理财查询结束...");
			return "ftzmis/FTZ210401_Qry";
		}
		logger.info("表外理财查询结束...");
		return "ftzmis/FTZ210401_Qry";
	}

	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("表外理财批量查询开始...");
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			logger.error("查询无记录！");
			logger.info("表外理财批量查询结束...");
			return "ftzmis/FTZ210401_Qry";
		} else {
			// 有数据则进行数据转换，查询明细数据
			result_FtzInMsgCtl.setSubmitDate(DateUtil
					.getFormatDateAddSprit(result_FtzInMsgCtl.getSubmitDate()));
			form.setFtzInMsgCtl(result_FtzInMsgCtl);
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(
					pageable, query_FtzInTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
				for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
					ftzInTxnDtl.setTranDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
					ftzInTxnDtl.setValueDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
					ftzInTxnDtl.setExpireDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
				}
				model.addAttribute("page", page);
				form.setSelected_msgId("");
				form.setSelected_seqNo("");
			}
			logger.info("表外理财批量查询结束...");
			return "ftzmis/FTZ210401_Qry_Dtl";
		}
	}

	@RequestMapping("QryDtlDtl")
	public String queryDtlDtl(Model model, FTZ210401Form form) {
		logger.info("表外理财明细查询开始...");
		// 组装查询信息
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzInTxnDtl result_FtzInTxnDtl = ftz210401Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("表外理财明细查询结束...");
			return "ftzmis/FTZ210401_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
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
			FtzBankCode result_FtzBankCode = ftz210401Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		logger.info("表外理财明细查询结束 ..");
		return "ftzmis/FTZ210401_Qry_Dtl_Dtl";
	}

	@RequestMapping("AddQry")
	public String queryAdd(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("表外理财录入查询开始...");
		
		OrgInf orgInf = new OrgInf();
		
		UserInf userInfo = ContextConst.getCurrentUser();
		orgInf.setRsv1(userInfo.getUserid());
		List<OrgInf> orgList = dp_Mpp_Service.queryOrgInfPage(pageable, orgInf).getContent();
		model.addAttribute("orgList", orgList);
		
		// trans form to queryObject
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzInMsgCtl.setAccountNo(form.getQuery_accountNo());
		query_FtzInMsgCtl.setRsv1(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start()));
		query_FtzInMsgCtl.setRsv2(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_end()));
		query_FtzInMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210401);
		query_FtzInMsgCtl.setMsgStatuss(new String[]{CommonConst.FTZ_MSG_STATUS_AUTH_FAIL,
				CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, CommonConst.FTZ_MSG_STATUS_INPUTING});

		// query DpMppCfg page list
		Page<FtzInMsgCtl> page = ftz210401Serv.queryFtzInMsgCtlPageInput(
				pageable, query_FtzInMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("表外理财录入查询结束...");
			return "ftzmis/FTZ210401_Input_Qry";
		}
		logger.info("表外理财录入查询结束...");
		return "ftzmis/FTZ210401_Input_Qry";
	}

	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210401Form form) {
		logger.info("表外理财查询批量删除开始...");
		FtzInMsgCtl del_FtzInMsgCtl = new FtzInMsgCtl();
		del_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210401Serv.
				queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null != ftzInTxnDtls) {
			for(FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(ftzInTxnDtl
						.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0035"));
					form.setSelected_msgId("");
					return "forward:/FTZ210310/AddQry";
				}
			}
		}
		
		//查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv.queryFtzInMsgCtl(del_FtzInMsgCtl);
		if (!CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl.getMsgStatus()) 
				&& !CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED.equals(result_FtzInMsgCtl.getMsgStatus())
				&& !CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(
						result_FtzInMsgCtl.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
			"e.ftzmis.210101.0036"));
			form.setSelected_msgId("");
			return "forward:/FTZ210310/AddQry";
		}
		int i = ftz210401Serv.deleteFtzInMsgCtl(del_FtzInMsgCtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Delete.Msg.Ctl.Success"));
			form.setSelected_msgId("");
			logger.info("表外理财查询批量删除结束...");
			return "forward:/FTZ210401/AddQry";
		}

		logger.info("表外理财查询批量删除结束...");
		return "forward:/FTZ210401/AddQry";
	}

	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210401Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		form.setFtzInMsgCtl(ftzInMsgCtl);
		form.setInput_flag("add");
		model.addAttribute("pageUrl", "/FTZ210401/AddDtlInit");
		return "ftzmis/FTZ210401_Input_Dtl";
	}

	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model, FTZ210401Form form) {
		logger.info("表外理财录入批量录入开始...");

		// 获取录入信息
		FtzInMsgCtl insert_FtzInMsgCtl = form.getFtzInMsgCtl();

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
					.fromCode("e.ftzmis.210101.0002");
			resultMessages.add(resultMessage);
		}

		// 货币
		if (null == insert_FtzInMsgCtl.getCurrency()
				|| "".equals(insert_FtzInMsgCtl.getCurrency().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0009");
			resultMessages.add(resultMessage);
		}

		// 日终余额
		if (null == insert_FtzInMsgCtl.getBalance()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0010");
			resultMessages.add(resultMessage);
		}
		
		// 资产负债指标代码
		if (null == insert_FtzInMsgCtl.getBalanceCode()
				|| "".equals(insert_FtzInMsgCtl.getBalanceCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0007");
			resultMessages.add(resultMessage);
		}

		// 所属机构代码
		if (null == insert_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(insert_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0011");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210401_Input_Dtl";
		}

		insert_FtzInMsgCtl.setMsgId(numberService.getSysIDSequence("", 8));
		insert_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzInMsgCtl.getSubmitDate()));
		insert_FtzInMsgCtl.setWorkDate(procCommonService.queryWorkDate());

		UserInf userInfo = ContextConst.getCurrentUser();
		insert_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		insert_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzInMsgCtl.setTotalCount(0);
		insert_FtzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		insert_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210401);
		// 插入信息
		int i = ftz210401Serv.insertFtzInMsgCtl(insert_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"ftzmis.Add.Msg.Ctl.Success"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		logger.info("表外理财录入批量录入结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210401/UptDtlInit");
		return "ftzmis/FTZ210401_Input_Dtl";
	}

	@RequestMapping("DtlInitReflash")
	public String DtlInitReflash(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			return "ftzmis/FTZ210401_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		ftzInMsgCtl.setBalanceCode(ftzInMsgCtl.getBalanceCode().trim());
		form.setFtzInMsgCtl(ftzInMsgCtl);

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
				ftzInTxnDtl.setValueDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
				ftzInTxnDtl.setExpireDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		return "ftzmis/FTZ210401_Input_Dtl";
	}

	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("表外理财录入批量录入更新初始化开始...");
		// 检查批量是否存在
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("表外理财录入批量录入更新初始化结束...");
			return "ftzmis/FTZ210401_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		ftzInMsgCtl.setBalanceCode(ftzInMsgCtl.getBalanceCode().trim());
		form.setFtzInMsgCtl(ftzInMsgCtl);
		// 将查询数据放入form
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
				ftzInTxnDtl.setValueDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
				ftzInTxnDtl.setExpireDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		logger.info("表外理财录入批量录入更新初始化结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210401/UptDtlInit");
		return "ftzmis/FTZ210401_Input_Dtl";
	}

	@RequestMapping("UptDtlSubmit")
	public String UptDtlSubmit(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl update_FtzInMsgCtl = form.getFtzInMsgCtl();

		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();
		// 申请日期
		if (null == update_FtzInMsgCtl.getSubmitDate()
				|| "".equals(update_FtzInMsgCtl.getSubmitDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		// 账号
		if (null == update_FtzInMsgCtl.getAccountNo()
				|| "".equals(update_FtzInMsgCtl.getAccountNo().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0002");
			resultMessages.add(resultMessage);
		}

		// 资产负债指标代码
		if (null == update_FtzInMsgCtl.getBalanceCode()
				|| "".equals(update_FtzInMsgCtl.getBalanceCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0007");
			resultMessages.add(resultMessage);
		}

		// 货币
		if (null == update_FtzInMsgCtl.getCurrency()
				|| "".equals(update_FtzInMsgCtl.getCurrency().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0009");
			resultMessages.add(resultMessage);
		}

		// 日终余额
		if (null == update_FtzInMsgCtl.getBalance()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0010");
			resultMessages.add(resultMessage);
		}else{
			if (!Validator.CheckAmount(update_FtzInMsgCtl.getBalance())) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210303.0013");
				resultMessages.add(resultMessage);
			}
		}

		// 所属机构代码
		if (null == update_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(update_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0011");
			resultMessages.add(resultMessage);
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			// 清空页面列表选择Key
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			model.addAttribute("pageUrl", "/FTZ210401/UptDtlInit");
			return "ftzmis/FTZ210401_Input_Dtl";
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
		int i = ftz210401Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			form.setFtzInMsgCtl(ftz210401Serv
					.queryFtzInMsgCtl(update_FtzInMsgCtl));
			form.getFtzInMsgCtl().setSubmitDate(
					DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
							.getSubmitDate()));
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(update_FtzInMsgCtl.getMsgId());
			// 查询明细数据列表
			Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(
					pageable, query_FtzInTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
				for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
					ftzInTxnDtl.setTranDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
					ftzInTxnDtl.setValueDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
					ftzInTxnDtl.setExpireDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
				}
				model.addAttribute("page", page);
			}
			// 清空页面列表选择Key
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			model.addAttribute("pageUrl", "/FTZ210401/UptDtlInit");
			return "ftzmis/FTZ210401_Input_Dtl";
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Update.Msg.Ctl.Success"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		form.setSelected_msgId("");
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(pageable,
				query_FtzInTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
			for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
				ftzInTxnDtl.setValueDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
				ftzInTxnDtl.setExpireDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		model.addAttribute("pageUrl", "/FTZ210401/UptDtlInit");
		return "ftzmis/FTZ210401_Input_Dtl";
	}

	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210401Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210401Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null != ftzInTxnDtls && ftzInTxnDtls.size() > 0) {
			for (FtzInTxnDtl dtl : ftzInTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(dtl
						.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
							"e.ftzmis.210101.0030"));
					return "forward:/FTZ210401/AddQry";
				}
			}
		}
		int i = ftz210401Serv.updateFtzInMsgCtlForSubmit(ftzInMsgCtl);

		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210401/AddQry";
		}

		model.addAttribute(ResultMessages.success().add("ftzmis.Submit.Msg.Ctl.Success"));
		return "forward:/FTZ210401/AddQry";
	}

	@RequestMapping("DtlAccountQry")
	public @ResponseBody
	String DtlAccountQry(@RequestParam("accountNo") String accountNo,
			@RequestParam("subAccountNo") String subAccountNo, Model model) {
		JSONObject jo = new JSONObject();
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(accountNo);
		if (null != subAccountNo && !"".equals(subAccountNo.trim())) {
			query_FtzActMstr.setSubAccountNo(subAccountNo);
		}
		List<FtzActMstr> result_FtzActMstrs = ftz210401Serv
				.queryFtzActMstrs(query_FtzActMstr);
		if (null == result_FtzActMstrs) {
			jo.put("dtlExist", false);
		} else {
			if (result_FtzActMstrs.size() == 1) {
				FtzActMstr result_FtzActMstr = result_FtzActMstrs.get(0);
				DecimalFormat df_amt = new DecimalFormat("###,###,###,###.00");
				jo.put("dtlExist", true);
				jo.put("branchId", result_FtzActMstr.getBranchId());
				jo.put("accountName", result_FtzActMstr.getAccountName());
				jo.put("balanceCode", result_FtzActMstr.getBalanceCode().trim());
				jo.put("currency", result_FtzActMstr.getCurrency());
				if (null != result_FtzActMstr.getBalance()) {
					jo.put("balance",
							df_amt.format(result_FtzActMstr.getBalance()));
				}
				jo.put("accOrgCode", result_FtzActMstr.getAccOrgCode());
			} else {
				jo.put("dtlExist", true);
			}

		}
		return jo.toString();
	}

	@RequestMapping("InputDtlDel")
	public String delDtlDtl(Model model, FTZ210401Form form) {
		FtzInTxnDtl del_FtzInTxnDtl = new FtzInTxnDtl();
		del_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		del_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		FtzInTxnDtl result_FtzInTxnDtl = ftz210401Serv.queryFtzInTxnDtl(del_FtzInTxnDtl);

		if(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(result_FtzInTxnDtl.getChkStatus())){
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0037"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "forward:/FTZ210401/UptDtlInit";
			
		}
		
		int i = ftz210401Serv.deleteFtzInTxnDtl(del_FtzInTxnDtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_seqNo("");
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Delete.Txn.Dtl.Success"));
			form.setSelected_seqNo("");
			return "forward:/FTZ210401/UptDtlInit";
		}

		return "forward:/FTZ210401/UptDtlInit";
	}

	@RequestMapping("AddDtlDtlInit")
	public String AddDtlDtlInit(Model model, FTZ210401Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getSelected_msgId());
		form.setFtzInTxnDtl(ftzInTxnDtl);
		form.setSelected_msgId("");
		form.setInput_Dtl_flag("add");
		return "ftzmis/FTZ210401_Input_Dtl_Dtl";
	}

	@RequestMapping("AddDtlDtlSubmit")
	public String AddDtlDtlSubmit(Model model, FTZ210401Form form) {
		FtzInTxnDtl issert_FtzInTxnDtl = form.getFtzInTxnDtl();
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();

		// 出/入账标志
		if (null == issert_FtzInTxnDtl.getCdFlag()
				|| "".equals(issert_FtzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}

		if ("3".equals(issert_FtzInTxnDtl.getCdFlag().trim())
				|| "4".equals(issert_FtzInTxnDtl.getCdFlag().trim())) {
			if (null == issert_FtzInTxnDtl.getOrgTranDate()
					|| "".equals(issert_FtzInTxnDtl.getOrgTranDate().trim())
					|| ((null != issert_FtzInTxnDtl.getTranDate()) && DateUtil
							.getFormatDateRemoveSprit(
									issert_FtzInTxnDtl.getTranDate())
							.compareToIgnoreCase(
									DateUtil.getFormatDateRemoveSprit(issert_FtzInTxnDtl
											.getOrgTranDate())) < 0)) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210101.0027");
				resultMessages.add(resultMessage);
			}
		}

		// 记帐日期
		if (null == issert_FtzInTxnDtl.getTranDate()
				|| "".equals(issert_FtzInTxnDtl.getTranDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0014");
			resultMessages.add(resultMessage);
		}

		// 金额
		if (null == issert_FtzInTxnDtl.getAmount()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0015");
			resultMessages.add(resultMessage);
		} else if (!Validator.CheckAmount(issert_FtzInTxnDtl.getAmount())) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210401.0002");
			resultMessages.add(resultMessage);
		}
		
		//期限长度
		if(null == issert_FtzInTxnDtl.getTermLength()){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210101.0018");
			resultMessages.add(resultMessage);
		}
		
		//期限单位
		if(null == issert_FtzInTxnDtl.getTermUnit()){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210101.0019");
			resultMessages.add(resultMessage);
		}
		
		//起息日
		if(null == issert_FtzInTxnDtl.getValueDate() || 
				"".equals(issert_FtzInTxnDtl.getValueDate().trim())){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210206.0013");
			resultMessages.add(resultMessage);
		}
		
		//到期日
		if (issert_FtzInTxnDtl.getTermLength() != 0) {
			if (null == issert_FtzInTxnDtl.getExpireDate() || 
					"".equals(issert_FtzInTxnDtl.getExpireDate())) {
				ResultMessage resultMessage = ResultMessage
				.fromCode("e.ftzmis.210401.0004");
				resultMessages.add(resultMessage);
			}
		}
		
		//潜在收益率
		if(null == issert_FtzInTxnDtl.getInterestRate()) {
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210401.0001");
			resultMessages.add(resultMessage);
		} else if (!Validator.CheckInterestRate(issert_FtzInTxnDtl.getInterestRate())) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210401.0003");
			resultMessages.add(resultMessage);
		}

		// 交易性质
		if (null == issert_FtzInTxnDtl.getTranType()
				|| "".equals(issert_FtzInTxnDtl.getTranType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0017");
			resultMessages.add(resultMessage);
		}

		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210401_Input_Dtl_Dtl";
		}

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(issert_FtzInTxnDtl.getMsgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210401Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null == ftzInTxnDtls || ftzInTxnDtls.size() == 0) {
			issert_FtzInTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		} else {
			FtzInTxnDtl ftzInTxnDtl_tmp = ftzInTxnDtls
					.get(ftzInTxnDtls.size() - 1);
			String seqNo = Integer.parseInt(ftzInTxnDtl_tmp.getSeqNo())+1+"";
			issert_FtzInTxnDtl.setSeqNo(StringUtil.addZeroForNum(seqNo, 6));
		}

		issert_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getTranDate()));
		issert_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getOrgTranDate()));
		issert_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getValueDate()));
		issert_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getExpireDate()));
		
		UserInf userInfo = ContextConst.getCurrentUser();
		issert_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		issert_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		issert_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = ftz210401Serv.insertFtzInTxnDtl(issert_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Add.Txn.Dtl.Success"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setValueDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getValueDate()));
		form.getFtzInTxnDtl().setExpireDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getExpireDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		return "ftzmis/FTZ210401_Input_Dtl_Dtl";
	}

	@RequestMapping("UptDtlDtlInit")
	public String UptDtlDtlInit(Model model, FTZ210401Form form) {

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		form.setFather_makTime(result_FtzInMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzInMsgCtl.getChkDatetime());

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		FtzInTxnDtl result_FtzInTxnDtl = ftz210401Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
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
			FtzBankCode result_FtzBankCode = ftz210401Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		form.setInput_Dtl_flag("upt");
		return "ftzmis/FTZ210401_Input_Dtl_Dtl";
	}

	@RequestMapping("UptDtlDtlSubmit")
	public String UptDtlDtlSubmit(Model model, FTZ210401Form form) {
		FtzInTxnDtl update_FtzInTxnDtl = form.getFtzInTxnDtl();
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();

		// 出/入账标志
		if (null == update_FtzInTxnDtl.getCdFlag()
				|| "".equals(update_FtzInTxnDtl.getCdFlag().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0013");
			resultMessages.add(resultMessage);
		}

		if ("3".equals(update_FtzInTxnDtl.getCdFlag().trim())
				|| "4".equals(update_FtzInTxnDtl.getCdFlag().trim())) {
			if (null == update_FtzInTxnDtl.getOrgTranDate()
					|| "".equals(update_FtzInTxnDtl.getOrgTranDate().trim())
					|| ((null != update_FtzInTxnDtl.getTranDate()) && DateUtil
							.getFormatDateRemoveSprit(
									update_FtzInTxnDtl.getTranDate())
							.compareToIgnoreCase(
									DateUtil.getFormatDateRemoveSprit(update_FtzInTxnDtl
											.getOrgTranDate())) < 0)) {
				ResultMessage resultMessage = ResultMessage
						.fromCode("e.ftzmis.210101.0027");
				resultMessages.add(resultMessage);
			}
		}

		// 记帐日期
		if (null == update_FtzInTxnDtl.getTranDate()
				|| "".equals(update_FtzInTxnDtl.getTranDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0014");
			resultMessages.add(resultMessage);
		}

		// 金额
		if (null == update_FtzInTxnDtl.getAmount()) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0015");
			resultMessages.add(resultMessage);
		} else if (!Validator.CheckAmount(update_FtzInTxnDtl.getAmount())) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210401.0002");
			resultMessages.add(resultMessage);
		}
		
		//期限长度
		if(null == update_FtzInTxnDtl.getTermLength()){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210101.0018");
			resultMessages.add(resultMessage);
		}
		
		//期限单位
		if(null == update_FtzInTxnDtl.getTermUnit()){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210101.0019");
			resultMessages.add(resultMessage);
		}
		
		//起息日
		if(null == update_FtzInTxnDtl.getValueDate() || 
				"".equals(update_FtzInTxnDtl.getValueDate().trim())){
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210206.0013");
			resultMessages.add(resultMessage);
		}
		
		//潜在收益率
		if(null == update_FtzInTxnDtl.getInterestRate() ||
				"".equals(update_FtzInTxnDtl.getInterestRate())) {
			ResultMessage resultMessage = ResultMessage
			.fromCode("e.ftzmis.210401.0001");
			resultMessages.add(resultMessage);
		} else if (!Validator.CheckInterestRate(update_FtzInTxnDtl.getInterestRate())) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210401.0003");
			resultMessages.add(resultMessage);
		}

		// 交易性质
		if (null == update_FtzInTxnDtl.getTranType()
				|| "".equals(update_FtzInTxnDtl.getTranType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0017");
			resultMessages.add(resultMessage);
		}

		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210401_Input_Dtl_Dtl";
		}

		update_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getTranDate()));
		update_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getOrgTranDate()));
		update_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getValueDate()));
		update_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getExpireDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		update_FtzInTxnDtl.setRsv1(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl
						.getMakDatetime()));
		update_FtzInTxnDtl.setRsv2(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl
						.getChkDatetime()));
		update_FtzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeRemoveSpritAndColon(update_FtzInTxnDtl
						.getChkDatetime()));
		update_FtzInTxnDtl
				.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = ftz210401Serv.updateFtzInTxnDtlSelective(update_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0026"));
		} else {
			model.addAttribute(ResultMessages.success().add("ftzmis.Update.Txn.Dtl.Success"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setValueDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getValueDate()));
		form.getFtzInTxnDtl().setExpireDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getExpireDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		return "ftzmis/FTZ210401_Input_Dtl_Dtl";
	}

	@RequestMapping("AuthQry")
	public String queryAuth(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("query...");

		// trans form to queryObject
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzInMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzInMsgCtl.setAccountNo(form.getQuery_accountNo());
		query_FtzInMsgCtl.setRsv1(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_start()));
		query_FtzInMsgCtl.setRsv2(DateUtil.getFormatDateRemoveSprit(form
				.getQuery_submitDate_end()));
		query_FtzInMsgCtl.setMsgStatuss(new String[] {
				CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED,
				CommonConst.FTZ_MSG_STATUS_INPUTING,
				CommonConst.FTZ_MSG_STATUS_AUTH_FAIL});
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210401);

		// query DpMppCfg page list
		Page<FtzInMsgCtl> page = ftz210401Serv.queryFtzInMsgCtlPage(pageable,
				query_FtzInMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
				FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
				query_FtzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
				List<FtzInTxnDtl> ftzInTxnDtls = ftz210401Serv
						.queryFtzInTxnDtlList(query_FtzInTxnDtl);
				int count = 0;
				if (null == ftzInTxnDtls || ftzInTxnDtls.size() < 1) {
					ftzInMsgCtl.setRsv1(ftzInMsgCtl.getTotalCount() + "");
				} else {
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						if (ftzInTxnDtl.getChkStatus().equals(
								CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)
								|| ftzInTxnDtl.getChkStatus().equals(
										CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
							count++;
						}
					}
					ftzInMsgCtl.setRsv1((ftzInMsgCtl.getTotalCount() - count)
							+ "");
				}
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210401_Auth_Qry";
		}

		return "ftzmis/FTZ210401_Auth_Qry";
	}

	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210401Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			return "ftzmis/FTZ210401_Auth_Qry_Dtl";
		} else {
			result_FtzInMsgCtl.setSubmitDate(DateUtil
					.getFormatDateAddSprit(result_FtzInMsgCtl.getSubmitDate()));
			result_FtzInMsgCtl.setBalanceCode(result_FtzInMsgCtl.getBalanceCode().trim());
			form.setFtzInMsgCtl(result_FtzInMsgCtl);
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			// 查询待审核数据
			if ("1".equals(form.getUnAuthFlag())) {
				query_FtzInTxnDtl
						.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
				Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
						ftzInTxnDtl.setValueDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
						ftzInTxnDtl.setExpireDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo("");
				}
			}
			// 查询全部数据
			else {
				Page<FtzInTxnDtl> page = ftz210401Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
						ftzInTxnDtl.setValueDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
						ftzInTxnDtl.setExpireDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo("");
				}
			}

			return "ftzmis/FTZ210401_Auth_Qry_Dtl";
		}
	}

	@RequestMapping("AuthDtlSubmit")
	public String AuthDtlSubmit(Model model, FTZ210401Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210401/QryAuthDtl";
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210401/QryAuthDtl";
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210401Serv
				.queryFtzInTxnDtlList(query_FtzInTxnDtl);
		if (null == ftzInTxnDtls || ftzInTxnDtls.size() < 1) {
			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setRsv1(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInMsgCtl()
							.getMakDatetime()));
			update_FtzInMsgCtl.setRsv2(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInMsgCtl()
							.getChkDatetime()));
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210401Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.2103.0008"));
			} else {
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210401/QryAuthDtl";
			}
		} else {
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
				}
				if (chkStatus.equals(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
					count_authFail++;
					sb_authFail.append(ftzInTxnDtl.getSeqNo().toString() + ",");
				}
			}
			if (count_unAuth > 0) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210310.0012",
						sb_unAuth.subSequence(0, sb_unAuth.length() - 1)));
				form.setAuthFinishFlag("");
				return "forward:/FTZ210401/QryAuthDtl";
			}
			if (count_authFail > 0) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210310.0012",
						sb_unAuth.subSequence(0, sb_authFail.length() - 1)));
				form.setAuthFinishFlag("");
				return "forward:/FTZ210401/QryAuthDtl";
			}

			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl.setMsgNo(form.getSelected_msgNo());

			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setRsv2(update_FtzInMsgCtl.getChkDatetime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210401Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210301.0008"));
			} else {
				if (update_FtzInMsgCtl.getMsgStatus().equals(
						CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
					ftzMsgProcService.submitMsg(update_FtzInMsgCtl.getMsgNo(),
							update_FtzInMsgCtl.getMsgId());
				}
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210401/QryAuthDtl";
			}
		}

		return "forward:/FTZ210401/QryAuthDtl";
	}

	@RequestMapping("QryAuthDtlDtl")
	public String queryAuthDtlDtl(Model model, FTZ210401Form form) {

		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210401Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		form.setFather_makTime(result_FtzInMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzInMsgCtl.getChkDatetime());
		model.addAttribute("fatherStatus", result_FtzInMsgCtl.getMsgStatus());

		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		FtzInTxnDtl result_FtzInTxnDtl = ftz210401Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210401_Auth_Qry_Dtl";
		}
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
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
			FtzBankCode result_FtzBankCode = ftz210401Serv
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		return "ftzmis/FTZ210401_Auth_Qry_Dtl_Dtl";
	}

	@RequestMapping("AuthDtlDtlSubmit")
	public String AuthDtlDtlSubmit(Model model, FTZ210401Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		ftzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());

		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(form.getFtzInTxnDtl().getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210401/QryAuthDtlDtl?selected_msgId="
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
		int i = ftz210401Serv.updateFtzInTxnDtlSelective(ftzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210301.0005"));
			form.setAuthFinishFlag("1");
		}
		return "forward:/FTZ210401/QryAuthDtlDtl?selected_msgId="
				+ form.getFtzInTxnDtl().getMsgId() + "&selected_seqNo="
				+ form.getFtzInTxnDtl().getSeqNo();
	}

	@Resource
	protected FTZ210401Service ftz210401Serv;

	@Autowired
	protected FtzMsgProcService ftzMsgProcService;

	@Resource
	protected NumberService numberService;

	@Resource
	protected DP_Mpp_Service dp_Mpp_Service;
	
	@Resource
	private ProcCommonService procCommonService;
}
