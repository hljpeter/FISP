package com.synesoft.fisp.domain.repository.sm;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.OrgRoleInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */

@Repository
public class OrgRoleInfTmpRepositoryImpl implements OrgRoleInfTmpRepository {

	@Override
	public Page<OrgRoleInfTmp> queryList(Pageable pageable, OrgRoleInfTmp orgRoleInfTmp) {
		return pageH.getPage(Table.ORGROLEINFTMP, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, orgRoleInfTmp, pageable);
	}

	@Override
	public OrgRoleInfTmp query(OrgRoleInfTmp orgRoleInfTmp) {
		return queryDAO.executeForObject(Table.ORGROLEINFTMP + "."
				+ SQLMap.SELECT_BYKEY, orgRoleInfTmp, OrgRoleInfTmp.class);
	}
	
	@Override
	public int queryOrgRoleInfo(OrgRoleInfTmp orgRoleInfTmp) {
		return queryDAO.executeForObject(Table.ORGROLEINFTMP + "."
				+ SQLMap.SELECT_COUNTS, orgRoleInfTmp, Integer.class);
	}
	
	@Override
	public int insertOrgRoleInfTmp(OrgRoleInfTmp orgRoleInfTmp) {
		return updateDAO.execute(Table.ORGROLEINFTMP + "." + SQLMap.INSERT, orgRoleInfTmp);
	}

	@Override
	public int updateOrgRoleInfTmp(OrgRoleInfTmp orgRoleInfTmp) {
		return updateDAO.execute(Table.ORGROLEINFTMP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, orgRoleInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<OrgRoleInfTmp> pageH;

}
