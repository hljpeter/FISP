package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.SysPasswordHistorys;

public interface SysPasswordHistorysRepository {
	
	public SysPasswordHistorys querySysPasswordHistorys(SysPasswordHistorys SysPasswordHistorys);

	public int insertSysPasswordHistorys(SysPasswordHistorys SysPasswordHistorys);
	
	public List<SysPasswordHistorys> querySysPasswordHistorysList(SysPasswordHistorys SysPasswordHistorys);

}
