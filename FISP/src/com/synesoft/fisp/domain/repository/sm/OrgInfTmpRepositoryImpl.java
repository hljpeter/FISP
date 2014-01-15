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
import com.synesoft.fisp.domain.model.OrgInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */

@Repository
public class OrgInfTmpRepositoryImpl implements OrgInfTmpRepository {

	@Override
	public Page<OrgInfTmp> queryList(Pageable pageable, OrgInfTmp orgInfTmp) {
		return pageH.getPage(Table.ORGINFTMP, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, orgInfTmp, pageable);
	}

	@Override
	public OrgInfTmp query(OrgInfTmp orgInfTmp) {
		return queryDAO.executeForObject(Table.ORGINFTMP + "."
				+ SQLMap.SELECT_BYKEY, orgInfTmp, OrgInfTmp.class);
	}
	
	@Override
	public int queryOrgInfo(OrgInfTmp orgInfTmp) {
		return queryDAO.executeForObject(Table.ORGINFTMP + "."
				+ SQLMap.SELECT_COUNTS, orgInfTmp, Integer.class);
	}
	
	@Override
	public int insertOrgInfTmp(OrgInfTmp orgInfTmp) {
		return updateDAO.execute(Table.ORGINFTMP + "." + SQLMap.INSERT, orgInfTmp);
	}

	@Override
	public int updateOrgInfTmp(OrgInfTmp orgInfTmp) {
		return updateDAO.execute(Table.ORGINFTMP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, orgInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<OrgInfTmp> pageH;

}
