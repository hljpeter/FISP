package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.UserRoleInfForm;
import com.synesoft.fisp.app.sm.model.UserRoleInfTmpForm;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */
public interface UserRoleInfMaintenanceService {
	
	/**
	 * 用户角色信息维护查询功能
	 * @param pageable
	 * @param userRoleInf
	 * @return
	 */
	public Page<UserRoleInf> transQueryUserRoleInfList(Pageable pageable,
			UserRoleInf userRoleInf);
	
	/**
	 * 查询用户角色信息表
	 * @param userRoleInf
	 * @return
	 */
	public UserRoleInf transQueryUserRoleInf(UserRoleInf userRoleInf);
	
	/**
	 * 查询用户角色信息表
	 * @param userRoleInf
	 * @return
	 */
	public List<UserRoleInf> transQueryUserRoleInfList(UserRoleInf userRoleInf);

	/**
	 * 查询用户角色信息表
	 * 使用transQueryUserRoleInfList(UserRoleInf)的结果
	 * @param userRoleInf
	 * @return
	 */
	public List<String> transQueryUserRoleIdList(UserRoleInf userRoleInf);
	
	/**
	 * 查询用户角色信息临时表
	 * @param userRoleInfTmp
	 * @return
	 */
	public UserRoleInfTmp transQueryUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp);
	/**
	 * 查询用户角色信息临时表
	 * @param userRoleInfTmp
	 * @return
	 */
	public List<UserRoleInfTmp> transQueryUserRoleInfTmpList(UserRoleInfTmp userRoleInfTmp);
	/** 
	 * 查询用户角色信息临时表
	 * 使用transQueryUserRoleInfTmpList(UserRoleInfTmp)的结果
	 * @param userRoleInfTmp
	 * @return
	 */
	public List<String> transQueryRoleIdListFromTmp(UserRoleInfTmp userRoleInfTmp);
	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param userOrgInf - 只用到UserId
	 * @return
	 */
	public List<UserRoleInfTmp> transQueryRoleListMerge(UserRoleInfTmp userRoleInfTmp);
	
	/**
	 * 用户角色信息维护授权查询功能
	 * @param pageable
	 * @param userRoleInfTmp
	 * @return
	 */
	public Page<UserRoleInfTmp> transQueryUserRoleInfTmpList(Pageable pageable,
			UserRoleInfTmp userRoleInfTmp);
	
	/**
	 * 新增操作
	 * 1.检查主表和临时表中是否存在相同用户角色ID的记录
	 * 2.修改主表中处理状态字段
	 * 3.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public int transAdd(UserRoleInfTmpForm form);
	
	/**
	 * 修改/删除操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同用户角色ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @param optType  02-修改 03-删除 
	 * @return
	 */
	public int transUpdate(UserRoleInfForm form,String optType);
	

	/**
	 * 授权操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.根据操作类型分别处理
	 * 	3.1.新增操作，将记录信息插入到主表中
	 * 	3.2.修改操作，将更新主表中的记录信息
	 * 	3.3.删除操作，删除主表中该记录信息
	 * @param form
	 * @return
	 */
	public int transAuth(UserRoleInfTmpForm form);
	
	/**
	 * 拒绝操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态
	  * @param form
	 * @return
	 */
	public int transRejct(UserRoleInfTmpForm form);

}
