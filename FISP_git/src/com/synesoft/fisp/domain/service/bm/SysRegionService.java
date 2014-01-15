package com.synesoft.fisp.domain.service.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.bm.model.Bm_Regin_QryForm;
import com.synesoft.fisp.domain.model.SysRegionInfo;


public interface SysRegionService {

	public Page<SysRegionInfo> querySysCurrencyPage(Pageable pageable,Bm_Regin_QryForm form);

}
