package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.UserOrgInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */
public interface UserOrgInfTmpRepository {

	
	/**
	 * 查询用户机构临时列表
	 * @param userOrgInfTmp
	 * @return	List<UserOrgInfTmp>
	 */
	 public List<UserOrgInfTmp> queryUserOrgInfTmpList(UserOrgInfTmp userOrgInfTmp);
	 
	 /**
	 * 查询用户机构临时表
	 * @param userOrgInfTmp
	 * @return	int
	 */
	 public int queryUserOrgInfTmpCnt(UserOrgInfTmp userOrgInfTmp);
	 
	 
	 /**
	 * 查询用户机构临时表
	 * @param userOrgInfTmp
	 * @return	int
	 */
	 public UserOrgInfTmp queryUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp);
	 
	 /**
	  * 
	  * @param userOrgInfTmp
	  * @return
	  */
	 public int insertUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp);
	 
	 /**
	  * 
	  * @param userOrgInfTmp
	  * @return
	  */
	 public int deleteUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp);
	 
	 
	 public int updateUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp);
	 

}
