package com.synesoft.ftzmis.domain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.vo.FtzInMsgCtlVO;

public interface FTZInCommonService {
	
	/** ============================= 报文 ============================= */
	/**
	 * 表内通用方法
	 * 查询一页数据(DAT_IN_MSG_CTL - 表内报文控制表)
	 * @param pageable - 分页参数
	 * @param ftzINMsgCtlVO - 封装了条件值的对象(BRANCH_ID,MSG_ID,MSG_STATUS,WORK_DATE,MSG_NO(default),EDIT_FLAG)
	 * @return
	 * 		Page<FtzINMsgCtlVO> - 分页对象
	 */
	public Page<FtzInMsgCtlVO> transQueryMsgPage(Pageable pageable, FtzInMsgCtlVO ftzInMsgCtlVO);

	/**
	 * 表内通用方法
	 * 查询一页数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param pageable - 分页参数
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @param status - CHK_STAUTS
	 * @return
	 * 		Map<String, Object> - 分页对象
	 */
	public Map<String, Object> transQueryMsgAndTxnDetail(Pageable pageable, FtzInMsgCtl ftzInMsgCtl, String status);

	/**
	 * 表内通用方法
	 * 查询一条数据(DAT_In_MSG_CTL - 表内报文控制表)， 根据主键
	 * @param ftzInMsgCtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		FtzInMsgCtl - 结果对象
	 */
	public FtzInMsgCtl transQueryMsgById(FtzInMsgCtl ftzInMsgCtl);

	/**
	 * 表内通用方法
	 * 新增报文头信息(DAT_IN_MSG_CTL - 表内报文控制表)
	 * @param ftzInMsgCtl - 封装了值的对象(MSG_ID, MSG_STATUS, BRANCH_ID, WORK_DATE)
	 * @param msgNo - 报文编号
	 */
	public void transUpdateAddMsgCtl(FtzInMsgCtl ftzInMsgCtl, String msgNo);

	/**
	 * 表内通用方法
	 * 修改报文信息(DAT_IN_MSG_CTL - 表内报文控制表)，为修改和删除
	 * @param ftzInMsgCtl - 封装了值的对象
	 * @param editFlag - 操作标志
	 */
	public void transUpdateMsgCtl(FtzInMsgCtl ftzInMsgCtl, String editFlag);

	/**
	 * 表内通用方法
	 * 修改报文状态(DAT_IN_MSG_CTL - 表内报文控制表)，为提交
	 * @param ftzInMsgCtlVO - 封装了值的对象
	 * @param editFlag - 操作标志
	 */
	public void transUpdateSubmitMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	/**
	 * 表内通用方法
	 * 修改报文状态(DAT_IN_MSG_CTL - 表内报文控制表)，为提交，审核等
	 * @param ftzInMsgCtlVO - 封装了值的对象
	 * @param editFlag - 操作标志
	 */
	public void transUpdateMsgStatus(FtzInMsgCtl ftzInMsgCtl, String editFlag);
	
	/** ============================= 交易明细 ============================= */
	/**
	 * 表内通用方法
	 * 查询交易明细最大序号(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		Integer - 最大SeqNo
	 */
	public Integer transQueryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 表内通用方法
	 * 查询一条数据(DAT_IN_TXN_DTL - 表内交易明细表)， 根据主键
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzInTxnDtl - 结果对象
	 */
	public FtzInTxnDtl transQueryTxnById(FtzInTxnDtl ftzInTxnDtl);
	
	/**
	 * 表内通用方法
	 * 新增交易信息(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl
	 */
	public void transUpdateAddTxnDtl(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 表内通用方法
	 * 更新交易信息 - 数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl
	 */
	public void transUpdateTxnDtl(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 表内通用方法
	 * 更新交易信息状态 - 数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl
	 */
	public void transUpdateTxnStatus(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 表内通用方法
	 * 删除交易信息 - 数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl
	 */
	public void transUpdateDeleteTxnDtl(FtzInTxnDtl ftzInTxnDtl);
	
	//删除表头信息
	public void transDeleteMsgCtl (FtzInMsgCtl ftzInMsgCtl);
	
	
	/**
	 * <p>表外通用方法</p>
	 * 查询一条待审核的数据(DAT_In_TXN_DTL - 表外交易明细表)， 根据主键
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		int - 结果条数
	 */
	public int getAuthTxnCountById(FtzInTxnDtl ftzInTxnDtl);
	
	
	/**
	 * <p>表外通用方法</p>
	 * 审核报文(DAT_In_MSG_CTL - 表外报文控制表)
	 * @param ftzInMsgCtlVO - 封装了值的对象
	 */
	public void authMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	/**
	 * <p>表外通用方法</p>
	 * 审核交易信息 - 数据(DAT_In_TXN_DTL - 表外交易明细表)
	 * @param ftzInTxnDtl
	 * @param status - 审核状态
	 */
	public void authTxnDtl(FtzInTxnDtl ftzInTxnDtl, String status);
	
	
	/* * <p>表外通用方法</p>
	 * 查询下一条待审核的数据(DAT_In_TXN_DTL - 表外交易明细表)，  根据主键,CHK_STATUS = 02
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzInTxnDtl - 结果列表
	 */
	public FtzInTxnDtl getNextAuthTxnById(FtzInTxnDtl ftzInTxnDtl);


}
