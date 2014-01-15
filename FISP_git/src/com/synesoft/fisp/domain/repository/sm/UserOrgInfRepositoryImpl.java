package com.synesoft.fisp.domain.repository.sm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.UserOrgInf;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;

/**
 * @author zhongHubo
 * @date 2013年7月25日 17:21:28
 * @version 1.0
 * @Description 用户机构RepositoryImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class UserOrgInfRepositoryImpl implements UserOrgInfRepository {

	/**
	 * 查询用户机构列表
	 * @param userOrgInf
	 * @return	List<UserOrgInf>
	 */
	@Override
	public List<UserOrgInf> queryUserOrgInfList(UserOrgInf userOrgInf) {
		return queryDAO.executeForObjectList(Table.USERORGINF + "." + SQLMap.SELECT_LIST, userOrgInf);
	}

	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;

	@Override
	public UserOrgInf queryUserOrgInf(UserOrgInf userOrgInf) {
		return queryDAO.executeForObject(Table.USERORGINF + "."
				+ SQLMap.SELECT_BYKEY, userOrgInf, UserOrgInf.class);
	}

	@Override
	public List<UserOrgInf> queryNotInList(List<String> notInList, String userId) {
		List<String> arg1 = new ArrayList<String>();
		arg1.add(userId);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("userId", arg1);
		map.put("notInList", notInList);
		return queryDAO.executeForObjectList(Table.USERORGINF + "."
				+ SQLMap.SELECT_NOT_IN_LIST, map);
	}

	@Override
	public List<UserOrgInfTmp> transQueryUserOrgInfMerge(UserOrgInfTmp userOrgInfTmp) {
		return queryDAO.executeForObjectList(Table.USERORGINF + "."
				+ SQLMap.SELECT_MERGE_LIST, userOrgInfTmp);
	}
	
	@Override
	public int insertUserOrgInf(UserOrgInf userOrgInf) {
		return updateDAO.execute(Table.USERORGINF + "." + SQLMap.INSERT, userOrgInf);
	}
	
	@Override
	public int updateUserOrgInf(UserOrgInf userOrgInf) {
		return updateDAO.execute(Table.USERORGINF + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, userOrgInf);
	}

	@Override
	public int deleteUserOrgInf(UserOrgInf userOrgInf) {
		return updateDAO.execute(Table.USERORGINF + "." + SQLMap.DELETE_BYKEY,
				userOrgInf);
	}

	@Override
	public int queryUserOrgInfCnt(String orgId) {
		return queryDAO.executeForObject(Table.USERORGINF + "."
				+ SQLMap.SELECT_ORG_CNTS, orgId, Integer.class);
	}

}
