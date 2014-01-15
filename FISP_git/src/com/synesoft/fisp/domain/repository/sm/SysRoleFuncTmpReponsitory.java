package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.SysRoleFuncTmp;

public interface SysRoleFuncTmpReponsitory {
	/**
	 * 查询角色功能信息临时表信息
	 * 
	 * @param sysRoleFuncTmp
	 * @return
	 */
	public List<SysRoleFuncTmp> querySysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp);
	/**
	 * 新增一条记录到角色功能信息临时表中
	 * 
	 * @param sysRoleFuncTmp
	 * @return
	 */
	public int insertSysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp);
	/**
	 * 修改一条记录到角色功能信息临时表中状态
	 * 
	 * @param sysRoleFuncTmp
	 * @return
	 */
	public int updateSysRoleFuncTmp(SysRoleFuncTmp sysRoleFuncTmp);
}
