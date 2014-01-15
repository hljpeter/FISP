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
import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * SYS_USER_ROLE_TMP(角色与机构无关)
 * @author yyw
 * @date 2013-12-20
 */
@Repository
public class SysUserRoleTmpRepositoryImpl implements SysUserRoleTmpRepository {

	@Override
	public SysUserRoleTmp query(SysUserRoleTmp sysUserRoleTmp) {
		return queryDAO.executeForObject(Table.SYS_USER_ROLE_TMP + "."
				+ SQLMap.SELECT_BYKEY, sysUserRoleTmp, SysUserRoleTmp.class);
	}

	@Override
	public int queryCount(SysUserRoleTmp sysUserRoleTmp) {
		return queryDAO.executeForObject(Table.SYS_USER_ROLE_TMP + "."
				+ SQLMap.SELECT_COUNTS, sysUserRoleTmp, Integer.class);
	}
	
	@Override
	public List<SysUserRoleTmp> querylist(SysUserRoleTmp sysUserRoleTmp) {
		return queryDAO.executeForObjectList(Table.SYS_USER_ROLE_TMP + "." 
				+ SQLMap.SELECT_LIST, sysUserRoleTmp);
	}
	
	@Override
	public int insert(SysUserRoleTmp sysUserRoleTmp) {
		return updateDAO.execute(Table.SYS_USER_ROLE_TMP + "." + SQLMap.INSERT, sysUserRoleTmp);
	}

	@Override
	public int update(SysUserRoleTmp sysUserRoleTmp) {
		return updateDAO.execute(Table.SYS_USER_ROLE_TMP + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, sysUserRoleTmp);
	}

	/** ======================== yyw updated ======================== */

	@Override
	public Page<UserRoleInfTmp> queryList(Pageable pageable, UserRoleInfTmp userRoleInfTmp) {
		return pageH.getPage(Table.USERROLEINFTMPQUERY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, userRoleInfTmp, pageable);
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
