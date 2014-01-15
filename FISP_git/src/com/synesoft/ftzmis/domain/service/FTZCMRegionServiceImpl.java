package com.synesoft.ftzmis.domain.service; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.fisp.domain.model.SysRegionInfo;
import com.synesoft.ftzmis.domain.repository.FTZCMRegQryRepository;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-27
 */
@Service
public class FTZCMRegionServiceImpl implements FTZCMRegionService {

	@Override
	public Page<SysRegionInfo> queryRegionPage(Pageable pageable,
			SysRegionInfo sysRegionInfo) {
		// TODO Auto-generated method stub
		return ftzcmRegQryRepository.queryFtzRegion(pageable,sysRegionInfo);
	}

	@Resource
	protected FTZCMRegQryRepository ftzcmRegQryRepository;
}
 