package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.RoleInfForm;
import com.synesoft.fisp.app.sm.model.RoleInfTmpForm;
import com.synesoft.fisp.domain.model.RoleInf;
import com.synesoft.fisp.domain.model.RoleInfTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * @author zhongHubo
 * @date 2013年7月10日 17:04:48
 * @version 1.0
 * @Description 角色操作Service
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface RoleInfService {
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找。
	 * 并排除list中所有的角色
	 * @param createorg	创建机构
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRecTmp(String createorg, List<UserRoleInfTmp> list);

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * 并排除list中所有的角色
	 * @param roleInf	角色对象
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRecTmp(RoleInf roleInf, List<UserRoleInfTmp> list);
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * 并排除list中所有的角色
	 * @param roleInf	角色对象
	 * @return			角色集合List
	 */
	public List<RoleInf> transQueryRoleInfListArr(String createorg, List<String> list);
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找。
	 * 并排除list中所有的角色
	 * @param createorg	创建机构
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRec(String createorg, List<UserRoleInf> list);

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * 并排除list中所有的角色
	 * @param roleInf	角色对象
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRec(RoleInf roleInf, List<UserRoleInf> list);
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * @param createorg	创建机构
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRec(String createorg);

	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色，递归查找
	 * @param roleInf	角色对象
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrgRec(RoleInf roleInf);
	
	/**
	 * 根据机构Id查询该机构所拥有的所有角色，包括上级机构允许的角色
	 * @param orgInf	机构
	 * @return			角色集合List
	 */
	public List<RoleInf> queryRolesByOrg(RoleInf roleInf);
	/**
	 * 角色信息维护查询功能
	 * @param pageable
	 * @param roleInf
	 * @return
	 */
	public Page<RoleInf> transQueryRoleInfList(Pageable pageable,
			RoleInf roleInf);
	
	/**
	 * 查询角色信息表
	 * @param roleInf
	 * @return
	 */
	public RoleInf transQueryRoleInf(RoleInf roleInf);
	
	/**
	 * 查询角色信息临时表
	 * @param roleInfTmp
	 * @return
	 */
	public RoleInfTmp transQueryRoleInfTmp(RoleInfTmp roleInfTmp);
	
	/**
	 * 角色信息维护授权查询功能
	 * @param pageable
	 * @param roleInfTmp
	 * @return
	 */
	public Page<RoleInfTmp> transQueryRoleInfTmpList(Pageable pageable,
			RoleInfTmp roleInfTmp);
	
	/**
	 * 新增操作
	 * 1.检查主表和临时表中是否存在相同角色ID的记录
	 * 2.修改主表中处理状态字段
	 * 3.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transAdd(RoleInfTmpForm form);
	
	/**
	 * 修改操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同角色ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transUpdate(RoleInfForm form);
	
	/**
	 * 删除操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同角色ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transDel(RoleInfForm form);
	

	/**
	 * 授权操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.根据操作类型分别处理
	 * 	3.1.新增操作，将记录信息插入到主表中
	 * 	3.2.修改操作，将更新主表中的记录信息
	 * 	3.3.删除操作，删除主表中该记录信息
	 * @param roleInfTmp
	 * @return
	 */
	public void transAuth(RoleInfTmp roleInfTmp);
	
	/**
	 * 拒绝操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态
	  * @param roleInfTmp
	 * @return
	 */
	public void transRejct(RoleInfTmp roleInfTmp);
	
}
