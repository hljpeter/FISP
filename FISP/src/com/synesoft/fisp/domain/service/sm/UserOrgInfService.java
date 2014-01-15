package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;


/**
 * 
 * @author michelle.wang
 * 
 */
public interface UserOrgInfService {
	
	/**
	 * 
	 * @return
	 */
	public List<UserOrgInf> transQueryUserOrgInfList(UserOrgInf userOrgInf);
	
	public List<UserOrgInfTmp> transQueryUserOrgInfTmpList(UserOrgInfTmp userOrgInfTmp);
	
	/**
	 * 查询正式表和临时表，将同一个User下的数据合并
	 * 临时表中存在的记录只执行两种操作：新增或者删除
	 * 1) 如果为新增，则正式表中不存在(需要合并)
	 * 2) 如果为删除，则正式表中存在(需要排除)
	 * @param userOrgInf - 只用到UserId
	 * @return
	 */
	public List<UserOrgInfTmp> transQueryUserOrgInfMerge(UserOrgInfTmp userOrgInfTmp);

}
