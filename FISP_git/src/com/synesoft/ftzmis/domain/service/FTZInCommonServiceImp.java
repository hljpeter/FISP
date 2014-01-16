package com.synesoft.ftzmis.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.fw.common.exception.BusinessException;
import org.terasoluna.fw.common.message.ResultMessage;
import org.terasoluna.fw.common.message.ResultMessages;

import com.synesoft.fisp.app.common.constants.ContextConst;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.app.common.utils.TlrLogPrint;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.UserInf;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.xmlproc.MsgHead;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;
import com.synesoft.ftzmis.domain.repository.FtzInMsgCtlRepository;
import com.synesoft.ftzmis.domain.repository.FtzInTxnDtlRepository;

public abstract class FTZInCommonServiceImp implements FTZInCommonService {
	private static final Logger log = LoggerFactory.getLogger(FTZInCommonServiceImp.class);

	protected String funcId;
	
	/**
	 * 表内抽象方法，由子类实现
	 * 新增批量时需要处理的逻辑
	 * @param ftzInMsgCtl
	 */
	protected abstract void addMsgLogic(FtzInMsgCtl ftzInMsgCtl);
	/**
	 * 表内抽象方法，由子类实现
	 * 新增交易时需要处理的逻辑
	 * @param ftzInMsgCtl
	 */
	protected abstract void addTxnLogic(FtzInTxnDtl ftzInTxnDtl);
	/**
	 * 表内抽象方法，由子类实现
	 * 更新交易时需要处理的逻辑
	 * @param ftzInTxnDtl
	 */
	protected abstract void updateTxnLogic(FtzInTxnDtl ftzInTxnDtl);
	/**
	 * 表内抽象方法，由子类实现
	 * 基础信息校验
	 * @param ftzInTxnDtl
	 */
	protected abstract void validateTxn(FtzInTxnDtl ftzInTxnDtl);
	protected abstract void validateMsg(FtzInMsgCtl ftzInMsgCtl);
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transQueryMsgPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public Page<FtzInMsgCtlVO> transQueryMsgPage(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO) {
		log.debug("FTZInCommonService.transQueryMsgPage() start ...");
		
		ResultMessages messages = ResultMessages.error();
		
		log.debug("Searching record, param[branchId=" + ftzInMsgCtlVO.getBranchId() 
				+ ", startDate=" + ftzInMsgCtlVO.getStartDate() 
				+ ", endDate=" + ftzInMsgCtlVO.getEndDate() 
				+ ", msgId=" + ftzInMsgCtlVO.getMsgId() 
				+ ", msgStatus=" + ftzInMsgCtlVO.getMsgStatus() 
				+ ", msgNo=" + ftzInMsgCtlVO.getMsgNo() 
				+ ", accountName=" + ftzInMsgCtlVO.getAccountName() 
				+ ", editFlag=" + ftzInMsgCtlVO.getEditFlag() 
				+ ", pageNubmer=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");

		Page<FtzInMsgCtlVO> page = ftzInMsgCtlRepository.queryPage(pageable, ftzInMsgCtlVO);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transQueryMsgById(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	public FtzInMsgCtl transQueryMsgById(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FTZInCommonService.transQueryMsgById() start ...");

		ResultMessages messages = ResultMessages.error();
		
		log.debug("Searching record, param[msgId=" + ftzInMsgCtl.getMsgId() + "]");
		FtzInMsgCtl result = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == result) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transQueryTxnById(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public FtzInTxnDtl transQueryTxnById(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transQueryTxnById() start ...");

		ResultMessages messages = ResultMessages.error();
		
		FtzInTxnDtl result = ftzInTxnDtlRepository.queryByPK(ftzInTxnDtl);
		if (result == null) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");	
			throw new BusinessException(messages);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transQueryMsgAndTxnDetail(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzInMsgCtl, java.lang.String)
	 */
	public Map<String, Object> transQueryMsgAndTxnDetail(Pageable pageable, FtzInMsgCtl ftzInMsgCtl, String status) {
		log.debug("FTZInCommonService.transQueryMsgDetail() start ...");

		ResultMessages messages = ResultMessages.error();
		
		FtzInMsgCtl msgCtl = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == msgCtl) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		} else {
			FtzInTxnDtl txnDtl = new FtzInTxnDtl();
			txnDtl.setMsgId(ftzInMsgCtl.getMsgId());
			txnDtl.setChkStatus(status);
			Page<FtzInTxnDtl> page = ftzInTxnDtlRepository.queryPage(pageable, txnDtl);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("MsgCtl", msgCtl);
			map.put("TxnDtl", page);
			
			return map;
		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateAddMsgCtl(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	@Transactional
	public void transUpdateAddMsgCtl(FtzInMsgCtl ftzInMsgCtl, String msgNo) {
		log.debug("FTZInCommonService.transUpdateAddMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateMsg(ftzInMsgCtl);
		
		addMsgLogic(ftzInMsgCtl);
		
		ftzInMsgCtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzInMsgCtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftzInMsgCtl.setMsgNo(msgNo);
		
		// 设置批量头信息
		MsgHead mh = MsgHead.getMsgHead();
		ftzInMsgCtl.setVer(mh.getVER());
		ftzInMsgCtl.setSrc(mh.getSRC());
		ftzInMsgCtl.setDes(mh.getDES());
		ftzInMsgCtl.setApp(mh.getAPP());
		ftzInMsgCtl.setWorkDate(mh.getWorkDate());
		ftzInMsgCtl.setEditFlag(mh.getEditFlag());
		ftzInMsgCtl.setReserve(mh.getReserve());
		
		int ret = ftzInMsgCtlRepository.insert(ftzInMsgCtl);
		if (ret != 1) {
			log.error("[e.sysrunner.0006] Add MsgCtl information failure!"); 
			messages.add("e.sysrunner.0006");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzInMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateMsgCtl(com.synesoft.ftzmis.domain.model.FtzInMsgCtl, java.lang.String)
	 */
	@Transactional
	public void transUpdateMsgCtl(FtzInMsgCtl ftzInMsgCtl, String editFlag) {
		log.debug("FTZInCommonService.transUpdateMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		FtzInMsgCtl result = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		ftzInMsgCtl.setMsgId(ftzInMsgCtl.getMsgId());
		ftzInMsgCtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInMsgCtl.getMakDatetime()));
		
		ftzInMsgCtl.setEditFlag(editFlag);
		ftzInMsgCtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzInMsgCtl.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		
	
		int ret = ftzInMsgCtlRepository.updateMsg(ftzInMsgCtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzInMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzInMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateSubmitMsgCtl(com.synesoft.ftzmis.domain.model.FtzInMsgCtl)
	 */
	@Transactional
	public void transUpdateSubmitMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FTZInCommonService.transUpdateSubmitMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		FtzInMsgCtl result = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		FtzInMsgCtl ftzInMsgCtl1=new FtzInMsgCtl();
		ftzInMsgCtl1.setMsgId(ftzInMsgCtl.getMsgId());
		//ftzInMsgCtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInMsgCtl.getMakDatetime()));
		
		// setup value when submitted
		//ftzInMsgCtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzInMsgCtl1.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);

		int ret = ftzInMsgCtlRepository.updateMsgStatus(ftzInMsgCtl1);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzInMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

//		FtzInTxnDtl txnDtl = new FtzInTxnDtl();
//		txnDtl.setMsgId(ftzInMsgCtl.getMsgId());
//		txnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
//		ftzInTxnDtlRepository.updateStatus(txnDtl);

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzInMsgCtl.toString());
	}

	/* (non-Javadoc)没用到
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateMsgStatus(com.synesoft.ftzmis.domain.model.FtzInMsgCtl, java.lang.String)
	 */
	@Transactional
	public void transUpdateMsgStatus(FtzInMsgCtl ftzInMsgCtl, String editFlag) {
		log.debug("FTZInCommonService.transUpdateMsgStatus() start ...");

		ResultMessages messages = ResultMessages.error();

		FtzInMsgCtl result = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		FtzInMsgCtlVO ftzInMsgCtlVO = new FtzInMsgCtlVO();
		ftzInMsgCtlVO.setMsgId(ftzInMsgCtl.getMsgId());
		ftzInMsgCtlVO.setOldMsgStatus(ftzInMsgCtl.getMsgStatus());
		
		// setup value when submitted
//		if (CommonConst.FTZ_MSG_EDIT_FLAG_SUBMIT.equals(editFlag)) {
//			ftzInMsgCtlVO.setMakDatetime(ftzInMsgCtl.getMakDatetime());
//			ftzInMsgCtlVO.setMakUserId(ContextConst.getCurrentUser().getUserid());
//			ftzInMsgCtlVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
//		}
		
		int ret = ftzInMsgCtlRepository.updateMsgStatus(ftzInMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzInMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzInMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transQueryTxnDtlMaxSeqNo(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public String transQueryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transQueryTxnDtlMaxSeqNo() start ...");

		return ftzInTxnDtlRepository.queryTxnDtlMaxSeqNo(ftzInTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateAddTxnDtl(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Transactional
	public void transUpdateAddTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transUpdateAddTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateTxn(ftzInTxnDtl);
		
		addTxnLogic(ftzInTxnDtl);
		
		ftzInTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzInTxnDtl.setMakDatetime(DateUtil.getNowInputDateTime());
		ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		
		
		int ret = ftzInTxnDtlRepository.insert(ftzInTxnDtl);
		FtzInMsgCtl ftzInMsgCtl=new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(ftzInTxnDtl.getMsgId());
		ftzInMsgCtl=ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		ftzInMsgCtl.setTotalCount(ftzInMsgCtl.getTotalCount()+1);
		ftzInMsgCtlRepository.updateMsg(ftzInMsgCtl);
		if (ret != 1) {
			log.error("[e.sysrunner.0006] Add TxnDtl information failure!"); 
			messages.add("e.sysrunner.0006");								
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzInTxnDtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateTxnDtl(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Transactional
	public void transUpdateTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transUpdateAddTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateTxn(ftzInTxnDtl);

		FtzInTxnDtl result = ftzInTxnDtlRepository.queryByPK(ftzInTxnDtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}
		
		updateTxnLogic(ftzInTxnDtl);
		
	    
		ftzInTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		ftzInTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInTxnDtl.getMakDatetime()));
		
		int ret = ftzInTxnDtlRepository.update(ftzInTxnDtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0007");								
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzInTxnDtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateTxnStatus(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Transactional
	public void transUpdateTxnStatus(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transUpdateTxnStatus() start ...");

//		ResultMessages messages = ResultMessages.error();
//
//		FtzInTxnDtlVO ftzInTxnDtlVO = transferObj(ftzInTxnDtl);
//		
//		updateTxnLogic(ftzInTxnDtlVO);
//		
//		ftzInTxnDtlVO.setMakUserId(ContextConst.getCurrentUser().getUserid());
//		ftzInTxnDtlVO.setMakDatetime(DateUtil.getCurrentDateToString(DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
//		ftzInTxnDtlVO.setChkStatus(CommonConst.TXN_DTL_CHK_STATUS_INPUT);
//		
//		int ret = ftzInTxnDtlRepository.updateStatus(ftzInTxnDtl);
//		if (ret != 1) {
//			log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
//			messages.add("e.ftzmis.2103.0007");								
//			throw new BusinessException(messages);
//		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#transUpdateDeleteTxnDtl(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	@Transactional
	public void transUpdateDeleteTxnDtl(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.transUpdateDeleteTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		int ret = ftzInTxnDtlRepository.delete(ftzInTxnDtl);
		if (ret != 1) {
			log.error("[i.sm.0003] Delete TxnDtl information failure!"); 
			messages.add("i.sm.0003");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE, "", ftzInTxnDtl.toString());
	}
	
	@Transactional
	public void transDeleteMsgCtl(FtzInMsgCtl tzInMsgCtl) {
		log.debug("FTZInCommonService.transUpdateDeleteTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		
		int ret=ftzInMsgCtlRepository.delete(tzInMsgCtl);
		FtzInTxnDtl ftzInTxnDtl=new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(tzInMsgCtl.getMsgId());
	    ftzInTxnDtlRepository.deleteByMsgID(ftzInTxnDtl);
		if (ret != 1) {
			log.error("[i.sm.0003] Delete TxnDtl information failure!"); 
			messages.add("i.sm.0003");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE, "", tzInMsgCtl.toString());
	}


	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}
	
	private void temp(FtzInMsgCtlVO ftzInMsgCtlVO,FtzInMsgCtl ftzInMsgCtl)
	{
		ftzInMsgCtlVO.setBranchId(ftzInMsgCtl.getBranchId());
		ftzInMsgCtlVO.setSubmitDate(ftzInMsgCtl.getSubmitDate());
		ftzInMsgCtlVO.setAccountNo(ftzInMsgCtl.getAccountNo());
		ftzInMsgCtlVO.setAccountName(ftzInMsgCtl.getAccountName());
		ftzInMsgCtlVO.setCurrency(ftzInMsgCtl.getCurrency());
		ftzInMsgCtlVO.setBalance(ftzInMsgCtl.getBalance());
		ftzInMsgCtlVO.setBalanceCode(ftzInMsgCtl.getBalanceCode());
		ftzInMsgCtlVO.setAccOrgCode(ftzInMsgCtl.getAccOrgCode());
	}
	
	
	public int getAuthTxnCountById(FtzInTxnDtl ftzInTxnDtl) {
		log.debug("FTZInCommonService.getAuthTxnCountById() start ...");

		validMsgId(ftzInTxnDtl);
		
		ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int count = ftzInTxnDtlRepository.queryCount(ftzInTxnDtl);
		
		return count;
	}
	

	
	//审核批量头
	@Transactional
	public void authMsgCtl(FtzInMsgCtl ftzInMsgCtl) {
		log.debug("FTZInCommonService.authMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgId(ftzInMsgCtl);
		
		FtzInMsgCtl result = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		FtzInTxnDtl ftzInTxnDtl=new FtzInTxnDtl();
		ftzInTxnDtl.setMsgId(ftzInMsgCtl.getMsgId());
		List<FtzInTxnDtl> page = ftzInTxnDtlRepository.queryList(ftzInTxnDtl);
		

       for (FtzInTxnDtl obj : page) {
				if(!obj.getChkStatus().equals(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)){
					messages.add("e.ftzmis.2103.0008");	
					throw new BusinessException(messages);
				}
					
			}
		
		//更新批量头
		FtzInMsgCtl ftzInMsgCtl1 = new FtzInMsgCtl();
		ftzInMsgCtl1.setMsgId(ftzInMsgCtl.getMsgId());
		ftzInMsgCtl1.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInMsgCtl.getMakDatetime()));
		ftzInMsgCtl1.setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInMsgCtl.getChkDatetime()));
		ftzInMsgCtl1.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		ftzInMsgCtl1.setChkUserId(ContextConst.getCurrentUser().getUserid());
		int ret = ftzInMsgCtlRepository.updateAuth(ftzInMsgCtl1);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0008] Auth FtzInMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0008");					
			throw new BusinessException(messages);
		}

		

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzInMsgCtl.toString());
	}
	
	
	//审核批量明细
	@Transactional
	public void authTxnDtl(FtzInTxnDtl ftzInTxnDtl, String status) {
		log.debug("FTZInCommonService.authTxnDtl() start ...");

		funcId = CommonConst.FUNCTION_FTZ_AUTH_2103;
		ResultMessages messages = ResultMessages.error();
		
		ftzInTxnDtl.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInTxnDtl.getMakDatetime()));
		ftzInTxnDtl.setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzInTxnDtl.getChkDatetime()));
		
		validMsgIdAndSeqNo(ftzInTxnDtl);
		
		FtzInTxnDtl txnDtlResult = ftzInTxnDtlRepository.queryByPK(ftzInTxnDtl);
		if (null == txnDtlResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}
		
		// 查询该交易对应的批量是否存在
		FtzInMsgCtl ftzInMsgCtl = new FtzInMsgCtl();
		ftzInMsgCtl.setMsgId(txnDtlResult.getMsgId());
		FtzInMsgCtl msgCtlResult = ftzInMsgCtlRepository.queryByPK(ftzInMsgCtl);
		if (null == msgCtlResult) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		// 如果报文状态为"发送成功"，则审核交易失败
		if (CommonConst.FTZ_MSG_STATUS_SEND_SUCC.equals(msgCtlResult.getMsgStatus())) {
			ftzInTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			ftzInTxnDtl.setChkUserId(ContextConst.getCurrentUser().getUserid());
			ftzInTxnDtl.setChkAddWord(ResultMessage.fromCode("e.ftzmis.2103.0018").getText());
			ftzInTxnDtlRepository.updateAuth(ftzInTxnDtl);

			BizLog(CommonConst.DATA_LOG_OPERTYPE_REJECT, txnDtlResult.toString(), ftzInTxnDtl.toString());
			
			log.error("[e.ftzmis.2103.0018] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0018");								
			throw new BusinessException(messages);
		}
		
		ftzInTxnDtl.setChkStatus(status);
		ftzInTxnDtl.setChkUserId(ContextConst.getCurrentUser().getUserid());
		int ret = ftzInTxnDtlRepository.updateAuth(ftzInTxnDtl);
		if(status.equals(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL))
		{
			FtzInMsgCtl ftzInMsgCtl1=new FtzInMsgCtl();
			ftzInMsgCtl1.setMsgId(ftzInMsgCtl.getMsgId());
			ftzInMsgCtl1.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			ftzInMsgCtlRepository.updateAuth(ftzInMsgCtl1);
		}
		
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0008] Auth TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0008");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(status)? CommonConst
				.DATA_LOG_OPERTYPE_CHECK: CommonConst.DATA_LOG_OPERTYPE_REJECT, txnDtlResult.toString(), ftzInTxnDtl.toString());
	}

	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZInCommonService#getNextAuthTxnById(com.synesoft.ftzmis.domain.model.FtzInTxnDtl)
	 */
	public FtzInTxnDtl getNextAuthTxnById(FtzInTxnDtl ftzInTxnDtl) {
//		log.debug("FTZInCommonService.getNextAuthTxnById() start ...");
//
//		ResultMessages messages = ResultMessages.error();
//
//		validMsgIdAndSeqNo(ftzInTxnDtl);
//		
//		FtzInTxnDtl result = ftzInTxnDtlRepository.queryAuthByPK(ftzInTxnDtl);
//		if (result == null) {
//			ftzInTxnDtl.setSeqNo(1);
//			result = ftzInTxnDtlRepository.queryAuthByPK(ftzInTxnDtl);
//			if (null == result) {
//				log.error("[i.ftzmis.210303.0006] No data need to auth!");
//				messages.add("i.ftzmis.210303.0006");	
//				throw new BusinessException(messages);
//			}
//		}
//		
		return new FtzInTxnDtl();
	}
	
	private void validMsgId(FtzInMsgCtl ftzInMsgCtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzInMsgCtl || !StringUtil.isNotTrimEmpty(ftzInMsgCtl.getMsgId())) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			m.add("e.ftzmis.2103.0002");								
			throw new BusinessException(m);
		}
	}

	private void validMsgId(FtzInTxnDtl ftzInTxnDtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzInTxnDtl || !StringUtil.isNotTrimEmpty(ftzInTxnDtl.getMsgId())) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			m.add("e.ftzmis.2103.0002");								
			throw new BusinessException(m);
		}
	}

	private void validMsgIdAndSeqNo(FtzInTxnDtl ftzInTxnDtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzInTxnDtl || !StringUtil.isNotTrimEmpty(ftzInTxnDtl.getMsgId()) || 
				null == ftzInTxnDtl.getSeqNo()) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			m.add("e.ftzmis.2103.0009");								
			throw new BusinessException(m);
		}
	}
	
	@Autowired
	protected FtzInMsgCtlRepository ftzInMsgCtlRepository;
	@Autowired
	protected FtzInTxnDtlRepository ftzInTxnDtlRepository;

}

