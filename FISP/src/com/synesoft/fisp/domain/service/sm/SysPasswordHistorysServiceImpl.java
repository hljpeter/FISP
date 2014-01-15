package com.synesoft.fisp.domain.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.synesoft.fisp.domain.model.SysPasswordHistorys;
import com.synesoft.fisp.domain.repository.sm.SysPasswordHistorysRepository;

@Service
public class SysPasswordHistorysServiceImpl implements SysPasswordHistorysService {
	
	public SysPasswordHistorys querySysPasswordHistorys(SysPasswordHistorys SysPasswordHistorys){
		return sysPasswordHistorysRepository.querySysPasswordHistorys(SysPasswordHistorys);
	}
	
	@Transactional
	public int insertSysPasswordHistorys(SysPasswordHistorys SysPasswordHistorys){
		return sysPasswordHistorysRepository.insertSysPasswordHistorys(SysPasswordHistorys);
	}

	public List<SysPasswordHistorys> querySysPasswordHistorysList(SysPasswordHistorys sysPasswordHistorys){
		return sysPasswordHistorysRepository.querySysPasswordHistorysList(sysPasswordHistorys);
	}
	
	@Autowired
	protected SysPasswordHistorysRepository sysPasswordHistorysRepository;

}
