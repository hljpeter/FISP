package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.domain.model.SysDataDict;


public interface SysDataDictRepository {	
	public Page<SysDataDict> queryInputList(Pageable pageable,Bm_Data_QryForm bmDataForm);
	public int deleteSysDataDict(SysDataDict sysDataDict);
	public SysDataDict query(SysDataDict sysDataDict);
	public int updateSysDataDict(SysDataDict sysDataDict);
	public int insertSysDataDict(SysDataDict sysDataDict) ;
}
