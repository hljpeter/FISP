package com.synesoft.ftzmis.domain.repository; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysDataDict;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public interface FTZCMMetaQryRepository {

	/**
	 * 交易性质查询
	 * @param pageable
	 * @param sysDataDict
	 * @return
	 */
	Page<SysDataDict> queryMetaRegion(Pageable pageable,SysDataDict sysDataDict);

}
 