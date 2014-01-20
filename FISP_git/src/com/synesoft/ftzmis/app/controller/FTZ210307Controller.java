package com.synesoft.ftzmis.app.controller;

import java.util.Map;

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
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZ210307Form;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;
import com.synesoft.ftzmis.domain.service.FTZOffCommonService;

/**
 * 6.3.7　应收保函/备用证（210307）
 * @author yyw
 * @date 2014-01-07
 */
@Controller
@RequestMapping(value = "FTZ210307")
public class FTZ210307Controller {
	private static final Logger log = LoggerFactory.getLogger(FTZ210307Controller.class);

	private final static String MODEL_KEY_PAGE = "page";
	private final static String DTL_MAP_KEY_MSG = "MsgCtl";
	private final static String DTL_MAP_KEY_TXN = "TxnDtl";
	
	@ModelAttribute
	public FTZ210307Form setForm() {
		return new FTZ210307Form();
	}

	/** ====================================== 录入 ====================================== */
	/**
	 * 查询批量 - 初始化
	 * 跳转FTZ210307_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/Qry/Init")
	public String qryInit() {
		log.info("FTZ210307Controller.qryInit() start ...");
		return "ftzmis/FTZ210307_Input_Qry";
	}

	/**
	 * 查询批量 - 查询
	 * 跳转FTZ210307_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/Qry")
	public String inputQry(FTZ210307Form form, 
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.inputQry() start ...");

		FtzOffMsgCtlVO vo = new FtzOffMsgCtlVO();
		vo.setMsgNo(CommonConst.MSG_NO_210307);
		
		// data ready
		FtzOffMsgCtlVO msgCtlVO = (FtzOffMsgCtlVO) form.getFtzOffMsgCtlVO();
		if (null != msgCtlVO) {
			vo.setBranchId(msgCtlVO.getBranchId());
			vo.setStartDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getStartDate())));
			vo.setEndDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getEndDate())));
			vo.setMsgId(StringUtil.trim(msgCtlVO.getMsgId()));
			vo.setEditFlag(CommonConst.FTZ_MSG_EDIT_FLAG_ADD);	// 录入查询中，只能查到报"新增"的报文
			if (!StringUtil.isNotTrimEmpty(msgCtlVO.getMsgStatus())) {
				vo.setMsgStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
						CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL });
			} else {
				vo.setMsgStatus(msgCtlVO.getMsgStatus());
			}
		} else {
			vo.setMsgStatuss(new String[] {CommonConst.FTZ_MSG_STATUS_INPUTING, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
					CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_PBOC_RTN_FAIL });
		}
		
		try {
			Page<FtzOffMsgCtlVO> page = ftz210307Service.getMsgPage(pageable, vo);
			model.addAttribute(MODEL_KEY_PAGE, page);
			return "ftzmis/FTZ210307_Input_Qry";
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data!");
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry";
		}
	}

	/**
	 * 查询批量 - 明细(批量明细和批量修改使用)
	 * 跳转FTZ210307_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Init")
	public String inputDtlInit(FTZ210307Form form,  
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.inputDtlInit() start ...");

		try {
			Map<String, Object> map = ftz210307Service.getMsgAndTxnDetail(pageable, form.getFtzOffMsgCtl(), null);
			
			if (!StringUtil.isNotTrimEmpty(form.getActionFlag())) {
				form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			}
			form.setFtzOffMsgCtl((FtzOffMsgCtl) map.get(DTL_MAP_KEY_MSG));
			model.addAttribute(MODEL_KEY_PAGE, (Page<FtzOffTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffMsgCtl detail failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			form.setFtzOffMsgCtl(null);
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 新增批量-初始化页面
	 * 跳转FTZ210307_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Init")
	public String inputAddMsgInit(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.inputAddMsgInit() start ...");
		
		FtzOffMsgCtl ctl = new FtzOffMsgCtl();
		ctl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		ctl.setBranchId(ContextConst.getCurrentUser().getLoginorg());
		ctl.setWorkDate(DateUtil.getNowOutputDate());
//		ctl.setMsgId(numberService.getSysIDSequence(32));

		form.setFtzOffMsgCtl(ctl);
		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
	
		return "ftzmis/FTZ210307_Input_Qry_Dtl";
	}

	/**
	 * 新增批量-提交
	 * 跳转FTZ210307_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Sumbit")
	public String inputAddMsgSubmit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputAddMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
		
		try {
			FtzOffMsgCtl ctl = form.getFtzOffMsgCtl();
			ctl.setWorkDate(DateUtil.getFormatDateRemoveSprit(ctl.getWorkDate()));
//			ctl.setBranchId(StringUtil.isNotTrimEmpty(ctl.getBranchId())? StringUtil
//					.trim(ctl.getBranchId()): ContextConst.getOrgInfByUser().getOrgid());
			ctl.setMsgStatus(StringUtil.isNotTrimEmpty(ctl.getMsgStatus())? StringUtil
					.trim(ctl.getMsgStatus()): CommonConst.FTZ_MSG_STATUS_INPUTING);
			
			ftz210307Service.addMsgCtl(ctl);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0000")));

			return "forward:/FTZ210307/Input/UptMsg/Init?ftzOffMsgCtl.msgId=" + ctl.getMsgId();
		} catch (BusinessException e) {
			log.error("Add FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 刷新明细页面(FTZ210307_Input_Qry_Dtl.jsp)
	 * 跳转FTZ210307_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Refresh")
	public String inputDtlRefresh(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.inputDtlRefresh() start ...");

		try {
			Map<String, Object> map = ftz210307Service.getMsgAndTxnDetail(pageable, form.getFtzOffMsgCtl(), null);

			form.setActionFlag(CommonConst.ACTION_FLAG_REF_MSG);
			form.setFtzOffMsgCtl((FtzOffMsgCtl) map.get(DTL_MAP_KEY_MSG));
			model.addAttribute("page", (Page<FtzOffTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Refresh FtzOffMsgCtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 修改批量-初始化页面
	 * forward:/FTZ210302/Input/Dtl/Init
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Init")
	public String inputUptMsgInit(FTZ210307Form form, 
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.inputUptMsgInit() start ...");

		return "forward:/FTZ210307/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
	
	}

	/**
	 * 修改批量-提交
	 * 跳转FTZ210307_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Sumbit")
	public String inputUptMsgSubmit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputUptMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_UPT_MSG);
		
		try {
			ftz210307Service.updateMsgCtl(form.getFtzOffMsgCtl());
			
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0001")));

			return "forward:/FTZ210307/Input/UptMsg/Init";
		} catch (BusinessException e) {
			log.error("Update FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除批量
	 * 跳转FTZ210307_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/DelMsg")
	public String inputDelMsg(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputDelMsg() start ...");

		try {
			ftz210307Service.deleteMsgCtl(form.getFtzOffMsgCtl());

			log.info("Delete success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0007")));

			return "forward:/FTZ210307/Input/Qry";
		} catch (BusinessException e) {
			log.error("Submit FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/FTZ210307/Input/Qry";
		}
	}
	
	/**
	 * 提交批量
	 * 跳转FTZ210307_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/SubmitMsg")
	public String inputSubmitMsg(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputSubmitMsg() start ...");

		try {
			ftz210307Service.submitMsgCtl(form.getFtzOffMsgCtl());

			log.info("Submit success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0002")));

			return "forward:/FTZ210307/Input/Qry";
		} catch (BusinessException e) {
			log.error("Submit FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/FTZ210307/Input/Qry";
		}
	}

	/**
	 * 新增交易 - 初始化
	 * 跳转FTZ210307_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Init")
	public String inputAddTxnInit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputAddTxnInit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_TXN);
		
		try {
			FtzOffTxnDtl txn = form.getFtzOffTxnDtl();
//			String seqNo = ftz210307Service.getTxnDtlMaxSeqNo(txn);
			
			FtzOffTxnDtl txnDtl = new FtzOffTxnDtl();
			txnDtl.setMsgId(txn.getMsgId());
//			txnDtl.setSeqNo(seqNo);
			
			form.setFtzOffTxnDtl(txnDtl);

			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 新增交易 - 提交
	 * 跳转FTZ210307_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Submit")
	public String inputAddTxnSubmit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputAddTxnSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_TXN);
		
		try {
			FtzOffTxnDtl txn = (FtzOffTxnDtl) form.getFtzOffTxnDtl().clone();
			ftz210307Service.addTxnDtl(txn);
			
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0003")));

//			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
			return "forward:/FTZ210307/Input/DtlTxn/Init?ftzOffTxnDtl.seqNo=" + txn.getSeqNo() + "&actionFlag=" + CommonConst.ACTION_FLAG_ADD_TXN;
		} catch (BusinessException e) {
			log.error("Submit FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		} catch (CloneNotSupportedException e) {
			log.error("Add FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.ftzmis.2103.0006")));
			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 修改交易 - 初始化
	 * forward:/FTZ210307/Input/DtlTxn/Init
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Init")
	public String inputUptTxnInit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputUptTxnInit() start ...");

		return "forward:/FTZ210307/Input/DtlTxn/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_TXN;
	
	}

	/**
	 * 修改交易 - 提交
	 * 跳转FTZ210307_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Submit")
	public String inputUptTxnSubmit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputUptTxnSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_UPT_TXN);
		
		try {
			FtzOffTxnDtl txn = (FtzOffTxnDtl) form.getFtzOffTxnDtl().clone();
			ftz210307Service.updateTxnDtl(txn);
			
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0004")));

			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Update FtzOffTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		} catch (CloneNotSupportedException e) {
			log.error("Update FtzOffMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", ResultMessages.error().add(ResultMessage.fromCode("e.ftzmis.2103.0007")));
			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 交易 - 明细
	 * 跳转FTZ210307_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/DtlTxn/Init")
	public String inputDtlTxnInit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputDtlTxnInit() start ...");

		try {
			FtzOffTxnDtl txn = ftz210307Service.getTxnById(form.getFtzOffTxnDtl());
			txn.setSubmitDate(DateUtil.getFormatDateAddSprit(txn.getSubmitDate()));
			txn.setExpirationDate(DateUtil.getFormatDateAddSprit(txn.getExpirationDate()));
			
			if (!StringUtil.isNotTrimEmpty(form.getActionFlag())) {
				form.setActionFlag(CommonConst.ACTION_FLAG_DTL_TXN);
			} 
			form.setFtzOffTxnDtl(txn);
			
			return "ftzmis/FTZ210307_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzOffTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除交易 - 提交
	 * forward:/FTZ210307/Input/Dtl/Refresh
	 * @return
	 */
	@RequestMapping("/Input/DelTxn/Submit")
	public String inputDelTxnSubmit(FTZ210307Form form, Model model) {
		log.info("FTZ210307Controller.inputDelTxnSubmit() start ...");

		try {
			ftz210307Service.deleteTxnDtl(form.getFtzOffTxnDtl());
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0008")));
			
			return "forward:/FTZ210307/Input/Dtl/Refresh";
		} catch (BusinessException e) {
			log.error("Submit FtzOffTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Input_Qry_Dtl";
		}
	}

	/** ====================================== 审核 ====================================== */
	/**
	 * 查询批量 - 明细(批量全部明细和批量审核明细两个按钮)
	 * 跳转FTZ210307_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Auth/DtlMsg/Init")
	public String authDtlMsgInit(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.authDtlMsgInit() start ...");

		try {
			String status = CommonConst.PAGE_SEARCH_ALL_DTL.equals(form.getOperFlag())? null: CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED;
			
			Map<String, Object> map = ftz210307Service.getMsgAndTxnDetail(pageable, form.getFtzOffMsgCtl(), status);
			
			form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			form.setFtzOffMsgCtl((FtzOffMsgCtl) map.get(DTL_MAP_KEY_MSG));
			model.addAttribute("page", (Page<FtzOffTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210307_Auth_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Auth_Qry";
		}
	}
	
	/**
	 * 查询交易 - 明细
	 * 跳转FTZ210307_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Init")
	public String authDtlTxnInit(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.authDtlTxnInit() start ...");

		try {
			FtzOffTxnDtl txn = ftz210307Service.getAuthTxnById(form.getFtzOffTxnDtl());
			form.setFtzOffTxnDtl(txn);

			int ret = ftz210307Service.getAuthTxnCountById(form.getFtzOffTxnDtl());
			if (ret == 1) {
				form.setMsg("i.ftzmis.210307.0006");
			}
			
			return "ftzmis/FTZ210307_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());

			FtzOffTxnDtl txn = ftz210307Service.getTxnById(form.getFtzOffTxnDtl());
			form.setFtzOffTxnDtl(txn);
			return "ftzmis/FTZ210307_Auth_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 审核批量（批量审核提交）
	 * 跳转FTZ210307_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlMsg/Auth")
	public String authDtlMsgAuth(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.authDtlMsgAuth() start ...");

		try {
			ftz210307Service.authMsgCtl(form.getFtzOffMsgCtl());

			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0005")));

			return "forward:/FTZ210307/Auth/DtlMsg/Init";
//			return "ftzmis/FTZ210307_Auth_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
//			return "ftzmis/FTZ210307_Auth_Qry_Dtl";
			return "forward:/FTZ210307/Auth/DtlMsg/Init?operFlag=" + CommonConst.PAGE_SEARCH_NEED_CHK_DTL;
		}
	}

	/**
	 * 审核交易（审核通过和审核拒绝按钮）
	 * 跳转FTZ210307_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Auth")
	public String authDtlTxnAuth(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.authDtlTxnAuth() start ...");

		try {
			ftz210307Service.authTxnDtl(form.getFtzOffTxnDtl(), form.getOperFlag());

			FtzOffTxnDtl txn = ftz210307Service.getTxnById(form.getFtzOffTxnDtl());
			form.setFtzOffTxnDtl(txn);
			
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210307.0010")));
			
			return "ftzmis/FTZ210307_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Auth_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 审核交易（下一条）
	 * 跳转FTZ210307_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Next")
	public String authDtlTxnNext(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.authDtlTxnNext() start ...");

		try {
			FtzOffTxnDtl result = ftz210307Service.getNextAuthTxnById(form.getFtzOffTxnDtl());
			form.setFtzOffTxnDtl(result);

			int ret = ftz210307Service.getAuthTxnCountById(form.getFtzOffTxnDtl());
			if (ret == 1) {
				form.setMsg("i.ftzmis.210307.0006");
			}
			
			return "ftzmis/FTZ210307_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Auth_Qry_Dtl";
		}
	}


	/** ====================================== 查询 ====================================== */
	/**
	 * 查询批量 - 明细
	 * 跳转FTZ210307_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Qry/DtlMsg/Init")
	public String qryDtlMsgInit(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.qryDtlMsgInit() start ...");

		try {
			Map<String, Object> map = ftz210307Service.getMsgAndTxnDetail(pageable, form.getFtzOffMsgCtl(), null);
			
			form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			form.setFtzOffMsgCtl((FtzOffMsgCtl) map.get(DTL_MAP_KEY_MSG));
			model.addAttribute("page", (Page<FtzOffTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210307_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffMsgCtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Qry_Dtl";
		}
	}
	
	/**
	 * 查询交易 - 明细
	 * 跳转FTZ210307_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Qry/DtlTxn/Init")
	public String qryDtlTxnInit(FTZ210307Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210307Controller.qryDtlTxnInit() start ...");

		try {
			FtzOffTxnDtl txn = ftz210307Service.getTxnById(form.getFtzOffTxnDtl());
			txn.setSubmitDate(DateUtil.getFormatDateAddSprit(txn.getSubmitDate()));
			txn.setExpirationDate(DateUtil.getFormatDateAddSprit(txn.getExpirationDate()));
			form.setFtzOffTxnDtl(txn);
			
			return "ftzmis/FTZ210307_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzOffTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210307_Qry_Dtl";
		}
	}
	
	@Autowired
	private FTZOffCommonService ftz210307Service;
}
