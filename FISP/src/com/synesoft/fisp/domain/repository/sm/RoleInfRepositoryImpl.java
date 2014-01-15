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
import com.synesoft.fisp.app.common.utils.StringUtil;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.RoleInf;

/**
 * @author zhongHubo
 * @date 2013年7月10日 17:29:21
 * @version 1.0
 * @Description 角色操作RepositoryImpl
 * @System TIPS
 * @Company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class RoleInfRepositoryImpl implements RoleInfRepository {

	/**
	 * 根据机构Id查询该机构所拥有的所有角色
	 * @param orgInf	机构
	 * @return			角色集合List
	 */
	@Override
	public List<RoleInf> queryRolesByOrg(RoleInf roleInf) {
		List<RoleInf> list = null;
		list = queryDAO.executeForObjectList(Table.ROLEINF + "." + SQLMap.SELECT_LIST, roleInf);
		return list;
	}


	@Override
	public Page<RoleInf> queryList(Pageable pageable, RoleInf roleInf) {
		return pageH.getPage(Table.ROLEINF, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, roleInf, pageable);
	}


	@Override
	public RoleInf query(String roleId) {
		return queryDAO.executeForObject(Table.ROLEINF + "."
				+ SQLMap.SELECT_BYKEY, roleId, RoleInf.class);
	}


	@Override
	public int insertRoleInf(RoleInf roleInf) {
		return updateDAO.execute(Table.ROLEINF + "." + SQLMap.INSERT, roleInf);
	}


	@Override
	public int updateRoleInf(RoleInf roleInf) {
		roleInf.setRoleid(StringUtil.trim(roleInf.getRoleid()));
		return updateDAO.execute(Table.ROLEINF + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, roleInf);
	}


	@Override
	public int deleteRoleInf(RoleInf roleInf) {
		roleInf.setRoleid(StringUtil.trim(roleInf.getRoleid()));
		return updateDAO.execute(Table.ROLEINF + "." + SQLMap.DELETE_BYKEY,
				roleInf);
	}

	@Resource
	private UpdateDAO updateDAO;
	
	@Resource
	private QueryDAO queryDAO;
	
	@Resource
	private PageHandler<RoleInf> pageH;

	@Override
	public int queryRoleInfCnt(String orgId) {
		return queryDAO.executeForObject(Table.ROLEINF + "."
				+ SQLMap.SELECT_ORG_CNTS, orgId, Integer.class);
	}
}
