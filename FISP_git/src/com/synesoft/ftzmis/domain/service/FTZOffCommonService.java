package com.synesoft.ftzmis.domain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzOffMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzOffMsgCtlVO;

/**
 * 6.3　表外及其他(通用)
 * @author yyw
 * @date 2014-01-03
 */
public interface FTZOffCommonService {	

	/** ============================= 报文 ============================= */
	/**
	 * <p>表外通用方法</p>
	 * 查询一页数据(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzOffMsgCtl - 封装了条件值的对象(BRANCH_ID,MSG_ID,MSG_STATUS,WORK_DATE,MSG_NO(default),EDIT_FLAG)
	 * @return
	 * 		Page<FtzOffMsgCtlVO> - 分页对象
	 */
	public Page<FtzOffMsgCtlVO> getMsgPage(Pageable pageable, FtzOffMsgCtlVO ftzOffMsgCtlVO);

	/**
	 * <p>表外通用方法</p>
	 * 查询一页数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param pageable - 分页参数
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @param status - CHK_STAUTS(查询明细数据的状态，可以为null)
	 * @return
	 * 		Map<String, Object> - 分页对象
	 */
	public Map<String, Object> getMsgAndTxnDetail(Pageable pageable, FtzOffMsgCtl ftzOffMsgCtl, String status);

	/**
	 * <p>表外通用方法</p>
	 * 查询一条数据(DAT_OFF_MSG_CTL - 表外报文控制表)， 根据主键
	 * @param ftzOffMsgCtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		FtzOffMsgCtl - 结果对象
	 */
	public FtzOffMsgCtl getMsgById(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * <p>表外通用方法</p>
	 * 新增报文头信息(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param ftzOffMsgCtl - 封装了值的对象(MSG_ID, MSG_STATUS, BRANCH_ID, WORK_DATE)
	 */
	public void addMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * <p>表外通用方法</p>
	 * 修改报文信息(DAT_OFF_MSG_CTL - 表外报文控制表)，为修改
	 * @param ftzOffMsgCtl - 封装了值的对象
	 */
	public void updateMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * <p>表外通用方法</p>
	 * 删除报文信息(DAT_OFF_MSG_CTL - 表外报文控制表)，为删除
	 * @param ftzOffMsgCtl - 封装了值的对象
	 */
	public void deleteMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * <p>表外通用方法</p>
	 * 修改报文状态(DAT_OFF_MSG_CTL - 表外报文控制表)，为提交
	 * @param ftzOffMsgCtlVO - 封装了值的对象
	 */
	public void submitMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);

	/**
	 * <p>表外通用方法</p>
	 * 审核报文(DAT_OFF_MSG_CTL - 表外报文控制表)
	 * @param ftzOffMsgCtlVO - 封装了值的对象
	 */
	public void authMsgCtl(FtzOffMsgCtl ftzOffMsgCtl);
	
	/**
	 * <p>表外通用方法</p>
	 * 修改报文状态(DAT_OFF_MSG_CTL - 表外报文控制表)，为提交，审核等
	 * @param ftzOffMsgCtlVO - 封装了值的对象
	 * @param editFlag - 操作标志
	 */
	public void updateMsgStatus(FtzOffMsgCtl ftzOffMsgCtl, String editFlag);
	
	/** ============================= 交易明细 ============================= */
	/**
	 * <p>表外通用方法</p>
	 * 查询交易明细最大序号(DAT_OFF_TXN_DTL - 表外交易明细表)，根据主键之一MSG_ID
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		String - 最大SeqNo
	 */
	public String getTxnDtlMaxSeqNo(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 查询一条数据(DAT_OFF_TXN_DTL - 表外交易明细表)， 根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzOffTxnDtl - 结果对象
	 */
	public FtzOffTxnDtl getTxnById(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 查询一条待审核的数据(DAT_OFF_TXN_DTL - 表外交易明细表)， 根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		int - 结果条数
	 */
	public int getAuthTxnCountById(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 查询一条待审核的数据(DAT_OFF_TXN_DTL - 表外交易明细表)， 根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzOffTxnDtl - 结果对象
	 */
	public FtzOffTxnDtl getAuthTxnById(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 查询下一条待审核的数据(DAT_OFF_TXN_DTL - 表外交易明细表)，  根据主键,CHK_STATUS = 02
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzOffTxnDtl - 结果列表
	 */
	public FtzOffTxnDtl getNextAuthTxnById(FtzOffTxnDtl ftzOffTxnDtl);
	
	/**
	 * <p>表外通用方法</p>
	 * 新增交易信息(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 */
	public void addTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 更新交易信息 - 数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 */
	public void updateTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 删除交易信息 - 数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 */
	public void submitTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 提交交易信息 - 数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 */
	public void submitTxn(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 删除交易信息 - 数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 */
	public void deleteTxnDtl(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * <p>表外通用方法</p>
	 * 审核交易信息 - 数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl
	 * @param status - 审核状态
	 */
	public void authTxnDtl(FtzOffTxnDtl ftzOffTxnDtl, String status);
	
}
