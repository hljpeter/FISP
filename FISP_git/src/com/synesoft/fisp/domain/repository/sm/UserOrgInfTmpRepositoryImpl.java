package com.synesoft.fisp.domain.repository.sm;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.UserOrgInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */
@Repository
public class UserOrgInfTmpRepositoryImpl implements UserOrgInfTmpRepository {

	/**
	 * 查询用户机构列表
	 * @param userOrgInfTmp
	 * @return	List<UserOrgInfTmp>
	 */
	@Override
	public List<UserOrgInfTmp> queryUserOrgInfTmpList(UserOrgInfTmp userOrgInfTmp) {
		return queryDAO.executeForObjectList(Table.USERORGINFTMP + "." + SQLMap.SELECT_LIST, userOrgInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;
	@Resource
	private UpdateDAO updateDAO;

	@Override
	public int queryUserOrgInfTmpCnt(UserOrgInfTmp userOrgInfTmp) {
		return queryDAO.executeForObject(Table.USERORGINFTMP + "."
				+ SQLMap.SELECT_COUNTS, userOrgInfTmp, Integer.class);
	}
	@Override
	public UserOrgInfTmp queryUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp) {
		return queryDAO.executeForObject(Table.USERORGINFTMP + "."
				+ SQLMap.SELECT_BYKEY, userOrgInfTmp,UserOrgInfTmp.class);
	}
	@Override
	public int insertUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp) {
		return updateDAO.execute(Table.USERORGINFTMP + "." + SQLMap.INSERT,
				userOrgInfTmp);
	}

	@Override
	public int deleteUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp) {
		return updateDAO.execute(Table.USERORGINFTMP + "." + SQLMap.DELETE_BYKEY,
				userOrgInfTmp);
	}

	@Override
	public int updateUserOrgInfTmp(UserOrgInfTmp userOrgInfTmp) {
		return updateDAO.execute(Table.USERORGINFTMP + "." + SQLMap.UPDATE_BYKEY_SELECTIVE,
				userOrgInfTmp);
	}

}
