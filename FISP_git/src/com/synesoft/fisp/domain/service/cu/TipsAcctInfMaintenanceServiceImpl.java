package com.synesoft.fisp.domain.service.cu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.TipsAcctInf;
import com.synesoft.fisp.domain.repository.cu.TipsAcctInfRepository;

@Service("tipsAcctInfMaintenanceService")
public class TipsAcctInfMaintenanceServiceImpl implements TipsAcctInfMaintenanceService {

	@Override
	public Page<TipsAcctInf> transQueryTipsAcctInfList(Pageable pageable, TipsAcctInf tipsAcctInf) {
		Page<TipsAcctInf> page = tipsAcctInfRepository.queryList(pageable, tipsAcctInf);
		return page;
	}
	
	@Override
	public TipsAcctInf transQueryTipsAcctInf(TipsAcctInf tipsAcctInf) {
		tipsAcctInf=tipsAcctInfRepository.query(StringUtil.trim(tipsAcctInf.getAcctcode()));
		return tipsAcctInf;
	}
	
	@Autowired
	protected TipsAcctInfRepository tipsAcctInfRepository;
	
}
