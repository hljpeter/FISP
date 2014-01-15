package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.SysRoleFunc;
import com.synesoft.fisp.domain.model.UserRoleInf;

@Repository
public class SysRoleFuncRepositoryImpl implements SysRoleFuncReponsitory{
	@Override
	public List<SysRoleFunc> querySysRoleFunc(SysRoleFunc sysRoleFunc){
		return queryDAO.executeForObjectList(Table.SYS_ROLE_FUNC+"."+SQLMap.SELECT_BY_ROLE_ID, sysRoleFunc);
	}
	
	@Override
	public int insertSysRoleFunc(SysRoleFunc sysRoleFunc) {
		return updateDAO.execute(Table.SYS_ROLE_FUNC + "." + SQLMap.INSERT, sysRoleFunc);
	}
	@Override
	public int deleteSysRoleFunc(SysRoleFunc sysRoleFunc) {
		return updateDAO.execute(Table.SYS_ROLE_FUNC + "." + SQLMap.DELETE_BYKEY,
				sysRoleFunc);
	}
	@Override
	public int updateSysRoleFunc(SysRoleFunc SysRoleFunc) {
		return updateDAO.execute(Table.SYS_ROLE_FUNC + "."+ SQLMap.UPDATE_BYKEY_SELECTIVE, SysRoleFunc);
	}
	@Override
	public List<SysRoleFunc> querySysRoleFunc(UserRoleInf userRoleInf) {
		return queryDAO.executeForObjectList(Table.SYS_ROLE_FUNC+"."+SQLMap.SELECT_BY_USER, userRoleInf);
	}
	
	/**
	 * 角色不绑定机构
	 * @param userRoleInf
	 * @return
	 */
	public List<SysRoleFunc> querySysRoleFuncNoOrg(UserRoleInf userRoleInf){
		return queryDAO.executeForObjectList(Table.SYS_ROLE_FUNC+"."+SQLMap.SELECT_ROLE_NO_ORG, userRoleInf);
	}
	
	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;
}
