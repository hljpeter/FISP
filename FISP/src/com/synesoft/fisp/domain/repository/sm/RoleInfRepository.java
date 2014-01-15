package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.RoleInf;

/**
 * @author zhongHubo
 * @date 2013年7月10日 17:29:08
 * @version 1.0
 * @Description 角色操作Repository
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface RoleInfRepository {
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色
	 * @param roleInf	角色
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrg(RoleInf roleInf);
	
	/**
	 * 根据创建机构Id查询角色信息
	 * @param orgId
	 * @return
	 */
	public int queryRoleInfCnt(String orgId);
	
	/**
	 * 角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param roleInf
	 * @return
	 */
	public Page<RoleInf> queryList(Pageable pageable, RoleInf roleInf);

	/**
	 * 查询角色信息表中信息
	 * 
	 * @param roleId
	 * @return
	 */
	public RoleInf query(String roleId);

	/**
	 * 新增一条记录到角色信息表中
	 * 
	 * @param roleInf
	 * @return
	 */

	public int insertRoleInf(RoleInf roleInf);

	/**
	 * 更新角色表中一条记录
	 * 
	 * @param roleInf
	 * @return
	 */
	public int updateRoleInf(RoleInf roleInf);

	/**
	 * 删除角色信息表中一条记录
	 * 
	 * @param roleInf
	 * @return
	 */
	public int deleteRoleInf(RoleInf roleInf);
}
