package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.SysPasswordHistorys;

public interface SysPasswordHistorysService {

	public SysPasswordHistorys querySysPasswordHistorys(SysPasswordHistorys sysPasswordHistorys);

	public int insertSysPasswordHistorys(SysPasswordHistorys sysPasswordHistorys);

	public List<SysPasswordHistorys> querySysPasswordHistorysList(SysPasswordHistorys sysPasswordHistorys);

}
