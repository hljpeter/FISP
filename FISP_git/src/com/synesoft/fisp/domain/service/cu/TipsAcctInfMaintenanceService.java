package com.synesoft.fisp.domain.service.cu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsAcctInf;

public interface TipsAcctInfMaintenanceService {
	
	/**
	 * 账户信息查询功能
	 * @param pageable
	 * @param tipsAcctInf
	 * @return
	 */
	public Page<TipsAcctInf> transQueryTipsAcctInfList(Pageable pageable,
			TipsAcctInf tipsAcctInf);
	
	/**
	 * 查询账户信息表
	 * @param tipsAcctInf
	 * @return
	 */
	public TipsAcctInf transQueryTipsAcctInf(TipsAcctInf tipsAcctInf);
	
}
