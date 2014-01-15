package com.synesoft.fisp.domain.repository.sm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysUserRole;
import com.synesoft.fisp.domain.model.SysUserRoleTmp;
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * SYS_USER_ROLE(角色与机构无关)
 * @author yyw
 * @date 2013-12-20
 */
@Repository
public class SysUserRoleRepositoryImpl implements SysUserRoleRepository {

	@Override
	public SysUserRole query(SysUserRole sysUserRole) {
		return queryDAO.executeForObject(Table.SYS_USER_ROLE + "."
				+ SQLMap.SELECT_BYKEY, sysUserRole, SysUserRole.class);
	}
	
	@Override
	public List<SysUserRole> querylist(SysUserRole sysUserRole) {
		return queryDAO.executeForObjectList(Table.SYS_USER_ROLE + "." 
				+ SQLMap.SELECT_LIST, sysUserRole);
	}

	@Override
	public List<SysUserRole> queryNotInlist(String[] list, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userRoleList", list);
		return queryDAO.executeForObjectList(Table.SYS_USER_ROLE + "." 
				+ SQLMap.SELECT_NOT_IN_LIST, map);
	}

	@Override
	public List<SysUserRoleTmp> queryRoleListMerge(SysUserRoleTmp sysUserRoleTmp) {
		return queryDAO.executeForObjectList(Table.SYS_USER_ROLE + "." 
				+ SQLMap.SELECT_MERGE_LIST, sysUserRoleTmp);
	}
	
	@Override
	public int insert(SysUserRole sysUserRole) {
		return updateDAO.execute(Table.SYS_USER_ROLE + "." + SQLMap.INSERT, sysUserRole);
	}

	@Override
	public int update(SysUserRole sysUserRole) {
		return updateDAO.execute(Table.SYS_USER_ROLE + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, sysUserRole);
	}

	@Override
	public int delete(SysUserRole sysUserRole) {
		return updateDAO.execute(Table.SYS_USER_ROLE + "." + SQLMap.DELETE_BYKEY, sysUserRole);
	}

	/** ======================== yyw updated ======================== */

	@Override
	public Page<UserRoleInfTmp> queryList(Pageable pageable, UserRoleInfTmp userRoleInfTmp) {
		return pageH.getPage(Table.USERROLEINFTMPQUERY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, userRoleInfTmp, pageable);
	}

	@Override
	public int queryUserRoleInfo(UserRoleInfTmp userRoleInfTmp) {
		return queryDAO.executeForObject(Table.USERROLEINFTMP + "."
				+ SQLMap.SELECT_COUNTS, userRoleInfTmp, Integer.class);
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
