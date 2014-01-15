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
import com.synesoft.fisp.domain.model.RoleInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */

@Repository
public class RoleInfTmpRepositoryImpl implements RoleInfTmpRepository {

	@Override
	public Page<RoleInfTmp> queryList(Pageable pageable, RoleInfTmp roleInfTmp) {
		return pageH.getPage(Table.ROLEINFTMP, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, roleInfTmp, pageable);
	}

	@Override
	public RoleInfTmp query(RoleInfTmp roleInfTmp) {
		return queryDAO.executeForObject(Table.ROLEINFTMP + "."
				+ SQLMap.SELECT_BYKEY, roleInfTmp, RoleInfTmp.class);
	}
	
	@Override
	public int queryRoleInfo(RoleInfTmp roleInfTmp) {
		return queryDAO.executeForObject(Table.ROLEINFTMP + "."
				+ SQLMap.SELECT_COUNTS, roleInfTmp, Integer.class);
	}
	
	@Override
	public int insertRoleInfTmp(RoleInfTmp roleInfTmp) {
		return updateDAO.execute(Table.ROLEINFTMP + "." + SQLMap.INSERT, roleInfTmp);
	}

	@Override
	public int updateRoleInfTmp(RoleInfTmp roleInfTmp) {
		return updateDAO.execute(Table.ROLEINFTMP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, roleInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<RoleInfTmp> pageH;

}
