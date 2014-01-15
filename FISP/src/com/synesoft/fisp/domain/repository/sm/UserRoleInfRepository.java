package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface UserRoleInfRepository {

	/**
	 * 用户角色信息维护界面查询
	 * 
	 * @param pageable
	 * @param userRoleInf
	 * @return
	 */
	public Page<UserRoleInf> queryList(Pageable pageable, UserRoleInf userRoleInf);
	
	/**
	 * 根据角色ID查看该角色是否已经分配给用户
	 * @param roleId
	 * @return
	 */
	public int queryUserRoleInfCnt(String roleId);

	/**
	 * 查询用户角色信息表中信息
	 * 
	 * @param userRoleInf
	 * @return
	 */
	public UserRoleInf query(UserRoleInf userRoleInf);
	
	/**
	 * 查询用户角色信息表中角色信息
	 * 
	 * @param userRoleInf
	 * @return
	 */
	 public List<UserRoleInf> queryUserRoleIdList(UserRoleInf userRoleInf);

	/**
	 * 查询不在给出List的用户机构角色信息
	 * @param params
	 * @param userId
	 * @return
	 */
	 public List<UserRoleInf> queryNotInList(List<String> notInList, String userId);
	 /**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param userRoleInfTmp - 只用到UserId
	 * @return
	 */
	 public List<UserRoleInfTmp> queryRoleListMerge(UserRoleInfTmp userRoleInfTmp);
	 
	/**
	 * 新增一条记录到用户角色信息表中
	 * 
	 * @param userRoleInf
	 * @return
	 */

	public int insertUserRoleInf(UserRoleInf userRoleInf);

	/**
	 * 更新用户角色表中一条记录
	 * 
	 * @param userRoleInf
	 * @return
	 */
	public int updateUserRoleInf(UserRoleInf userRoleInf);

	/**
	 * 删除用户角色信息表中一条记录
	 * 
	 * @param userRoleInf
	 * @return
	 */
	public int deleteUserRoleInf(UserRoleInf userRoleInf);

}
