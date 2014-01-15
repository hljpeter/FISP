package com.synesoft.ftzmis.domain.repository; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.fisp.domain.model.SysRegionInfo;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;


/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-27
 */
@Repository
public class FTZCMRegQryRepositoryImpl implements FTZCMRegQryRepository{

	@Override
	public Page<SysRegionInfo> queryFtzRegion(Pageable pageable,
			SysRegionInfo sysRegionInfo) {
		return pageFtzReg.getPage(Table.SYS_REGION_INFO, SQLMap.SELECT_REGION_COUNTS,
				SQLMap.SELECT_LIST_REGION,sysRegionInfo, pageable);
	}
	
	@Resource
	private PageHandler<SysRegionInfo> pageFtzReg;

}
 