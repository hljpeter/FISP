package com.synesoft.ftzmis.domain.service; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.SysDataDict;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
public interface FTZCMMetaService {

	/**
	 * 交易性质查询
	 * @param pageable
	 * @param sysDataDict
	 * @return
	 */
	Page<SysDataDict> queryMetaPage(Pageable pageable, SysDataDict sysDataDict);

}
 