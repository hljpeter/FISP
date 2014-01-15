package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.SysPasswordHistorys;

@Repository
public class SysPasswordHistorysRepositoryImpl implements SysPasswordHistorysRepository {

	public SysPasswordHistorys querySysPasswordHistorys(SysPasswordHistorys sysPasswordHistorys){
		return queryDAO.executeForObject(Table.SYS_PASSWORD_HISTORYS+"."+SQLMap.SELECT_BYKEY, sysPasswordHistorys, SysPasswordHistorys.class);
	}

	public int insertSysPasswordHistorys(SysPasswordHistorys sysPasswordHistorys) {
		return updateDAO.execute(Table.SYS_PASSWORD_HISTORYS + "." + SQLMap.INSERT, sysPasswordHistorys);
	}

	public List<SysPasswordHistorys> querySysPasswordHistorysList(SysPasswordHistorys sysPasswordHistorys){
		return queryDAO.executeForObjectList(Table.SYS_PASSWORD_HISTORYS + "." + SQLMap.SELECT_LIST, sysPasswordHistorys);
	}
	
	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;
}
