package com.synesoft.fisp.domain.repository.sm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.UserInf;

/**
 * @author zhongHubo
 * @date 2013年7月8日 15:52:44
 * @version 1.0
 * @Description 用户操作Repository
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface UserInfRepository {

	/**
	 * 根据用户Id查询用户信息
	 * @param userid	用户Id
	 * @return			UserInf
	 */
	public UserInf queryUser(String userid);
	
	/**
	 * 根据创建机构Id查询用户信息
	 * @param orgId
	 * @return
	 */
	public int queryUserInfCnt(String orgId);
	
	/**
	 * 用户信息维护界面查询
	 * 
	 * @param pageable
	 * @param userInf
	 * @return
	 */
	public Page<UserInf> queryList(Pageable pageable, UserInf userInf);

	/**
	 * 新增一条记录到用户信息表中
	 * 
	 * @param userInf
	 * @return
	 */

	public int insertUserInf(UserInf userInf);
	
	/**
	 * 修改用户信息
	 * @param userInf	待修改的用户信息
	 * @return			
	 */
	public int updateUser(UserInf userInf);
	
	/**
	 * 删除用户信息表中一条记录
	 * 
	 * @param userInf
	 * @return
	 */
	public int deleteUserInf(UserInf userInf);
}
