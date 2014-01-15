package com.synesoft.fisp.domain.repository.bm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.synesoft.fisp.domain.model.SysRegionInfo;


public interface SysRegionRepository {	
	public Page<SysRegionInfo> queryInputList(Pageable pageable,SysRegionInfo sysRegionInfo);

}
