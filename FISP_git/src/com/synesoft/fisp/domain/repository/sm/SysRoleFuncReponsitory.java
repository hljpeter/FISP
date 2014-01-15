package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.SysRoleFunc;
import com.synesoft.fisp.domain.model.UserRoleInf;

public interface SysRoleFuncReponsitory {
	public List<SysRoleFunc> querySysRoleFunc(SysRoleFunc sysRoleFunc);
	
	public int insertSysRoleFunc(SysRoleFunc sysRoleFunc);
	
	public int deleteSysRoleFunc(SysRoleFunc sysRoleFunc);
	
	public int updateSysRoleFunc(SysRoleFunc SysRoleFunc);
	
	public List<SysRoleFunc> querySysRoleFunc(UserRoleInf userRoleInf);

	/**
	 * 角色不绑定机构
	 * @param userRoleInf
	 * @return
	 */
	public List<SysRoleFunc> querySysRoleFuncNoOrg(UserRoleInf userRoleInf);
}
