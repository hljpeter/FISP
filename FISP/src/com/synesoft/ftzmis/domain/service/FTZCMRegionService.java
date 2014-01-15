package com.synesoft.ftzmis.domain.service; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysRegionInfo;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-27
 */
public interface FTZCMRegionService {

	/**
	 * 国内地区代码查询
	 * @param pageable
	 * @param sysRegionInfo
	 * @return
	 */
	Page<SysRegionInfo> queryRegionPage(Pageable pageable,SysRegionInfo sysRegionInfo);

}
 