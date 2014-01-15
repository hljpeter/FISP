package com.synesoft.fisp.domain.repository.sm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.OrgInf;

/**
 * 
 * @author michelle.wang
 * 
 */

@Repository
public class OrgInfRepositoryImpl implements OrgInfRepository {

	@Override
	public Page<OrgInf> queryList(Pageable pageable, OrgInf orgInf) {
		return pageH.getPage(Table.ORGINF, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, orgInf, pageable);
	}
	@Override
	public List<OrgInf> queryOrgInfList(String orgIdList){
		List<OrgInf> list = null;
		list = queryDAO.executeForObjectList(Table.ORGINF + "." + SQLMap.SELECT_ORGID_LIST, orgIdList);
		return list;
	}
	@Override
	public List<OrgInf> queryOrgInfList(OrgInf orgInf){
		List<OrgInf> list = null;
		list = queryDAO.executeForObjectList(Table.ORGINF + "." + SQLMap.SELECT_LIST, orgInf);
		return list;
	}
	@Override
	public OrgInf query(String orgId) {
		return queryDAO.executeForObject(Table.ORGINF + "."
				+ SQLMap.SELECT_BYKEY, orgId, OrgInf.class);
	}

	@Override
	public int insertOrgInf(OrgInf orgInf) {
		return updateDAO.execute(Table.ORGINF + "." + SQLMap.INSERT, orgInf);
	}

	@Override
	public int updateOrgInf(OrgInf orgInf) {
		orgInf.setOrgid(StringUtil.trim(orgInf.getOrgid()));
		return updateDAO.execute(Table.ORGINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, orgInf);
	}

	@Override
	public int deleteOrgInf(OrgInf orgInf) {
		orgInf.setOrgid(StringUtil.trim(orgInf.getOrgid()));
		return updateDAO.execute(Table.ORGINF + "." + SQLMap.DELETE_BYKEY,
				orgInf);
	}

	/**
	 * 查询机构Id不在参数列表中的机构信息
	 * @desc yyw updated on 2013-12-20
	 * @param orgIdList
	 * @return
	 */
	public List<OrgInf> queryNotInOrgIdList(ArrayList<String> orgIdList) {
		return queryDAO.executeForObjectList(Table.ORGINF + "." + SQLMap.SELECT_NOT_IN_LIST, orgIdList);
	}

	/**
	 * 查询机构Id在参数列表中的机构信息
	 * @desc yyw updated on 2013-12-20
	 * @param orgIdList
	 * @return
	 */
	public List<OrgInf> queryInOrgIdList(ArrayList<String> orgIdList) {
		return queryDAO.executeForObjectList(Table.ORGINF + "." + SQLMap.SELECT_IN_LIST, orgIdList);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<OrgInf> pageH;

	@Override
	public int queryOrgInfBySuprOrgId(String suprOrgid) {
		return queryDAO.executeForObject(Table.ORGINF + "."
				+ SQLMap.SELECT_ORG_CNTS, suprOrgid, Integer.class);
	}
}
