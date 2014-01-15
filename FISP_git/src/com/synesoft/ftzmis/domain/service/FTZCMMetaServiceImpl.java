package com.synesoft.ftzmis.domain.service; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.SysDataDict;
import com.synesoft.ftzmis.domain.repository.FTZCMMetaQryRepository;


/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
@Service
public  class FTZCMMetaServiceImpl implements FTZCMMetaService {

	@Override
	public Page<SysDataDict> queryMetaPage(Pageable pageable,
			SysDataDict sysDataDict) {
		return ftzcmmetaqryrepository.queryMetaRegion(pageable,sysDataDict);
	}
	@Resource
	protected FTZCMMetaQryRepository ftzcmmetaqryrepository;
}
 