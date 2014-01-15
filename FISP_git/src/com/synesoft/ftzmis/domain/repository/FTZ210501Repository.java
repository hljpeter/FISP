package com.synesoft.ftzmis.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzActMstrTmp;

/**
 * @author hb_huang
 *
 * 2013-12-27 下午01:00:34
 *
 */
public interface FTZ210501Repository {
	
	public Page<FtzActMstr> queryFtzActMstrPage(Pageable pageable,FtzActMstr ftzActMstr);
	
	public Page<FtzActMstrTmp> queryFtzActMstrTmpPage(Pageable pageable, FtzActMstrTmp ftzActMstrTmp);
	
	public FtzActMstr queryFtzActMstr(FtzActMstr ftzActMstr);
	
	public List<FtzActMstr> queryFtzActMstrList(FtzActMstr ftzActMstr);
	
	public FtzActMstrTmp queryFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp);
	
	public int insertFtzActMstr(FtzActMstr ftzActMstr);
	
	public int insertFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp);
	
	public int updateFtzActMstr(FtzActMstr ftzActMstr);
	
	public int deleteFtzActMstr(FtzActMstr ftzActMstr);
	
	public int deleteFtzActMstrTmp(FtzActMstrTmp ftzActMstrTmp);
	
	public int updateFtzActMstrStatus(FtzActMstr ftzActMstr);
}
