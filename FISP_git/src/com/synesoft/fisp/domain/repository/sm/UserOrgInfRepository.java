package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;

/**
 * @author zhongHubo
 * @date 2013年7月25日 17:20:59
 * @version 1.0
 * @Description 用户机构Repository
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
public interface UserOrgInfRepository {

	
	/**
	 * 查询用户机构列表
	 * @param userOrgInf
	 * @return	List<UserOrgInf>
	 */
	 public List<UserOrgInf> queryUserOrgInfList(UserOrgInf userOrgInf);
	 
	 /**
	  * 
	  * @param orgId
	  * @return
	  */
	 public int queryUserOrgInfCnt(String orgId);
	 
	 /**
	 * 查询用户机构表
	 * @param userOrgInf
	 * @return	userOrgInf
	 */
	 public UserOrgInf queryUserOrgInf(UserOrgInf userOrgInf);
	
	 /**
	  * 查询机构Id不在参数List中的用户机构信息
	  * @param vo
	  * @return
	  */
	 public List<UserOrgInf> queryNotInList(List<String> notInList, String userId);
	 
	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在
	 * 2) 如果为删除，则正式表中存在
	 * 因此使用UNION可以合并这两种情况
	 * @param userOrgInfTmp - 只用到UserId
	 * @return
	 */
	public List<UserOrgInfTmp> transQueryUserOrgInfMerge(UserOrgInfTmp userOrgInfTmp);
	 
	 /**
	  * 
	  * @param userOrgInf
	  * @return
	  */
	 public int insertUserOrgInf(UserOrgInf userOrgInf);
	 
	 /**
	  * 
	  * @param userOrgInf
	  * @return
	  */
	 public int deleteUserOrgInf(UserOrgInf userOrgInf);
	 
	 /**
	  * 
	  * @param userOrgInf
	  * @return
	  */
	public int updateUserOrgInf(UserOrgInf userOrgInf);
}
