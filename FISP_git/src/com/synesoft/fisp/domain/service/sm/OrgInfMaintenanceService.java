package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.sm.model.OrgInfForm;
import com.synesoft.fisp.app.sm.model.OrgInfTmpForm;
import com.synesoft.fisp.domain.model.OrgInf;
import com.synesoft.fisp.domain.model.OrgInfTmp;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */
public interface OrgInfMaintenanceService {
	
	/**
	 * 机构信息维护查询功能
	 * @param pageable
	 * @param orgInf
	 * @return
	 */
	public Page<OrgInf> transQueryOrgInfList(Pageable pageable,
			OrgInf orgInf);
	
	/**
	 * 查询部分机构信息
	 * @param orgIdList
	 * @return
	 */
	public List<OrgInf> transQueryOrgInfList(String orgIdList);
	
	/**
	 * 查询机构信息
	 * @return
	 */
	public List<OrgInf> transQueryOrgInfList();
	/**
	 * 查询机构信息
	 * @return
	 */
	public List<OrgInf> transQueryOrgInfList(List<UserOrgInf> list);
	/**
	 * 查询机构信息
	 * @return
	 */
	public List<OrgInf> transQueryOrgInfListArr(List<String> list);
	/**
	 * 查询机构信息
	 * @return
	 */
	public List<OrgInf> transQueryOrgInfListTmp(List<UserOrgInfTmp> list);
	/**
	 * 查询机构信息表
	 * @param orgInf
	 * @return
	 */
	public OrgInf transQueryOrgInf(OrgInf orgInf);
	
	/**
	 * 查询机构信息临时表
	 * @param orgInfTmp
	 * @return
	 */
	public OrgInfTmp transQueryOrgInfTmp(OrgInfTmp orgInfTmp);
	
	/**
	 * 机构信息维护授权查询功能
	 * @param pageable
	 * @param orgInfTmp
	 * @return
	 */
	public Page<OrgInfTmp> transQueryOrgInfTmpList(Pageable pageable,
			OrgInfTmp orgInfTmp);
	
	/**
	 * 新增操作
	 * 1.检查主表和临时表中是否存在相同机构ID的记录
	 * 2.修改主表中处理状态字段
	 * 3.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transAdd(OrgInfTmpForm form);
	
	/**
	 * 修改/操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同机构ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transUpdate(OrgInfForm form);
	
	/**
	 * 删除操作
	 * 1.检查主表中该记录是否处于正常状态
	 * 2.检查临时表中是否已经存在相同机构ID的记录
	 * 3.修改主表中处理状态字段
	 * 4.临时表中新增一条记录
	 * @param form
	 * @return
	 */
	public void transDel(OrgInfForm form);
	

	/**
	 * 授权操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.根据操作类型分别处理
	 * 	3.1.新增操作，将记录信息插入到主表中
	 * 	3.2.修改操作，将更新主表中的记录信息
	 * 	3.3.删除操作，删除主表中该记录信息
	 * @param orgInfTmp
	 * @return
	 */
	public void transAuth(OrgInfTmp orgInfTmp);
	
	/**
	 * 拒绝操作
	 * 1.检查临时表中该记录的状态是否为待授权
	 * 2.更新临时表中记录状态
	 * 3.当维护操作类型为修改或删除时，更新主表的处理状态为02-正常状态
	  * @param orgInfTmp
	 * @return
	 */
	public void transRejct(OrgInfTmp orgInfTmp);

	/**
	 * 根据选择的机构IDList，查询机构信息，共两部分
	 * index = 0， 选择的Id，查询得到的机构信息List
	 * index = 1，未被选择的机构信息List
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList(List<String> selectedList);

	/**
	 * 根据选择的机构信息List，查询机构信息，共两部分
	 * 1) index = 0， 选择的Id，查询得到的机构信息List
	 * 2) index = 1，未被选择的机构信息List
	 * 使用transQueryAvailabledAndSelectedList(List<String>)的结果
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList2(List<UserOrgInf> selectedList);

	/**
	 * 根据选择的机构信息List，查询机构信息，共两部分
	 * 1) index = 0， 选择的Id，查询得到的机构信息List
	 * 2) index = 1，未被选择的机构信息List
	 * 使用transQueryAvailabledAndSelectedList(List<String>)的结果
	 * @param list
	 * @return
	 */
	public List<List<OrgInf>> transQueryAvailabledAndSelectedList3(List<UserOrgInfTmp> selectedList);
}
