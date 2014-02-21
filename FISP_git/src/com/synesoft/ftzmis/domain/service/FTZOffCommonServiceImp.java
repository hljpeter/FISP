package com.synesoft.ftzmis.domain.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.synesoft.fisp.domain.service.NumberService;
import com.synesoft.ftzmis.app.common.constants.CommonConst;
import com.synesoft.ftzmis.app.common.msgproc.FtzMsgProcService;
import com.synesoft.ftzmis.app.common.util.DateUtil;
import com.synesoft.ftzmis.app.common.util.Validator;
import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;
import com.synesoft.ftzmis.domain.model.vo.FtzOffTxnDtlVO;
import com.synesoft.ftzmis.domain.repository.FtzOffMsgCtlRepository;
import com.synesoft.ftzmis.domain.repository.FtzOffTxnDtlRepository;

/**
 * 6.3　表外及其他(通用)
 * @author yyw
 * @date 2014-01-03
 */
public abstract class FTZOffCommonServiceImp implements FTZOffCommonService {
	private static final Logger log = LoggerFactory.getLogger(FTZOffCommonServiceImp.class);

	protected String funcId;
	
	/**
	 * 表外抽象方法，由子类实现
	 * 新增批量时需要处理的逻辑
	 * @param ftzOffMsgCtl
	 */
	protected abstract void addMsgLogic(FtzOffMsgCtl ftzOffMsgCtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 更新批量时需要处理的逻辑
	 * @param ftzOffMsgCtl
	 */
	protected abstract void updateMsgLogic(FtzOffMsgCtl ftzOffMsgCtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 删除批量时需要处理的逻辑
	 * @param ftzOffMsgCtl
	 */
	protected abstract void deleteMsgLogic(FtzOffMsgCtl ftzOffMsgCtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 新增交易时需要处理的逻辑
	 * @param ftzOffMsgCtl
	 */
	protected abstract void addTxnLogic(FtzOffTxnDtl ftzOffTxnDtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 更新交易时需要处理的逻辑
	 * @param ftzOffTxnDtl
	 */
	protected abstract void updateTxnLogic(FtzOffTxnDtl ftzOffTxnDtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 删除交易时需要处理的逻辑
	 * @param ftzOffTxnDtl
	 */
	protected abstract void deleteTxnLogic(FtzOffTxnDtl ftzOffTxnDtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 审核交易时需要处理的逻辑
	 * @param ftzOffTxnDtl
	 */
//	protected abstract void checkTxnLogic(FtzOffTxnDtl ftzOffTxnDtl);
	/**
	 * 表外抽象方法，由子类实现
	 * 基础信息校验
	 * 代替Spring验证
	 * @param ftzOffTxnDtl
	 */
	protected abstract void validateTxn(FtzOffTxnDtl ftzOffTxnDtl);
	/**
	 * 表外通用方法，子类可重写
	 * 基础信息校验
	 * 代替Spring验证
	 * @param ftzOffMsgCtl
	 */
	protected void validateMsg(FtzOffMsgCtl ftzOffMsgCtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzOffMsgCtl || !StringUtil.isNotTrimEmpty(ftzOffMsgCtl.getBranchId())) {
			log.error("[e.ftzmis.2103.0026] The branchId cannot be empty!"); 
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.2103.0026");									
			m.addAll(msg);
		}
		if (null == ftzOffMsgCtl || !StringUtil.isNotTrimEmpty(ftzOffMsgCtl.getWorkDate())) {
			log.error("[e.ftzmis.2103.0025] The workDate cannot be empty!"); 
			ResultMessage msg = ResultMessage.fromCode("e.ftzmis.2103.0025");								
			m.addAll(msg);
		}
		if (m.isNotEmpty()) {
			throw new BusinessException(m);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getMsgPage(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public Page<FtzOffMsgCtlVO> getMsgPage(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FTZOffCommonService.getMsgPage() start ...");
		
		ResultMessages messages = ResultMessages.error();
		
		log.debug("Searching record, param[branchId=" + ftzOffMsgCtlVO.getBranchId() 
				+ ", startDate=" + ftzOffMsgCtlVO.getStartDate() 
				+ ", endDate=" + ftzOffMsgCtlVO.getEndDate() 
				+ ", msgId=" + ftzOffMsgCtlVO.getMsgId() 
				+ ", msgStatus=" + ftzOffMsgCtlVO.getMsgStatus() 
				+ ", msgNo=" + ftzOffMsgCtlVO.getMsgNo() 
				+ ", pageNubmer=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");

		Page<FtzOffMsgCtlVO> page = ftzOffMsgCtlRepository.queryPage(pageable, ftzOffMsgCtlVO);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return page;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getMsgPageForAuth(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public Page<FtzOffMsgCtlVO> getMsgPageForAuth(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO) {
		log.debug("FTZOffCommonService.getMsgPage() start ...");
		
		ResultMessages messages = ResultMessages.error();
		
		log.debug("Searching record, param[branchId=" + ftzOffMsgCtlVO.getBranchId() 
				+ ", startDate=" + ftzOffMsgCtlVO.getStartDate() 
				+ ", endDate=" + ftzOffMsgCtlVO.getEndDate() 
				+ ", msgId=" + ftzOffMsgCtlVO.getMsgId() 
				+ ", msgStatus=" + ftzOffMsgCtlVO.getMsgStatus() 
				+ ", msgNo=" + ftzOffMsgCtlVO.getMsgNo() 
				+ ", pageNubmer=" + pageable.getPageNumber() + ", pagesize=" + pageable.getPageSize() + "]");

		Page<FtzOffMsgCtlVO> page = ftzOffMsgCtlRepository.queryPageForApr(pageable, ftzOffMsgCtlVO);
		if (null == page || page.getContent().isEmpty()) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getMsgById(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	public FtzOffMsgCtl getMsgById(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.getMsgById() start ...");

		ResultMessages messages = ResultMessages.error();
		
		log.debug("Searching record, param[msgId=" + ftzOffMsgCtl.getMsgId() + "]");
		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getTxnById(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public FtzOffTxnDtl getTxnById(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.getTxnById() start ...");

		ResultMessages messages = ResultMessages.error();
		
		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl result = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (result == null) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");	
			throw new BusinessException(messages);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getTxnById(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public FtzOffTxnDtl getAuthTxnById(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.getAuthTxnById() start ...");

		ResultMessages messages = ResultMessages.error();
		
		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl result = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (result == null) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");	
			throw new BusinessException(messages);
		}
		
		// 已经审核通过
//		if (CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(result.getChkStatus())) {
//			log.error("[ftz.validate.chk.success] No data!");
//			messages.add("ftz.validate.chk.success");	
//			throw new BusinessException(messages);
//		}
		
		// 状态不正确
		if (!CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED.equals(result.getChkStatus()) && 
				!CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(result.getChkStatus()) && 
				!CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(result.getChkStatus())) {
			log.error("[i.ftzmis.2103.0021] No data!");
			messages.add("i.ftzmis.2103.0021");	
			throw new BusinessException(messages);
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getNextAuthTxnById(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public FtzOffTxnDtl getNextAuthTxnById(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.getNextAuthTxnById() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl result = ftzOffTxnDtlRepository.queryAuthByPK(ftzOffTxnDtl);
		if (result == null) {
			ftzOffTxnDtl.setSeqNo(CommonConst.TXN_SEQ_NO_INIT_VLAUE);
			result = ftzOffTxnDtlRepository.queryAuthByPK(ftzOffTxnDtl);
			if (null == result) {
				log.error("[i.ftzmis.210303.0006] No data need to auth!");
				messages.add("i.ftzmis.210303.0006");	
				throw new BusinessException(messages);
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getAuthTxnCountById(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public int getAuthTxnCountById(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.getAuthTxnCountById() start ...");

		validMsgId(ftzOffTxnDtl);
		
		ftzOffTxnDtl.setChkStatuss(new String[]{CommonConst.FTZ_MSG_STATUS_INPUTING, 
				CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, CommonConst.FTZ_MSG_STATUS_AUTH_FAIL});
		int count = ftzOffTxnDtlRepository.queryCount(ftzOffTxnDtl);
		
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getMsgAndTxnDetail(org.springframework.data.domain.Pageable, com.synesoft.ftzmis.domain.model.FtzOffMsgCtl, java.lang.String)
	 */
	public Map<String, Object> getMsgAndTxnDetail(Pageable pageable, FtzOffMsgCtl ftzOffMsgCtl, String status) {
		log.debug("FTZOffCommonService.getMsgAndTxnDetail() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgId(ftzOffMsgCtl);
		
		FtzOffMsgCtl msgCtl = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgCtl) {
			log.error("[w.dp.0001] No data!");
			messages.add("w.dp.0001");
			throw new BusinessException(messages);
		} else {
			FtzOffTxnDtl txnDtl = new FtzOffTxnDtl();
			txnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
			txnDtl.setChkStatus(status);
			Page<FtzOffTxnDtl> page = ftzOffTxnDtlRepository.queryPage(pageable, txnDtl);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("MsgCtl", msgCtl);
			map.put("TxnDtl", page);
			
			return map;
		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#addMsgCtl(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	@Transactional
	public void addMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.addMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateMsg(ftzOffMsgCtl);
		
		addMsgLogic(ftzOffMsgCtl);
		
		ftzOffMsgCtl.setMsgId(numberService.getSysIDSequence(16));
		ftzOffMsgCtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		
		int ret = ftzOffMsgCtlRepository.insert(ftzOffMsgCtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0000] Add MsgCtl information failure!"); 
			messages.add("e.ftzmis.2103.0000");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzOffMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#updateMsgCtl(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl, java.lang.String)
	 */
	@Transactional
	public void updateMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.updateMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateMsg(ftzOffMsgCtl);
		
		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		updateMsgLogic(ftzOffMsgCtl);
		
		FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
		ftzOffMsgCtlVO.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffMsgCtlVO.setOldMsgStatus(ftzOffMsgCtl.getMsgStatus());
		ftzOffMsgCtlVO.setMakDatetime(ftzOffMsgCtl.getMakDatetime());
		ftzOffMsgCtlVO.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffMsgCtlVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
		ftzOffMsgCtlVO.setBranchId(ftzOffMsgCtl.getBranchId());
		int ret = ftzOffMsgCtlRepository.updateMsg(ftzOffMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzOffMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzOffMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#deleteMsgCtl(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl, java.lang.String)
	 */
	@Transactional
	public void deleteMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.deleteMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgId(ftzOffMsgCtl);
		
		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		// 查询所有交易 - 有审核通过的交易，不能删除
		FtzOffTxnDtl txnDtl = new FtzOffTxnDtl();
		txnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
		txnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		int count = ftzOffTxnDtlRepository.queryCount(txnDtl);
		if (0 != count) {
			log.error("[e.ftzmis.2103.0031] There is not any TxnDtl!");
			messages.add("e.ftzmis.2103.0031");					
			throw new BusinessException(messages);
		}
		
		// 如果接收应答报文处理结果为成功，表示报文发送过，不能删除
		if (CommonConst.MSG_PROCESS_RESULT_SUCCESS.equals(result.getResult())) {
			log.error("[e.ftzmis.2103.0012] The msg was sent to PBOC successfully, cannot be deleted!"); 
			messages.add("e.ftzmis.2103.0012");								
			throw new BusinessException(messages);
		}
		
		deleteMsgLogic(ftzOffMsgCtl);
		
		FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
		ftzOffMsgCtlVO.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffMsgCtlVO.setOldMsgStatus(ftzOffMsgCtl.getMsgStatus());
		ftzOffMsgCtlVO.setMakDatetime(ftzOffMsgCtl.getMakDatetime());
		ftzOffMsgCtlVO.setChkDatetime(ftzOffMsgCtl.getChkDatetime());
		int ret = ftzOffMsgCtlRepository.deleteMsg(ftzOffMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0014] Delete FtzOffMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0014");					
			throw new BusinessException(messages);
		}
		
		// 删除交易明细
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffTxnDtlRepository.deleteByMsgId(ftzOffTxnDtl);
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE, result.toString(), "");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#submitMsgCtl(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	@Transactional
	public void submitMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.submitMsgCtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgId(ftzOffMsgCtl);
		
		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		// 批量已经提交
		if (CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED.equals(result.getMsgStatus())) {
			log.error("[e.ftzmis.2103.0024] The MsgCtl has been submitted!"); 
			messages.add("e.ftzmis.2103.0024");								
			throw new BusinessException(messages);
		}
		
		// 是否存在交易明细
		FtzOffTxnDtl ftzOffTxnDtl = new FtzOffTxnDtl();
		ftzOffTxnDtl.setMsgId(result.getMsgId());
//		int count = ftzOffTxnDtlRepository.queryCount(ftzOffTxnDtl);
//		if (count == 0) {
//			log.error("[e.ftzmis.2103.0028] The MsgCtl has not any TxnDtl!"); 
//			messages.add("e.ftzmis.2103.0028");								
//			throw new BusinessException(messages);
//		}

		// 是否存在审核失败的交易明细
		ftzOffTxnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
		int count2 = ftzOffTxnDtlRepository.queryCount(ftzOffTxnDtl);
		if (count2 != 0) {
			log.error("[e.ftzmis.2103.0029] The MsgCtl has some TxnDtl unauthorized!"); 
			messages.add("e.ftzmis.2103.0029");								
			throw new BusinessException(messages);
		}
		
		// 更新状态
		FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
		ftzOffMsgCtlVO.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffMsgCtlVO.setOldMsgStatus(ftzOffMsgCtl.getMsgStatus());
		ftzOffMsgCtlVO.setMakDatetime(ftzOffMsgCtl.getMakDatetime());
		ftzOffMsgCtlVO.setChkDatetime(ftzOffMsgCtl.getChkDatetime());
		ftzOffMsgCtlVO.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffMsgCtlVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		int ret = ftzOffMsgCtlRepository.updateMsgStatus(ftzOffMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzOffMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

//		FtzOffTxnDtl txnDtl = new FtzOffTxnDtl();
//		txnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
//		txnDtl.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
//		ftzOffTxnDtlRepository.updateStatus(txnDtl);

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzOffMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#authMsgCtl(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl)
	 */
	@Transactional
	public void authMsgCtl(FtzOffMsgCtl ftzOffMsgCtl) {
		log.debug("FTZOffCommonService.authMsgCtl() start ...");

		funcId = CommonConst.FUNCTION_FTZ_AUTH_2103;
		ResultMessages messages = ResultMessages.error();

		validMsgId(ftzOffMsgCtl);
		
		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		// 审核录入是否同一人
		if (Validator.CheckSameUser(result.getMakUserId())) {
			log.error("[e.ftzmis.2103.0030] The maker and checker cannot be the same person!"); 
			messages.add("e.ftzmis.2103.0030");								
			throw new BusinessException(messages);
		}
		
		// 查询所有交易 - 无，不能审核通过批量
		FtzOffTxnDtl txnDtl = new FtzOffTxnDtl();
		txnDtl.setMsgId(ftzOffMsgCtl.getMsgId());
//		List<FtzOffTxnDtl> allList = ftzOffTxnDtlRepository.queryList(txnDtl);
//		if (null == allList || allList.isEmpty()) {
//			log.error("[e.ftzmis.2103.0020] There is not any TxnDtl!");
//			messages.add("e.ftzmis.2103.0020");					
//			throw new BusinessException(messages);
//		}
		
		// 查询未审核通过的交易 - 有，不能审核通过批量
		txnDtl.setChkStatuss(new String[]{CommonConst.FTZ_MSG_STATUS_AUTH_FAIL, CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED, 
				CommonConst.FTZ_MSG_STATUS_INPUTING });
		List<FtzOffTxnDtl> authList = ftzOffTxnDtlRepository.queryAuthList(txnDtl);
		if (null != authList && !authList.isEmpty()) {
			log.error("[e.ftzmis.2103.0019] There is some TxnDtl to be auth!");
			messages.add("e.ftzmis.2103.0019", authList.size());					
			throw new BusinessException(messages);
		}
		
		FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
		ftzOffMsgCtlVO.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffMsgCtlVO.setMsgNo(result.getMsgNo());
		ftzOffMsgCtlVO.setOldMsgStatus(ftzOffMsgCtl.getMsgStatus());
		ftzOffMsgCtlVO.setMakDatetime(ftzOffMsgCtl.getMakDatetime());
		ftzOffMsgCtlVO.setChkDatetime(ftzOffMsgCtl.getChkDatetime());
		ftzOffMsgCtlVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC);
		ftzOffMsgCtlVO.setChkUserId(ContextConst.getCurrentUser().getUserid());
		int ret = ftzOffMsgCtlRepository.updateMsgStatus(ftzOffMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0008] Auth FtzOffMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0008");					
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_CHECK, result.toString(), ftzOffMsgCtl.toString());
		if (ftzOffMsgCtlVO.getMsgStatus().equals(
				CommonConst.FTZ_MSG_STATUS_AUTH_SUCC)) {
			generateXml.submitMsg(ftzOffMsgCtlVO.getMsgNo(), ftzOffMsgCtlVO.getMsgId());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#updateMsgStatus(com.synesoft.ftzmis.domain.model.FtzOffMsgCtl, java.lang.String)
	 */
	@Transactional
	public void updateMsgStatus(FtzOffMsgCtl ftzOffMsgCtl, String editFlag) {
		log.debug("FTZOffCommonService.updateMsgStatus() start ...");

		ResultMessages messages = ResultMessages.error();

		FtzOffMsgCtl result = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == result) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}
		
		FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
		ftzOffMsgCtlVO.setMsgId(ftzOffMsgCtl.getMsgId());
		ftzOffMsgCtlVO.setOldMsgStatus(ftzOffMsgCtl.getMsgStatus());
		
		int ret = ftzOffMsgCtlRepository.updateMsgStatus(ftzOffMsgCtlVO);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0001] Update FtzOffMsgCtl information failure!");
			messages.add("e.ftzmis.2103.0001");					
			throw new BusinessException(messages);
		}

		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, result.toString(), ftzOffMsgCtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#getTxnDtlMaxSeqNo(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	public String getTxnDtlMaxSeqNo(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.getTxnDtlMaxSeqNo() start ...");

		validMsgId(ftzOffTxnDtl);
		
		return ftzOffTxnDtlRepository.queryTxnDtlMaxSeqNo(ftzOffTxnDtl);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#addTxnDtl(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Transactional
	public void addTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.addTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateTxn(ftzOffTxnDtl);

		// 这笔交易对应的报文是否存在
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(ftzOffTxnDtl.getMsgId());
		FtzOffMsgCtl msgResult = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}

		// 1) 如果接收应答报文处理结果为成功，表示报文发送过
		// 2) 如果报文本次报送修改或者删除
		// 以上两种情况下不能新增
//		if (CommonConst.MSG_PROCESS_RESULT_SUCCESS.equals(msgResult.getResult())) {
//			log.error("[e.ftzmis.2103.0015] The MsgCtl need be sent 'update' or 'delete' to PBOC, so you cannot add new TxnDtl!"); 
//			messages.add("e.ftzmis.2103.0015");								
//			throw new BusinessException(messages);
//		}
		
		addTxnLogic(ftzOffTxnDtl);
		
		// 插入交易
		ftzOffTxnDtl.setSeqNo(ftzOffTxnDtlRepository.queryTxnDtlMaxSeqNo(ftzOffTxnDtl));
		ftzOffTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffTxnDtl.setChkStatus(CommonConst.TXN_SKIP_SUBMIT? CommonConst
				.FTZ_MSG_STATUS_INPUT_COMPLETED: CommonConst.FTZ_MSG_STATUS_INPUTING);
		int ret = ftzOffTxnDtlRepository.insert(ftzOffTxnDtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0006] Add TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0006");								
			throw new BusinessException(messages);
		}

		// 如果报文状态不是正在录入，修改状态
		if (!CommonConst.FTZ_MSG_STATUS_INPUTING.equals(msgResult.getMsgStatus())) {
			FtzOffMsgCtlVO msgVO = new FtzOffMsgCtlVO();
			msgVO.setMsgId(ftzOffTxnDtl.getMsgId());
			msgVO.setMakDatetime(msgResult.getMakDatetime());
			msgVO.setOldMsgStatus(msgResult.getMsgStatus());
			msgVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
			ret = ftzOffMsgCtlRepository.updateMsg(msgVO);
			if (ret != 1) {
				log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
				messages.add("e.ftzmis.2103.0007");								
				throw new BusinessException(messages);
			}
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_ADD, "", ftzOffTxnDtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#updateTxnDtl(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Transactional
	public void updateTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.updateTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();

		validateTxn(ftzOffTxnDtl);

		// 交易是否存在
		FtzOffTxnDtl txnResult = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (null == txnResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}
		
		// 审核通过不可修改
		if (CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(txnResult.getChkStatus())) {
			log.error("[e.ftzmis.2103.0022] The TxnDtl auditing successfully and cannot be updated!"); 
			messages.add("e.ftzmis.2103.0022");								
			throw new BusinessException(messages);
		}
		
		// 这笔交易对应的报文是否存在
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(ftzOffTxnDtl.getMsgId());
		FtzOffMsgCtl msgResult = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}

		// 1) 如果接收应答报文处理结果为成功，表示报文发送过
		// 2) 如果报文本次报送删除
		// 以上两种情况下不能修改
//		if (CommonConst.MSG_PROCESS_RESULT_SUCCESS.equals(msgResult.getResult()) && 
//				CommonConst.FTZ_MSG_EDIT_FLAG_DELETE.equals(msgResult.getEditFlag())) {
//			log.error("[e.ftzmis.2103.0016] The MsgCtl need be sent 'delete' to PBOC, so you cannot update TxnDtl!"); 
//			messages.add("e.ftzmis.2103.0016");								
//			throw new BusinessException(messages);
//		}
		
		updateTxnLogic(ftzOffTxnDtl);
		
		// 更新交易
		ftzOffTxnDtl.setMakUserId(ContextConst.getCurrentUser().getUserid());
		ftzOffTxnDtl.setChkStatus(CommonConst.TXN_SKIP_SUBMIT? CommonConst
				.FTZ_MSG_STATUS_INPUT_COMPLETED: CommonConst.FTZ_MSG_STATUS_INPUTING);
		int ret = ftzOffTxnDtlRepository.update(ftzOffTxnDtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0007");								
			throw new BusinessException(messages);
		}

		// 如果报文状态不是正在录入，修改状态
		if (!CommonConst.FTZ_MSG_STATUS_INPUTING.equals(msgResult.getMsgStatus())) {
			FtzOffMsgCtlVO msgVO = new FtzOffMsgCtlVO();
			msgVO.setMsgId(ftzOffTxnDtl.getMsgId());
			msgVO.setMakDatetime(msgResult.getMakDatetime());
			msgVO.setOldMsgStatus(msgResult.getMsgStatus());
			msgVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_INPUTING);
			ret = ftzOffMsgCtlRepository.updateMsg(msgVO);
			if (ret != 1) {
				log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
				messages.add("e.ftzmis.2103.0007");								
				throw new BusinessException(messages);
			}
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, txnResult.toString(), ftzOffTxnDtl.toString());
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#submitTxn(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Transactional
	public void submitTxn(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.updateTxnStatus() start ...");

		ResultMessages messages = ResultMessages.error();

		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl txn = new FtzOffTxnDtl();
		txn.setMsgId(ftzOffTxnDtl.getMsgId());
		txn.setSeqNo(ftzOffTxnDtl.getSeqNo());
		txn.setMakDatetime(ftzOffTxnDtl.getMakDatetime());
		txn.setChkDatetime(ftzOffTxnDtl.getChkDatetime());
		txn.setMakUserId(ContextConst.getCurrentUser().getUserid());
		txn.setChkStatus(CommonConst.FTZ_MSG_STATUS_INPUT_COMPLETED);
		
		int ret = ftzOffTxnDtlRepository.update(txn);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0007] Update TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0007");								
			throw new BusinessException(messages);
		}
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#deleteTxnDtl(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Transactional
	public void deleteTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.deleteTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();
		
		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl txnDtlResult = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (null == txnDtlResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}

		// 审核通过不可删除
		if (CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(txnDtlResult.getChkStatus())) {
			log.error("[e.ftzmis.2103.0023] The TxnDtl auditing successfully and cannot be deleted!"); 
			messages.add("e.ftzmis.2103.0023");								
			throw new BusinessException(messages);
		}
		
		// 查询该交易对应的批量是否存在
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(txnDtlResult.getMsgId());
		FtzOffMsgCtl msgCtlResult = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgCtlResult) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		// 如果接收应答报文处理结果为成功，表示报文发送过，不能删除
		if (CommonConst.MSG_PROCESS_RESULT_SUCCESS.equals(msgCtlResult.getResult())) {
			log.error("[e.ftzmis.2103.0013] The TxnDtl was sent to PBOC successfully, cannot be deleted!"); 
			messages.add("e.ftzmis.2103.0013");								
			throw new BusinessException(messages);
		}
		
		deleteTxnLogic(ftzOffTxnDtl);
		
		int ret = ftzOffTxnDtlRepository.delete(ftzOffTxnDtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0011] Delete TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0011");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_DELETE, msgCtlResult.toString(), "");
	}

	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#submitTxnDtl(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl)
	 */
	@Transactional
	public void submitTxnDtl(FtzOffTxnDtl ftzOffTxnDtl) {
		log.debug("FTZOffCommonService.submitTxnDtl() start ...");

		ResultMessages messages = ResultMessages.error();
		
		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl txnDtlResult = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (null == txnDtlResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}
		
		// 查询该交易对应的批量是否存在
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(txnDtlResult.getMsgId());
		FtzOffMsgCtl msgCtlResult = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgCtlResult) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		int ret = ftzOffTxnDtlRepository.update(ftzOffTxnDtl);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0017] Sumbit TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0017");								
			throw new BusinessException(messages);
		}
		
		BizLog(CommonConst.DATA_LOG_OPERTYPE_MODIFY, txnDtlResult.toString(), ftzOffTxnDtl.toString());
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.ftzmis.domain.service.FTZOffCommonService#authTxnDtl(com.synesoft.ftzmis.domain.model.FtzOffTxnDtl, java.lang.String)
	 */
	@Transactional
	public void authTxnDtl(FtzOffTxnDtl ftzOffTxnDtl, String status) {
		log.debug("FTZOffCommonService.authTxnDtl() start ...");

		funcId = CommonConst.FUNCTION_FTZ_AUTH_2103;
		ResultMessages messages = ResultMessages.error();
		
		validMsgIdAndSeqNo(ftzOffTxnDtl);
		
		FtzOffTxnDtl txnDtlResult = ftzOffTxnDtlRepository.queryByPK(ftzOffTxnDtl);
		if (null == txnDtlResult) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			messages.add("e.ftzmis.2103.0009");								
			throw new BusinessException(messages);
		}
		
		// 审核录入是否同一人
		if (Validator.CheckSameUser(txnDtlResult.getMakUserId())) {
			log.error("[e.ftzmis.2103.0030] The maker and checker cannot be the same person!"); 
			messages.add("e.ftzmis.2103.0030");								
			throw new BusinessException(messages);
		}
		
		// 查询该交易对应的批量是否存在
		FtzOffMsgCtl ftzOffMsgCtl = new FtzOffMsgCtl();
		ftzOffMsgCtl.setMsgId(txnDtlResult.getMsgId());
		FtzOffMsgCtl msgCtlResult = ftzOffMsgCtlRepository.queryByPK(ftzOffMsgCtl);
		if (null == msgCtlResult) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			messages.add("e.ftzmis.2103.0002");								
			throw new BusinessException(messages);
		}

		// 如果报文状态为"发送成功"，则审核交易失败
		
		// 更新状态
		FtzOffTxnDtlVO vo = new FtzOffTxnDtlVO();
		vo.setMsgId(ftzOffTxnDtl.getMsgId());
		vo.setSeqNo(ftzOffTxnDtl.getSeqNo());
		vo.setOldChkStatus(ftzOffTxnDtl.getChkStatus());
		vo.setMakUserId(ftzOffTxnDtl.getMakUserId());
		vo.setMakDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getMakDatetime()));
		vo.setChkDatetime(DateUtil.getFormatDateTimeRemoveSpritAndColon(ftzOffTxnDtl.getChkDatetime()));
		vo.setChkStatus(status);
		vo.setChkUserId(ContextConst.getCurrentUser().getUserid());
		vo.setChkAddWord(ftzOffTxnDtl.getChkAddWord());
		int ret = ftzOffTxnDtlRepository.updateStatus(vo);
		if (ret != 1) {
			log.error("[e.ftzmis.2103.0008] Auth TxnDtl information failure!"); 
			messages.add("e.ftzmis.2103.0008");								
			throw new BusinessException(messages);
		}
		
		// 如果审核失败，更新批量状态为审核失败
		if (CommonConst.FTZ_MSG_STATUS_AUTH_FAIL.equals(status)) {
			FtzOffMsgCtlVO ftzOffMsgCtlVO = new FtzOffMsgCtlVO();
			ftzOffMsgCtlVO.setMsgId(msgCtlResult.getMsgId());
			ftzOffMsgCtlVO.setOldMsgStatus(msgCtlResult.getMsgStatus());
			ftzOffMsgCtlVO.setMakDatetime(msgCtlResult.getMakDatetime());
			ftzOffMsgCtlVO.setChkDatetime(msgCtlResult.getChkDatetime());
			ftzOffMsgCtlVO.setMsgStatus(CommonConst.FTZ_MSG_STATUS_AUTH_FAIL);
			ftzOffMsgCtlVO.setChkUserId(ContextConst.getCurrentUser().getUserid());
			ret = ftzOffMsgCtlRepository.updateMsgStatus(ftzOffMsgCtlVO);
			if (ret != 1) {
				log.error("[e.ftzmis.2103.0008] Auth FtzOffMsgCtl information failure!");
				messages.add("e.ftzmis.2103.0008");					
				throw new BusinessException(messages);
			}
		}
		
		BizLog(CommonConst.FTZ_MSG_STATUS_AUTH_SUCC.equals(status)? CommonConst
				.DATA_LOG_OPERTYPE_CHECK: CommonConst.DATA_LOG_OPERTYPE_REJECT, txnDtlResult.toString(), ftzOffTxnDtl.toString());
	}

	private void BizLog(String operType, String beforeData, String afterData) {
		OrgInf orgInfo = ContextConst.getOrgInfByUser();
		UserInf userInfo = ContextConst.getCurrentUser();
		TlrLogPrint.tlrBizLogPrint(funcId, orgInfo.getOrgid(), userInfo.getUserid(), userInfo.getUsername(), operType, 
				DateUtil.getNowInputDate(), DateUtil.getNowInputTime(), beforeData, afterData);
	}
	
	private void validMsgId(FtzOffMsgCtl ftzOffMsgCtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzOffMsgCtl || !StringUtil.isNotTrimEmpty(ftzOffMsgCtl.getMsgId())) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			m.add("e.ftzmis.2103.0002");								
			throw new BusinessException(m);
		}
		
		ftzOffMsgCtl.setMsgId(ftzOffMsgCtl.getMsgId().replace(",", "").trim());
	}

	private void validMsgId(FtzOffTxnDtl ftzOffTxnDtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzOffTxnDtl || !StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getMsgId())) {
			log.error("[e.ftzmis.2103.0002] The MsgCtl is wrong!"); 
			m.add("e.ftzmis.2103.0002");								
			throw new BusinessException(m);
		}
	}

	private void validMsgIdAndSeqNo(FtzOffTxnDtl ftzOffTxnDtl) {
		ResultMessages m = ResultMessages.error();
		if (null == ftzOffTxnDtl || !StringUtil.isNotTrimEmpty(ftzOffTxnDtl.getMsgId()) || 
				null == ftzOffTxnDtl.getSeqNo()) {
			log.error("[e.ftzmis.2103.0009] The TxnDtl is wrong!"); 
			m.add("e.ftzmis.2103.0009");								
			throw new BusinessException(m);
		}

		ftzOffTxnDtl.setMsgId(ftzOffTxnDtl.getMsgId().replace(",", "").trim());
		ftzOffTxnDtl.setSeqNo(ftzOffTxnDtl.getSeqNo().replace(",", "").trim());
	}
	
	@Autowired
	protected FtzOffMsgCtlRepository ftzOffMsgCtlRepository;
	@Autowired
	protected FtzOffTxnDtlRepository ftzOffTxnDtlRepository;
	@Autowired
	private NumberService numberService;
	@Resource
	protected FtzMsgProcService generateXml;
}
