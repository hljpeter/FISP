package com.synesoft.fisp.domain.repository.sm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.OrgInf;

/**
 * 
 * @author michelle.wang
 * 
 */

public interface OrgInfRepository {

	/**
	 * 机构信息维护界面查询
	 * 
	 * @param pageable
	 * @param orgInf
	 * @return
	 */
	public Page<OrgInf> queryList(Pageable pageable, OrgInf orgInf);

	/**
	 * 查询机构信息表所有机构信息
	 * @return
	 */
	public List<OrgInf> queryOrgInfList(String orgIdList);
	public List<OrgInf> queryOrgInfList(OrgInf orgInf);
	/**
	 * 查询机构信息表中信息
	 * 
	 * @param orgId
	 * @return
	 */
	public OrgInf query(String orgId);

	/**
	 * 新增一条记录到机构信息表中
	 * 
	 * @param orgInf
	 * @return
	 */

	public int insertOrgInf(OrgInf orgInf);

	/**
	 * 更新机构表中一条记录
	 * 
	 * @param orgInf
	 * @return
	 */
	public int updateOrgInf(OrgInf orgInf);

	/**
	 * 删除机构信息表中一条记录
	 * 
	 * @param orgInf
	 * @return
	 */
	public int deleteOrgInf(OrgInf orgInf);
	
	/**
	 * 根据上级机构ID查询机构信息
	 * @param suprOrgid
	 * @return
	 */
	public int queryOrgInfBySuprOrgId(String suprOrgid);

	/**
	 * 查询机构Id不在参数列表中的机构信息
	 * @desc yyw updated on 2013-12-20
	 * @param orgIdList
	 * @return
	 */
	public List<OrgInf> queryNotInOrgIdList(ArrayList<String> orgIdList);

	/**
	 * 查询机构Id在参数列表中的机构信息
	 * @desc yyw updated on 2013-12-20
	 * @param orgIdList
	 * @return
	 */
	public List<OrgInf> queryInOrgIdList(ArrayList<String> orgIdList);
}
