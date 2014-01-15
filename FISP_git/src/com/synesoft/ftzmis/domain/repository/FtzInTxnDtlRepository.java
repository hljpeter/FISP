package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

public interface FtzInTxnDtlRepository {	

	/**
	 * 查询一页数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param pageable - 分页参数
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		Page<FtzInTxnDtl> - 分页对象
	 */
	public Page<FtzInTxnDtl> queryPage(Pageable pageable, FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 查询所有数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, CHK_STATUS)
	 * @return
	 * 		List<FtzInTxnDtl> - 结果对象
	 */
	public List<FtzInTxnDtl> queryList(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 查询一条数据(DAT_IN_TXN_DTL - 表内交易明细表)，根据主键
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzInTxnDtl - 结果对象
	 */
	public FtzInTxnDtl queryByPK(FtzInTxnDtl ftzInTxnDtl);
	
	/**
	 * 查询交易明细最大序号(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		Integer - 最大SeqNo(最小为0)
	 */
	public String queryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 插入一条数据(DAT_IN_TXN_DTL - 表内交易明细表)，根据主键
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int insert(FtzInTxnDtl ftzInTxnDtl);
	
	/**
	 * 更新交易数据(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int update(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 更新交易状态(DAT_IN_TXN_DTL - 表内交易明细表)
	 * @param ftzInTxnDtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateStatus(FtzInTxnDtl ftzInTxnDtl);

	/**
	 * 删除一条数据(DAT_IN_TXN_DTL - 表内交易明细表)，根据主键
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int delete(FtzInTxnDtl ftzInTxnDtl);
	
	
	public int deleteByMsgID(FtzInTxnDtl ftzInTxnDtl);
	
	public int queryCount(FtzInTxnDtl ftzInTxnDtl);
	
	
	/**
	 * 查询待审核的数据(DAT_In_TXN_DTL - 表外交易明细表)，按SEQ_NO顺序返回第一条符合的记录
	 * @param ftzInTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO, CHK_STATUS)
	 * @return
	 * 		FtzInTxnDtl - 结果对象
	 */
	public FtzInTxnDtl queryAuthByPK(FtzInTxnDtl ftzInTxnDtl);
	
	//更新审核状态
	public int updateAuth(FtzInTxnDtl ftzInTxnDtl) ;
	
}