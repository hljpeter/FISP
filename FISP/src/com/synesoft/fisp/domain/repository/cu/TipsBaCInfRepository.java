package com.synesoft.fisp.domain.repository.cu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsBaCInf;

public interface TipsBaCInfRepository {

	/**
	 * 银行代码查询
	 * 
	 * @param pageable
	 * @param tipsBaCInf
	 * @return
	 */
	public Page<TipsBaCInf> queryList(Pageable pageable, TipsBaCInf tipsBaCInf);

	/**
	 * 查询银行代码表中信息
	 * 
	 * @param reckbankno
	 * @return
	 */
	public TipsBaCInf query(String reckbankno);

	/**
	 * 新增一条记录到银行代码表中
	 * 
	 * @param tipsBaCInf
	 * @return
	 */

	public int insertTipsBaCInf(TipsBaCInf tipsBaCInf);

	/**
	 * 更新账户表中一条记录
	 * 
	 * @param tipsBaCInf
	 * @return
	 */
	public int updateTipsBaCInf(TipsBaCInf tipsBaCInf);

	/**
	 * 删除银行代码表中一条记录
	 * 
	 * @param tipsBaCInf
	 * @return
	 */
	public int deleteTipsBaCInf(TipsBaCInf tipsBaCInf);

}
