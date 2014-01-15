package com.synesoft.ftzmis.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzActMstr;

public interface FTZCMAccountQryService {
	
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable, FtzActMstr ftzActMstr);
	
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr);
	
}
