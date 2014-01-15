package com.synesoft.ftzmis.domain.repository; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
@Repository
public class FTZCMMetaQryRepositoryImpl implements FTZCMMetaQryRepository{

	@Override
	public Page<SysDataDict> queryMetaRegion(Pageable pageable,
			SysDataDict sysDataDict) {
		return pageFtzMeta.getPage(Table.SYS_DATA_DICT, SQLMap.SELECT_META_COUNTS,
				SQLMap.SELECT_META_LIST,sysDataDict, pageable);
	}
	@Resource
	private PageHandler<SysDataDict> pageFtzMeta;
}
 