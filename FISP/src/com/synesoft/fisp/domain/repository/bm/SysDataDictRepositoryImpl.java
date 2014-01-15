package com.synesoft.fisp.domain.repository.bm;

import javax.annotation.Resource;






import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.bm.model.Bm_Data_QryForm;
import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysDataDict;
@Repository
public class SysDataDictRepositoryImpl implements SysDataDictRepository {

	@Resource
	private PageHandler<SysDataDict> pageH;
	@Resource
	private UpdateDAO updateDAO;
	@Resource
	private QueryDAO queryDAO;
	
	@Override
	public Page<SysDataDict> queryInputList(Pageable pageable,
			Bm_Data_QryForm bmDataForm) {		
		return pageH.getPage(Table.SYS_DATA_DICT, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, bmDataForm, pageable);
	}
	@Override
	public int deleteSysDataDict(SysDataDict sysDataDict) {	
		return updateDAO.execute(Table.SYS_DATA_DICT + "." + SQLMap.DELETE_BYKEY,
				sysDataDict);
	}
	@Override
	public SysDataDict query(SysDataDict sysDataDict) {		
		return queryDAO.executeForObject(Table.SYS_DATA_DICT + "."
				+ SQLMap.SELECT_BYKEY, sysDataDict, SysDataDict.class);
	}
	@Override
	public int updateSysDataDict(SysDataDict sysDataDict) {
		return updateDAO.execute(Table.SYS_DATA_DICT + "."
				+ SQLMap.UPDATE_BYKEY_SELECTIVE, sysDataDict);
	}
	@Override
	public int insertSysDataDict(SysDataDict sysDataDict) {
		return updateDAO.execute(Table.SYS_DATA_DICT + "." + SQLMap.INSERT,
				sysDataDict);
	}

}
