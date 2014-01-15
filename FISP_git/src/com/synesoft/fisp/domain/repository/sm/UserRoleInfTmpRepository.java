package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface UserRoleInfTmpRepository {

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
	 * @param userRoleInfTmp
	 * @return
	 */
	public UserRoleInfTmp query(UserRoleInfTmp UserRoleInfTmp);
	
	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param userRoleInfTmp
	 * @return
	 */
	public List<UserRoleInfTmp> querylist(UserRoleInfTmp userRoleInfTmp);
	
	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param UserRoleInfTmp
	 * @return
	 */
	public int queryUserRoleInfo(UserRoleInfTmp userRoleInfTmp);

	/**
	 * 新增一条记录到用户角色信息临时表中
	 * 
	 * @param userRoleInfTmp
	 * @return
	 */

	public int insertUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param userRoleInfTmp
	 * @return
	 */
	public int updateUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp);

}
