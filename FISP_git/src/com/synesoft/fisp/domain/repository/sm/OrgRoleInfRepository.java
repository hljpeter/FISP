package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.OrgRoleInf;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface OrgRoleInfRepository {

	/**
	 * 用户角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param orgRoleInf
	 * @return
	 */
	public Page<OrgRoleInf> queryList(Pageable pageable, OrgRoleInf orgRoleInf);

	public int queryOrgRoleInfCnt(OrgRoleInf orgRoleInf);
	/**
	 * 查询用户角色信息表中信息
	 * 
	 * @param orgRoleInf
	 * @return
	 */
	public OrgRoleInf query(OrgRoleInf orgRoleInf);
	
	/**
	 * 查询用户角色信息表中角色信息
	 * 
	 * @param orgRoleInf
	 * @return
	 */
	 public List<OrgRoleInf> queryOrgRoleIdList(OrgRoleInf orgRoleInf);

	/**
	 * 新增一条记录到用户角色信息表中
	 * 
	 * @param orgRoleInf
	 * @return
	 */

	public int insertOrgRoleInf(OrgRoleInf orgRoleInf);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param orgRoleInf
	 * @return
	 */
	public int updateOrgRoleInf(OrgRoleInf orgRoleInf);

	/**
	 * 删除用户角色信息表中一条记录
	 * 
	 * @param orgRoleInf
	 * @return
	 */
	public int deleteOrgRoleInf(OrgRoleInf orgRoleInf);

}
