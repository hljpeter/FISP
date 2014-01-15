package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.SysRoleFuncTmp;

@Repository
public class SysRoleFuncTmpRepositoryImpl implements SysRoleFuncTmpReponsitory{
	@Override
	public List<SysRoleFuncTmp> querySysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp){
		return queryDAO.executeForObjectList(Table.SYS_ROLE_FUNC_TMP+"."+SQLMap.SELECT_LIST, sysRoleFuncTmp);
	}
	@Override
	public int insertSysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp) {
		return updateDAO.execute(Table.SYS_ROLE_FUNC_TMP + "." + SQLMap.INSERT, sysRoleFuncTmp);
	}
	@Override
	public int updateSysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp) {
		return updateDAO.execute(Table.SYS_ROLE_FUNC_TMP + "."+ SQLMap.UPDATE_BYKEY_SELECTIVE, sysRoleFuncTmp);
	}
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;
}
