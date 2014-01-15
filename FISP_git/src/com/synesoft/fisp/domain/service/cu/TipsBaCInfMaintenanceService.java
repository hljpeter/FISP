package com.synesoft.fisp.domain.service.cu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.TipsBaCInf;

public interface TipsBaCInfMaintenanceService {
	
	/**
	 * 银行信息查询功能
	 * @param pageable
	 * @param tipsBaCInf
	 * @return
	 */
	public Page<TipsBaCInf> transQueryTipsBaCInfList(Pageable pageable,
			TipsBaCInf tipsBaCInf);
	
	/**
	 * 查询银行信息表
	 * @param tipsBaCInf
	 * @return
	 */
	public TipsBaCInf transQueryTipsBaCInf(TipsBaCInf tipsBaCInf);
	
}
