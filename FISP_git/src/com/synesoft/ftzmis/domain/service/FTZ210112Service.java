package com.synesoft.ftzmis.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synesoft.ftzmis.domain.model.FtzActMstr;
import com.synesoft.ftzmis.domain.model.FtzBankCode;
import com.synesoft.ftzmis.domain.model.FtzInMsgCtl;
import com.synesoft.ftzmis.domain.model.FtzInTxnDtl;

public interface FTZ210112Service {

	public Page<FtzInMsgCtl> queryFtzInMsgCtlPage(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInMsgCtl> queryFtzInMsgCtlPageInput(Pageable pageable,
			FtzInMsgCtl ftzInMsgCtl);

	public FtzInMsgCtl queryFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public Page<FtzInTxnDtl> queryFtzInTxnDtlPage(Pageable pageable,FtzInTxnDtl ftzInTxnDtl);
	
	public FtzInTxnDtl queryFtzInTxnDtl(FtzInTxnDtl ftzInTxnDtl);
	
	public int deleteFtzInMsgCtl(FtzInMsgCtl ftzInMsgCtl);
	
	public int insertFtzInMsgCtlAndTxnDtl(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl);
	
	public int updateFtzInMsgCtlAndTxnDtl(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl);
	
	public int updateFtzInMsgForAudit(FtzInMsgCtl ftzInMsgCtl,FtzInTxnDtl ftzInTxnDtl);
	
	public List<FtzActMstr> queryFtzActMstrs(FtzActMstr ftzActMstr);
	
	public List<FtzInTxnDtl> queryFtzInTxnDtlList(FtzInTxnDtl ftzInTxnDtl);
	
	public int updateFtzInMsgCtlForSubmit(FtzInMsgCtl ftzInMsgCtl);
	
	public FtzBankCode queryFtzBankCode(FtzBankCode ftzBankCode);
	
	public String queryTxnDtlMaxSeqNo(FtzInTxnDtl ftzInTxnDtl);
}
