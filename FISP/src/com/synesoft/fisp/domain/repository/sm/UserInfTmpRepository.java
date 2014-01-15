package com.synesoft.fisp.domain.repository.sm;


import com.synesoft.fisp.domain.model.UserInfTmp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * 
 * @author michelle.wang
 * 
 */

public interface UserInfTmpRepository {

	/**
	 * 用户信息维护界面查询
	 * 
	 * @param pageable
	 * @param userInfTmp
	 * @return
	 */
	public Page<UserInfTmp> queryList(Pageable pageable, UserInfTmp userInfTmp);

	/**
	 * 查询用户信息临时表中信息
	 * 
	 * @param userInfTmp
	 * @return
	 */
	public UserInfTmp query(UserInfTmp UserInfTmp);
	
	/**
	 * 查询用户信息临时表中信息
	 * 
	 * @param UserInfTmp
	 * @return
	 */
	public int queryUserInfo(UserInfTmp UserInfTmp);

	/**
	 * 新增一条记录到用户信息临时表中
	 * 
	 * @param userInfTmp
	 * @return
	 */

	public int insertUserInfTmp(UserInfTmp userInfTmp);

	/**
	 * 更新角色表中一条记录
	 * 
	 * @param userInfTmp
	 * @return
	 */
	public int updateUserInfTmp(UserInfTmp userInfTmp);

}
