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
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.xmlproc.GenerateXml;
import com.synesoft.ftzmis.app.common.xmlproc.MsgHead;
import com.synesoft.ftzmis.app.model.FTZ210101Form;
import com.synesoft.ftzmis.app.model.FTZ210101Form.FTZ210101FormAddDtl;
import com.synesoft.ftzmis.app.model.FTZ210101Form.FTZ210101FormAddDtlDtl;
import com.synesoft.ftzmis.app.model.FTZ210106Form;
import com.synesoft.ftzmis.app.model.FTZ210106Form.FTZ210106FormAddDtl;
import com.synesoft.ftzmis.app.model.FTZ210106Form.FTZ210106FormAddDtlDtl;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.service.FTZ210101Service;
import com.synesoft.ftzmis.domain.service.FTZ210106Service;

@Controller
@RequestMapping(value = "FTZ210106")
public class FTZ210106Controller {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public FTZ210106Form setUpForm() {
		return new FTZ210106Form();
	}
	@RequestMapping("AuthDtlDtlSubmit")
	public String AuthDtlDtlSubmit(Model model, FTZ210106Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		ftzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());

		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(form.getFtzInTxnDtl().getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210106/QryAuthDtlDtl?selected_msgId="
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
		int i = ftz210106Serv.updateFtzInTxnDtlSelective(ftzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210301.0008"));
		} else {
			model.addAttribute(ResultMessages.success().add(
					"i.ftzmis.210301.0005"));
			form.setAuthFinishFlag("1");
		}
		return "forward:/FTZ210106/QryAuthDtlDtl?selected_msgId="
				+ form.getFtzInTxnDtl().getMsgId() + "&selected_seqNo="
				+ form.getFtzInTxnDtl().getSeqNo();
	}
	
	@RequestMapping("QryAuthDtlDtl")
	public String queryAuthDtlDtl(Model model, FTZ210106Form form) {
		
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		if(form.getSelected_msgId()==null ||"".equals(form.getSelected_msgId())){
			query_FtzInMsgCtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		}
		FtzInMsgCtl result_FtzInMsgCtl = ftz210106Serv.queryFtzInMsgCtl(query_FtzInMsgCtl);
		form.setFather_makTime(result_FtzInMsgCtl.getMakDatetime());
		form.setFather_chkTime(result_FtzInMsgCtl.getChkDatetime());
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		if(form.getSelected_msgId()==null ||"".equals(form.getSelected_msgId())){
			query_FtzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		}
		
		if(form.getSelected_seqNo()==null ||"".equals(form.getSelected_seqNo())){
			query_FtzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());
		}else{
			query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		}

		FtzInTxnDtl result_FtzInTxnDtl = ftz210106Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			return "ftzmis/FTZ210106_Auth_Qry_Dtl";
		}
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
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getMakDatetime()));
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
		if(null != result_FtzInTxnDtl.getOppBankCode() || !"".equals(result_FtzInTxnDtl.getOppBankCode())){
			FtzBankCode query_FtzBankCode = new FtzBankCode();
			query_FtzBankCode.setBankCode(result_FtzInTxnDtl.getOppBankCode());
			FtzBankCode result_FtzBankCode = ftz210106Serv.queryFtzBankCode(query_FtzBankCode);
			if(null != result_FtzBankCode){
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode.getBankName());
			}
		}
		
		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		return "ftzmis/FTZ210106_Auth_Qry_Dtl_Dtl";
	}
	@RequestMapping("QryAuthDtl")
	public String queryAuthDtl(Model model, FTZ210106Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl  = new FtzInMsgCtl();
		try{
			 result_FtzInMsgCtl = ftz210106Serv
			.queryFtzInMsgCtl(query_FtzInMsgCtl);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		if (null == result_FtzInMsgCtl) {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			return "ftzmis/FTZ210106_Auth_Qry_Dtl";
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
			// 查询待审核数据
			if ("1".equals(form.getUnAuthFlag())) {
				query_FtzInTxnDtl
						.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
				Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
						
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}
			// 查询全部数据
			else {
				Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(
						pageable, query_FtzInTxnDtl);
				if (page.getContent().size() > 0) {
					List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
					for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
						ftzInTxnDtl.setTranDate(DateUtil
								.getFormatDateAddSprit(ftzInTxnDtl
										.getTranDate()));
					
					}
					model.addAttribute("page", page);
					form.setSelected_msgId("");
					form.setSelected_seqNo(null);
				}
			}

			return "ftzmis/FTZ210106_Auth_Qry_Dtl";
		}
	}
	
	@RequestMapping("AuthDtlSubmit")
	public String AuthDtlSubmit(Model model, FTZ210106Form form) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl result_FtzInMsgCtl = ftz210101Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
				.getMsgStatus())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0032"));
			return "forward:/FTZ210106/QryAuthDtl";
		}
		UserInf userInfo = ContextConst.getCurrentUser();
		if (userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0025"));
			return "forward:/FTZ210106/QryAuthDtl";
		}
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210101Serv
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
			int i = ftz210106Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.2103.0008"));
			} else {
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210106/QryAuthDtl";
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
						"e.ftzmis.210101.0024",
						sb_unAuth.subSequence(0, sb_unAuth.length() - 1)));
				return "forward:/FTZ210106/QryAuthDtl";
			}
			if (count_authFail > 0) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0031",
						sb_unAuth.subSequence(0, sb_authFail.length() - 1)));
				return "forward:/FTZ210106/QryAuthDtl";
			}

			FtzInMsgCtl update_FtzInMsgCtl = new FtzInMsgCtl();
			update_FtzInMsgCtl
					.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
			update_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			update_FtzInMsgCtl.setMsgNo(form.getSelected_msgNo());

			update_FtzInMsgCtl.setChkUserId(userInfo.getUserid());
			update_FtzInMsgCtl.setRsv2(update_FtzInMsgCtl.getChkDatetime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			update_FtzInMsgCtl.setChkDatetime(DateUtil.getNowInputDateTime());
			int i = ftz210106Serv.updateFtzInMsgCtl(update_FtzInMsgCtl, null);
			if (i < 1) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210301.0008"));
			} else {
				if (update_FtzInMsgCtl.getMsgStatus().equals(
						CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
					generateXml.writeXml(update_FtzInMsgCtl.getMsgNo(),
							update_FtzInMsgCtl.getMsgId());
				}
				model.addAttribute(ResultMessages.success().add(
						"i.ftzmis.210301.0005"));
				form.setAuthFinishFlag("1");
				return "forward:/FTZ210106/QryAuthDtl";
			}
		}

		return "ftzmis/FTZ210106_Auth_Qry_Dtl";
	}
	@Resource
	protected GenerateXml generateXml;
	
	@Resource
	protected FTZ210106Service ftz210106Serv;
	@Resource
	protected FTZ210101Service ftz210101Serv;
	@RequestMapping("DtlAccountQry")
	public @ResponseBody
	String DtlAccountQry(@RequestParam("accountNo") String accountNo, Model model) {
		JSONObject jo = new JSONObject();
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(accountNo);
	//	query_FtzActMstr.setSubAccountNo(subAccountNo);
		FtzActMstr result_FtzActMstr = ftz210106Serv
				.queryFtzActMstr(query_FtzActMstr);
		if (null == result_FtzActMstr) {
			jo.put("dtlExist", false);
		} else {
			jo.put("dtlExist", true);
			jo.put("branchId", result_FtzActMstr.getBranchId());
			jo.put("accType", result_FtzActMstr.getAccType());
			jo.put("accountName", result_FtzActMstr.getAccountName());
			jo.put("depositRate", result_FtzActMstr.getDepositRate());
			jo.put("customType", result_FtzActMstr.getCustomType());
			jo.put("balanceCode", result_FtzActMstr.getBalanceCode());
			jo.put("documentType", result_FtzActMstr.getDocumentType());
			jo.put("currency", result_FtzActMstr.getCurrency());
			jo.put("documentNo", result_FtzActMstr.getDocumentNo());
			jo.put("balance", result_FtzActMstr.getBalance());
			jo.put("accOrgCode", result_FtzActMstr.getAccOrgCode());

		}
		return jo.toString();
	}
	
	//新增明细
//	@RequestMapping("AddDtlDtlInit")
//	public String AddDtlDtlInit(Model model, FTZ210106Form form) {
//		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
//		ftzInTxnDtl.setMsgId(form.getSelected_msgId());
//		form.setFtzInTxnDtl(ftzInTxnDtl);
//		form.setSelected_msgId("");
//		return "ftzmis/FTZ210106_Input_Add_Dtl";
//	}
	@RequestMapping("AddDtlDtlInit")
	public String AddDtlDtlInit(Model model, FTZ210106Form form) {
		FtzInTxnDtl ftzInTxnDtl = new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(form.getSelected_msgId());
		form.setFtzInTxnDtl(ftzInTxnDtl);
		form.setSelected_msgId("");
		form.setInput_Dtl_flag("add");
		return "ftzmis/FTZ210106_Input_Dtl_Dtl";
	}
	
	//修改明细
	@RequestMapping("UptDtlDtlSubmit")
	public String UptDtlDtlSubmit(Model model,  FTZ210106Form form, BindingResult result) {

		FtzInTxnDtl update_FtzInTxnDtl = form.getFtzInTxnDtl();
		
		// 获取录入信息
		ResultMessages resultMessages = ResultMessages.error();


		// 记帐日期
		if (null == update_FtzInTxnDtl.getTranDate()
				|| "".equals(update_FtzInTxnDtl.getTranDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0014");
			resultMessages.add(resultMessage);
		}

		// 金额
		if (null == update_FtzInTxnDtl.getAmount()
				|| update_FtzInTxnDtl.getAmount().compareTo(BigDecimal.ZERO) != 1) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0015");
			resultMessages.add(resultMessage);
		}
		
		// 对方户名
		if (null == update_FtzInTxnDtl.getOppName()
				|| "".equals(update_FtzInTxnDtl.getOppName().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0011");
			resultMessages.add(resultMessage);
		}
		//起息日
		if (null == update_FtzInTxnDtl.getValueDate()
				|| "".equals(update_FtzInTxnDtl.getValueDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0001");
			resultMessages.add(resultMessage);
		}
		//利率
		if (null == update_FtzInTxnDtl.getInterestRate()
				|| "".equals(update_FtzInTxnDtl.getInterestRate())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0002");
			resultMessages.add(resultMessage);
		}
		//到息日
		if (null == update_FtzInTxnDtl.getExpireDate()
				|| "".equals(update_FtzInTxnDtl.getExpireDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0021");
			resultMessages.add(resultMessage);
		}
		// 期限单位update_FtzInTxnDtl
		if (null == update_FtzInTxnDtl.getTermUnit()
				|| "".equals(update_FtzInTxnDtl.getTermUnit().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0019");
			resultMessages.add(resultMessage);
		}
		// 期限长度
		if (null == update_FtzInTxnDtl.getTermLength()
				|| "".equals(update_FtzInTxnDtl.getTermLength())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0018");
			resultMessages.add(resultMessage);
		}
		// 交易性质
		if (null == update_FtzInTxnDtl.getTranType()
				|| "".equals(update_FtzInTxnDtl.getTranType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0017");
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
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			if (result.hasErrors()) {
				return "ftzmis/FTZ210106_Input_Dtl_Dtl";
			}
			return "ftzmis/FTZ210106_Input_Dtl_Dtl";
		}
		update_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getTranDate()));
		update_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getValueDate()));
		update_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInTxnDtl.getOrgTranDate()));
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
		int i = ftz210101Serv.updateFtzInTxnDtlSelective(update_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0026"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setExpireDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getExpireDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		form.getFtzInTxnDtl().setValueDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getValueDate()));
		return "ftzmis/FTZ210106_Input_Dtl_Dtl";
	}

	
	@RequestMapping("UptDtlDtlInit")
	public String UptDtlDtlInit(Model model, FTZ210106Form form) {
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());
		FtzInTxnDtl result_FtzInTxnDtl = ftz210106Serv.queryFtzInTxnDtl(query_FtzInTxnDtl);
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
	//	result_FtzInTxnDtl.setValueDate(DateUtil
	//			.getFormatDateAddSprit(result_FtzInTxnDtl
	//					.getValueDate()));
		
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
		result_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getChkDatetime()));
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getMakDatetime()));
		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		form.setInput_Dtl_flag("upt");
		return "ftzmis/FTZ210106_Input_Dtl_Dtl";
	}
	
	@RequestMapping("AddQry")
	public String queryAdd(Model model, FTZ210101Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("向中央银行借款录入查询开始...");
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
		query_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210106);

		// query DpMppCfg page list
		Page<FtzInMsgCtl> page = ftz210101Serv.queryFtzInMsgCtlPageInput(
				pageable, query_FtzInMsgCtl);

		if (page.getContent().size() > 0) {
			List<FtzInMsgCtl> ftzInMsgCtls = page.getContent();
			for (FtzInMsgCtl ftzInMsgCtl : ftzInMsgCtls) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}
			model.addAttribute("page", page);
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.info("向中央银行借款录入查询开始...");
			return "ftzmis/FTZ210106_Input_Qry";
		}
		
		
		logger.info("向中央银行借款录入查询结束...");
		return "ftzmis/FTZ210106_Input_Qry";
	}
	//新增批量a
	@RequestMapping("AddDtlInit")
	public String AddDtlInit(Model model, FTZ210106Form form) {
		form.setInput_flag("add");
		model.addAttribute("pageUrl", "/FTZ210106/AddDtlInit");
		return "ftzmis/FTZ210106_Input_Dtl";
	}
	//修改批量
	@RequestMapping("UptDtlInit")
	public String UptDtlInit(Model model, FTZ210106Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("向中央银行借款录入批量录入更新初始化开始...");
		// 检查批量是否存在
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210106Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			logger.info("向中央银行借款录入批量录入更新初始化结束...");
			return "ftzmis/FTZ210106_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		form.setFtzInMsgCtl(ftzInMsgCtl);
		// 将查询数据放入form
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(pageable,
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
				ftzInTxnDtl.setValueDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
			}
			model.addAttribute("page", page);
		}

		// 清空页面列表选择Key
		form.setSelected_msgId("");
		form.setSelected_seqNo(null);
		logger.info("向中央银行借款录入批量录入更新初始化结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210106/UptDtlInit");
		return "ftzmis/FTZ210106_Input_Dtl";
	}
	//提交批量
	@RequestMapping("UptDtlSubmit")
	public String UptDtlSubmit(Model model, FTZ210106Form form, @PageableDefaults Pageable pageable) {
		FtzInMsgCtl update_FtzInMsgCtl = form.getFtzInMsgCtl();
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
					.fromCode("e.ftzmis.210101.0033");
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
		if (null == insert_FtzInMsgCtl.getBalance()
				|| insert_FtzInMsgCtl.getBalance().compareTo(BigDecimal.ZERO) != 1) {
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
		// 开户机构代码
		if (null == insert_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(insert_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0003");
			resultMessages.add(resultMessage);
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			model.addAttribute("pageUrl", "/FTZ210106/UptDtlInit");
			return "ftzmis/FTZ210106_Input_Dtl";
		}
		
		UserInf userInfo = ContextConst.getCurrentUser();
		update_FtzInMsgCtl.setMakUserId(userInfo.getUserid());
		update_FtzInMsgCtl.setRsv1(update_FtzInMsgCtl.getMakDatetime());
		update_FtzInMsgCtl.setRsv2(update_FtzInMsgCtl.getChkDatetime());
		update_FtzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		update_FtzInMsgCtl.setSubmitDate(DateUtil
				.getFormatDateRemoveSprit(update_FtzInMsgCtl.getSubmitDate()));
		int i = ftz210106Serv.updateFtzInMsgCtl(update_FtzInMsgCtl,null);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0003"));
			form.setFtzInMsgCtl(ftz210106Serv
					.queryFtzInMsgCtl(update_FtzInMsgCtl));
			form.getFtzInMsgCtl().setSubmitDate(
					DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
							.getSubmitDate()));
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			// 查询明细数据列表
			Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(
					pageable, query_FtzInTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
				for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
					ftzInTxnDtl.setTranDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
				}
				model.addAttribute("page", page);
			}

			return "ftzmis/FTZ210106_Input_Dtl";
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.mpp.0002"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		form.setSelected_msgId("");
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(pageable,
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
		form.setSelected_seqNo(null);
		model.addAttribute("pageUrl", "/FTZ210106/UptDtlInit");
		return "ftzmis/FTZ210106_Input_Dtl";
	}
	//录入
	@RequestMapping("AddDtlSubmit")
	public String AddDtlSubmit(Model model,  FTZ210106Form form) {
		logger.info("向中央银行录入批量录入开始...");
		
		
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
					.fromCode("e.ftzmis.210101.0033");
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
		if (null == insert_FtzInMsgCtl.getBalance()
				|| insert_FtzInMsgCtl.getBalance().compareTo(BigDecimal.ZERO) != 1) {
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
		// 开户机构代码
		if (null == insert_FtzInMsgCtl.getAccOrgCode()
				|| "".equals(insert_FtzInMsgCtl.getAccOrgCode().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0003");
			resultMessages.add(resultMessage);
		}
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210106_Input_Add";
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
		insert_FtzInMsgCtl.setMsgNo(CommonConst.MSG_NO_210106);
		
		FtzInTxnDtl insert_FtzInTxnDtl = new FtzInTxnDtl();
		insert_FtzInTxnDtl = form.getFtzInTxnDtl();
		// 插入信息批量头
		int i = ftz210106Serv.insertFtzInMsgCtl(insert_FtzInMsgCtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
		}
		form.getFtzInMsgCtl().setSubmitDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInMsgCtl()
						.getSubmitDate()));
		logger.info("向中央银行借款录入批量录入结束...");
		form.setInput_flag("upt");
		model.addAttribute("pageUrl", "/FTZ210106/UptDtlInit");
		return "ftzmis/FTZ210106_Input_Dtl";
	}
	//批量删除
	@RequestMapping("InputDel")
	public String delDtl(Model model, FTZ210106Form form) {
		logger.info("向中央银行借款查询批量删除开始...");
		FtzInMsgCtl del_FtzInMsgCtl = new FtzInMsgCtl();
		del_FtzInMsgCtl.setMsgId(form.getSelected_msgId());

		int i = ftz210106Serv.deleteFtzInMsgCtl(del_FtzInMsgCtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_msgId("");
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_msgId("");
			logger.info("向中央银行借款查询批量删除结束...");
			return "forward:/FTZ210106/AddQry";
		}

		logger.info("向中央银行借款查询批量删除结束...");
		return "forward:/FTZ210106/AddQry";
	}
	//录入完成
	@RequestMapping("SubmitDtl")
	public String SubmitDtl(Model model, FTZ210106Form form) {
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(form.getSelected_msgId());
		if(form.getQuery_msgStatus().equals("02")){
			model.addAttribute(ResultMessages.error().add(
			"该数据已经录入完成!"));
	return "forward:/FTZ210106/AddQry";
		}
		int i =  ftz210106Serv.updateFtzInMsgCtlForSubmit(ftzInMsgCtl);

		if (i == 0) {
			model.addAttribute(ResultMessages.error().add(
					"e.ftzmis.210101.0001"));
			return "forward:/FTZ210106/AddQry";
		}

		model.addAttribute(ResultMessages.success().add("i.ftzmis.210101.0001"));
		return "forward:/FTZ210106/AddQry";
	}
	//批量明细
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210106Form form,
			@PageableDefaults Pageable pageable) {
		logger.info("向中央银行借款批量查询开始...");
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210106Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			form.setSelected_msgId("");
			form.setSelected_seqNo(null);
			logger.error("查询无记录！");
			logger.info("向中央银行借款批量查询结束...");
			return "ftzmis/FTZ210106_Qry";
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
			form.setFtzInMsgCtl(result_FtzInMsgCtl);
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
			Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(
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
				form.setSelected_seqNo(null);
			}
			logger.info("向中央银行借款批量查询结束...");
			return "ftzmis/FTZ210106_Qry_Dtl";
		}
	}
	@RequestMapping("InputDtlDel")
	public String delDtlDtl(Model model, FTZ210106Form form) {
		FtzInTxnDtl del_FtzInTxnDtl = new FtzInTxnDtl();
		del_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		del_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		int i = ftz210106Serv.deleteFtzInTxnDtl(del_FtzInTxnDtl);

		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0002"));
			form.setSelected_seqNo(null);
		} else {
			model.addAttribute(ResultMessages.success().add("i.dp.0003"));
			form.setSelected_seqNo(null);
			return "forward:/FTZ210106/UptDtlInit";
		}

		return "forward:/FTZ210106/UptDtlInit";
	}
	@RequestMapping("QryDtlDtl")
	public String queryDtlDtl(Model model, FTZ210106Form form) {
		logger.info("向中央银行借款明细详情查询开始...");
		// 组装查询信息
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		query_FtzInTxnDtl.setSeqNo(form.getSelected_seqNo());

		// 查询数据
		FtzInTxnDtl result_FtzInTxnDtl = ftz210106Serv
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			logger.error("查询无记录！");
			logger.info("向中央银行借款明细查询结束...");
			return "ftzmis/FTZ210106_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
		result_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl.getMakDatetime()));
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
		
		result_FtzInTxnDtl.setChkDatetime(DateUtil
				.getFormatDateTimeAddSpritAndColon(result_FtzInTxnDtl
						.getChkDatetime()));
		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		logger.info("向中央银行借款明细详情查询结束 ..");
		return "ftzmis/FTZ210106_Qry_Dtl_Dtl";
	}
	@RequestMapping("DtlInitReflash")
	public String DtlInitReflash(Model model, FTZ210106Form form,
			@PageableDefaults Pageable pageable) {
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getSelected_msgId());
		FtzInMsgCtl ftzInMsgCtl = ftz210106Serv
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == ftzInMsgCtl) {
			model.addAttribute(ResultMessages.error().add("w.sm.0001"));
			return "ftzmis/FTZ210106_Input_Dtl";
		}
		ftzInMsgCtl.setSubmitDate(DateUtil.getFormatDateAddSprit(ftzInMsgCtl
				.getSubmitDate()));
		form.setFtzInMsgCtl(ftzInMsgCtl);
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getSelected_msgId());
		// 查询明细数据列表
		Page<FtzInTxnDtl> page = ftz210106Serv.queryFtzInTxnDtlPage(pageable,
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
		form.setSelected_seqNo(null);
		return "ftzmis/FTZ210106_Input_Dtl";
	}
	
	//提交明细
	@RequestMapping("AddDtlDtlSubmit")
	public String AddDtlDtlSubmit(Model model, FTZ210106Form form) {
		
		FtzInTxnDtl issert_FtzInTxnDtl = form.getFtzInTxnDtl();
		
		// 开始校验
		ResultMessages resultMessages = ResultMessages.error();


		// 记帐日期
		if (null == issert_FtzInTxnDtl.getTranDate()
				|| "".equals(issert_FtzInTxnDtl.getTranDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0014");
			resultMessages.add(resultMessage);
		}

		// 金额
		if (null == issert_FtzInTxnDtl.getAmount()
				|| issert_FtzInTxnDtl.getAmount().compareTo(BigDecimal.ZERO) != 1) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0015");
			resultMessages.add(resultMessage);
		}
		// 对方户名
		if (null == issert_FtzInTxnDtl.getOppName()
				|| "".equals(issert_FtzInTxnDtl.getOppName().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210310.0011");
			resultMessages.add(resultMessage);
		}
		//起息日
		if (null == issert_FtzInTxnDtl.getValueDate()
				|| "".equals(issert_FtzInTxnDtl.getValueDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0001");
			resultMessages.add(resultMessage);
		}
		if (null == issert_FtzInTxnDtl.getInterestRate()
				|| "".equals(issert_FtzInTxnDtl.getInterestRate())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210106.0002");
			resultMessages.add(resultMessage);
		}
		//到息日
		if (null == issert_FtzInTxnDtl.getExpireDate()
				|| "".equals(issert_FtzInTxnDtl.getExpireDate().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0021");
			resultMessages.add(resultMessage);
		}
		// 期限单位
		if (null == issert_FtzInTxnDtl.getTermUnit()
				|| "".equals(issert_FtzInTxnDtl.getTermUnit().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0019");
			resultMessages.add(resultMessage);
		}
		// 期限长度
		if (null == issert_FtzInTxnDtl.getTermLength()
				|| "".equals(issert_FtzInTxnDtl.getTermLength())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0018");
			resultMessages.add(resultMessage);
		}
		// 交易性质
		if (null == issert_FtzInTxnDtl.getTranType()
				|| "".equals(issert_FtzInTxnDtl.getTranType().trim())) {
			ResultMessage resultMessage = ResultMessage
					.fromCode("e.ftzmis.210101.0017");
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
		if (resultMessages.isNotEmpty()) {
			model.addAttribute(resultMessages);
			return "ftzmis/FTZ210106_Input_Dtl_Dtl";
		}
		
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(issert_FtzInTxnDtl.getMsgId());
		List<FtzInTxnDtl> ftzInTxnDtls = ftz210106Serv
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
		issert_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getValueDate()));
		issert_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getOrgTranDate()));
		issert_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateRemoveSprit(issert_FtzInTxnDtl.getExpireDate()));
		UserInf userInfo = ContextConst.getCurrentUser();
		issert_FtzInTxnDtl.setMakUserId(userInfo.getUserid());
		issert_FtzInTxnDtl.setMakDatetime(DateUtil
				.getFormatDateRemoveSprit(DateUtil.getNowInputDateTime().substring(0, 8)));
		issert_FtzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int i = ftz210106Serv.insertFtzInTxnDtl(issert_FtzInTxnDtl);
		if (i < 1) {
			model.addAttribute(ResultMessages.error().add("e.sysrunner.0006"));
		} else {
			model.addAttribute(ResultMessages.success().add("i.sm.0001"));
			model.addAttribute("uptFlag", "1");
		}
		form.getFtzInTxnDtl().setTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getTranDate()));
		form.getFtzInTxnDtl().setOrgTranDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getOrgTranDate()));
		form.getFtzInTxnDtl().setExpireDate(
				DateUtil.getFormatDateAddSprit(form.getFtzInTxnDtl()
						.getExpireDate()));
		form.getFtzInTxnDtl().setValueDate(DateUtil
				.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));
		form.getFtzInTxnDtl().setMakDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getMakDatetime()));
		form.getFtzInTxnDtl().setChkDatetime(
				DateUtil.getFormatDateTimeAddSpritAndColon(form
						.getFtzInTxnDtl().getChkDatetime()));
		return "ftzmis/FTZ210106_Input_Dtl_Dtl";
	}
	
	@Resource
	protected NumberService numberService;

}
