package com.synesoft.fisp.domain.repository.sm;

import java.util.ArrayList;
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
import com.synesoft.fisp.domain.model.UserRoleInf;
import com.synesoft.fisp.domain.model.UserRoleInfTmp;

/**
 * 
 * @author michelle.wang
 * 
 */

@Repository
public class UserRoleInfRepositoryImpl implements UserRoleInfRepository {

	@Override
	public Page<UserRoleInf> queryList(Pageable pageable, UserRoleInf userRoleInf) {
		return pageH.getPage(Table.USERROLEINFQUERY, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_USERROLE_LIST, userRoleInf, pageable);
	}

	@Override
	public UserRoleInf query(UserRoleInf userRoleInf) {
		return queryDAO.executeForObject(Table.USERROLEINF + "."
				+ SQLMap.SELECT_BYKEY, userRoleInf, UserRoleInf.class);
	}
	@Override
	public List<UserRoleInf> queryUserRoleIdList(UserRoleInf userRoleInf) {
		return queryDAO.executeForObjectList(Table.USERROLEINF + "."
				+ SQLMap.SELECT_LIST, userRoleInf);
	}
	@Override
	public List<UserRoleInf> queryNotInList(List<String> notInList, String userId) {
		List<String> arg1 = new ArrayList<String>();
		arg1.add(userId);
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("userId", arg1);
		map.put("notInList", notInList);
		return queryDAO.executeForObjectList(Table.USERROLEINF + "."
				+ SQLMap.SELECT_NOT_IN_LIST, map);
	}
	@Override
	public List<UserRoleInfTmp> queryRoleListMerge(UserRoleInfTmp userRoleInfTmp) {
		return queryDAO.executeForObjectList(Table.USERROLEINF + "."
				+ SQLMap.SELECT_MERGE_LIST, userRoleInfTmp);
	}
		 
	@Override
	public int insertUserRoleInf(UserRoleInf userRoleInf) {
		return updateDAO.execute(Table.USERROLEINF + "." + SQLMap.INSERT, userRoleInf);
	}

	@Override
	public int updateUserRoleInf(UserRoleInf userRoleInf) {
		return updateDAO.execute(Table.USERROLEINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, userRoleInf);
	}

	@Override
	public int deleteUserRoleInf(UserRoleInf userRoleInf) {
		return updateDAO.execute(Table.USERROLEINF + "." + SQLMap.DELETE_BYKEY,
				userRoleInf);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<UserRoleInf> pageH;

	@Override
	public int queryUserRoleInfCnt(String roleId) {
		return queryDAO.executeForObject(Table.USERROLEINF + "."
				+ SQLMap.SELECT_ROLE_CNTS, roleId, Integer.class);
	}
}
