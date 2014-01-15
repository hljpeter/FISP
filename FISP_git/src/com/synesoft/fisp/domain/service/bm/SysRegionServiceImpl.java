package com.synesoft.fisp.domain.service.bm;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.app.bm.model.Bm_Regin_QryForm;
import com.synesoft.fisp.domain.model.SysRegionInfo;
import com.synesoft.fisp.domain.repository.bm.SysRegionRepository;
@Service("sysRegionService")
public class SysRegionServiceImpl implements SysRegionService {
	@Resource
	SysRegionRepository sysRegionRepository;
	@Override
	public Page<SysRegionInfo> querySysCurrencyPage(Pageable pageable,
			Bm_Regin_QryForm form) {
		SysRegionInfo sysRegionInfo = form.getSysRegionInfo();
		return sysRegionRepository.queryInputList(pageable, sysRegionInfo);
	}

}
