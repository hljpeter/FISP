package com.synesoft.ftzmis.domain.service; 

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synesoft.ftzmis.domain.model.SysNationInfo;
import com.synesoft.ftzmis.domain.repository.FTZCMNationQryRepository;

/** 
 *
 * description:
 * @author wjl 
 * @version 2013-12-29
 */
@Service
public class FTZCMNationServiceImpl implements FTZCMNationService{

	
	
	@Resource
	protected FTZCMNationQryRepository ftzcmnationqryrepository;

	@Override
	public Page<SysNationInfo> queryMetaPage(Pageable pageable,
			SysNationInfo sysNationInfo) {
		return ftzcmnationqryrepository.queryFtzNattion(pageable,sysNationInfo);
	}
}
 