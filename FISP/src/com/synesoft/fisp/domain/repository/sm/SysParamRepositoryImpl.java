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
import com.synesoft.fisp.domain.model.SysParam;

@Repository
public class SysParamRepositoryImpl implements SysParamRepository {

	public Page<SysParam> querySysParamPage(Pageable pageable) {
		return pageH.getPage(Table.SYS_PARAM, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, null, pageable);
	}
	
	public SysParam querySysParam(SysParam sysParam){
		return queryDAO.executeForObject(Table.SYS_PARAM+"."+SQLMap.SELECT_BYKEY, sysParam, SysParam.class);
	}

	public int insertSysParam(SysParam sysParam) {
		return updateDAO.execute(Table.SYS_PARAM + "." + SQLMap.INSERT, sysParam);
	}

	public int updateSysParam(SysParam sysParam) {
		return updateDAO.execute(Table.SYS_PARAM + "." + SQLMap.UPDATE_BYKEY, sysParam);
	}

	public int delSysParam(SysParam sysParam){
		return updateDAO.execute(Table.SYS_PARAM + "." + SQLMap.DELETE_BYKEY, sysParam);
	}
	
	public List<SysParam> querySysParamList(){
		return queryDAO.executeForObjectList(Table.SYS_PARAM + "." + SQLMap.SELECT_LIST, null);
	}
	
	@Resource
	private QueryDAO queryDAO;

	@Resource
	private UpdateDAO updateDAO;

	@Resource
	private PageHandler<SysParam> pageH;

	@Override
	public Page<SysParam> querySysParamPage(Pageable pageable,
			SysParam sysParam) {
		return pageH.getPage(Table.SYS_PARAM, SQLMap.SELECT_COUNTS,SQLMap.SELECT_LIST, sysParam, pageable);
	}

	@Override
	public int add(SysParam sysParam) {
		return updateDAO.execute(Table.SYS_PARAM + "." + SQLMap.INSERT, sysParam);
	}

	@Override
	public int queryGroupCode(SysParam sysParam) {
		return queryDAO.executeForObject(Table.SYS_PARAM+"."+SQLMap.QUERY_SYS_PARAM_COUNT_CHECK, sysParam, Integer.class);
	}

}
