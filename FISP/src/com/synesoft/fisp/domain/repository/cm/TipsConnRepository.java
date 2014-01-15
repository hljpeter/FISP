package com.synesoft.fisp.domain.repository.cm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsConn;

public interface TipsConnRepository {

	/**
	 * TIPS连接表查询
	 * 
	 * @param pageable
	 * @param tipsConn
	 * @return
	 */
	public Page<TipsConn> queryList(Pageable pageable, TipsConn tipsConn);

	/**
	 * 查询TIPS连接表表中信息
	 * 
	 * @param nodecode
	 * @return
	 */
	public TipsConn query(String nodecode);

	/**
	 * 新增一条记录到TIPS连接表表中
	 * 
	 * @param tipsConn
	 * @return
	 */

	public int insertTipsConn(TipsConn tipsConn);

	/**
	 * 更新报文头表中一条记录
	 * 
	 * @param tipsConn
	 * @return
	 */
	public int updateTipsConn(TipsConn tipsConn);

	/**
	 * 删除TIPS连接表表中一条记录
	 * 
	 * @param tipsConn
	 * @return
	 */
	public int deleteTipsConn(TipsConn tipsConn);

}
