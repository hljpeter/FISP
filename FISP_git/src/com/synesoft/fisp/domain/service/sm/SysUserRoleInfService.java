package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.SysUserRoleTmp;

/**
 * @author yyw
 * @date 2013-12-23
 * @version 1.0
 * @Description 用户角色Service（角色不绑定机构）
 */
public interface SysUserRoleInfService {
	
	/**
	 * 根据UserId查询该用户所拥有的所有角色（临时表）
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 * 		List<SysUserRoleTmp>
	 */
	public List<SysUserRoleTmp> queryByUserIdFromTmp(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 根据UserId查询该用户所拥有的所有角色的角色ID（临时表）
	 * 使用queryByUserIdFromTmp(SysUserRoleTmp)的结果
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 * 		List<String>
	 */
	public List<String> queryRoleIdByUserIdFromTmp(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 根据UserId查询该用户所拥有的所有角色（正式表）
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 * 		List<SysUserRole>
	 */
	public List<SysUserRole> queryByUserId(SysUserRole sysUserRole);

	/**
	 * 根据UserId查询该用户所拥有的所有角色的角色ID（正式表）
	 * 使用queryByUserId(SysUserRole)的结果
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 * 		List<String>
	 */
	public List<String> queryRoleIdByUserId(SysUserRole sysUserRole);

	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 */
	public List<SysUserRoleTmp> queryRoleListMerge(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param sysUserRoleTmp - 只用到UserId
	 * @return
	 */
	public String[] queryRoleListMerge(SysUserRole sysUserRole);
}
