package com.synesoft.fisp.domain.service.cu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.TipsBaCInf;
import com.synesoft.fisp.domain.repository.cu.TipsBaCInfRepository;

@Service("tipsBaCInfMaintenanceService")
public class TipsBaCInfMaintenanceServiceImpl implements TipsBaCInfMaintenanceService {

	@Override
	public Page<TipsBaCInf> transQueryTipsBaCInfList(Pageable pageable, TipsBaCInf tipsBaCInf) {
		Page<TipsBaCInf> page = tipsBaCInfRepository.queryList(pageable, tipsBaCInf);
		return page;
	}
	
	@Override
	public TipsBaCInf transQueryTipsBaCInf(TipsBaCInf tipsBaCInf) {
		tipsBaCInf=tipsBaCInfRepository.query(StringUtil.trim(tipsBaCInf.getReckbankno()));
		return tipsBaCInf;
	}
	
	@Autowired
	protected TipsBaCInfRepository tipsBaCInfRepository;
	
}
