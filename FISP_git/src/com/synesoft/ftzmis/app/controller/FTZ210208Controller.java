package com.synesoft.ftzmis.app.controller;





import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;

import com.synesoft.ftzmis.app.model.FTZ210101Form;
import com.synesoft.ftzmis.app.model.FTZ210208Form;
import com.synesoft.ftzmis.app.model.FTZ210208Form;
import com.synesoft.ftzmis.app.model.FTZ210208Form.FTZInFormMsgKey;
import com.synesoft.ftzmis.app.model.FTZ210208Form.FTZInFormMsgQry;
import com.synesoft.ftzmis.app.model.FTZ210208Form.FTZInFormTxnKey;

import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;
import com.synesoft.ftzmis.domain.service.FTZ210101Service;
import com.synesoft.ftzmis.domain.service.FTZInCommonService;


/**
 * 6.2.6　应付保函/备用证（210208）
 * @author yyw
 * @date 2013-12-25
 */
@Controller
@RequestMapping(value = "FTZ210208")
public class FTZ210208Controller {
	private static final Logger log = LoggerFactory.getLogger(FTZ210208Controller.class);

	private final static String MODEL_KEY_PAGE = "page";
	private final static String DTL_MAP_KEY_MSG = "MsgCtl";
	private final static String DTL_MAP_KEY_TXN = "TxnDtl";
	private final static String AUTH_PASS = "pass";
	private final static String AUTH_REJECT = "reject";
	
	@ModelAttribute
	public FTZ210208Form setForm() {
		return new FTZ210208Form();
	}

	/**
	 * 一级页面 - 初始化
	 * 跳转FTZ210208_Input_Qry.jsp
	 * @return
	 */
//	@RequestMapping("/AddQry")
//	public String qryInit() {
//		log.info("FTZ210208Controller.qryInit() start ...");
//		return "ftzmis/FTZ210208_Input_Qry";
//		//return "forward:/FTZ210208/Input/Qry";
//	}

	/**
	 * 一级页面 - 查询ok
	 * 跳转FTZ210208_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/AddQry")
	public String inputQry( Model model, FTZ210208Form form,
			@PageableDefaults Pageable pageable) {
		log.info("FTZ210208Controller.inputQry() start ...");

//		if (result.hasErrors()) {
//			return "ftzmis/FTZ210208_Input_Qry";
//		}
		
		FtzInMsgCtlVO vo = new FtzInMsgCtlVO();
		vo.setMsgNo(CommonConst.MSG_NO_210208);
		// data ready
		FtzInMsgCtlVO msgCtlVO = (FtzInMsgCtlVO)form.getFtzInMsgCtlVO();
		if(null != msgCtlVO)
		{
			vo.setBranchId(msgCtlVO.getBranchId());
			vo.setStartDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getStartDate())));
			vo.setEndDate(DateUtil.getFormatDateRemoveSprit(StringUtil.trim(msgCtlVO.getEndDate())));
			vo.setMsgNo(CommonConst.MSG_NO_210208);
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
			Page<FtzInMsgCtlVO> page = ftz210208Service.transQueryMsgPage(pageable, vo);
			
			for (FtzInMsgCtl ftzInMsgCtl : page) {
				ftzInMsgCtl.setSubmitDate(DateUtil
						.getFormatDateAddSprit(ftzInMsgCtl.getSubmitDate()));
			}//申报时间格式化
			
			model.addAttribute(MODEL_KEY_PAGE, page);
			return "ftzmis/FTZ210208_Input_Qry";
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data!");
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry";
		}
	}

	/**
	 * 跳转到2级页面
	 * 跳转FTZ210208_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Init")
	public String inputDtlInit(@Validated({FTZInFormMsgKey.class}) FTZ210208Form form, BindingResult result,  
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.inputDtlInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry";
		}
		
		try {
			Map<String, Object> map = ftz210208Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), null);
			
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
			
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInMsgCtl detail failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry";
		}
	}

	/**
	 * 新增批量头-2级页面
	 * 跳转FTZ210208_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Init")
	public String inputAddMsgInit(FTZ210208Form form, BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.inputAddMsgInit() start ...");
		
		FtzInMsgCtl ctl = new FtzInMsgCtl();
		ctl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		ctl.setBranchId(ContextConst.getCurrentUser().getLoginorg());
		//ctl.setWorkDate(DateUtil.getNowOutputDate());
	//	ctl.setMsgId(numberService.getSysIDSequence(16));

		form.setFtzInMsgCtl(ctl);
		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
	
		return "ftzmis/FTZ210208_Input_Qry_Dtl";
	}

	/**
	 * 新增批量头-提交
	 * 跳转FTZ210301_Input_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddMsg/Sumbit")
	public String inputAddMsgSubmit(FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputAddMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_MSG);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
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
			
			ftz210208Service.transUpdateAddMsgCtl(ctl, CommonConst.MSG_NO_210208);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("ftzmis.Add.Msg.Ctl.Success")));

			//return "ftzmis/FTZ210208_Input_Qry_Dtl";
			return "forward:/FTZ210208/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
		} catch (BusinessException e) {
			log.error("Add FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
	}

	/**
	 * 刷新明细页面(FTZ210301_Input_Qry_Dtl.jsp)
	 * 跳转FTZ210301_Input_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Input/Dtl/Refresh")
	public String inputDtlRefresh(FTZ210208Form form, BindingResult result, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.inputDtlRefresh() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}

		try {
			Map<String, Object> map = ftz210208Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), null);

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
			
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Refresh FtzInMsgCtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
	}

	/**
	 * 修改批量头-二级页面
	 * forward:/FTZ210302/Input/Dtl/Init
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Init")
	public String inputUptMsgInit(@Validated({FTZInFormMsgKey.class}) FTZ210208Form form, BindingResult result, 
			@PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.inputUptMsgInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry";
		}
		
		return "forward:/FTZ210208/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
	
	}

	/**
	 * 修改批量头-提交
	 * 跳转FTZ210301_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptMsg/Sumbit")
	public String inputUptMsgSubmit(FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputUptMsgSubmit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_UPT_MSG);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
		
		try {
			
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateRemoveSprit(form.getFtzInMsgCtl().getSubmitDate()));
			
			ftz210208Service.transUpdateMsgCtl(form.getFtzInMsgCtl(), CommonConst.FTZ_MSG_EDIT_FLAG_UPDATE);
			
			form.getFtzInMsgCtl().setSubmitDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInMsgCtl().getSubmitDate() ));//申报日格式化
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.sm.0002")));
			
			return "forward:/FTZ210208/Input/Dtl/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_MSG;
			//return "forward:/FTZ210208/Input/Dtl/Refresh";
		} catch (BusinessException e) {
			log.error("Update FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除批量头
	 * 跳转FTZ210208_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/DelMsg")
	public String inputDelMsg(@Validated({FTZInFormMsgKey.class}) FTZ210208Form form, BindingResult result, Model model) { // TODO validate
		log.info("FTZ210208Controller.inputDelMsg() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry";
		}
		
		try {
			ftz210208Service.transDeleteMsgCtl(form.getFtzInMsgCtl());

			log.info("Delete success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.sm.0003")));

			return "forward:/FTZ210208/AddQry";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry";
		}
	}
	
	/**
	 * 提交批量头
	 * 跳转FTZ210208_Input_Qry.jsp
	 * @return
	 */
	@RequestMapping("/Input/SubmitMsg")
	public String inputSubmitMsg(@Validated({FTZInFormMsgKey.class}) FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputSubmitMsg() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry";
		}
		
		try {
			ftz210208Service.transUpdateSubmitMsgCtl(form.getFtzInMsgCtl());

			log.info("Submit success");
			form.setActionFlag(CommonConst.ACTION_FLAG_SUB_MSG);
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0002")));

			return "forward:/FTZ210208/AddQry";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry";
		}
	}

	/**
	 * 新增交易 - 初始化
	 * 跳转FTZ210208_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Init")
	public String inputAddTxnInit(@Validated({FTZInFormMsgKey.class}) FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputAddTxnInit() start ...");

		form.setActionFlag(CommonConst.ACTION_FLAG_ADD_TXN);
		
		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
		
		try {
			FtzInTxnDtl txn=form.getFtzInTxnDtl();
			//String seqNo = ftz210208Service.transQueryTxnDtlMaxSeqNo(txn);
			
			FtzInTxnDtl txn1=new FtzInTxnDtl();
			//txn1.setSeqNo(seqNo);
			txn1.setMsgId(txn.getMsgId());
			form.setFtzInTxnDtl(txn1);

			return "ftzmis/FTZ210208_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
	}

	/**
	 * 新增交易明细 - 提交
	 * 跳转FTZ210208_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/AddTxn/Submit")
	public String inputAddTxnSubmit(FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputAddTxnSubmit() start ...");

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
			
			String seqNo = ftz210208Service.transQueryTxnDtlMaxSeqNo(form.getFtzInTxnDtl());
			form.getFtzInTxnDtl().setSeqNo(seqNo);
			ftz210208Service.transUpdateAddTxnDtl(form.getFtzInTxnDtl());
//			form.getFtzInMsgCtl().setTotalCount(form.getFtzInMsgCtl().getTotalCount()+1);//加一 
//			ftz210208Service.transUpdateMsgCtl(form.getFtzInMsgCtl(), CommonConst.FTZ_MSG_EDIT_FLAG_UPDATE);
			form.getFtzInTxnDtl().setTranDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getTranDate()));//记账日期格式化
			form.getFtzInTxnDtl().setValueDate(DateUtil
					.getFormatDateAddSprit(form.getFtzInTxnDtl().getValueDate()));//起息日格式化
			
			if(form.getFtzInTxnDtl().getOrgTranDate()!=null)
			{
				form.getFtzInTxnDtl().setOrgTranDate(DateUtil
						.getFormatDateAddSprit(form.getFtzInTxnDtl().getOrgTranDate()));//原始格式化
			}
			if(form.getFtzInTxnDtl().getExpireDate()!=null)
			{
				form.getFtzInTxnDtl().setExpireDate(DateUtil
						.getFormatDateAddSprit(form.getFtzInTxnDtl().getExpireDate()));//到期日式化
			}
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.sm.0001")));

			return "ftzmis/FTZ210208_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInMsgCtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 修改交易 - 初始化
	 * forward:/FTZ210208/Input/DtlTxn/Init
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Init")
	public String inputUptTxnInit(@Validated({FTZInFormTxnKey.class}) FTZ210208Form form, BindingResult result, Model model) {
		log.info("FTZ210208Controller.inputUptTxnInit() start ...");

		return "forward:/FTZ210208/Input/DtlTxn/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_TXN;
	
	}

	/**
	 * 修改交易 - 提交
	 * 跳转FTZ210208_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/UptTxn/Submit")
	public String inputUptTxnSubmit(FTZ210208Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210208Controller.inputUptTxnSubmit() start ...");

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
			
			ftz210208Service.transUpdateTxnDtl(form.getFtzInTxnDtl());
			
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.sm.0002")));

			return "ftzmis/FTZ210208_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
			//return "forward:/FTZ210208/Input/DtlTxn/Init?actionFlag=" + CommonConst.ACTION_FLAG_UPT_TXN;
		}
	}

	/**
	 * 交易 - 明细
	 * 跳转FTZ210208_Input_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Input/DtlTxn/Init")
	public String inputDtlTxnInit(@Validated({FTZInFormTxnKey.class}) FTZ210208Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210208Controller.inputDtlTxnInit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
		
		try {
			FtzInTxnDtl txn = ftz210208Service.transQueryTxnById(form.getFtzInTxnDtl());
	
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
			
			form.setFtzInTxnDtl(txn);
			
			return "ftzmis/FTZ210208_Input_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
	}

	/**
	 * 删除交易明细 
	 * forward:/FTZ210208/Input/Dtl/Refresh
	 * @return
	 */
	@RequestMapping("/Input/DelTxn/Submit")
	public String inputDelTxnSubmit(@Validated({FTZInFormTxnKey.class}) FTZ210208Form form, BindingResult result, Model model) {// TODO
		log.info("FTZ210208Controller.inputDelTxnSubmit() start ...");

		if (result.hasErrors()) {
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
		}
		
		try {
			
			form.getFtzInTxnDtl().setMakDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getMakDatetime()));//录入时间
			form.getFtzInTxnDtl().setChkDatetime(DateUtil
					.getFormatDateTimeRemoveSpritAndColon(form.getFtzInTxnDtl().getChkDatetime()));//审核时间
			
			ftz210208Service.transUpdateDeleteTxnDtl(form.getFtzInTxnDtl());
			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.sm.0003")));
			
			return "forward:/FTZ210208/Input/Dtl/Refresh";
		} catch (BusinessException e) {
			log.error("Submit FtzInTxnDtl failure!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Input_Qry_Dtl";
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
	 * 跳转FTZ210208_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Auth/DtlMsg/Init")
	public String authDtlMsgInit(FTZ210208Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.authDtlMsgInit() start ...");

		try {
			String status = CommonConst.PAGE_SEARCH_ALL_DTL.equals(form.getOperFlag())? null: CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED;
			
			Map<String, Object> map = ftz210208Service.transQueryMsgAndTxnDetail(pageable, form.getFtzInMsgCtl(), status);
			
			form.setActionFlag(CommonConst.ACTION_FLAG_DTL_MSG);
			form.setFtzInMsgCtl((FtzInMsgCtl) map.get(DTL_MAP_KEY_MSG));
			model.addAttribute("page", (Page<FtzInTxnDtl>) map.get(DTL_MAP_KEY_TXN));
			
			return "ftzmis/FTZ210208_Auth_Qry_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Auth_Qry";
		}
	}
	
	/**
	 * 查询交易 - 明细
	 * 跳转FTZ210208_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Init")
	public String authDtlTxnInit(FTZ210208Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.authDtlTxnInit() start ...");

		try {
			FtzInTxnDtl txn = ftz210208Service.transQueryTxnById(form.getFtzInTxnDtl());
			form.setFtzInTxnDtl(txn);

//			int ret = ftz210208Service.getAuthTxnCountById(form.getFtzInTxnDtl());
//			if (ret == 1) {
//				form.setMsg("i.ftzmis.210208.0006");
//			}
			
			return "ftzmis/FTZ210208_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());

			FtzInTxnDtl txn = ftz210208Service.transQueryTxnById(form.getFtzInTxnDtl());
			form.setFtzInTxnDtl(txn);
			return "ftzmis/FTZ210208_Auth_Qry_Dtl_Dtl";
		}
	}

	/**
	 * 审核批量（批量审核提交）
	 * 跳转FTZ210208_Auth_Qry_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlMsg/Auth")
	public String authDtlMsgAuth(FTZ210208Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.authDtlMsgAuth() start ...");

		try {
			ftz210208Service.authMsgCtl(form.getFtzInMsgCtl());

			model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
			
			return "forward:/FTZ210208/Auth/DtlMsg/Init";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "forward:/FTZ210208/Auth/DtlMsg/Init";
		}
	}

	/**
	 * 审核交易（审核通过和审核拒绝按钮）
	 * 跳转FTZ210208_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Auth")
	public String authDtlTxnAuth(FTZ210208Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.authDtlTxnAuth() start ...");

		try {
			ftz210208Service.authTxnDtl(form.getFtzInTxnDtl(), form.getOperFlag());

//			if(form.getOperFlag()==CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)
//			{
//			 model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
//			}
//			else
//			{
		     model.addAttribute("successmsg", ResultMessages.success().add(ResultMessage.fromCode("i.ftzmis.210301.0005")));
//			}
			
			return "ftzmis/FTZ210208_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Auth_Qry_Dtl";
		}
	}

	/**
	 * 审核交易（下一条）
	 * 跳转FTZ210208_Auth_Qry_Dtl_Dtl.jsp
	 * @return
	 */
	@RequestMapping("/Auth/DtlTxn/Next")
	public String authDtlTxnNext(FTZ210208Form form, @PageableDefaults Pageable pageable, Model model) {
		log.info("FTZ210208Controller.authDtlTxnNext() start ...");

		try {
			FtzInTxnDtl result = ftz210208Service.getNextAuthTxnById(form.getFtzInTxnDtl());
			form.setFtzInTxnDtl(result);

			int ret = ftz210208Service.getAuthTxnCountById(form.getFtzInTxnDtl());
			if (ret == 1) {
				form.setMsg("i.ftzmis.210208.0006");
			}
			
			return "ftzmis/FTZ210208_Auth_Qry_Dtl_Dtl";
		} catch (BusinessException e) {
			log.error("Init FtzInTxnDtl failure, the MsgId is invalid!" + e.getMessage());
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZ210208_Auth_Qry_Dtl";
		}
	}
	
	@Autowired
	private NumberService numberService;
	@Autowired
	private FTZInCommonService ftz210208Service;
	
	@Autowired
	private FTZ210101Service ftz210101Service;
}
