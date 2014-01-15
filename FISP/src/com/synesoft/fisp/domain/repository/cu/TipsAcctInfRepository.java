package com.synesoft.fisp.domain.repository.cu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsAcctInf;

public interface TipsAcctInfRepository {

	/**
	 * 账户信息维护界面查询
	 * 
	 * @param pageable
	 * @param tipsAcctInf
	 * @return
	 */
	public Page<TipsAcctInf> queryList(Pageable pageable, TipsAcctInf tipsAcctInf);

	/**
	 * 查询账户信息表中信息
	 * 
	 * @param acctcode
	 * @return
	 */
	public TipsAcctInf query(String acctcode);

	/**
	 * 新增一条记录到账户信息表中
	 * 
	 * @param tipsAcctInf
	 * @return
	 */

	public int insertTipsAcctInf(TipsAcctInf tipsAcctInf);

	/**
	 * 更新账户表中一条记录
	 * 
	 * @param tipsAcctInf
	 * @return
	 */
	public int updateTipsAcctInf(TipsAcctInf tipsAcctInf);

	/**
	 * 删除账户信息表中一条记录
	 * 
	 * @param tipsAcctInf
	 * @return
	 */
	public int deleteTipsAcctInf(TipsAcctInf tipsAcctInf);

}
