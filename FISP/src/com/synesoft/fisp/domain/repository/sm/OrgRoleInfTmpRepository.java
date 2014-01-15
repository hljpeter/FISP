package com.synesoft.fisp.domain.repository.sm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.OrgRoleInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface OrgRoleInfTmpRepository {

	/**
	 * 用户角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param orgRoleInfTmp
	 * @return
	 */
	public Page<OrgRoleInfTmp> queryList(Pageable pageable, OrgRoleInfTmp orgRoleInfTmp);

	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param orgRoleInfTmp
	 * @return
	 */
	public OrgRoleInfTmp query(OrgRoleInfTmp OrgRoleInfTmp);
	
	/**
	 * 查询用户角色信息临时表中信息
	 * 
	 * @param OrgRoleInfTmp
	 * @return
	 */
	public int queryOrgRoleInfo(OrgRoleInfTmp OrgRoleInfTmp);

	/**
	 * 新增一条记录到用户角色信息临时表中
	 * 
	 * @param orgRoleInfTmp
	 * @return
	 */

	public int insertOrgRoleInfTmp(OrgRoleInfTmp orgRoleInfTmp);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param orgRoleInfTmp
	 * @return
	 */
	public int updateOrgRoleInfTmp(OrgRoleInfTmp orgRoleInfTmp);

}
