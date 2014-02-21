/**
 * 
 */
package com.synesoft.fisp.domain.repository;

import java.util.List;

import javax.annotation.Resource;

import jp.terasoluna.fw.dao.QueryDAO;

import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.model.SysControlDept;

/**
 * @author Peter
 * @date 2014-2-18 下午3:39:17
 * @version 1.0
 * @description 
 * @system FTZMIS
 * @company 上海恩梯梯数据晋恒软件有限公司
 */
@Repository
public class SysControlDeptRepositoryImpl implements SysControlDeptRepository {
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysControlDeptRepository#queryListByPage(com.synesoft.fisp.domain.model.SysControlDept)
	 */
	@Override
	public List<SysControlDept> queryListByPageAll(SysControlDept sysControlDept) {
		return queryDAO.executeForObjectList(Table.SYS_CONTROL_DEPT + "." + SQLMap.SELECT_LIST_PAGE_ALL, sysControlDept);
	}

	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysControlDeptRepository#queryListByPageCheck(com.synesoft.fisp.domain.model.SysControlDept)
	 */
	@Override
	public List<SysControlDept> queryListByPageCheck(
			SysControlDept sysControlDept) {
		return queryDAO.executeForObjectList(Table.SYS_CONTROL_DEPT + "." + SQLMap.SELECT_LIST_PAGE_CHECK, sysControlDept);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysControlDeptRepository#queryListForPage()
	 */
	@Override
	public List<SysControlDept> queryListForPage() {
		return queryDAO.executeForObjectList(Table.SYS_CONTROL_DEPT + "." + SQLMap.SELECT_LIST_FOR_PAGE, null);
	}
	
	/* (non-Javadoc)
	 * @see com.synesoft.fisp.domain.repository.SysControlDeptRepository#querySysControlDept(com.synesoft.fisp.domain.model.SysControlDept)
	 */
	@Override
	public SysControlDept querySysControlDept(SysControlDept sysControlDept) {
		return queryDAO.executeForObject(Table.SYS_CONTROL_DEPT + "." + SQLMap.SELECT_BYKEY, sysControlDept, SysControlDept.class);
	}
	
	@Resource
	private QueryDAO queryDAO;

}
