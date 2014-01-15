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
import com.synesoft.fisp.domain.model.UserInfTmp;

/**
 * 
 * @author michelle.wang
 *
 */

@Repository
public class UserInfTmpRepositoryImpl implements UserInfTmpRepository {

	@Override
	public Page<UserInfTmp> queryList(Pageable pageable, UserInfTmp userInfTmp) {
		return pageH.getPage(Table.USERINFTMP, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, userInfTmp, pageable);
	}

	@Override
	public UserInfTmp query(UserInfTmp userInfTmp) {
		return queryDAO.executeForObject(Table.USERINFTMP + "."
				+ SQLMap.SELECT_BYKEY, userInfTmp, UserInfTmp.class);
	}
	
	@Override
	public int queryUserInfo(UserInfTmp userInfTmp) {
		return queryDAO.executeForObject(Table.USERINFTMP + "."
				+ SQLMap.SELECT_COUNTS, userInfTmp, Integer.class);
	}
	
	@Override
	public int insertUserInfTmp(UserInfTmp userInfTmp) {
		return updateDAO.execute(Table.USERINFTMP + "." + SQLMap.INSERT, userInfTmp);
	}

	@Override
	public int updateUserInfTmp(UserInfTmp userInfTmp) {
		return updateDAO.execute(Table.USERINFTMP + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, userInfTmp);
	}

	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<UserInfTmp> pageH;

}
