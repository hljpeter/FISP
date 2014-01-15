package com.synesoft.fisp.domain.service.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.bm.model.Bm_Data_AddForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.app.bm.model.Bm_Data_UpdForm;
import com.synesoft.fisp.domain.model.SysDataDict;

public interface SysDataDictService {

	public Page<SysDataDict> querySysDataDictGroupPage(Pageable pageable,Bm_Data_QryForm form);

	
	public int insertSysDataDict(Bm_Data_AddForm form);
	
	public int updateSysDataDict(Bm_Data_UpdForm form);
	
	public int deleteSysDataDict(SysDataDict sysDataDict);
	
	public SysDataDict querySysDataDictByKey(SysDataDict sysDataDict);
	

}
