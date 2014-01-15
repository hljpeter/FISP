package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;
/**
 * SYS_USER_ROLE_TMP(角色与机构无关)
 * @author yyw
 * @date 2013-12-20
 */
public interface SysUserRoleTmpRepository {

	/** ======================== yyw updated ======================== */
	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 * 		SysUserRoleTmp
	 */
	public SysUserRoleTmp query(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public int queryCount(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public List<SysUserRoleTmp> querylist(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 新增一条记录到SYS_USER_ROLE_TMP中
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public int insert(SysUserRoleTmp sysUserRoleTmp);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param sysUserRoleTmp
	 * @return
	 */
	public int update(SysUserRoleTmp sysUserRoleTmp);
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
	 * 更新用户角色表中一条记录
	 * 
	 * @param userRoleInfTmp
	 * @return
	 */
	public int updateUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp);

}
