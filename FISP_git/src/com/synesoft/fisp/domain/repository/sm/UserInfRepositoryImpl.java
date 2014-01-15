package com.synesoft.fisp.domain.repository.sm;


import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.UserInf;

/**
 * @author zhongHubo
 * @date 2013年7月8日 15:54:44
 * @version 1.0
 * @Description 用户操作RepositoryImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class UserInfRepositoryImpl implements UserInfRepository {

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<UserInf> pageH;

	
	/**
	 * 根据用户Id查询用户信息
	 * @param userid	用户Id
	 * @return			UserInf
	 */
	@Override
	public UserInf queryUser(String userid) {
		UserInf user = null;
		
		user = queryDAO.executeForObject(Table.USERINF + "." + SQLMap.SELECT_BYKEY, userid, UserInf.class);
		
		return user;
	}

	/**
	 * 修改用户信息
	 * @param userInf	待修改的用户信息
	 * @return			
	 */
	@Override
	public int updateUser(UserInf userInf) {
		userInf.setUserid(StringUtil.trim(userInf.getUserid()));
		int res = updateDAO.execute(Table.USERINF + "." + SQLMap.UPDATE_BYKEY_SELECTIVE, userInf);
		return res;
	}

	@Override
	public Page<UserInf> queryList(Pageable pageable, UserInf userInf) {
		return pageH.getPage(Table.USERINF, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, userInf, pageable);
	}

	@Override
	public int insertUserInf(UserInf userInf) {
		return updateDAO.execute(Table.USERINF + "." + SQLMap.INSERT, userInf);
	}

	@Override
	public int deleteUserInf(UserInf userInf) {
		return updateDAO.execute(Table.USERINF + "." + SQLMap.DELETE_BYKEY,
				userInf);
	}

	@Override
	public int queryUserInfCnt(String orgId) {
		return queryDAO.executeForObject(Table.USERINF + "."
				+ SQLMap.SELECT_ORG_CNTS, orgId, Integer.class);
	}
	
	
}
