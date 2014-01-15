package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.dp.model.LoanBalanceForm;
import com.synesoft.fisp.domain.model.DmLoanBalance;

public interface DmLoanBalanceService {

	
	public DmLoanBalance transQueryDmLoanBalance(DmLoanBalance loanBalance);
	
	public Page<DmLoanBalance> transQueryDmLoanBalanceInputList(Pageable pageable,DmLoanBalance loanBalance);
	
	public Page<DmLoanBalance> transQueryDmLoanBalanceAuthList(Pageable pageable,DmLoanBalance loanBalance);
	
	public Page<DmLoanBalance> transQueryDmLoanBalanceQueryList(Pageable pageable,DmLoanBalance loanBalance);
	
	public void transUpdate(LoanBalanceForm form);
	
	public void transDel(LoanBalanceForm form);
	
	public void transAuth(LoanBalanceForm form);
	
	public void transReject(LoanBalanceForm form);
	
	public void transCancel(LoanBalanceForm form);
	
	public void transAdd(LoanBalanceForm form);
	
}
