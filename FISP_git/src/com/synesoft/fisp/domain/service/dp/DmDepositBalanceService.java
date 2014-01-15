package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.dp.model.DepositBalanceForm;
import com.synesoft.fisp.domain.model.DmDepositBalance;

public interface DmDepositBalanceService {

	
	public DmDepositBalance transQueryDmDepositBalance(DmDepositBalance depositBalance);
	
	public Page<DmDepositBalance> transQueryDmDepositBalanceInputList(Pageable pageable,DmDepositBalance depositBalance);
	
	public Page<DmDepositBalance> transQueryDmDepositBalanceAuthList(Pageable pageable,DmDepositBalance depositBalance);
	
	public Page<DmDepositBalance> transQueryDmDepositBalanceQueryList(Pageable pageable,DmDepositBalance depositBalance);
	
	public void transUpdate(DepositBalanceForm form);
	
	public void transDel(DepositBalanceForm form);
	
	public void transAuth(DepositBalanceForm form);
	
	public void transReject(DepositBalanceForm form);
	
	public void transCancel(DepositBalanceForm form);
	
	public void transAdd(DepositBalanceForm form);
	
}
