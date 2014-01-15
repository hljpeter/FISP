package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
/**
 * SYS_USER_ROLE(角色与机构无关)
 * @author yyw
 * @date 2013-12-20
 */
public interface SysUserRoleRepository {

	/** ======================== yyw updated ======================== */
	/**
	 * 查询用户角色信息表中信息
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 * 		SysUserRoleTmp
	 */
	public SysUserRole query(SysUserRole sysUserRole);
	
	/**
	 * 查询用户角色信息表中信息
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public List<SysUserRole> querylist(SysUserRole sysUserRole);

	/**
	 * 新增一条记录到SYS_USER_ROLE中
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public int insert(SysUserRole sysUserRole);

	/**
	 * 查询角色Id不在参数给出List的用户角色信息
	 * 
	 * @param list
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> queryNotInlist(String[] list, String userId);

	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param userOrgInf - 只用到UserId
	 * @return
	 */
	public List<SysUserRoleTmp> queryRoleListMerge(SysUserRoleTmp sysUserRoleTmp);


	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param userRoleInf
	 * @return
	 */
	public int update(SysUserRole sysUserRole);

	/**
	 * 删除用户角色信息表中一条记录
	 * 
	 * @param userRoleInf
	 * @return
	 */
	public int delete(SysUserRole sysUserRole);
	
	/** ======================== yyw updated ======================== */
	
	/**
	 * 用户角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param userRoleInfTmp
	 * @return
	 */
	public Page<UserRoleInfTmp> queryList(Pageable pageable, UserRoleInfTmp userRoleInfTmp);

	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param UserRoleInfTmp
	 * @return
	 */
	public int queryUserRoleInfo(UserRoleInfTmp userRoleInfTmp);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param userRoleInfTmp
	 * @return
	 */
	public int updateUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp);

}
