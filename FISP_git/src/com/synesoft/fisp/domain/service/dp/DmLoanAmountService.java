package com.synesoft.fisp.domain.service.dp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.app.dp.model.LoanAmountForm;
import com.synesoft.fisp.domain.model.DmLoanAmount;

public interface DmLoanAmountService {

	
	public DmLoanAmount transQueryDmLoanAmount(DmLoanAmount loanAmount);
	
	public Page<DmLoanAmount> transQueryDmLoanAmountInputList(Pageable pageable,DmLoanAmount loanAmount);
	
	public Page<DmLoanAmount> transQueryDmLoanAmountAuthList(Pageable pageable,DmLoanAmount loanAmount);
	
	public Page<DmLoanAmount> transQueryDmLoanAmountQueryList(Pageable pageable,DmLoanAmount loanAmount);
	
	public void transUpdate(LoanAmountForm form);
	
	public void transDel(LoanAmountForm form);
	
	public void transAuth(LoanAmountForm form);
	
	public void transReject(LoanAmountForm form);
	
	public void transCancel(LoanAmountForm form);
	
	public void transAdd(LoanAmountForm form);
	
}
