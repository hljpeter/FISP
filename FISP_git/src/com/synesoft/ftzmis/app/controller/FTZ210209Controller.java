package com.synesoft.ftzmis.app.controller;





import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZ210209Form;
import com.synesoft.ftzmis.app.model.FTZ210209Form.FTZInFormMsgKey;
import com.synesoft.ftzmis.app.model.FTZ210209Form.FTZInFormTxnKey;
import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;
import com.synesoft.ftzmis.domain.service.FTZ210101Service;
import com.synesoft.ftzmis.domain.service.FTZInCommonService;


/**
 * 6.2.6　应付保函/备用证（210209）
 * @author yyw
 * @date 2013-12-25
 */
@Controller
@RequestMapping(value = "FTZ210209")
public class FTZ210209Controller {
	private static final Logger log = LoggerFactory.getLogger(FTZ210209Controller.class);

	private final static String MODEL_KEY_PAGE = "page";
	private final static String DTL_MAP_KEY_MSG = "MsgCtl";
	private final static String DTL_MAP_KEY_TXN = "TxnDtl";
	private final static String AUTH_PASS = "pass";
	private final static String AUTH_REJECT = "reject";
	
	@ModelAttribute
	public FTZ210209Form setForm() {
		return new FTZ210209Form();
	}

	/**
	 * 一级页面 - 初始化
	 * 跳转FTZ210209_Input_Qry.jsp
	 * @return
	 */
//	@RequestMapping("/AddQry")
//	public String qryInit() {
//		log.info("FTZ210209Controller.qryInit() start ...");
//		return "ftzmis/FTZ210209_Input_Qry";
//		//return "forward:/FTZ210209/Input/Qry";
//	}

	/**
	 * 一级页面 - 查询ok
	 * 跳转FTZ210209_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/AddQry")
	public String inputQry( Model model, FTZ210209Form form,
			@PageableDefaults Pageable pageable) {
		log.info("FTZ210209Controller.inputQry() start ...");

//		if (result.hasErrors()) {
//			return "ftzmis/FTZ210209_Input_Qry";
//		}
		
		FtzInMsgCtlVO vo = new FtzInMsgCtlVO();
		vo.setMsgNo(CommonConst.MSG_NO_210209);
		// data ready
		FtzInMsgCtlVO msgCtlVO = (FtzInMsgCtlVO)form.getFtzInMsgCtlVO();
		if(null != msgCtlVO)
		{
			vo.setBranchId(msgCtlVO.getBranchId());
			vo.setStartDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getStartDate())));
			vo.setEndDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getEndDate())));
			vo.setMsgNo(CommonConst.MSG_NO_210209);
			vo.setMsgId(StringUtil.trim(msgCtlVO.getMsgId()));
			vo.setAccountNo(msgCtlVO.getAccountNo());//账号 
			if (!StringUtil.isNotTrimEmpty(msgCtlVO.getMsgStatus())) {
				vo.setMsgStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
						CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL });
			} else {
				vo.setMsgStatus(msgCtlVO.getMsgStatus());
			}
		}
		else {
			vo.setMsgStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
					CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL });
		}
		try {
			Page<FtzInMsgCtlVO> page = ftz210209Service.transQueryMsgPage(pageable, vo);
			
			for (FtzInMsgCtl ftzInMsgCtl : page) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}//申报时间格式化
			
			model.addAttribute(MODEL_KEY_PAGE, page);
			return "ftzmis/FTZ210209_Input_Qry";
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data!");
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry";
		}
	}

	/**
	 * 跳转到2级页面
	 * 跳转FTZ210209_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Init")
	public String inputDtlInit(@Validated({FTZInFormMsgKey.class}) FTZ210209Form form, BindingResult result,  
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.inputDtlInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry";
		}
		
		try {
			Map<String, Object> map = ftz210209Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), null);
			
			if (!StringUtil.isNotTrimEmpty(form.getActionFlag())) {
				form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			}
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setSubmitDate(DateUtil
						.getFormatDateAddSprit(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).getSubmitDate()));//格式化申报时间
			form.setFtzInMsgCtl((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG));
			 
			Page<FtzInTxnDtl> page=(Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN);
			for (FtzInTxnDtl ftzInTxnDtl : page) {
				ftzInTxnDtl.setTranDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));//记账日期格式化
				ftzInTxnDtl.setValueDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));//起息日格式化
				ftzInTxnDtl.setExpireDate(DateUtil
						.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));//到息日格式化
			}
			model.addAttribute(MODEL_KEY_PAGE, page);
			
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInMsgCtl detail failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry";
		}
	}

	/**
	 * 新增批量头-2级页面
	 * 跳转FTZ210209_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Init")
	public String inputAddMsgInit(FTZ210209Form form, BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.inputAddMsgInit() start ...");
		
		FtzInMsgCtl ctl = new FtzInMsgCtl();
		ctl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		//ctl.setBranchId(ContextConst.getCurrentUser().getLoginorg());
		//ctl.setWorkDate(DateUtil.getNowOutputDate());
		//ctl.setMsgId(numberService.getSysIDSequence(16));

		form.setFtzInMsgCtl(ctl);
		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
	
		return "ftzmis/FTZ210209_Input_Qry_Dtl";
	}

	/**
	 * 新增批量头-提交
	 * 跳转FTZ210301_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Sumbit")
	public String inputAddMsgSubmit(FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputAddMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
		
		try {
			FtzInMsgCtl ctl = form.getFtzInMsgCtl();
			ctl.setMsgId(numberService.getSysIDSequence(16));
			//ctl.setWorkDate(DateUtil.getFormatDateRemoveSprit(ctl.getWorkDate()));
			ctl.setBranchId(StringUtil.isNotTrimEmpty(ctl.getBranchId())? StringUtil
					.trim(ctl.getBranchId()): ContextConst.getOrgInfByUser().getOrgid());//批量id
			ctl.setMsgStatus(StringUtil.isNotTrimEmpty(ctl.getMsgStatus())? StringUtil
					.trim(ctl.getMsgStatus()): CommonConst.FTZ_MSG_STATUS_INPUTING);
			ctl.setSubmitDate(DateUtil
					.getFormatDateRemoveSprit(ctl.getSubmitDate()));//申报日期
			ctl.setTotalCount(0);//总数为0
			
			ftz210209Service.transUpdateAddMsgCtl(ctl, CommonConst.MSG_NO_210209);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Add.Msg.Ctl.Success")));

			form.setActionFlag(CommonConst.ACTION_FLAG_UPT_MSG);
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInMsgCtl().getSubmitDate()));
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Add FtzInMsgCtl failure!" + e.getMessage());
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInMsgCtl().getSubmitDate()));
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}

	/**
	 * 刷新明细页面(FTZ210301_Input_Qry_Dtl.jsp)
	 * 跳转FTZ210301_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Refresh")
	public String inputDtlRefresh(FTZ210209Form form, BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.inputDtlRefresh() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}

		try {
			Map<String, Object> map = ftz210209Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), null);

			form.setActionFlag(CommonConst.ACTION_FLAG_REF_MSG);
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setSubmitDate(DateUtil
					.getFormatDateAddSprit(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).getSubmitDate()));//格式化申报时间
		form.setFtzInMsgCtl((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG));
		 
		Page<FtzInTxnDtl> page=(Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN);
		for (FtzInTxnDtl ftzInTxnDtl : page) {
			ftzInTxnDtl.setTranDate(DateUtil
					.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));//记账日期格式化
			ftzInTxnDtl.setValueDate(DateUtil
					.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));//起息日格式化
		}
			model.addAttribute("page", (Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Refresh FtzInMsgCtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}

	/**
	 * 修改批量头-二级页面
	 * forward:/FTZ210302/Input/Dtl/Init
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Init")
	public String inputUptMsgInit(@Validated({FTZInFormMsgKey.class}) FTZ210209Form form, BindingResult result, 
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.inputUptMsgInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry";
		}
		
		return "forward:/FTZ210209/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
	
	}

	/**
	 * 修改批量头-提交
	 * 跳转FTZ210301_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Sumbit")
	public String inputUptMsgSubmit(FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputUptMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_UPT_MSG);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
		
		try {
			
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInMsgCtl().getSubmitDate()));
			
			ftz210209Service.transUpdateMsgCtl(form.getFtzInMsgCtl(), CommonConst.FTZ_MSG_EDIT_FLAG_UPDATE);
			
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInMsgCtl().getSubmitDate() ));//申报日格式化
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Update.Msg.Ctl.Success")));
			
			return "forward:/FTZ210209/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
			//return "forward:/FTZ210209/Input/Dtl/Refresh";
		} catch (BusinessException e) {
			log.error("Update FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除批量头
	 * 跳转FTZ210209_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/DelMsg")
	public String inputDelMsg(@Validated({FTZInFormMsgKey.class}) FTZ210209Form form, BindingResult result, Model model) { // TODO validate
		log.info("FTZ210209Controller.inputDelMsg() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry";
		}
		
		try {
			ftz210209Service.transDeleteMsgCtl(form.getFtzInMsgCtl());

			log.info("Delete success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Delete.Msg.Ctl.Success")));

			return "forward:/FTZ210209/AddQry";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry";
		}
	}
	
	/**
	 * 提交批量头
	 * 跳转FTZ210209_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/SubmitMsg")
	public String inputSubmitMsg(@Validated({FTZInFormMsgKey.class}) FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputSubmitMsg() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry";
		}
		
		try {
			
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			List<FtzInTxnDtl> ftzInTxnDtls = ftz210101Service
					.queryFtzInTxnDtlList(query_FtzInTxnDtl);
			if (null != ftzInTxnDtls && ftzInTxnDtls.size() > 0) {
				for (FtzInTxnDtl dtl : ftzInTxnDtls) {
					if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(dtl
							.getChkStatus())) {
						model.addAttribute(ResultMessages.error().add(
								"e.ftzmis.210101.0030"));
						return "forward:/FTZ210209/AddQry";
					}
				}
			}
			
			ftz210209Service.transUpdateSubmitMsgCtl(form.getFtzInMsgCtl());

			log.info("Submit success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Update.Msg.Ctl.Success")));

			return "forward:/FTZ210209/AddQry";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry";
		}
	}

	/**
	 * 新增交易 - 初始化
	 * 跳转FTZ210209_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Init")
	public String inputAddTxnInit(@Validated({FTZInFormMsgKey.class}) FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputAddTxnInit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_TXN);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
		
		try {
			FtzInTxnDtl txn=form.getFtzInTxnDtl();
			//String seqNo = ftz210209Service.transQueryTxnDtlMaxSeqNo(txn);
			
			FtzInTxnDtl txn1=new FtzInTxnDtl();
			//txn1.setSeqNo(seqNo);
			txn1.setMsgId(txn.getMsgId());
			form.setFtzInTxnDtl(txn1);

			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}

	/**
	 * 新增交易明细 - 提交
	 * 跳转FTZ210209_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Submit")
	public String inputAddTxnSubmit(FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputAddTxnSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_TXN);
		
		try {
			
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getTranDate()));//记帐日期
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始交易日期
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getValueDate()));//起息日
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日
			
			String seqNo = ftz210209Service.transQueryTxnDtlMaxSeqNo(form.getFtzInTxnDtl());
			form.getFtzInTxnDtl().setSeqNo(seqNo);
			ftz210209Service.transUpdateAddTxnDtl(form.getFtzInTxnDtl());
//			form.getFtzInMsgCtl().setTotalCount(form.getFtzInMsgCtl().getTotalCount()+1);//加一 
//			ftz210209Service.transUpdateMsgCtl(form.getFtzInMsgCtl(), CommonConst.FTZ_MSG_EDIT_FLAG_UPDATE);
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getTranDate()));//记账日期格式化
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));//起息日格式化
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始格式化
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日式化
			form.getFtzInTxnDtl().setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Add.Txn.Dtl.Success")));

			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getTranDate()));//记账日期格式化
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));//起息日格式化
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始格式化
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日式化
			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 修改交易 - 初始化
	 * forward:/FTZ210209/Input/DtlTxn/Init
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Init")
	public String inputUptTxnInit(@Validated({FTZInFormTxnKey.class}) FTZ210209Form form, BindingResult result, Model model) {
		log.info("FTZ210209Controller.inputUptTxnInit() start ...");

		return "forward:/FTZ210209/Input/DtlTxn/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_TXN;
	
	}

	/**
	 * 修改交易 - 提交
	 * 跳转FTZ210209_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Submit")
	public String inputUptTxnSubmit(FTZ210209Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210209Controller.inputUptTxnSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_UPT_TXN);
		
		try {
			
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getTranDate()));//记帐日期
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始交易日期
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getValueDate()));//起息日
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日
			
			form.getFtzInTxnDtl().setMakDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));//录入时间
			form.getFtzInTxnDtl().setChkDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getChkDatetime()));//审核时间
			
			ftz210209Service.transUpdateTxnDtl(form.getFtzInTxnDtl());
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getTranDate()));//记账日期格式化
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));//起息日格式化
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始格式化
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日式化
			
			FtzInTxnDtl ftzInTxnDtl=new FtzInTxnDtl();
			ftzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
			ftzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());
			ftzInTxnDtl=ftz210209Service.transQueryTxnById(ftzInTxnDtl);
			form.getFtzInTxnDtl().setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(ftzInTxnDtl.getMakDatetime()));
			form.getFtzInTxnDtl().setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInTxnDtl().getChkDatetime()));//chkdatetime
			form.getFtzInTxnDtl().setMakUserId(ftzInTxnDtl.getMakUserId());
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Update.Txn.Dtl.Success")));

			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getTranDate()));//记账日期格式化
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));//起息日格式化
			form.getFtzInTxnDtl().setOrgTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始格式化
			form.getFtzInTxnDtl().setExpireDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日式化
			form.getFtzInTxnDtl().setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));
			form.getFtzInTxnDtl().setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInTxnDtl().getChkDatetime()));//chkdatetime
			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
			//return "forward:/FTZ210209/Input/DtlTxn/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_TXN;
		}
	}

	/**
	 * 交易 - 明细
	 * 跳转FTZ210209_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/DtlTxn/Init")
	public String inputDtlTxnInit(@Validated({FTZInFormTxnKey.class}) FTZ210209Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210209Controller.inputDtlTxnInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
		
		try {
			FtzInTxnDtl txn = ftz210209Service.transQueryTxnById(form.getFtzInTxnDtl());
	
//			txn.setSubmitDate(DateUtil.getFormatDateAddSprit(txn.getSubmitDate()));
//			txn.setExpirationDate(DateUtil.getFormatDateAddSprit(txn.getExpirationDate()));
			
			if (!StringUtil.isNotTrimEmpty(form.getActionFlag())) {
				form.setActionFlag(CommonConst.ACTION_FLAG_DTL_TXN);
			} 
			
			txn.setValueDate(DateUtil
					.getFormatDateAddSprit(txn.getValueDate()));//起息日期格式化
		
			txn.setTranDate(DateUtil
					.getFormatDateAddSprit(txn.getTranDate()));//记账交易日期
			
			if(txn.getOrgTranDate()!=null)
			{
			txn.setOrgTranDate(DateUtil
					.getFormatDateAddSprit(txn.getOrgTranDate()));//原始交易日期
			}
			if(txn.getExpireDate()!=null)
			{
			txn.setExpireDate(DateUtil
					.getFormatDateAddSprit(txn.getExpireDate()));//到期日
			}
			txn.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(txn.getMakDatetime()));//makdatetime
			txn.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(txn.getChkDatetime()));//chkdatetime
			form.setFtzInTxnDtl(txn);
			
			return "ftzmis/FTZ210209_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除交易明细 
	 * forward:/FTZ210209/Input/Dtl/Refresh
	 * @return
	 */
	@RequestMapping("/Input/DelTxn/Submit")
	public String inputDelTxnSubmit(@Validated({FTZInFormTxnKey.class}) FTZ210209Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210209Controller.inputDelTxnSubmit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
		
		try {
			
			form.getFtzInTxnDtl().setMakDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));//录入时间
			form.getFtzInTxnDtl().setChkDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getChkDatetime()));//审核时间
			
			ftz210209Service.transUpdateDeleteTxnDtl(form.getFtzInTxnDtl());
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Delete.Txn.Dtl.Success")));
			
			return "forward:/FTZ210209/Input/Dtl/Refresh";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Input_Qry_Dtl";
		}
	}
	
	@RequestMapping("DtlAccountQry")
	public @ResponseBody
	String DtlAccountQry(@RequestParam("accountNo") String accountNo, Model model) {
		JSONObject jo = new JSONObject();
		FtzActMstr query_FtzActMstr = new FtzActMstr();
		query_FtzActMstr.setAccountNo(accountNo);
//		if(null != subAccountNo && !"".equals(subAccountNo.trim())){
//			query_FtzActMstr.setSubAccountNo(subAccountNo);
//		}
		List<FtzActMstr> result_FtzActMstrs = ftz210101Service
				.queryFtzActMstrs(query_FtzActMstr);
		DecimalFormat df_amt = new DecimalFormat("###,###,###,###.00");
		if (null == result_FtzActMstrs) {
			jo.put("dtlExist", false);
		} else {
			if(result_FtzActMstrs.size()==1)
			{
				FtzActMstr result_FtzActMstr = result_FtzActMstrs.get(0);
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

		}
		return jo.toString();
		// response.getWriter().write(jo.toString());
	}
	
	/** ====================================== 审核 ====================================== */
	/**
	 * 查询批量 - 明细(批量全部明细和批量审核明细两个按钮)
	 * 跳转FTZ210209_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Auth/DtlMsg/Init")
	public String authDtlMsgInit(FTZ210209Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.authDtlMsgInit() start ...");

		try {
			String status = CommonConst.PAGE_SEARCH_ALL_DTL.equals(form.getOperFlag())? null: CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED;
			
			Map<String, Object> map = ftz210209Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), status);
			
			form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setSubmitDate(DateUtil
					.getFormatDateAddSprit(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).getSubmitDate()));
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setSndDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG))
							.getSndDatetime()));
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setAckDatetime(DateUtil
					.getFormatDateTimeAddSpritAndColon(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG))
							.getAckDatetime()));
			((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG)).setBalanceCode(((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG))
					.getBalanceCode().trim());
			
			form.setFtzInMsgCtl((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG));
			
			for (FtzInTxnDtl ftzInTxnDtl : (Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN))
			{
			ftzInTxnDtl.setTranDate(DateUtil
					.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
			ftzInTxnDtl.setValueDate(DateUtil
					.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
			ftzInTxnDtl.setExpireDate(DateUtil
					.getFormatDateAddSprit(ftzInTxnDtl.getExpireDate()));
			}
			model.addAttribute("page", (Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210209_Auth_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Auth_Qry";
		}
	}
	
	/**
	 * 查询交易 - 明细
	 * 跳转FTZ210209_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Init")
	public String authDtlTxnInit(FTZ210209Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.authDtlTxnInit() start ...");

		try {
			FtzInTxnDtl txn = ftz210209Service.transQueryTxnById(form.getFtzInTxnDtl());
			txn.setTranDate(DateUtil
					.getFormatDateAddSprit(txn.getTranDate()));//记账日期格式化
			txn.setValueDate(DateUtil
					.getFormatDateAddSprit(txn.getValueDate()));//起息日格式化
			txn.setOrgTranDate(DateUtil
					.getFormatDateAddSprit(txn.getOrgTranDate()));//原始格式化
			txn.setExpireDate(DateUtil
					.getFormatDateAddSprit(txn.getExpireDate()));//到期日式化
			txn.setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(txn.getMakDatetime()));
		    txn.setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(txn.getChkDatetime()));
			form.setFtzInTxnDtl(txn);

			FtzInMsgCtl msg=new FtzInMsgCtl();
			msg.setMsgId(txn.getMsgId());
			msg=ftz210209Service.transQueryMsgById(msg);
			form.setFtzInMsgCtl(msg);
//			int ret = ftz210209Service.getAuthTxnCountById(form.getFtzInTxnDtl());
//			if (ret == 1) {
//				form.setMsg("i.ftzmis.210209.0006");
//			}
			
			return "ftzmis/FTZ210209_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());

			FtzInTxnDtl txn = ftz210209Service.transQueryTxnById(form.getFtzInTxnDtl());
			form.setFtzInTxnDtl(txn);
			return "ftzmis/FTZ210209_Auth_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 审核批量（批量审核提交）
	 * 跳转FTZ210209_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlMsg/Auth")
	public String authDtlMsgAuth(FTZ210209Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.authDtlMsgAuth() start ...");

		try {
			FtzInMsgCtl result_FtzInMsgCtl = form.getFtzInMsgCtl();
			if (CommonConst.FTZ_MSG_STATUS_INPUTING.equals(result_FtzInMsgCtl
					.getMsgStatus())) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0032"));
				return "forward:/FTZ210209/Auth/DtlMsg/Init";
			}
			UserInf userInfo = ContextConst.getCurrentUser();
			if (userInfo.getUserid().equals(result_FtzInMsgCtl.getMakUserId())) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0025"));
				return "forward:/FTZ210209/Auth/DtlMsg/Init";
			}
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			List<FtzInTxnDtl> ftzInTxnDtls = ftz210101Service
					.queryFtzInTxnDtlList(query_FtzInTxnDtl);
			if (null == ftzInTxnDtls || ftzInTxnDtls.size() < 1) {
				ftz210209Service.authMsgCtl(form.getFtzInMsgCtl());

				model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
				return "forward:/FTZ210209/Auth/DtlMsg/Init";
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
					//return "forward:/FTZ210209/Auth/DtlMsg/Init";
					return "forward:/FTZ210209/Auth/DtlMsg/Init";
				}
				if (count_authFail > 0) {
					model.addAttribute(ResultMessages.error().add(
							"e.ftzmis.210101.0031",
							sb_unAuth.subSequence(0, sb_authFail.length() - 1)));
					return "forward:/FTZ210209/Auth/DtlMsg/Init";
				}
			
			
			ftz210209Service.authMsgCtl(form.getFtzInMsgCtl());
			ftzMsgProcService.submitMsg(form.getFtzInMsgCtl().getMsgNo(),
					form.getFtzInMsgCtl().getMsgId());
			model.addAttribute(ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
			return "forward:/FTZ210209/Auth/DtlMsg/Init";
			}
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/FTZ210209/Auth/DtlMsg/Init";
		}
	}

	/**
	 * 审核交易（审核通过和审核拒绝按钮）
	 * 跳转FTZ210209_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Auth")
	public String authDtlTxnAuth(FTZ210209Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.authDtlTxnAuth() start ...");

		try {
			
			UserInf userInfo = ContextConst.getCurrentUser();
			if (userInfo.getUserid().equals(form.getFtzInTxnDtl().getMakUserId())) {
				model.addAttribute(ResultMessages.error().add(
						"e.ftzmis.210101.0025"));
				return "forward:/FTZ210209/Auth/DtlTxn/Init";
			}
			ftz210209Service.authTxnDtl(form.getFtzInTxnDtl(), form.getOperFlag());

//			if(form.getOperFlag()==CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)
//			{
//			 model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
//			}
//			else
//			{
		     model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
//			}
		     form.getFtzInTxnDtl().setMakDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));
		     FtzInTxnDtl txn=ftz210209Service.transQueryTxnById(form.getFtzInTxnDtl());
		     form.getFtzInTxnDtl().setChkDatetime(DateUtil.getFormatDateTimeAddSpritAndColon(txn.getChkDatetime()));
			return "ftzmis/FTZ210209_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Auth_Qry_Dtl";
		}
	}

	/**
	 * 审核交易（下一条）
	 * 跳转FTZ210209_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Next")
	public String authDtlTxnNext(FTZ210209Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210209Controller.authDtlTxnNext() start ...");

		try {
			FtzInTxnDtl result = ftz210209Service.getNextAuthTxnById(form.getFtzInTxnDtl());
			form.setFtzInTxnDtl(result);

			int ret = ftz210209Service.getAuthTxnCountById(form.getFtzInTxnDtl());
			if (ret == 1) {
				form.setMsg("i.ftzmis.210209.0006");
			}
			
			return "ftzmis/FTZ210209_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210209_Auth_Qry_Dtl";
		}
	}
	
	
	@RequestMapping("QryDtl")
	public String queryDtl(Model model, FTZ210209Form form,
			@PageableDefaults Pageable pageable) {
		
		// 组装查询信息
		FtzInMsgCtl query_FtzInMsgCtl = new FtzInMsgCtl();
		query_FtzInMsgCtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
		// 查询数据
		FtzInMsgCtl result_FtzInMsgCtl = ftz210101Service
				.queryFtzInMsgCtl(query_FtzInMsgCtl);
		if (null == result_FtzInMsgCtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			
			return "ftzmis/FTZ210201_Qry";
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
			result_FtzInMsgCtl.setBalanceCode(result_FtzInMsgCtl
					.getBalanceCode().trim());
			form.setFtzInMsgCtl(result_FtzInMsgCtl);
			FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
			query_FtzInTxnDtl.setMsgId(form.getFtzInMsgCtl().getMsgId());
			Page<FtzInTxnDtl> page = ftz210101Service.queryFtzInTxnDtlPage(
					pageable, query_FtzInTxnDtl);
			if (page.getContent().size() > 0) {
				List<FtzInTxnDtl> ftzInTxnDtls = page.getContent();
				for (FtzInTxnDtl ftzInTxnDtl : ftzInTxnDtls) {
					ftzInTxnDtl.setTranDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getTranDate()));
					ftzInTxnDtl.setValueDate(DateUtil
							.getFormatDateAddSprit(ftzInTxnDtl.getValueDate()));
				}
				model.addAttribute("page", page);
				
			}
			return "ftzmis/FTZ210209_Qry_Dtl";
		}
	}

	@RequestMapping("QryDtlDtl")
	public String queryDtlDtl(Model model, FTZ210209Form form) {
		
		// 组装查询信息
		FtzInTxnDtl query_FtzInTxnDtl = new FtzInTxnDtl();
		query_FtzInTxnDtl.setMsgId(form.getFtzInTxnDtl().getMsgId());
		query_FtzInTxnDtl.setSeqNo(form.getFtzInTxnDtl().getSeqNo());
		// 查询数据
		FtzInTxnDtl result_FtzInTxnDtl = ftz210101Service
				.queryFtzInTxnDtl(query_FtzInTxnDtl);

		if (null == result_FtzInTxnDtl) {
			// 若无数据 则返回提示信息
			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
			
			return "ftzmis/FTZ210209_Qry_Dtl";
		}
		// 有数据则进行数据转换
		result_FtzInTxnDtl.setTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getTranDate()));
		result_FtzInTxnDtl.setOrgTranDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getOrgTranDate()));
		result_FtzInTxnDtl.setExpireDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getExpireDate()));
		result_FtzInTxnDtl.setValueDate(DateUtil
				.getFormatDateAddSprit(result_FtzInTxnDtl.getValueDate()));
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
			FtzBankCode result_FtzBankCode = ftz210101Service
					.queryFtzBankCode(query_FtzBankCode);
			if (null != result_FtzBankCode) {
				result_FtzInTxnDtl.setOppBankName(result_FtzBankCode
						.getBankName());
			}
		}

		form.setFtzInTxnDtl(result_FtzInTxnDtl);
		return "ftzmis/FTZ210209_Qry_Dtl_Dtl";
	}
	
	@Autowired
	private NumberService numberService;
	@Autowired
	private FTZInCommonService ftz210209Service;
	
	@Autowired
	private FTZ210101Service ftz210101Service;
	
	@Resource
	private FtzMsgProcService ftzMsgProcService;
}
