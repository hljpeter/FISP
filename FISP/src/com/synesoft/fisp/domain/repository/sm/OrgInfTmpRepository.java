package com.synesoft.fisp.domain.repository.sm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.OrgInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface OrgInfTmpRepository {

	/**
	 * 机构信息维护界面查询
	 * 
	 * @param pageable
	 * @param orgInfTmp
	 * @return
	 */
	public Page<OrgInfTmp> queryList(Pageable pageable, OrgInfTmp orgInfTmp);

	/**
	 * 查询机构信息临时表中信息
	 * 
	 * @param orgInfTmp
	 * @return
	 */
	public OrgInfTmp query(OrgInfTmp OrgInfTmp);
	
	/**
	 * 查询机构信息临时表中信息
	 * 
	 * @param OrgInfTmp
	 * @return
	 */
	public int queryOrgInfo(OrgInfTmp OrgInfTmp);

	/**
	 * 新增一条记录到机构信息临时表中
	 * 
	 * @param orgInfTmp
	 * @return
	 */

	public int insertOrgInfTmp(OrgInfTmp orgInfTmp);

	/**
	 * 更新机构表中一条记录
	 * 
	 * @param orgInfTmp
	 * @return
	 */
	public int updateOrgInfTmp(OrgInfTmp orgInfTmp);

}
