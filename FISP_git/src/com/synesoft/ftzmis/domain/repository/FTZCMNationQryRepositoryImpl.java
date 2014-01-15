package com.synesoft.ftzmis.domain.repository; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.synesoft.fisp.domain.component.PageHandler;
import com.synesoft.ftzmis.app.common.constants.SQLMap;
import com.synesoft.ftzmis.app.common.constants.Table;
import com.synesoft.ftzmis.domain.model.SysNationInfo;

/** 
 *国别代码查询
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
@Repository
public class FTZCMNationQryRepositoryImpl implements  FTZCMNationQryRepository{

	
	@Resource
	private PageHandler<SysNationInfo> pageFtzNat;

	@Override
	public Page<SysNationInfo> queryFtzNattion(Pageable pageable,
			SysNationInfo sysNationInfo) {
	
		return pageFtzNat.getPage(Table.SYS_NATION_INFO, SQLMap.SELECT_NATION_COUNTS,
				SQLMap.SELECT_NATION_LIST,sysNationInfo, pageable);
	}
}
 