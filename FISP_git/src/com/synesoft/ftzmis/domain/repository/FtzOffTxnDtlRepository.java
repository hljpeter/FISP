package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzOffTxnDtl;

/**
 * 表外交易明细表(DAT_OFF_TXN_DTL)
 * @author yyw
 * @date 2013-12-25
 */
public interface FtzOffTxnDtlRepository {	

	/**
	 * 查询符合条件数据数量(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param pageable - 分页参数
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		int - 结果条数
	 */
	public int queryCount(FtzOffTxnDtl ftzOffTxnDtl);
	
	/**
	 * 查询一页数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param pageable - 分页参数
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		Page<FtzOffTxnDtl> - 分页对象
	 */
	public Page<FtzOffTxnDtl> queryPage(Pageable pageable, FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 查询所有数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, CHK_STATUS)
	 * @return
	 * 		List<FtzOffTxnDtl> - 结果对象
	 */
	public List<FtzOffTxnDtl> queryList(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 查询所有待审核的数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, CHK_STATUS = 02)
	 * @return
	 * 		List<FtzOffTxnDtl> - 结果对象
	 */
	public List<FtzOffTxnDtl> queryAuthList(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 查询待审核的数据(DAT_OFF_TXN_DTL - 表外交易明细表)，按SEQ_NO顺序返回第一条符合的记录
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO, CHK_STATUS)
	 * @return
	 * 		FtzOffTxnDtl - 结果对象
	 */
	public FtzOffTxnDtl queryAuthByPK(FtzOffTxnDtl ftzOffTxnDtl);
	
	/**
	 * 查询一条数据(DAT_OFF_TXN_DTL - 表外交易明细表)，根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		FtzOffTxnDtl - 结果对象
	 */
	public FtzOffTxnDtl queryByPK(FtzOffTxnDtl ftzOffTxnDtl);
	
	/**
	 * 查询交易明细最大序号(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		String - 最大SeqNo(最小为0)
	 */
	public String queryTxnDtlMaxSeqNo(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 插入一条数据(DAT_OFF_TXN_DTL - 表外交易明细表)，根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int insert(FtzOffTxnDtl ftzOffTxnDtl);
	
	/**
	 * 更新交易数据(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int update(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 更新交易状态(DAT_OFF_TXN_DTL - 表外交易明细表)
	 * @param ftzOffTxnDtl - 封装了值的对象
	 * @return
	 * 		int - 受影响的行数
	 */
	public int updateStatus(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 删除一条数据(DAT_OFF_TXN_DTL - 表外交易明细表)，根据主键
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID, SEQ_NO)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int delete(FtzOffTxnDtl ftzOffTxnDtl);

	/**
	 * 删除一条数据(DAT_OFF_TXN_DTL - 表外交易明细表)，根据MSG_ID
	 * @param ftzOffTxnDtl - 封装了条件值的对象(MSG_ID)
	 * @return
	 * 		int - 受影响的行数
	 */
	public int deleteByMsgId(FtzOffTxnDtl ftzOffTxnDtl);
}
