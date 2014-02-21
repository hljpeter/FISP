package com.synesoft.ftzmis.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.terasoluna.fw.common.exception.BusinessException;

import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.model.FTZOFFForm;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;
import com.synesoft.ftzmis.domain.service.FTZOffCommonService;

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

	private Logger log = LoggerFactory.getLogger(getClass());

	private final static String MODEL_KEY_PAGE = "page";
	
	@Autowired
	private FTZOffCommonService ftz210302Service;
	
	@RequestMapping("Qry")
	public String query(Model model, FTZOFFForm form, @PageableDefaults Pageable pageable){

		try {
			// trans form to queryObject
			FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
			ftzOffMsgCtlVO.setMsgId(form.getQuery_msgId());
			ftzOffMsgCtlVO.setBranchId(form.getQuery_branchId().trim());
			ftzOffMsgCtlVO.setStartDate(DateUtil.getFormatDateRemoveSprit(form.getQuery_workDate_start()));
			ftzOffMsgCtlVO.setEndDate(DateUtil.getFormatDateRemoveSprit(form.getQuery_workDate_end()));
			ftzOffMsgCtlVO.setMsgStatus(form.getQuery_msgStatus());
			ftzOffMsgCtlVO.setMsgNo(form.getQuery_msgNo());
			
			//Query
			// query DpMppCfg page list
			Page<FtzOffMsgCtlVO> page = ftz210302Service.getMsgPage(pageable, ftzOffMsgCtlVO);

			model.addAttribute(MODEL_KEY_PAGE, page);
			return "ftzmis/FTZOFF_Qry";
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data!");
			model.addAttribute("errmsg", e.getResultMessages());
			return "ftzmis/FTZOFF_Qry";
		}
	}
	
	@RequestMapping("AuthQry")
	public String authQuery(Model model, FTZOFFForm form, @PageableDefaults Pageable pageable) {

		try {
			// trans form to queryObject
			FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
			ftzOffMsgCtlVO.setMsgId(form.getQuery_msgId());
			ftzOffMsgCtlVO.setBranchId(form.getQuery_branchId().trim());
			ftzOffMsgCtlVO.setStartDate(DateUtil.getFormatDateRemoveSprit(form.getQuery_workDate_start()));
			ftzOffMsgCtlVO.setEndDate(DateUtil.getFormatDateRemoveSprit(form.getQuery_workDate_end()));
			ftzOffMsgCtlVO.setMsgNo(form.getQuery_msgNo());
			ftzOffMsgCtlVO.setMsgStatuss(new String[] {
					CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED,
					CommonConst.FTZ_MSG_STATUS_INPUTING,
					CommonConst.FTZ_MSG_STATUS_AUTH_FAIL});
			
			//Query
			// query DpMppCfg page list
			Page<FtzOffMsgCtlVO> page = ftz210302Service.getMsgPageForAuth(pageable, ftzOffMsgCtlVO);

			model.addAttribute(MODEL_KEY_PAGE, page);
//		if (page.getContent().size() > 0) {
//			List<FtzOffMsgCtl> ftzOffMsgCtls = page.getContent();
//			for (FtzOffMsgCtl ftzOffMsgCtl : ftzOffMsgCtls) {
//				FtzOffTxnDtl query_FtzOffTxnDtl = new FtzOffTxnDtl();
//				query_FtzOffTxnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
//				List<FtzOffTxnDtl> ftzOffTxnDtls = ftz210302Service.
//						queryFtzOffTxnDtlList(query_FtzOffTxnDtl);
//				int count = 0;
//				if (null == ftzOffTxnDtls || ftzOffTxnDtls.size() < 1) {
//					ftzOffMsgCtl.setRsv1("0");
//					ftzOffMsgCtl.setRsv2("0");
//				} else {
//					for (FtzOffTxnDtl ftzOffTxnDtl : ftzOffTxnDtls) {
//						if (ftzOffTxnDtl.getChkStatus().equals(
//								CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)
//								|| ftzOffTxnDtl.getChkStatus().equals(
//										CommonConst.FTZ_MSG_STATUS_AUTH_FAIL)) {
//							count++;
//						}
//					}
//					ftzOffMsgCtl.setRsv1(ftzOffTxnDtls.size()+"");
//					int lastCnt = ftzOffTxnDtls.size()-count;
//					ftzOffMsgCtl.setRsv2(lastCnt+"");
//				}
//				
//				ftzOffMsgCtl.setWorkDate(DateUtil
//						.getFormatDateAddSprit(ftzOffMsgCtl.getWorkDate()));
//			}
//			model.addAttribute("page", page);
//		} else {
//			model.addAttribute(ResultMessages.info().add("w.sm.0001"));
//			logger.info("表外查询结束...");
			return "ftzmis/FTZOFF_Auth_Qry";
		} catch (BusinessException e) {
			log.info("[w.dp.0001] No data!");
			model.addAttribute("infomsg", e.getResultMessages());
			return "ftzmis/FTZOFF_Auth_Qry";
		}
	
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
			return "redirect:/FTZ210302/Qry/DtlMsg/Init?selected_msgId="+ form.getSelected_msgId();
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
			return "redirect:/FTZ210302/Auth/DtlMsg/Init?operFlag=2&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
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
			return "redirect:/FTZ210302/Auth/DtlMsg/Init?operFlag=1&FtzOffMsgCtl.msgId="+ form.getSelected_msgId();
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
