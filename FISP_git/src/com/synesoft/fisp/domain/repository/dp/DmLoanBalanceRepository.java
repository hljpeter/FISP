package com.synesoft.fisp.domain.repository.dp;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.fisp.domain.model.DmLoanBalance;

public interface DmLoanBalanceRepository {
	
	public Page<DmLoanBalance> queryInputList(Pageable pageable,DmLoanBalance loanBalance);
	
	public Page<DmLoanBalance> queryAuthList(Pageable pageable,DmLoanBalance loanBalance);
	
	public Page<DmLoanBalance> queryList(Pageable pageable,DmLoanBalance loanBalance);

	public DmLoanBalance query(DmLoanBalance loanBalance);
	
	public List<DmLoanBalance> queryList(DmLoanBalance loanBalance);
	
	public int insert(DmLoanBalance loanBalance);
	
	public int update(DmLoanBalance loanBalance);
	
	public int delete(DmLoanBalance loanBalance);
}
