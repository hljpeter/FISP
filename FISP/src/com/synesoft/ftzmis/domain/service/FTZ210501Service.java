package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrTmp;

/**
 * @author hb_huang
 *
 * 2013-12-27 下午01:01:48
 *
 */
public interface FTZ210501Service {

	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable, FtzActMstr ftzActMstr);
	
	public Page<FtzActMstrTmp> queryFtzActMstrTmpPage(Pageable pageable, FtzActMstrTmp ftzActMstrTmp);
	
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr);
	
	public FtzActMstrTmp queryFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp);
	
	public List<FtzActMstr> queryFtzActMstrList(FtzActMstr ftzActMstr);
	
	public void insertFtzActMstr(FtzActMstrTmp ftzActMstrTmp);
	
	public void updateFtzActMstr(FtzActMstr ftzActMstr);
	
	public void deleteFtzActMstr(FtzActMstr ftzActMstr);
	
	public void authFtzActMstr(FtzActMstrTmp ftzActMstrTmp);
	
	public void refuseFtzActMstr(FtzActMstrTmp ftzActMstrTmp);
}
