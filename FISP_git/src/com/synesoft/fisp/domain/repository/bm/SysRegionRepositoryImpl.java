package com.synesoft.fisp.domain.repository.bm;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.app.common.constants.SQLMap;
import com.synesoft.fisp.app.common.constants.Table;
import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysRegionInfo;
@Repository
public class SysRegionRepositoryImpl implements SysRegionRepository {

	@Resource
	private PageHandler<SysRegionInfo> pageH;
	@Override
	public Page<SysRegionInfo> queryInputList(Pageable pageable,
			SysRegionInfo sysRegionInfo) {		
		return pageH.getPage(Table.SYS_REGION_INFO, SQLMap.SELECT_COUNTS,
				SQLMap.SELECT_LIST, sysRegionInfo, pageable);
	}

}
