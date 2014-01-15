package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.OrgRoleInf;

/**
 * 
 * @author michelle.wang
 * 
 */

@Repository
public class OrgRoleInfRepositoryImpl implements OrgRoleInfRepository {

	@Override
	public Page<OrgRoleInf> queryList(Pageable pageable, OrgRoleInf orgRoleInf) {
		return pageH.getPage(Table.ORGROLEINF, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, orgRoleInf, pageable);
	}

	@Override
	public OrgRoleInf query(OrgRoleInf orgRoleInf) {
		return queryDAO.executeForObject(Table.ORGROLEINF + "."
				+ SQLMap.SELECT_BYKEY, orgRoleInf, OrgRoleInf.class);
	}
	@Override
	public List<OrgRoleInf> queryOrgRoleIdList(OrgRoleInf orgRoleInf) {
		return queryDAO.executeForObjectList(Table.ORGROLEINF + "."
				+ SQLMap.SELECT_LIST, orgRoleInf);
	}

	@Override
	public int insertOrgRoleInf(OrgRoleInf orgRoleInf) {
		return updateDAO.execute(Table.ORGROLEINF + "." + SQLMap.INSERT, orgRoleInf);
	}

	@Override
	public int updateOrgRoleInf(OrgRoleInf orgRoleInf) {
		return updateDAO.execute(Table.ORGROLEINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, orgRoleInf);
	}

	@Override
	public int deleteOrgRoleInf(OrgRoleInf orgRoleInf) {
		return updateDAO.execute(Table.ORGROLEINF + "." + SQLMap.DELETE_BYKEY,
				orgRoleInf);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<OrgRoleInf> pageH;

	@Override
	public int queryOrgRoleInfCnt(OrgRoleInf orgRoleInf) {
		return queryDAO.executeForObject(Table.ORGROLEINF + "."
				+ SQLMap.SELECT_COUNTS, orgRoleInf, Integer.class);
	}
}
