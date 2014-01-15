package com.synesoft.fisp.domain.service.cm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsConn;

public interface TipsConnMaintenanceService {
	
	/**
	 * TIPS连接表查询功能
	 * @param pageable
	 * @param tipsConn
	 * @return
	 */
	public Page<TipsConn> transQueryTipsConnList(Pageable pageable,
			TipsConn tipsConn);
	
	/**
	 * 查询TIPS连接表表
	 * @param tipsConn
	 * @return
	 */
	public TipsConn transQueryTipsConn(TipsConn tipsConn);
	
}
