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
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.xmlproc.GenerateXml;
import com.synesoft.ftzmis.app.common.xmlproc.MsgHead;
import com.synesoft.ftzmis.app.model.FTZ210310Form;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210310Service;

/**
 * @author hb_huang
 * @system FTZMIS(远期结售汇)
 * @date 2014-1-3上午10:01:59
 */
@Controller
@RequestMapping(value="FTZ210310")
public class FTZ210310Controller {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	protected FTZ210310Service ftz210310Service;
	
	@Resource
	private NumberService numberService;
	
	@Resource
	protected GenerateXml generateXml;
	
	@ModelAttribute
	public FTZ210310Form setForm() {
		return new FTZ210310Form();
	}
	//批量查询
	@RequestMapping("AddQry")
	public String addQuery(Model model, FTZ210310Form form, @PageableDefaults Pageable pageable) {
		logger.info("远期结售汇查询开始");
		
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getQuery_msgId());
		query_FtzOffMsgCtl.setBranchId(form.getQuery_branchId().trim());
		query_FtzOffMsgCtl.setRsv1(DateUtil
				.getFormatDateRemoveSprit(form.getQuery_workDate_start()));
		query_FtzOffMsgCtl.setRsv2(DateUtil
				.getFormatDateRemoveSprit(form.getQuery_workDate_end()));
		query_FtzOffMsgCtl.setMsgStatus(form.getQuery_msgStatus());
		query_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210310);
		query_FtzOffMsgCtl.setMsgStatuss(new String[]{CommonConst.FTZ_MSG_STATUS_AUTH_FAIL,
				CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, CommonConst.FTZ_MSG_STATUS_INPUTING});
		// query DpMppCfg page list
		Page<FtzOffMsgCtl> page = ftz210310Service.queryFtzOffMsgCtlPage(pageable,
				query_FtzOffMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
				ftzOffMsgCtl.setWorkDate(DateUtil
						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("远期结售汇查询结束...");
			return "ftzmis/FTZ210310_Input_Qry";
		}
		logger.info("远期结售汇查询结束");
		return "ftzmis/FTZ210310_Input_Qry";
	}
	
	//新增批量初始化
	@RequestMapping("AddDtlInit")
	public String addDtlInit(Model model, FTZ210310Form form) {
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		form.setFtzOffMsgCtl(ftzOffMsgCtl);
		form.setActionFlag("addMsg");
		model.addAttribute("pageUrl", "/FTZ210310/AddDtlInit");
		return "ftzmis/FTZ210310_Input_Dtl";
	}
	
	//新增批量提交
	@RequestMapping("AddDtlSubmit")
	public String addDtlSubmit(Model model, FTZ210310Form form) {
		logger.info("远期结售汇录入开始");
		
		//获取录入信息
		FtzOffMsgCtl insert_FtzOffMsgCtl = form.getFtzOffMsgCtl();
		//页面校验
		ResultMessages resultMessages = ResultMessages.error();
		
		//机构
		if ("".equals(insert_FtzOffMsgCtl.getBranchId()) || null == insert_FtzOffMsgCtl.getBranchId()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210302.0001");
			resultMessages.add(resultMessage);
		}
		
		//工作日期
		if ("".equals(insert_FtzOffMsgCtl.getWorkDate()) || null == insert_FtzOffMsgCtl.getWorkDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210302.0002");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210310_Input_Dtl";
		}
		
		insert_FtzOffMsgCtl.setMsgId(numberService.getSysIDSequence(16));
		insert_FtzOffMsgCtl.setWorkDate(DateUtil
				.getFormatDateRemoveSprit(insert_FtzOffMsgCtl.getWorkDate()));
		
		// 设置批量头信息
		MsgHead mh = MsgHead.getMsgHead();
		insert_FtzOffMsgCtl.setVer(mh.getVER());
		insert_FtzOffMsgCtl.setSrc(mh.getSRC());
		insert_FtzOffMsgCtl.setDes(mh.getDES());
		insert_FtzOffMsgCtl.setApp(mh.getAPP());
		insert_FtzOffMsgCtl.setEditFlag(mh.getEditFlag());
		insert_FtzOffMsgCtl.setReserve(mh.getReserve());
		
		insert_FtzOffMsgCtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		insert_FtzOffMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		insert_FtzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		insert_FtzOffMsgCtl.setMsgNo(CommonConst.MSG_NO_210310);
		//插入信息
		int i = ftz210310Service.insertFtzOffMsgCtl(insert_FtzOffMsgCtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		}
		form.getFtzOffMsgCtl().setWorkDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffMsgCtl()
						.getWorkDate()));
		logger.info("远期结售汇录入结束");
		form.setActionFlag("updMsg");
		model.addAttribute("pageUrl", "/FTZ210310/UpdDtlInit");
		return "ftzmis/FTZ210310_Input_Dtl";
	}
	
	//关闭明细页面后刷新批量页面
	@RequestMapping("DtlInitReflash")
	public String dtlInitReflash(Model model, FTZ210310Form form, @PageableDefaults Pageable pageable) {
		
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl ftzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == ftzOffMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			form.setActionFlag("updMsg");
			return "ftzmis/FTZ210310_Input_Dtl";
		} 
		ftzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
		form.setFtzOffMsgCtl(ftzOffMsgCtl);
		
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		//查询明细数据列表
		Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(pageable, query_FtzOffTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
			for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
				FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
		}
		// 清空页面列表选择Key
		form.setActionFlag("updMsg");
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		return "ftzmis/FTZ210310_Input_Dtl";
	}
	
	//新增批量明细初始化
	@RequestMapping("AddDtlDtlInit")
	public String addDtlDtlInit(Model model, FTZ210310Form form) {
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(form.getSelected_msgId());
		ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		form.setFtzOffTxnDtl(ftzOffTxnDtl);
		form.setSelected_msgId("");
		form.setActionFlag("addTxn");
		return "ftzmis/FTZ210310_Input_Dtl_Dtl";
	}
	
	//新增批量明细提交
	@RequestMapping("AddDtlDtlSubmit")
	public String addDtlDtlSubmit(Model model, FTZ210310Form form) {
		
		FtzOffTxnDtl ftzOffTxnDtl = form.getFtzOffTxnDtl();
		//页面校验
		ResultMessages resultMessages = ResultMessages.error();
		
		//申报日期
		if ("".equals(ftzOffTxnDtl.getSubmitDate()) || null == ftzOffTxnDtl.getSubmitDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		
		//所属机构代码
		if ("".equals(ftzOffTxnDtl.getAccOrgCode()) || null == ftzOffTxnDtl.getAccOrgCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210303.0002");
			resultMessages.add(resultMessage);
		}
		
		//结售汇交易类型
		if ("".equals(ftzOffTxnDtl.getTranGenre()) || null == ftzOffTxnDtl.getTranGenre()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0001");
			resultMessages.add(resultMessage);
		}
		
		//买入币种
		if ("".equals(ftzOffTxnDtl.getBuyCurr()) || null == ftzOffTxnDtl.getBuyCurr()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0002");
			resultMessages.add(resultMessage);
		}
		
		//卖出币种
		if ("".equals(ftzOffTxnDtl.getSellCurr()) || null == ftzOffTxnDtl.getSellCurr()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0003");
			resultMessages.add(resultMessage);
		}
		
		//买入金额
		if ("".equals(ftzOffTxnDtl.getBuyAmt()) || null == ftzOffTxnDtl.getBuyAmt()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0004");
			resultMessages.add(resultMessage);
		}
		
		//卖出金额
		if ("".equals(ftzOffTxnDtl.getSellAmt()) || null == ftzOffTxnDtl.getSellAmt()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0005");
			resultMessages.add(resultMessage);
		}
		
		//买入牌价
		if ("".equals(ftzOffTxnDtl.getBuyRate()) || null == ftzOffTxnDtl.getBuyRate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0006");
			resultMessages.add(resultMessage);
		}
		
		//卖出牌价
		if ("".equals(ftzOffTxnDtl.getSellRate()) || null == ftzOffTxnDtl.getSellRate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0007");
			resultMessages.add(resultMessage);
		}
		
		//交割日1
		if ("".equals(ftzOffTxnDtl.getTranDate()) || null == ftzOffTxnDtl.getTranDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0008");
			resultMessages.add(resultMessage);
		}
		
		//交割日2
		if ("".equals(ftzOffTxnDtl.getExpirationDate()) || null == ftzOffTxnDtl.getExpirationDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0009");
			resultMessages.add(resultMessage);
		}
		
		//对方账号
		if ("".equals(ftzOffTxnDtl.getAccountNo()) || null == ftzOffTxnDtl.getAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0010");
			resultMessages.add(resultMessage);
		}
		
		//对方户名
		if ("".equals(ftzOffTxnDtl.getAccountName()) || null == ftzOffTxnDtl.getAccountName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0011");
			resultMessages.add(resultMessage);
		}
		
		//交易性质
		if ("".equals(ftzOffTxnDtl.getTranType()) || null == ftzOffTxnDtl.getTranType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210101.0017");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210310_Input_Dtl_Dtl";
		}
		
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(ftzOffTxnDtl.getMsgId());
		List<FtzOffTxnDtl> ftzOffTxnDtls = ftz210310Service.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if (null == ftzOffTxnDtls || ftzOffTxnDtls.size() == 0) {
			ftzOffTxnDtl.setSeqNo(StringUtil.addZeroForNum("1", 6));
		} else {
			FtzOffTxnDtl ftzOffTxnDtl_tmp = ftzOffTxnDtls.get(ftzOffTxnDtls.size()-1);
			String seqNo = Integer.parseInt(ftzOffTxnDtl_tmp.getSeqNo())+1+"";
			ftzOffTxnDtl.setSeqNo(StringUtil.addZeroForNum(seqNo, 6));
		}
		
		ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(
				ftzOffTxnDtl.getExpirationDate()));
		ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		ftzOffTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		
		int i = ftz210310Service.insertFtzOffTxnDtl(ftzOffTxnDtl);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
			model.addAttribute("updFlag", "1");
		}
		form.getFtzOffTxnDtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getSubmitDate()));
		form.getFtzOffTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getTranDate()));
		form.getFtzOffTxnDtl().setExpirationDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getExpirationDate()));
		form.getFtzOffTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzOffTxnDtl().getMakDatetime()));
		form.getFtzOffTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzOffTxnDtl().getChkDatetime()));
		form.setActionFlag("addTxn");
		return "ftzmis/FTZ210310_Input_Dtl_Dtl";
	}
	
	//更新批量初始化
	@RequestMapping("UpdDtlInit")
	public String updDtlInit(Model model, FTZ210310Form form, 
			@PageableDefaults Pageable pageable) {
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl ftzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == ftzOffMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			return "ftzmis/FTZ210310_Input_Dtl";
		}
		ftzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
		form.setFtzOffMsgCtl(ftzOffMsgCtl);
		// 将查询数据放入form
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(pageable,query_FtzOffTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
			for (FtzOffTxnDtl ftzOffTxnDtl : FtzOffTxnDtls) {
				ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzOffTxnDtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		form.setActionFlag("updMsg");
		model.addAttribute("pageUrl", "/FTZ210310/UpdDtlInit");
		return "ftzmis/FTZ210310_Input_Dtl";
	}
	
	//更新批量明细初始化
	@RequestMapping("UpdDtlDtlInit")
	public String updDtlDtlInit(Model model, FTZ210310Form form) {
		
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		form.setFather_makTime(result_FtzOffMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzOffMsgCtl.getChkDatetime());
		
		// 组装查询信息
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210310Service.queryFtzOffTxnDtl(query_FtzOffTxnDtl);

		if (null == result_FtzOffTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210302_Input_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getMakDatetime()));
		result_FtzOffTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getChkDatetime()));
		
		//对方银行名称的处理
		if (null != result_FtzOffTxnDtl.getInstitutionCode() || !"".equals(
				result_FtzOffTxnDtl.getInstitutionCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzOffTxnDtl.getInstitutionCode());
			FtzBankCode result_FtzBankCode = ftz210310Service.queryBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzOffTxnDtl.setOppBankName(result_FtzBankCode.getBankName());
			}
		}
		
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		form.setActionFlag("updTxn");
		return "ftzmis/FTZ210310_Input_Dtl_Dtl";
	}
	
	//更新批量提交
	@RequestMapping("UpdDtlSubmit")
	public String updDtlSubmit(Model model, FTZ210310Form form, @PageableDefaults Pageable pageable) {
		//获取录入信息
		FtzOffMsgCtl update_FtzOffMsgCtl = form.getFtzOffMsgCtl();
		//页面校验
		ResultMessages resultMessages = ResultMessages.error();
		
		//机构号
		if ("".equals(update_FtzOffMsgCtl.getBranchId()) || null == update_FtzOffMsgCtl.getBranchId()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210302.0001");
			resultMessages.add(resultMessage);
		}
		
		//工作日期
		if ("".equals(update_FtzOffMsgCtl.getWorkDate()) || null == update_FtzOffMsgCtl.getWorkDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210302.0002");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			model.addAttribute("pageUrl", "/FTZ210310/UpdDtlInit");
			return "ftzmis/FTZ210310_Input_Dtl";
		}
		
		update_FtzOffMsgCtl.setRsv1(DateUtil.getFormatDateTimeRemoveSpritAndColon(
				update_FtzOffMsgCtl.getMakDatetime()));
		update_FtzOffMsgCtl.setRsv2(DateUtil.getFormatDateTimeRemoveSpritAndColon(
				update_FtzOffMsgCtl.getChkDatetime()));
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		update_FtzOffMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzOffMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateRemoveSprit(
				update_FtzOffMsgCtl.getWorkDate()));
		int i = ftz210310Service.updateFtzOffMsgCtl(update_FtzOffMsgCtl, null);
		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			form.setFtzOffMsgCtl(ftz210310Service.queryFtzOffMsgCtl(update_FtzOffMsgCtl));
			form.getFtzOffMsgCtl().setWorkDate(
					DateUtil.getFormatDateAddSprit(form.getFtzOffMsgCtl().getWorkDate()));
			// 将查询数据放入form
			FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
			query_FtzOffTxnDtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
			// 查询明细数据列表
			Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(pageable,query_FtzOffTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
				for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
					FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
				}
				model.addAttribute("page", page);
			}
			// 清空页面列表选择Key
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			form.setActionFlag("updMsg");
			model.addAttribute("pageUrl", "/FTZ210310/UpdDtlInit");
			return "ftzmis/FTZ210310_Input_Dtl";
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		}
		
		form.getFtzOffMsgCtl().setWorkDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffMsgCtl().getWorkDate()));
		// 将查询数据放入form
		form.setSelected_msgId("");
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(update_FtzOffMsgCtl.getMsgId());
		
		// 查询明细数据列表
		Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(pageable,query_FtzOffTxnDtl);
		if (page.getContent().size() > 0) {
			List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
			for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
				FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
		}
		
		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo("");
		form.setActionFlag("updMsg");
		model.addAttribute("pageUrl", "/FTZ210310/UpdDtlInit");
		return "ftzmis/FTZ210310_Input_Dtl";
	}
	
	//更新批量明细提交
	@RequestMapping("UpdDtlDtlSubmit")
	public String updDtlDtlSubmit(Model model, FTZ210310Form form) {
		
		FtzOffTxnDtl ftzOffTxnDtl = form.getFtzOffTxnDtl();
		//页面校验
		ResultMessages resultMessages = ResultMessages.error();
		
		//申报日期
		if ("".equals(ftzOffTxnDtl.getSubmitDate()) || null == ftzOffTxnDtl.getSubmitDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210101.0012");
			resultMessages.add(resultMessage);
		}
		
		//所属机构代码
		if ("".equals(ftzOffTxnDtl.getAccOrgCode()) || null == ftzOffTxnDtl.getAccOrgCode()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210303.0002");
			resultMessages.add(resultMessage);
		}
		
		//结售汇交易类型
		if ("".equals(ftzOffTxnDtl.getTranGenre()) || null == ftzOffTxnDtl.getTranGenre()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0001");
			resultMessages.add(resultMessage);
		}
		
		//买入币种
		if ("".equals(ftzOffTxnDtl.getBuyCurr()) || null == ftzOffTxnDtl.getBuyCurr()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0002");
			resultMessages.add(resultMessage);
		}
		
		//卖出币种
		if ("".equals(ftzOffTxnDtl.getSellCurr()) || null == ftzOffTxnDtl.getSellCurr()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0003");
			resultMessages.add(resultMessage);
		}
		
		//买入金额
		if ("".equals(ftzOffTxnDtl.getBuyAmt()) || null == ftzOffTxnDtl.getBuyAmt()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0004");
			resultMessages.add(resultMessage);
		}
		
		//卖出金额
		if ("".equals(ftzOffTxnDtl.getSellAmt()) || null == ftzOffTxnDtl.getSellAmt()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0005");
			resultMessages.add(resultMessage);
		}
		
		//买入牌价
		if ("".equals(ftzOffTxnDtl.getBuyRate()) || null == ftzOffTxnDtl.getBuyRate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0006");
			resultMessages.add(resultMessage);
		}
		
		//卖出牌价
		if ("".equals(ftzOffTxnDtl.getSellRate()) || null == ftzOffTxnDtl.getSellRate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0007");
			resultMessages.add(resultMessage);
		}
		
		//交割日1
		if ("".equals(ftzOffTxnDtl.getTranDate()) || null == ftzOffTxnDtl.getTranDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0008");
			resultMessages.add(resultMessage);
		}
		
		//交割日2
		if ("".equals(ftzOffTxnDtl.getExpirationDate()) || null == ftzOffTxnDtl.getExpirationDate()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0009");
			resultMessages.add(resultMessage);
		}
		
		//对方账号
		if ("".equals(ftzOffTxnDtl.getAccountNo()) || null == ftzOffTxnDtl.getAccountNo()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0010");
			resultMessages.add(resultMessage);
		}
		
		//对方户名
		if ("".equals(ftzOffTxnDtl.getAccountName()) || null == ftzOffTxnDtl.getAccountName()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210310.0011");
			resultMessages.add(resultMessage);
		}
		
		//交易性质
		if ("".equals(ftzOffTxnDtl.getTranType()) || null == ftzOffTxnDtl.getTranType()) {
			ResultMessage resultMessage = ResultMessage.fromCode("e.ftzmis.210101.0017");
			resultMessages.add(resultMessage);
		}
		
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210310_Input_Dtl_Dtl";
		}
		
		ftzOffTxnDtl.setRsv1(DateUtil.getFormatDateTimeRemoveSpritAndColon(
				ftzOffTxnDtl.getMakDatetime()));
		ftzOffTxnDtl.setRsv2(DateUtil.getFormatDateTimeRemoveSpritAndColon(
				ftzOffTxnDtl.getChkDatetime()));
		ftzOffTxnDtl.setSubmitDate(DateUtil.
				getFormatDateRemoveSprit(ftzOffTxnDtl.getSubmitDate()));
		ftzOffTxnDtl.setTranDate(DateUtil.
				getFormatDateRemoveSprit(ftzOffTxnDtl.getTranDate()));
		ftzOffTxnDtl.setExpirationDate(DateUtil.getFormatDateRemoveSprit(
				ftzOffTxnDtl.getExpirationDate()));
		ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		ftzOffTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftzOffTxnDtl.setChkDatetime(DateUtil.
				getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getChkDatetime()));
		
		int i = ftz210310Service.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
			"e.ftzmis.210101.0026"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
			model.addAttribute("updFlag", "1");
		}
		form.getFtzOffTxnDtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getSubmitDate()));
		form.getFtzOffTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getTranDate()));
		form.getFtzOffTxnDtl().setExpirationDate(
				DateUtil.getFormatDateAddSprit(form.getFtzOffTxnDtl().getExpirationDate()));
		form.getFtzOffTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzOffTxnDtl().getMakDatetime()));
		form.getFtzOffTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzOffTxnDtl().getChkDatetime()));
		
		return "ftzmis/FTZ210310_Input_Dtl_Dtl";
	}
	
	//删除批量
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210310Form form) {
		FtzOffMsgCtl del_FtzOffMsgCtl = new FtzOffMsgCtl();
		del_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());

		int i = ftz210310Service.deleteFtzOffMsgCtl(del_FtzOffMsgCtl);

		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_msgId("");
			return "forward:/FTZ210310/AddQry";
		}
		return "forward:/FTZ210310/AddQry";
	}
	
	//删除批量明细
	@RequestMapping("InputDtlDel")
	public String delDtlDtl(Model model, FTZ210310Form form) {
		FtzOffTxnDtl del_FtzOffTxnDtl = new FtzOffTxnDtl();
		del_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		del_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());
		
		int i = ftz210310Service.deleteFtzOffTxnDtl(del_FtzOffTxnDtl);

		if (i < 0) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_seqNo("");
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_seqNo("");
			return "forward:/FTZ210310/UpdDtlInit";
		}
		return "forward:/FTZ210310/UpdDtlInit";
	}
	
	//提交批量
	@RequestMapping("SubmitDtl")
	public String submitDtl(Model model, FTZ210310Form form) {
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzOffTxnDtl> ftzOffTxnDtls = ftz210310Service.
			queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if (null != ftzOffTxnDtls && ftzOffTxnDtls.size() > 0) {
			for (FtzOffTxnDtl dtl : ftzOffTxnDtls) {
				if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(dtl.getChkStatus())) {
					model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0030"));
					return "forward:/FTZ210310/AddQry";
				}
			}
		}
		int i = ftz210310Service.updateFtzOffMsgCtlForSubmit(ftzOffMsgCtl);
		
		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
			"e.ftzmis.210101.0001"));
			return "forward:/FTZ210310/AddQry";
		}
		
		model.addAttribute(ResultMessages.success().add("i.ftzmis.210101.0001"));
		return "forward:/FTZ210310/AddQry";
	}
	
	//批量查询
	@RequestMapping("QryDtl")
	public String qryDtl(Model model,FTZ210310Form form, @PageableDefaults Pageable pageable) {
		// 组装查询信息
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == result_FtzOffMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			return "ftzmis/FTZOFF_Qry";
		} else {
			// 有数据则进行数据转换，查询明细数据
			result_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(result_FtzOffMsgCtl.getWorkDate()));
			form.setFtzOffMsgCtl(result_FtzOffMsgCtl);
			FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
			query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
			Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(
					pageable, query_FtzOffTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
				for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
					FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
				}
				model.addAttribute("page", page);
				form.setSelected_msgId("");
				form.setSelected_seqNo("");
			}
		}
		return "ftzmis/FTZ210310_Qry_Dtl";
	}
	
	//批量明细查询
	@RequestMapping("QryDtlDtl")
	public String qryDtlDtl(Model model, FTZ210310Form form) {
		// 组装查询信息
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210310Service.queryFtzOffTxnDtl(query_FtzOffTxnDtl);
		if (null == result_FtzOffTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210310_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getMakDatetime()));
		result_FtzOffTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getChkDatetime()));
		//对方银行名称的处理
		if (null != result_FtzOffTxnDtl.getInstitutionCode() || !"".equals(
				result_FtzOffTxnDtl.getInstitutionCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzOffTxnDtl.getInstitutionCode());
			FtzBankCode result_FtzBankCode = ftz210310Service.queryBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzOffTxnDtl.setOppBankName(result_FtzBankCode.getBankName());
			}
		}
		
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		return "ftzmis/FTZ210310_Qry_Dtl_Dtl";
	}
	
	//批量审核页面
	@RequestMapping("QryAuthDtl")
	public String qryAuthDtl(Model model,FTZ210310Form form, @PageableDefaults Pageable pageable) {
		// 组装查询信息
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (null == result_FtzOffMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo("");
			return "ftzmis/FTZ210310_Auth_Qry_Dtl";
		} else {
			// 有数据则进行数据转换，查询明细数据
			result_FtzOffMsgCtl.setWorkDate(DateUtil.getFormatDateAddSprit(result_FtzOffMsgCtl.getWorkDate()));
			form.setFtzOffMsgCtl(result_FtzOffMsgCtl);
			FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
			query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
			//查询待审核数据
			if ("1".equals(form.getUnAuthFlag())) {
				query_FtzOffTxnDtl
						.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
				Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(
						pageable, query_FtzOffTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzOffTxnDtl> ftzOffTxnDtls = page.getContent();
					for (FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
						ftzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(
								ftzOffTxnDtl.getSubmitDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo("");
				}
			}
			//查询全部数据 
			else {
				Page<FtzOffTxnDtl> page = ftz210310Service.queryFtzOffTxnDtlPage(
						pageable, query_FtzOffTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzOffTxnDtl> FtzOffTxnDtls = page.getContent();
					for (FtzOffTxnDtl FtzOffTxnDtl : FtzOffTxnDtls) {
						FtzOffTxnDtl.setSubmitDate(DateUtil.getFormatDateAddSprit(FtzOffTxnDtl.getSubmitDate()));
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo("");
				}
			}
			return "ftzmis/FTZ210310_Auth_Qry_Dtl";
		}
	}
	
	//批量审核提交
	@RequestMapping("AuthDtlSubmit")
	public String authDtlSubmit(Model model, FTZ210310Form form) {
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzOffMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210310/QryAuthDtl";
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(result_FtzOffMsgCtl.getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210310/QryAuthDtl";
		}
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzOffTxnDtl> ftzOffTxnDtls = ftz210310Service
				.queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
		if (null == ftzOffTxnDtls || ftzOffTxnDtls.size() < 1) {
			FtzOffMsgCtl update_FtzOffMsgCtl = new FtzOffMsgCtl();
			
			update_FtzOffMsgCtl.setRsv1(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzOffMsgCtl().getMakDatetime()));
			update_FtzOffMsgCtl.setRsv2(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzOffMsgCtl().getChkDatetime()));
			update_FtzOffMsgCtl.setMsgId(form.getFtzOffMsgCtl().getMsgId());
			update_FtzOffMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzOffMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzOffMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210310Service.updateFtzOffMsgCtl(update_FtzOffMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
				"e.ftzmis.2103.0008"));
			} else {
				model.addAttribute(ResultMessages.success().add(
				"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210310/QryAuthDtl";
			}
		} else {
			int count_unAuth = 0;
			int count_authFail = 0;
			StringBuffer sb_unAuth = new StringBuffer();
			StringBuffer sb_authFail = new StringBuffer();
			for(FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
				String chkStatus = ftzOffTxnDtl.getChkStatus();
				if (chkStatus
						.equals(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED)) {
					count_unAuth++;
					sb_unAuth.append(ftzOffTxnDtl.getSeqNo().toString() + ",");
				}
				if (chkStatus.equals(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
					count_authFail++;
					sb_authFail.append(ftzOffTxnDtl.getSeqNo().toString() + ",");
				}
			}
			if (count_unAuth > 0) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0024",
						sb_unAuth.subSequence(0, sb_unAuth.length() - 1)));
				return "forward:/FTZ210310/QryAuthDtl";
			}
			if (count_authFail > 0) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0031",
						sb_unAuth.subSequence(0, sb_authFail.length() - 1)));
				return "forward:/FTZ210310/QryAuthDtl";
			} 
			
			FtzOffMsgCtl update_FtzOffMsgCtl = new FtzOffMsgCtl();
			update_FtzOffMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzOffMsgCtl.setMsgId(form.getFtzOffMsgCtl().getMsgId());
			update_FtzOffMsgCtl.setMsgNo(form.getSelected_msgNo());
			
			update_FtzOffMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzOffMsgCtl.setRsv2(update_FtzOffMsgCtl.getChkDatetime());
			update_FtzOffMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			
			int i = ftz210310Service.updateFtzOffMsgCtl(update_FtzOffMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
				"e.ftzmis.210301.0008"));
			} else {
				if (update_FtzOffMsgCtl.getMsgStatus().equals(
						CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
					generateXml.writeXml(update_FtzOffMsgCtl.getMsgNo(),
							update_FtzOffMsgCtl.getMsgId());
				}
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210310/QryAuthDtl";
			}
		}
		
		return "ftzmis/FTZ210310_Auth_Qry_Dtl";
	}
	
	//批量明细审核
	@RequestMapping("QryAuthDtlDtl")
	public String qryAuthDtlDtl(Model model, FTZ210310Form form) {
		
		FtzOffMsgCtl query_FtzOffMsgCtl = new FtzOffMsgCtl();
		query_FtzOffMsgCtl.setMsgId(form.getSelected_msgId());
		FtzOffMsgCtl result_FtzOffMsgCtl = ftz210310Service.queryFtzOffMsgCtl(query_FtzOffMsgCtl);
		form.setFather_makTime(result_FtzOffMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzOffMsgCtl.getChkDatetime());
		model.addAttribute("fatherStatus", result_FtzOffMsgCtl.getMsgStatus());
		
		// 组装查询信息
		FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
		query_FtzOffTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzOffTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzOffTxnDtl result_FtzOffTxnDtl = ftz210310Service.queryFtzOffTxnDtl(query_FtzOffTxnDtl);
		if (null == result_FtzOffTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210310_Auth_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzOffTxnDtl.setSubmitDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getSubmitDate()));
		result_FtzOffTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzOffTxnDtl.getTranDate()));
		result_FtzOffTxnDtl.setExpirationDate(
				DateUtil.getFormatDateAddSprit(result_FtzOffTxnDtl.getExpirationDate()));
		result_FtzOffTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getMakDatetime()));
		result_FtzOffTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(
				result_FtzOffTxnDtl.getChkDatetime()));
		
		//对方银行名称的处理
		if (null != result_FtzOffTxnDtl.getInstitutionCode() || !"".equals(
				result_FtzOffTxnDtl.getInstitutionCode())) {
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzOffTxnDtl.getInstitutionCode());
			FtzBankCode result_FtzBankCode = ftz210310Service.queryBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzOffTxnDtl.setOppBankName(result_FtzBankCode.getBankName());
			}
		}
		
		form.setFtzOffTxnDtl(result_FtzOffTxnDtl);
		return "ftzmis/FTZ210310_Auth_Qry_Dtl_Dtl";
	}
	
	//批量明细审核提交 
	@RequestMapping("AuthDtlDtlSubmit")
	public String authDtlDtlSubmit(Model model, FTZ210310Form form) {
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(form.getFtzOffTxnDtl().getMsgId());
		ftzOffTxnDtl.setSeqNo(form.getFtzOffTxnDtl().getSeqNo());
		
		
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(form.getFtzOffTxnDtl().getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210310/QryAuthDtlDtl?selected_msgId="
				+ form.getFtzOffTxnDtl().getMsgId() + "&selected_seqNo="
				+ form.getFtzOffTxnDtl().getSeqNo();
		}
		ftzOffTxnDtl.setChkUserId(userInfo.getUserid());
		ftzOffTxnDtl.setChkDatetime(DateUtil.getNowInputDateTime());
		ftzOffTxnDtl.setChkAddWord(form.getFtzOffTxnDtl().getChkAddWord());
		
		if ("1".equals(form.getAuthStat())) {
			ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		} else if ("0".equals(form.getAuthStat())) {
			ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		}
		
		int i = ftz210310Service.updateFtzOffTxnDtlSelective(ftzOffTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.2103.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210301.0005"));
			form.setAuthFinishFlag("1");
		}
		return "forward:/FTZ210310/QryAuthDtlDtl?selected_msgId="
		+ form.getFtzOffTxnDtl().getMsgId() + "&selected_seqNo="
		+ form.getFtzOffTxnDtl().getSeqNo();
	}
}
