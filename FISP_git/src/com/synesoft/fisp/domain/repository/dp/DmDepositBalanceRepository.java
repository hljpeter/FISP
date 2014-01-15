package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmDepositBalance;

public interface DmDepositBalanceRepository {
	
	public Page<DmDepositBalance> queryInputList(Pageable pageable,DmDepositBalance depositBalance);
	
	public Page<DmDepositBalance> queryAuthList(Pageable pageable,DmDepositBalance depositBalance);
	
	public Page<DmDepositBalance> queryList(Pageable pageable,DmDepositBalance depositBalance);

	public DmDepositBalance query(DmDepositBalance depositBalance);
	
	public List<DmDepositBalance> queryList(DmDepositBalance depositBalance);
	
	public int insert(DmDepositBalance depositBalance);
	
	public int update(DmDepositBalance depositBalance);
	
	public int delete(DmDepositBalance depositBalance);
}
