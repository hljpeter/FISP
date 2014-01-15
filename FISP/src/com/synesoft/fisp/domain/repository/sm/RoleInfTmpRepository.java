package com.synesoft.fisp.domain.repository.sm;


import com.synesoft.fisp.domain.model.RoleInfTmp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * 
 * @author michelle.wang
 * 
 */

public interface RoleInfTmpRepository {

	/**
	 * 角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param roleInfTmp
	 * @return
	 */
	public Page<RoleInfTmp> queryList(Pageable pageable, RoleInfTmp roleInfTmp);

	/**
	 * 查询角色信息临时表中信息
	 * 
	 * @param roleInfTmp
	 * @return
	 */
	public RoleInfTmp query(RoleInfTmp RoleInfTmp);
	
	/**
	 * 查询角色信息临时表中信息
	 * 
	 * @param RoleInfTmp
	 * @return
	 */
	public int queryRoleInfo(RoleInfTmp RoleInfTmp);

	/**
	 * 新增一条记录到角色信息临时表中
	 * 
	 * @param roleInfTmp
	 * @return
	 */

	public int insertRoleInfTmp(RoleInfTmp roleInfTmp);

	/**
	 * 更新角色表中一条记录
	 * 
	 * @param roleInfTmp
	 * @return
	 */
	public int updateRoleInfTmp(RoleInfTmp roleInfTmp);

}
