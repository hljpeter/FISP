package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanAmount;

public interface DmLoanAmountRepository {
	
	public Page<DmLoanAmount> queryInputList(Pageable pageable,DmLoanAmount loanAmount);
	
	public Page<DmLoanAmount> queryAuthList(Pageable pageable,DmLoanAmount loanAmount);
	
	public Page<DmLoanAmount> queryList(Pageable pageable,DmLoanAmount loanAmount);

	public DmLoanAmount query(DmLoanAmount loanAmount);
	
	public List<DmLoanAmount> queryList(DmLoanAmount loanAmount);
	
	public int insert(DmLoanAmount loanAmount);
	
	public int update(DmLoanAmount loanAmount);
	
	public int delete(DmLoanAmount loanAmount);
}
