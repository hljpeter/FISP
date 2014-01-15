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
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */

@Repository
public class UserRoleInfTmpRepositoryImpl implements UserRoleInfTmpRepository {

	@Override
	public Page<UserRoleInfTmp> queryList(Pageable pageable, UserRoleInfTmp userRoleInfTmp) {
		return pageH.getPage(Table.USERROLEINFTMPQUERY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, userRoleInfTmp, pageable);
	}

	@Override
	public UserRoleInfTmp query(UserRoleInfTmp userRoleInfTmp) {
		return queryDAO.executeForObject(Table.USERROLEINFTMP + "."
				+ SQLMap.SELECT_BYKEY, userRoleInfTmp, UserRoleInfTmp.class);
	}
	@Override
	public List<UserRoleInfTmp> querylist(UserRoleInfTmp userRoleInfTmp) {
		return queryDAO.executeForObjectList(Table.USERROLEINFTMP + "." + SQLMap.SELECT_LIST, userRoleInfTmp);
	}
	@Override
	public int queryUserRoleInfo(UserRoleInfTmp userRoleInfTmp) {
		return queryDAO.executeForObject(Table.USERROLEINFTMP + "."
				+ SQLMap.SELECT_COUNTS, userRoleInfTmp, Integer.class);
	}
	
	@Override
	public int insertUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp) {
		return updateDAO.execute(Table.USERROLEINFTMP + "." + SQLMap.INSERT, userRoleInfTmp);
	}

	@Override
	public int updateUserRoleInfTmp(UserRoleInfTmp userRoleInfTmp) {
		return updateDAO.execute(Table.USERROLEINFTMP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, userRoleInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<UserRoleInfTmp> pageH;

}
