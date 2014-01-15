package com.synesoft.fisp.domain.service.cm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.model.TipsConn;
import com.synesoft.fisp.domain.repository.cm.TipsConnRepository;

@Service("tipsConnMaintenanceService")
public class TipsConnMaintenanceServiceImpl implements TipsConnMaintenanceService {

	@Override
	public Page<TipsConn> transQueryTipsConnList(Pageable pageable, TipsConn tipsConn) {
		Page<TipsConn> page = tipsConnRepository.queryList(pageable, tipsConn);
		return page;
	}
	
	@Override
	public TipsConn transQueryTipsConn(TipsConn tipsConn) {
		tipsConn=tipsConnRepository.query(StringUtil.trim(tipsConn.getNodecode()));
		return tipsConn;
	}

	@Autowired
	protected TipsConnRepository tipsConnRepository;

}
